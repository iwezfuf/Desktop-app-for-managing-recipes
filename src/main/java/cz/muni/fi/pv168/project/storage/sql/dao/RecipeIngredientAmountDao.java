package cz.muni.fi.pv168.project.storage.sql.dao;

import cz.muni.fi.pv168.project.storage.sql.db.ConnectionHandler;
import cz.muni.fi.pv168.project.storage.sql.entity.RecipeIngredientAmountEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

public class RecipeIngredientAmountDao implements DataAccessObject<RecipeIngredientAmountEntity>{
    private final Supplier<ConnectionHandler> connections;

    public RecipeIngredientAmountDao(Supplier<ConnectionHandler> connections) {
        this.connections = connections;
    }

    @Override
    public RecipeIngredientAmountEntity create(RecipeIngredientAmountEntity newRecipeIngredientAmount) {
        var sql = "INSERT INTO RecipeIngredientAmount (guid, recipeId, ingredientId, amount) VALUES (?, ?, ?, ?);";

        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, newRecipeIngredientAmount.guid());
            statement.setLong(2, newRecipeIngredientAmount.ingredientId());
            statement.setDouble(3, newRecipeIngredientAmount.amount());
            statement.executeUpdate();

            try (ResultSet keyResultSet = statement.getGeneratedKeys()) {
                long recipeIngredientAmountId;

                if (keyResultSet.next()) {
                    recipeIngredientAmountId = keyResultSet.getLong(1);
                } else {
                    throw new DataStorageException("Failed to fetch generated key for: " + newRecipeIngredientAmount);
                }
                if (keyResultSet.next()) {
                    throw new DataStorageException("Multiple keys returned for: " + newRecipeIngredientAmount);
                }

                return findById(recipeIngredientAmountId).orElseThrow();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to store: " + newRecipeIngredientAmount, ex);
        }
    }

    @Override
    public Collection<RecipeIngredientAmountEntity> findAll() {
        var sql = """
                SELECT id, guid, ingredientId, amount
                FROM RecipeIngredientAmount
                """;

        try (var connection = connections.get();
             var statement = connection.use().prepareStatement(sql);
        ){

            var recipeIngredientAmounts = new ArrayList<RecipeIngredientAmountEntity>();
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var recipeIngredientAmount = recipeIngredientAmountFromResultSet(resultSet);
                    recipeIngredientAmounts.add(recipeIngredientAmount);
                }
            }

            return recipeIngredientAmounts;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to fetch all recipeIngredientAmounts", ex);
        }
    }

    @Override
    public java.util.Optional<RecipeIngredientAmountEntity> findById(long id) {
        var sql = """
                SELECT id, guid, recipeId, ingredientId, amount
                FROM RecipeIngredientAmount
                WHERE id = ?
                """;

        try (var connection = connections.get();
             var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(recipeIngredientAmountFromResultSet(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to fetch recipeIngredientAmount by id: " + id, ex);
        }
    }

    @Override
    public Optional<RecipeIngredientAmountEntity> findByGuid(String guid) {
        var sql = """
                SELECT id, guid, recipeId, ingredientId, amount
                FROM RecipeIngredientAmount
                WHERE guid = ?
                """;

        try (var connection = connections.get();
             var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){
            statement.setString(1, guid);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(recipeIngredientAmountFromResultSet(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to fetch recipeIngredientAmount by guid: " + guid, ex);
        }
    }

    @Override
    public RecipeIngredientAmountEntity update(RecipeIngredientAmountEntity entity) {
        var sql = """
                UPDATE RecipeIngredientAmount
                SET guid = ?,
                    ingredientId = ?,
                    amount = ?
                WHERE id = ?
                """;

        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, entity.guid());
            statement.setLong(2, entity.ingredientId());
            statement.setDouble(3, entity.amount());
            statement.setLong(5, entity.id());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("RecipeIngredientAmount not found, id: " + entity.id());
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 recipeIngredientAmount (rows=%d) has been updated: %s"
                        .formatted(rowsUpdated, entity));
            }
            return entity;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to update recipeIngredientAmount: " + entity, ex);
        }
    }

    @Override
    public void deleteByGuid(String guid) {
        var sql = """
                DELETE FROM RecipeIngredientAmount
                WHERE guid = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, guid);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("RecipeIngredientAmount not found, guid: " + guid);
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 recipeIngredientAmount (rows=%d) has been deleted: %s"
                        .formatted(rowsUpdated, guid));
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete recipeIngredientAmount, guid: " + guid, ex);
        }
    }

    @Override
    public void deleteAll() {
        var sql = "DELETE FROM RecipeIngredientAmount";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete all recipeIngredientAmounts", ex);
        }
    }

    @Override
    public boolean existsByGuid(String guid) {
        var sql = """
                SELECT id
                FROM RecipeIngredientAmount
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
            throw new DataStorageException("Failed to check if recipeIngredientAmount exists, guid: " + guid, ex);
        }
    }

    private RecipeIngredientAmountEntity recipeIngredientAmountFromResultSet(ResultSet resultSet) throws SQLException {
        return new RecipeIngredientAmountEntity(
                resultSet.getLong("id"),
                resultSet.getString("guid"),
                resultSet.getLong("recipeId"),
                resultSet.getLong("ingredientId"),
                resultSet.getInt("amount")
        );
    }
}
