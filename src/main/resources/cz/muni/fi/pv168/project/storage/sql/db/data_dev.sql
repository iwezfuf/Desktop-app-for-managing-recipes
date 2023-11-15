--
-- Development data - used for development
--

--  Departments
INSERT INTO "Department" ("guid", "number", "name")
VALUES ('d-1', '007', 'IT'),
       ('d-2', '666', 'Sales'),
       ('d-3', '112', 'HR')
;

--  Employees
INSERT INTO "Employees" ("guid", "firstName", "lastName", "birthDate", "gender", "departmentId")
VALUES ("martin", "martin", "drazkovec", CURRENT_TIMESTAMP, 'MALE', 'd-1')
;

--  RecipeCategories
INSERT INTO "RecipeCategory" ("guid", "name", "color")
VALUES ('lunch', 'lunch', '1'),
       ('dinner', 'dinner', '2')
;

--  Recipes
INSERT INTO "RecipeCategory" ("guid", "name", "description", "preparationTime", "numOfServings", "instructions", "recipeCategoryId")
VALUES ('pancakes', 'pancakes', 'delicious pancakes', '10', '2', 'mix everything together', 'lunch')
;