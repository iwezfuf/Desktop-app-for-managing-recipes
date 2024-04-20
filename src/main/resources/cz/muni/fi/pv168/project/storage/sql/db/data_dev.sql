--
-- Development data - used for development
--

--  RecipeCategories
INSERT INTO "RecipeCategory" ("guid", "name", "color")
VALUES ('lunch', 'lunch', '1'),
       ('dinner', 'dinner', '2')
;

--  Recipes
INSERT INTO "RecipeCategory" ("guid", "name", "description", "preparationTime", "numOfServings", "instructions", "recipeCategoryId")
VALUES ('pancakes', 'pancakes', 'delicious pancakes', '10', '2', 'mix everything together', 'lunch')
;