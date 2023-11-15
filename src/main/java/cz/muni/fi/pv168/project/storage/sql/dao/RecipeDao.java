package cz.muni.fi.pv168.project.storage.sql.dao;

import cz.muni.fi.pv168.project.storage.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.db.ConnectionHandler;
import cz.muni.fi.pv168.project.storage.sql.entity.RecipeEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

public class RecipeDao implements DataAccessObject<RecipeEntity> {
    private final Supplier<ConnectionHandler> connections;

    public RecipeDao(Supplier<ConnectionHandler> connections) {
        this.connections = connections;
    }

    @Override
    public RecipeEntity create(RecipeEntity newRecipe) {
        var sql = "INSERT INTO Recipe (guid, name, description, preparationTime, numOfServings, instructions, recipeCategoryId, recipeIngredientAmountId) VALUES (?, ?, ?, ?, ?, ?, ?);";

        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, newRecipe.guid());
            statement.setString(2, newRecipe.name());
            statement.setString(3, newRecipe.description());
            statement.setInt(4, newRecipe.preparationTime());
            statement.setInt(5, newRecipe.numOfServings());
            statement.setString(6, newRecipe.instructions());
            statement.setLong(7, newRecipe.recipeCategoryId());
            statement.executeUpdate();

            try (ResultSet keyResultSet = statement.getGeneratedKeys()) {
                long recipeId;

                if (keyResultSet.next()) {
                    recipeId = keyResultSet.getLong(1);
                } else {
                    throw new DataStorageException("Failed to fetch generated key for: " + newRecipe);
                }
                if (keyResultSet.next()) {
                    throw new DataStorageException("Multiple keys returned for: " + newRecipe);
                }

                return findById(recipeId).orElseThrow();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to store: " + newRecipe, ex);
        }
    }

    @Override
    public Collection<RecipeEntity> findAll() {
        var sql = """
                SELECT id, guid, name, description, preparationTime, numOfServings, instructions, recipeCategoryId
                FROM Recipe
                """;

        try (var connection = connections.get();
             var statement = connection.use().prepareStatement(sql);
        ){

            var recipes = new ArrayList<RecipeEntity>();
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var recipe = recipeFromResultSet(resultSet);
                    recipes.add(recipe);
                }
            }

            return recipes;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to fetch all recipes", ex);
        }
    }

    @Override
    public java.util.Optional<RecipeEntity> findById(long id) {
        var sql = """
                SELECT id, guid, name, description, preparationTime, numOfServings, instructions, recipeCategoryId
                FROM Recipe
                WHERE id = ?
                """;

        try (var connection = connections.get();
             var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(recipeFromResultSet(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to fetch recipe by id: " + id, ex);
        }
    }

    @Override
    public Optional<RecipeEntity> findByGuid(String guid) {
        var sql = """
                SELECT id, guid, name, description, preparationTime, numOfServings, instructions, recipeCategoryId
                FROM Recipe
                WHERE guid = ?
                """;

        try (var connection = connections.get();
             var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){
            statement.setString(1, guid);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(recipeFromResultSet(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to fetch recipe by guid: " + guid, ex);
        }
    }

    @Override
    public RecipeEntity update(RecipeEntity entity) {
        var sql = """
                UPDATE Recipe
                SET guid = ?,
                    name = ?,
                    description = ?,
                    preparationTime = ?,
                    numOfServings = ?,
                    instructions = ?,
                    recipeCategoryId = ?
                WHERE id = ?
                """;

        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, entity.guid());
            statement.setString(2, entity.name());
            statement.setString(3, entity.description());
            statement.setInt(4, entity.preparationTime());
            statement.setInt(5, entity.numOfServings());
            statement.setString(6, entity.instructions());
            statement.setLong(7, entity.recipeCategoryId());
            statement.setLong(8, entity.id());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Recipe not found, id: " + entity.id());
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 recipe (rows=%d) has been updated: %s"
                        .formatted(rowsUpdated, entity));
            }
            return entity;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to update recipe: " + entity, ex);
        }
    }

    @Override
    public void deleteByGuid(String guid) {
        var sql = """
                DELETE FROM Recipe
                WHERE guid = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, guid);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Recipe not found, guid: " + guid);
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 recipe (rows=%d) has been deleted: %s"
                        .formatted(rowsUpdated, guid));
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete recipe, guid: " + guid, ex);
        }
    }

    @Override
    public void deleteAll() {
        var sql = "DELETE FROM Recipe";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete all recipes", ex);
        }
    }

    @Override
    public boolean existsByGuid(String guid) {
        var sql = """
                SELECT id
                FROM Recipe
                WHERE guid = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, guid);
            try (var resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to check if recipe exists, guid: " + guid, ex);
        }
    }

    private RecipeEntity recipeFromResultSet(ResultSet resultSet) throws SQLException {
        return new RecipeEntity(
                resultSet.getLong("id"),
                resultSet.getString("guid"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getInt("preparationTime"),
                resultSet.getInt("numOfServings"),
                resultSet.getString("instructions"),
                resultSet.getLong("recipeCategoryId")
        );
    }
}
