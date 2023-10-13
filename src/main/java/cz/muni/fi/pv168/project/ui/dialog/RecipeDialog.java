package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.model.RecipeCategory;

import javax.swing.*;
import java.awt.*;

/**
 * @author Marek Eibel
 */
public class RecipeDialog extends EntityDialog<Recipe> {

    private final JTextField recipeNameTextField = new JTextField();
    private final JTextArea briefDescriptionTextArea = new JTextArea();
    SpinnerModel model = new SpinnerNumberModel(2, 0, 20, 1);
    JSpinner numberOfServingsSpinner = new JSpinner(model);
    private final JComboBox<RecipeCategory> recipeCategoryComboBox = new JComboBox<>();

    private final JSlider preparationTimeSlider = new JSlider(JSlider.HORIZONTAL, 0, 120, 60);

    private final Recipe recipe;

    public RecipeDialog(Recipe recipe) {
        super(new Dimension(600, 300));

        numberOfServingsSpinner.setEditor(new JSpinner.DefaultEditor(numberOfServingsSpinner));

        this.recipe = recipe;
        recipeNameTextField.setColumns(600); // Set the number of visible columns (width).
        limitComponentToOneRow(recipeNameTextField);
        //this.recipeCategoryCompoBox = new ComboBoxModelAdapter<>(recipe.getCategoryIds());
        this.briefDescriptionTextArea.setRows(5);
        this.preparationTimeSlider.setMajorTickSpacing(10);
        this.preparationTimeSlider.setMinorTickSpacing(1);
        this.preparationTimeSlider.setPaintTicks(true);
        this.preparationTimeSlider.setPaintLabels(true);
        setValues();
        addFields();
    }

    private void setValues() {
        recipeNameTextField.setText(recipe.getName());
        briefDescriptionTextArea.setText(recipe.getDescription());
        numberOfServingsSpinner.setValue(recipe.getNumOfServings());
        preparationTimeSlider.setValue(recipe.getPreparationTime());

        for (RecipeCategory category : RecipeCategory.listOfCategories) {
            recipeCategoryComboBox.addItem(category);
        }

        if (recipe.getCategory() != null) {
            recipeCategoryComboBox.setSelectedItem(recipe.getCategory());
        }
    }

    private void addFields() {
        add("Recipe name:", recipeNameTextField);
        addAsScrollable("Brief description:", briefDescriptionTextArea);
        add("Number of servings:", numberOfServingsSpinner);
        add("Preparation time [min.]:", preparationTimeSlider);
        add("Recipe category:", recipeCategoryComboBox);
    }

    @Override
    Recipe getEntity() {

        recipe.setName(recipeNameTextField.getText());
        recipe.setDescription(briefDescriptionTextArea.getText());
        recipe.setNumOfServings((int)numberOfServingsSpinner.getValue());
        recipe.setPreparationTime((int)preparationTimeSlider.getValue());
        //recipe.addRecipeCategory((RecipeCategory)recipeCategoryCompoBox.getSelectedItem());

        return recipe;
    }
}
