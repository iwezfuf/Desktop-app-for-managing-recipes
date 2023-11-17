package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.business.model.Employee;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.model.Unit;

public class EntityTableModelProvider {
    private final EntityTableModel<Employee> employeeTableModel;
    private final EntityTableModel<Recipe> recipeTableModel;
    private final EntityTableModel<Ingredient> ingredientTableModel;
    private final EntityTableModel<Unit> unitTableModel;
    private final EntityTableModel<RecipeCategory> recipeCategoryTableModel;
    private final DepartmentListModel departmentListModel;

    public EntityTableModelProvider(EntityTableModel<Employee> employeeTableModel, EntityTableModel<Recipe> recipeTableModel, EntityTableModel<Ingredient> ingredientTableModel, EntityTableModel<Unit> unitTableModel, EntityTableModel<RecipeCategory> recipeCategoryTableModel, DepartmentListModel departmentListModel) {
        this.employeeTableModel = employeeTableModel;
        this.recipeTableModel = recipeTableModel;
        this.ingredientTableModel = ingredientTableModel;
        this.unitTableModel = unitTableModel;
        this.recipeCategoryTableModel = recipeCategoryTableModel;
        this.departmentListModel = departmentListModel;
    }

    public EntityTableModel<Employee> getEmployeeTableModel() {
        return employeeTableModel;
    }

    public EntityTableModel<Recipe> getRecipeTableModel() {
        return recipeTableModel;
    }

    public EntityTableModel<Ingredient> getIngredientTableModel() {
        return ingredientTableModel;
    }

    public EntityTableModel<Unit> getUnitTableModel() {
        return unitTableModel;
    }

    public EntityTableModel<RecipeCategory> getRecipeCategoryTableModel() {
        return recipeCategoryTableModel;
    }

    public DepartmentListModel getDepartmentListModel() {
        return departmentListModel;
    }
}
