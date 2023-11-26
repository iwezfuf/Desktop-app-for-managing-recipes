package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.model.RecipeIngredientAmount;
import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.wiring.EntityTableModelProvider;
import cz.muni.fi.pv168.project.ui.model.FormattedInput;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Marek Eibel
 */
public class RecipeDialog extends EntityDialog<Recipe> {

    private final Recipe recipe;
    private final ArrayList<RecipeIngredientAmount> currentIngredients = new ArrayList<>();

    private final JTextField recipeNameTextField = new JTextField();
    private final JTextArea briefDescriptionTextArea = new JTextArea();
    private final SpinnerModel portionsModel = new SpinnerNumberModel(2, 0, 100, 1);
    private final JSpinner numberOfServingsSpinner = new JSpinner(portionsModel);
    private final JTextField preparationTimeHoursTextField = FormattedInput.createIntTextField(0, 999);
    private final JTextField preparationTimeMinutesTextField = FormattedInput.createIntTextField(0, 59);
    private final JComboBox<RecipeCategory> recipeCategoryComboBox = new JComboBox<>();
    private final JComboBox<Ingredient> ingredientComboBox = new JComboBox<>();
    private final SpinnerModel ingredientsModel = new SpinnerNumberModel(1, 0, 999, 1);
    private final JSpinner ingredientsSpinner = new JSpinner(ingredientsModel);
    private final JButton addIngredientButton = new JButton(Icons.ADD_ICON);
    private final JTextArea instructionsTextArea = new JTextArea();
    private final JPanel centerPanel = new JPanel(new GridBagLayout());
    private final JPanel ingredientsPanel = new JPanel(new GridBagLayout());

    public RecipeDialog(Recipe recipe,
                        EntityTableModelProvider entityTableModelProvider,
                        Validator<Recipe> entityValidator) {

        super(recipe, entityTableModelProvider, Objects.requireNonNull(entityValidator));
        this.recipe = recipe;

        initField();
        fillComboBoxes();
        setValues();
        addFields();
        addListenerToAddButton();
    }

    private void initField() {

        final int width = 75;

        numberOfServingsSpinner.setEditor(new JSpinner.DefaultEditor(numberOfServingsSpinner));
        recipeNameTextField.setColumns(width);
        briefDescriptionTextArea.setRows(5);
        briefDescriptionTextArea.setLineWrap(true);
        briefDescriptionTextArea.setWrapStyleWord(true);
        preparationTimeHoursTextField.setColumns(width);
        preparationTimeMinutesTextField.setColumns(width);
        instructionsTextArea.setRows(10);
        instructionsTextArea.setLineWrap(true);
        instructionsTextArea.setWrapStyleWord(true);
    }

    private void addListenerToAddButton() {
        this.addIngredientButton.addActionListener(e -> {
            Ingredient selectedIngredient = (Ingredient) ingredientComboBox.getSelectedItem();
            int amount = (int) ingredientsSpinner.getValue();
            updateRecipeIngredientAmounts(selectedIngredient, amount);
            addToIngredientsPanel(selectedIngredient, amount);
        });
    }

    private void updateRecipeIngredientAmounts(Ingredient ingredient, int amount) {
        for (RecipeIngredientAmount recipeIngredientAmount : currentIngredients) {
            if (recipeIngredientAmount.getIngredient().getName().equals(ingredient.getName())) {
                recipeIngredientAmount.setAmount(recipeIngredientAmount.getAmount() + amount);
                return;
            }
        }
        currentIngredients.add(new RecipeIngredientAmount(recipe, ingredient, amount));
    }

    private void fillComboBoxes() {

        for (RecipeCategory category : entityTableModelProvider.getRecipeCategoryTableModel().getEntities()) {
            recipeCategoryComboBox.addItem(category);
        }

        for (Ingredient ingredient : entityTableModelProvider.getIngredientTableModel().getEntities()) {
            ingredientComboBox.addItem(ingredient);
        }
    }

    private void setValues() {

        recipeNameTextField.setText(recipe.getName());
        briefDescriptionTextArea.setText(recipe.getDescription());
        numberOfServingsSpinner.setValue(recipe.getNumOfServings());
        preparationTimeHoursTextField.setText(String.valueOf(recipe.getPreparationTime() / 60));
        preparationTimeMinutesTextField.setText(String.valueOf(recipe.getPreparationTime() % 60));

        if (recipe.getCategory() != null) {
            recipeCategoryComboBox.setSelectedItem(recipe.getCategory());
        }

        if (ingredientComboBox.getItemCount() != 0) {
            ingredientComboBox.setSelectedIndex(0);
        }

        instructionsTextArea.setText(recipe.getInstructions());

        for (RecipeIngredientAmount ingredientAmount : recipe.getIngredients()) {
            addToIngredientsPanel(ingredientAmount.getIngredient(), ingredientAmount.getAmount());
            currentIngredients.add(ingredientAmount);
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
        labelConstraints.insets = spacing;
        centerPanel.add(label, labelConstraints);

        GridBagConstraints componentConstraints = new GridBagConstraints();
        componentConstraints.fill = GridBagConstraints.BOTH;
        componentConstraints.gridy = GridBagConstraints.RELATIVE;
        componentConstraints.gridx = 1;
        componentConstraints.weightx = 3;
        componentConstraints.weighty = weighty;
        componentConstraints.insets = spacing;
        centerPanel.add(component, componentConstraints);
    }

    private void addToIngredientsPanel(Ingredient ingredient, int amount) {

        if (tryToUpdateAmount(ingredient, amount)) {
            return;
        }

        var panel = new JPanel(new GridBagLayout());
        var removeButton = getRemoveIngredientButton(ingredient, panel);

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

    private JButton getRemoveIngredientButton(Ingredient ingredient, JPanel panel) {
        var removeButton = new JButton(Icons.DELETE_ICON);
        removeButton.addActionListener(e -> {
            for (RecipeIngredientAmount recipeIngredientAmount : currentIngredients) {
                if (recipeIngredientAmount.getIngredient().getName().equals(ingredient.getName())) {
                    currentIngredients.remove(recipeIngredientAmount);
                    if (recipeIngredientAmount.getGuid() != null) {
                        entityTableModelProvider.getIngredientAmountCrudService().deleteByGuid(recipeIngredientAmount.getGuid());
                    }
                    break;
                }
            }
            ingredientsPanel.remove(panel);
            ingredientsPanel.revalidate();
            ingredientsPanel.repaint();
        });
        return removeButton;
    }

    private boolean tryToUpdateAmount(Ingredient ingredient, int amount) {

        if (currentIngredients.stream().anyMatch(i -> i.getIngredient().getName().equals(ingredient.getName()))) {
            for (Component panel : ingredientsPanel.getComponents()) {
                if (panel instanceof JPanel) {
                    var ingredientLabel = ((JLabel)((JPanel) panel).getComponents()[0]);
                    if (ingredientLabel.getText().equals(ingredient.toString())) {
                        var amountLabel = ((JLabel)((JPanel) panel).getComponents()[1]);
                        amountLabel.setText(String.valueOf(Integer.parseInt(amountLabel.getText()) + amount));
                        ingredientsPanel.revalidate();
                        ingredientsPanel.repaint();
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private void addFields() {

        add("", centerPanel);

        addToCenterPanel("Recipe name:", recipeNameTextField, 1);
        addToCenterPanel("Brief description:", briefDescriptionTextArea, 1);
        addToCenterPanel("Number of servings:", numberOfServingsSpinner, 1);
        addToCenterPanel("Preparation time:", createPreparationTimePanel(), 1);
        addToCenterPanel("Recipe category:", recipeCategoryComboBox, 1);

        var panel = new JPanel();
        var bl = new BoxLayout(panel, BoxLayout.X_AXIS);
        panel.setLayout(bl);
        panel.add(ingredientComboBox);
        panel.add(ingredientsSpinner);
        panel.add(addIngredientButton);

        addToCenterPanel("Add instructions: ", new JScrollPane(instructionsTextArea), 3);
        addToCenterPanel("Add ingredients:", panel, 1);

        var scrollPane = new JScrollPane(ingredientsPanel);
        scrollPane.setMinimumSize(new Dimension(0, 420));
        addToCenterPanel("Used ingredients: ", scrollPane, 6);
    }

    private JPanel createPreparationTimePanel() {
        JPanel preparationTimePanel = new JPanel();
        preparationTimePanel.setLayout(new BoxLayout(preparationTimePanel, BoxLayout.X_AXIS));
        preparationTimeHoursTextField.setColumns(1);
        preparationTimePanel.add(preparationTimeHoursTextField);
        preparationTimePanel.add(new JLabel(" : "));
        preparationTimeMinutesTextField.setColumns(1);
        preparationTimePanel.add(preparationTimeMinutesTextField);
        preparationTimePanel.add(new JLabel(" Hours : Minutes "));
        return preparationTimePanel;
    }

    @Override
    Recipe getEntity() {

        recipe.setName(recipeNameTextField.getText());
        recipe.setDescription(briefDescriptionTextArea.getText());
        recipe.setNumOfServings((int)numberOfServingsSpinner.getValue());
        recipe.setPreparationTime(FormattedInput.getInt(preparationTimeHoursTextField.getText()) * 60
                + FormattedInput.getInt(preparationTimeMinutesTextField.getText()));
        recipe.setCategory((RecipeCategory)recipeCategoryComboBox.getSelectedItem());
        recipe.setIngredients(currentIngredients);
        recipe.setInstructions(instructionsTextArea.getText());

        ValidationResult result = entityValidator.validate(recipe);
        if (!result.isValid()) {
            new JOptionPane().showMessageDialog(null, "Invalid entered data: " + result.getValidationErrors() + ".", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return recipe;
    }
}
