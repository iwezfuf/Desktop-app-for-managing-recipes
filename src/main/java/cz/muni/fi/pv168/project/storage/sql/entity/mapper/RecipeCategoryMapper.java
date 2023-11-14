package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.Department;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.storage.sql.entity.DepartmentEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.RecipeCategoryEntity;

import java.awt.*;

public class RecipeCategoryMapper implements EntityMapper<RecipeCategoryEntity, RecipeCategory> {
    @Override
    public RecipeCategory mapToBusiness(RecipeCategoryEntity dbRecipeCategory) {
        return new RecipeCategory(
                dbRecipeCategory.guid(),
                dbRecipeCategory.name(),
                new Color(dbRecipeCategory.color())
        );
    }

    @Override
    public RecipeCategoryEntity mapNewEntityToDatabase(RecipeCategory entity) {
        return getRecipeCategoryEntity(entity, null);
    }

    @Override
    public RecipeCategoryEntity mapExistingEntityToDatabase(RecipeCategory entity, Long dbId) {
        return getRecipeCategoryEntity(entity, dbId);
    }

    private static RecipeCategoryEntity getRecipeCategoryEntity(RecipeCategory entity, Long dbId) {
        return new RecipeCategoryEntity(
                dbId,
                entity.getGuid(),
                entity.getName(),
                entity.getColor().getRGB()
        );
    }
}
