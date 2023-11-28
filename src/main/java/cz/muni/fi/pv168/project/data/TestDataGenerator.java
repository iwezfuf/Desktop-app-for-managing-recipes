package cz.muni.fi.pv168.project.data;

import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RecipeCategory;
import cz.muni.fi.pv168.project.business.model.RecipeIngredientAmount;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.ui.model.EntityTableModel;
import cz.muni.fi.pv168.project.wiring.EntityTableModelProvider;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public final class TestDataGenerator {
    public void fillTables(EntityTableModelProvider models) {
        EntityTableModel<Recipe> recipesTable = models.getRecipeTableModel();
        EntityTableModel<Ingredient> ingredientsTable = models.getIngredientTableModel();
        EntityTableModel<Unit> unitsTable = models.getUnitTableModel();
        EntityTableModel<RecipeCategory> categoriesTable = models.getRecipeCategoryTableModel();

        RecipeCategory breakfast = new RecipeCategory("breakfast", Color.orange);
        RecipeCategory lunch = new RecipeCategory("lunch", Color.pink);
        RecipeCategory dinner = new RecipeCategory("dinner", Color.green);
        RecipeCategory dessert = new RecipeCategory("dessert", Color.red);
        RecipeCategory snack = new RecipeCategory("snack", Color.yellow);
        RecipeCategory drink = new RecipeCategory("drink", Color.blue);

        categoriesTable.addRow(breakfast);
        categoriesTable.addRow(lunch);
        categoriesTable.addRow(dinner);
        categoriesTable.addRow(dessert);
        categoriesTable.addRow(snack);
        categoriesTable.addRow(drink);

        // Find base units
        Unit gram = null;
        Unit piece = null;
        Unit liter= null;
        for (Unit unit : models.getUnitTableModel().getEntities()) {
            if (unit.getName().equals("gram")) {
                gram = unit;
            }
            if (unit.getName().equals("piece")) {
                piece = unit;
            }
            if (unit.getName().equals("liter")) {
                liter = unit;
            }
        }

        Unit kilogram = new Unit("kilogram", gram, 1000, "kg");
        Unit milliliter = new Unit("milliliter", null, 1, "ml");
        Unit deciliter = new Unit("deciliter", milliliter, 100, "dl");
        Unit dozen = new Unit("dozen", piece, 12, "pcs");
        Unit teaspoon = new Unit("teaspoon", milliliter, 5, "tsp");
        Unit tablespoon = new Unit("tablespoon", milliliter, 15, "tbsp");
        Unit cup = new Unit("cup", milliliter, 250, "cup");

        unitsTable.addRow(kilogram);
        unitsTable.addRow(milliliter);
        unitsTable.addRow(deciliter);
        unitsTable.addRow(dozen);
        unitsTable.addRow(teaspoon);
        unitsTable.addRow(tablespoon);
        unitsTable.addRow(cup);

        Ingredient oatmeal = new Ingredient("oatmeal", 4, gram);
        Ingredient milk = new Ingredient("milk", 65, liter);
        Ingredient pork = new Ingredient("pork", 100, kilogram);
        Ingredient egg = new Ingredient("egg", 1, piece);
        Ingredient flour = new Ingredient("flour", 1, kilogram);
        Ingredient oil = new Ingredient("oil", 1, liter);
        Ingredient salt = new Ingredient("salt", 1, gram);
        Ingredient pepper = new Ingredient("pepper", 1, gram);
        Ingredient garlic = new Ingredient("garlic", 1, piece);
        Ingredient onion = new Ingredient("onion", 1, piece);
        Ingredient potato = new Ingredient("potato", 1, piece);
        Ingredient carrot = new Ingredient("carrot", 1, piece);
        Ingredient parsley = new Ingredient("parsley", 1, piece);
        Ingredient tomato = new Ingredient("tomato", 1, piece);
        Ingredient cucumber = new Ingredient("cucumber", 1, piece);
        Ingredient cheese = new Ingredient("cheese", 1, kilogram);
        Ingredient ham = new Ingredient("ham", 1, kilogram);
        Ingredient butter = new Ingredient("butter", 1, kilogram);
        Ingredient sugar = new Ingredient("sugar", 1, kilogram);
        Ingredient spaghetti = new Ingredient("spaghetti", 1, kilogram);
        Ingredient oliveOil = new Ingredient("olive oil", 1, liter);
        Ingredient redPepperFlakes = new Ingredient("red pepper flakes", 1, teaspoon);
        Ingredient salsa = new Ingredient("salsa", 1, cup);
        Ingredient lettuce = new Ingredient("lettuce", 1, piece);
        Ingredient avocado = new Ingredient("avocado", 1, piece);
        Ingredient chickenBreast = new Ingredient("chicken breast", 1, kilogram);
        Ingredient tacoSeasoning = new Ingredient("taco seasoning", 1, piece);
        Ingredient tortillas = new Ingredient("tortillas", 1, piece);

        ingredientsTable.addRow(oatmeal);
        ingredientsTable.addRow(milk);
        ingredientsTable.addRow(pork);
        ingredientsTable.addRow(egg);
        ingredientsTable.addRow(flour);
        ingredientsTable.addRow(oil);
        ingredientsTable.addRow(salt);
        ingredientsTable.addRow(pepper);
        ingredientsTable.addRow(garlic);
        ingredientsTable.addRow(onion);
        ingredientsTable.addRow(potato);
        ingredientsTable.addRow(carrot);
        ingredientsTable.addRow(parsley);
        ingredientsTable.addRow(tomato);
        ingredientsTable.addRow(cucumber);
        ingredientsTable.addRow(cheese);
        ingredientsTable.addRow(ham);
        ingredientsTable.addRow(butter);
        ingredientsTable.addRow(sugar);
        ingredientsTable.addRow(spaghetti);
        ingredientsTable.addRow(oliveOil);
        ingredientsTable.addRow(redPepperFlakes);
        ingredientsTable.addRow(salsa);
        ingredientsTable.addRow(lettuce);
        ingredientsTable.addRow(avocado);
        ingredientsTable.addRow(chickenBreast);
        ingredientsTable.addRow(tacoSeasoning);
        ingredientsTable.addRow(tortillas);

        ArrayList<RecipeIngredientAmount> ingredientsPancakes = new ArrayList<>();
        String pancakesInstructions = """
                1. Mix all ingredients together.
                2. Fry pancakes on a pan.
                3. Serve with jam, honey, chocolate or whatever you like.""";
        Recipe pancakes = new Recipe("Pancakes", "Traditional healthy dish.", 90, 5, pancakesInstructions, breakfast, ingredientsPancakes);
        ingredientsPancakes.add(new RecipeIngredientAmount(pancakes, flour, 250));
        ingredientsPancakes.add(new RecipeIngredientAmount(pancakes, milk, 250));
        ingredientsPancakes.add(new RecipeIngredientAmount(pancakes, egg, 2));
        ingredientsPancakes.add(new RecipeIngredientAmount(pancakes, oil, 1));
        ingredientsPancakes.add(new RecipeIngredientAmount(pancakes, salt, 1));
        ingredientsPancakes.add(new RecipeIngredientAmount(pancakes, sugar, 1));

        recipesTable.addRow(pancakes);

        ArrayList<RecipeIngredientAmount> ingredientsSpaghettiAglioOlio = new ArrayList<>();
        String spaghettiAglioOlioInstructions = """
                1. Boil spaghetti in salted water until al dente. Drain and set aside.
                2. In a pan, heat olive oil over low heat. Add minced garlic and red pepper flakes. Cook until garlic is fragrant but not browned.
                3. Toss the cooked spaghetti in the garlic and oil mixture.
                4. Season with salt and garnish with chopped parsley.
                5. Serve hot and optionally sprinkle with grated Parmesan cheese.""";

        Recipe spaghettiAglioOlio = new Recipe("Spaghetti Aglio e Olio", "A simple and classic Italian pasta dish.", 20, 2, spaghettiAglioOlioInstructions, lunch, ingredientsSpaghettiAglioOlio);
        ingredientsSpaghettiAglioOlio.add(new RecipeIngredientAmount(spaghettiAglioOlio, spaghetti, 200));
        ingredientsSpaghettiAglioOlio.add(new RecipeIngredientAmount(spaghettiAglioOlio, oliveOil, 3));
        ingredientsSpaghettiAglioOlio.add(new RecipeIngredientAmount(spaghettiAglioOlio, garlic, 4));
        ingredientsSpaghettiAglioOlio.add(new RecipeIngredientAmount(spaghettiAglioOlio, redPepperFlakes, 1));
        ingredientsSpaghettiAglioOlio.add(new RecipeIngredientAmount(spaghettiAglioOlio, parsley, 1));
        ingredientsSpaghettiAglioOlio.add(new RecipeIngredientAmount(spaghettiAglioOlio, salt, 1));

        recipesTable.addRow(spaghettiAglioOlio);


        ArrayList<RecipeIngredientAmount> ingredientsChickenTaco = new ArrayList<>();
        String chickenTacoInstructions = """
                1. Season chicken breasts with taco seasoning and grill or cook in a skillet until fully cooked.
                2. While the chicken is cooking, warm the tortillas in a dry skillet or microwave.
                3. Slice the cooked chicken into thin strips.
                4. Assemble the tacos: place chicken strips, salsa, cheese, lettuce, diced tomatoes, and sliced avocado on each tortilla.
                5. Fold the tortillas and serve immediately.""";
        Recipe chickenTaco = new Recipe("Chicken Tacos", "A delicious and satisfying taco recipe.", 30, 4, chickenTacoInstructions, dinner, ingredientsChickenTaco);
        ingredientsChickenTaco.add(new RecipeIngredientAmount(chickenTaco, chickenBreast, 2));
        ingredientsChickenTaco.add(new RecipeIngredientAmount(chickenTaco, tacoSeasoning, 1));
        ingredientsChickenTaco.add(new RecipeIngredientAmount(chickenTaco, tortillas, 8));
        ingredientsChickenTaco.add(new RecipeIngredientAmount(chickenTaco, salsa, 1));
        ingredientsChickenTaco.add(new RecipeIngredientAmount(chickenTaco, cheese, 1));
        ingredientsChickenTaco.add(new RecipeIngredientAmount(chickenTaco, lettuce, 1));
        ingredientsChickenTaco.add(new RecipeIngredientAmount(chickenTaco, tomato, 2));
        ingredientsChickenTaco.add(new RecipeIngredientAmount(chickenTaco, avocado, 1));

        recipesTable.addRow(chickenTaco);



        ArrayList<RecipeIngredientAmount> ingredientsOatMeal = new ArrayList<>();
        Recipe r = new Recipe("Ovesna kase",
                "Ovesná kaše, or Czech oatmeal porridge, is a beloved breakfast dish in Czech cuisine.",
                5,
                2,
                "1. make it",
                breakfast,
                ingredientsOatMeal);
        ingredientsOatMeal.add(new RecipeIngredientAmount(r, oatmeal, 100));
        ingredientsOatMeal.add(new RecipeIngredientAmount(r, milk, 100));

        recipesTable.addRow(r);
    }
}
