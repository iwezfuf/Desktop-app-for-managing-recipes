package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Recipe;

import javax.swing.*;

/**
 * @author Marek Eibel
 */
public class RecipeDialog extends EntityDialog<Recipe> {

    private final JTextField recipeNameTextField = new JTextField();
    private final JTextArea briefDescriptionTextArea = new JTextArea();
    private final JSpinner numberOfServingsSpinner = new JSpinner();
    //private final ComboBoxModel<RecipeCategory> recipeCategoryCompoBox;

    private final Recipe recipe;

    public RecipeDialog(Recipe recipe) {
        this.recipe = recipe;
        //this.recipeCategoryCompoBox = new ComboBoxModelAdapter<>(recipe.getCategoryIds());
        setValues();
        addFields();
    }

    private void setValues() {
        recipeNameTextField.setText(recipe.getName());
        briefDescriptionTextArea.setText(recipe.getDescription());
        numberOfServingsSpinner.setValue(recipe.getNumOfServings());
        //recipeCategoryCompoBox.setSelectedItem(recipe.getCategoryIds());
    }

    private void addFields() {
        add("Recipe name:", recipeNameTextField);
        add("Brief description:", briefDescriptionTextArea);
        add("Number of servings:", numberOfServingsSpinner);
        //add("Recipe category:", new JComboBox<>(recipeCategoryCompoBox));
    }

    @Override
    Recipe getEntity() {

        recipe.setName(recipeNameTextField.getText());
        recipe.setDescription(briefDescriptionTextArea.getText());
        recipe.setNumOfServings((int)numberOfServingsSpinner.getValue());
        //recipe.addRecipeCategory((RecipeCategory)recipeCategoryCompoBox.getSelectedItem());

        return recipe;
    }
}
