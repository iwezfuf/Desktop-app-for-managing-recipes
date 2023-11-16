package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.Department;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.model.RecipeIngredientAmount;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * @author Marek Eibel
 */
public class RecipeDialog extends EntityDialog<Recipe> {

    private final JTextField recipeNameTextField = new JTextField();
    private final JTextArea briefDescriptionTextArea = new JTextArea();
    private final SpinnerModel portionsModel = new SpinnerNumberModel(2, 0, 100, 1);
    private final JSpinner numberOfServingsSpinner = new JSpinner(portionsModel);

    private final JTextField preparationTimeTextField = new JTextField();

    private final JComboBox<RecipeCategory> recipeCategoryComboBox = new JComboBox<>();
    private final JComboBox<Ingredient> ingredientComboBox = new JComboBox<>();
    private final SpinnerModel ingredientsModel = new SpinnerNumberModel(1, 0, 999, 1);
    private final JSpinner ingredientsSpinner = new JSpinner(ingredientsModel);
    private final JButton addIngredientButton = new JButton(Icons.ADD_ICON);
    private final JTextArea instructionsTextArea = new JTextArea();
    private final JPanel centerPanel = new JPanel(new GridBagLayout());
    private final JPanel ingredientsPanel = new JPanel(new GridBagLayout());

    private final Recipe recipe;

    public RecipeDialog(Recipe recipe,
                        ListModel<Department> departmentListModel,
                        Validator<Recipe> entityValidator) {
        super(recipe, departmentListModel, Objects.requireNonNull(entityValidator));
//        this.setLayout(new GridBagLayout());

//        var centerPanelConstraints = new GridBagConstraints();
//        centerPanelConstraints.fill = GridBagConstraints.BOTH;
//        centerPanelConstraints.gridy = 0;
//        centerPanelConstraints.gridx = 0;
//        centerPanelConstraints.weightx = 2;
//        centerPanelConstraints.weighty = 1;
//        this.add(centerPanel, centerPanelConstraints);

//        numberOfServingsSpinner.setEditor(new JSpinner.DefaultEditor(numberOfServingsSpinner));
//
        this.recipe = recipe;
//        recipeNameTextField.setColumns(600); // Set the number of visible columns (width).
////        limitComponentToOneRow(recipeNameTextField);
//        this.briefDescriptionTextArea.setRows(5);
//        briefDescriptionTextArea.setLineWrap(true);
//        briefDescriptionTextArea.setWrapStyleWord(true);
//        this.preparationTimeTextField.setColumns(600);
////        limitComponentToOneRow(preparationTimeTextField);
//        this.instructionsTextArea.setRows(10);
//        instructionsTextArea.setLineWrap(true);
//        instructionsTextArea.setWrapStyleWord(true);
//
//        fillComboBoxes();
//        setValues();
//        addFields();
//        this.addIngredientButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Ingredient selectedIngredient = (Ingredient) ingredientComboBox.getSelectedItem();
//                int amount = (int) ingredientsSpinner.getValue();
//                // TODO
////                recipe.addIngredient(selectedIngredient, amount);
//                addToIngredientsPanel(selectedIngredient, amount);
//            }
//        });
    }

    private void fillComboBoxes() {
//        for (RecipeCategory category : RecipeCategory.getListOfCategories()) {
//            recipeCategoryComboBox.addItem(category);
//        }

//        for (Ingredient ingredient : Ingredient.getListOfIngredients()) {
//            ingredientComboBox.addItem(ingredient);
//        }
    }

    private void setValues() {
        recipeNameTextField.setText(recipe.getName());
        briefDescriptionTextArea.setText(recipe.getDescription());
        numberOfServingsSpinner.setValue(recipe.getNumOfServings());
        preparationTimeTextField.setText(recipe.getPreparationTime() + "");

        if (recipe.getCategory() != null) {
            recipeCategoryComboBox.setSelectedItem(recipe.getCategory());
        }

        ingredientComboBox.setSelectedIndex(0);
        instructionsTextArea.setText(recipe.getInstructions());

        for (RecipeIngredientAmount ingredientAmount : recipe.getIngredients()) {
            addToIngredientsPanel(ingredientAmount.getIngredient(), ingredientAmount.getAmount());
        }
    }

    private void addToCenterPanel(String labelText, JComponent component, int weighty) {
        var label = new JLabel(labelText);
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.fill = GridBagConstraints.BOTH;
        labelConstraints.gridy = GridBagConstraints.RELATIVE;
        labelConstraints.gridx = 0;
        labelConstraints.weightx = 1;
        labelConstraints.weighty = weighty;
        Insets spacing = new Insets(5, 5, 5, 5);
        labelConstraints.insets = spacing; // Set the insets for the label
        centerPanel.add(label, labelConstraints);

        GridBagConstraints componentConstraints = new GridBagConstraints();
        componentConstraints.fill = GridBagConstraints.BOTH;
        componentConstraints.gridy = GridBagConstraints.RELATIVE;
        componentConstraints.gridx = 1;
        componentConstraints.weightx = 3;
        componentConstraints.weighty = weighty;
        componentConstraints.insets = spacing; // Set the insets for the component
        centerPanel.add(component, componentConstraints);
    }


    private void addToIngredientsPanel(Ingredient ingredient, int amount) {
        // update only the amount
        if (recipe.getIngredientAmount(ingredient) != null) {
            for (Component panel : ingredientsPanel.getComponents()) {
                if (panel instanceof JPanel) {
                    var ingredientLabel = ((JLabel)((JPanel) panel).getComponents()[0]);
                    if (ingredientLabel.getText().equals(ingredient.toString())) {
                        var amountLabel = ((JLabel)((JPanel) panel).getComponents()[1]);
                        amountLabel.setText(String.valueOf(Integer.parseInt(amountLabel.getText()) + amount));
                        ingredientsPanel.revalidate();
                        ingredientsPanel.repaint();
                        return;
                    }
                }
            }
        }

        var panel = new JPanel(new GridBagLayout());
        var removeButton = new JButton(Icons.DELETE_ICON);
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recipe.removeIngredient(ingredient);
                ingredientsPanel.remove(panel);
                ingredientsPanel.revalidate();
                ingredientsPanel.repaint();
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 2;
        panel.add(new JLabel(ingredient.toString()), gbc);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 0;
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(new JLabel(String.valueOf(amount)));

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 0;
        gbc.gridx = 2;
        gbc.weightx = 1;
        panel.add(removeButton);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridx = 0;
        gbc.weightx = 1;

        ingredientsPanel.add(panel, gbc);
        ingredientsPanel.revalidate();
        ingredientsPanel.repaint();
    }

    private void addFields() {
        addToCenterPanel("Recipe name:", recipeNameTextField, 1);
        addToCenterPanel("Brief description:", briefDescriptionTextArea, 1);
        addToCenterPanel("Number of servings:", numberOfServingsSpinner, 1);
        addToCenterPanel("Preparation time [min.]:", preparationTimeTextField, 1);
        addToCenterPanel("Recipe category:", recipeCategoryComboBox, 1);

        var panel = new JPanel();
        var bl = new BoxLayout(panel, BoxLayout.X_AXIS);
        panel.setLayout(bl);
        panel.add(ingredientComboBox);
        panel.add(ingredientsSpinner);
        panel.add(addIngredientButton);

        addToCenterPanel("Add instructions: ", instructionsTextArea, 3);
        addToCenterPanel("Add ingredients:", panel, 1);

        var scrollPane = new JScrollPane(this.ingredientsPanel);
        addToCenterPanel("Used ingredients: ", scrollPane, 3);
    }

    @Override
    Recipe getEntity() {

        recipe.setName(recipeNameTextField.getText());
        recipe.setDescription(briefDescriptionTextArea.getText());
        recipe.setNumOfServings((int)numberOfServingsSpinner.getValue());
        recipe.setPreparationTime(Integer.parseInt(preparationTimeTextField.getText()));
        recipe.setCategory((RecipeCategory)recipeCategoryComboBox.getSelectedItem());
        // ingredients are added/removed on button click
        recipe.setInstructions(instructionsTextArea.getText());

        return recipe;
    }
}
