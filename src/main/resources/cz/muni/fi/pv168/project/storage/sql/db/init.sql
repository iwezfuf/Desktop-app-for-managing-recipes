--
-- Department table definition
--
CREATE TABLE IF NOT EXISTS "Department"
(
    `id`        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `guid`      VARCHAR     NOT NULL UNIQUE,
    `number`    VARCHAR(10) NOT NULL UNIQUE,
    `name`      VARCHAR(50) NOT NULL,
    `createdAt` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--
-- Employee table definition
--
CREATE TABLE IF NOT EXISTS "Employee"
(
    `id`           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `guid`         VARCHAR      NOT NULL UNIQUE,
    `firstName`    VARCHAR(150) NOT NULL,
    `lastName`     VARCHAR(150) NOT NULL,
    `birthDate`    DATE         NOT NULL,
    `gender`       ENUM('MALE', 'FEMALE') NOT NULL,
    `departmentId` BIGINT REFERENCES "Department"(`id`),
    `createdAt`    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--
-- RecipeCategory table definition
--
CREATE TABLE IF NOT EXISTS "RecipeCategory"
(
    `id`               BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `guid`             VARCHAR       NOT NULL UNIQUE,
    `name`             VARCHAR(150)  NOT NULL,
    `color`            INT           NOT NULL,
    `createdAt`        TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--
-- Recipe table definition
--
CREATE TABLE IF NOT EXISTS "Recipe"
(
    `id`               BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `guid`             VARCHAR       NOT NULL UNIQUE,
    `name`             VARCHAR(150)  NOT NULL,
    `description`      VARCHAR(1500) NOT NULL,
    `preparationTime`  INT           NOT NULL,
    `numOfServings`    INT           NOT NULL,
    `instructions`     VARCHAR(3500) NOT NULL,
    `recipeCategoryId` BIGINT REFERENCES "RecipeCategory"(`id`),
    `createdAt`        TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP
);


--
-- Unit table definition
--
CREATE TABLE IF NOT EXISTS "Unit"
(
    `id`               BIGINT GENERATED ALWAYS AS IDENTITY (START WITH 0) PRIMARY KEY,
    `guid`             VARCHAR       NOT NULL UNIQUE,
    `name`             VARCHAR(150)  NOT NULL,
    `abbreviation`     VARCHAR(30)   NOT NULL,
    `conversionRatio`  FLOAT         NOT NULL,
    `conversionUnitId` BIGINT REFERENCES "Unit"(`id`),
    `createdAt`        TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--ALTER TABLE Unit ADD FOREIGN KEY (conversionUnit) REFERENCES Unit (id);


--
-- Ingredient table definition
--
CREATE TABLE IF NOT EXISTS "Ingredient"
(
    `id`               BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `guid`             VARCHAR       NOT NULL UNIQUE,
    `name`             VARCHAR(150)  NOT NULL,
    `nutritionalValue` INT           NOT NULL,
    `unitId`           BIGINT REFERENCES "Unit"(`id`),
    `createdAt`        TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP
);


--
-- RecipeIngredientAmount table definition
--
CREATE TABLE IF NOT EXISTS "RecipeIngredientAmount"
(
    `id`               BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `guid`             VARCHAR       NOT NULL UNIQUE,
    `recipeId`         BIGINT REFERENCES "Recipe"(`id`),
    `ingredientId`     BIGINT REFERENCES "Ingredient"(`id`),
    `amount`           INT           NOT NULL,
    `createdAt`        TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP
);


--
-- Development data - used for development
--
-- // TODO: remove this later

--  Departments
INSERT INTO "Department" ("guid", "number", "name")
VALUES ('d-1', '007', 'IT'),
       ('d-2', '666', 'Sales'),
       ('d-3', '112', 'HR')
;

--  Employees
INSERT INTO "Employee" ("guid", "firstName", "lastName", "birthDate", "gender", "departmentId")
VALUES ('martin', 'martin', 'drazkovec', CURRENT_TIMESTAMP, 'MALE', '1')
;

--  RecipeCategories
INSERT INTO "RecipeCategory" ("guid", "name", "color")
VALUES ('lunch', 'lunch', '1'),
       ('dinner', 'dinner', '2')
;

--  Recipes
INSERT INTO "Recipe" ("guid", "name", "description", "preparationTime", "numOfServings", "instructions", "recipeCategoryId")
VALUES ('pancakes', 'pancakes', 'delicious pancakes', '10', '2', 'mix everything together', '1')
;

INSERT INTO "Recipe" ("guid", "name", "description", "preparationTime", "numOfServings", "instructions", "recipeCategoryId")
VALUES ('aaa', 'zzz', 'aaa', '15', '5', 'aaaaaaa', '1')
;

--  Units
-- TODO this is probably not the best way to do this...
INSERT INTO "Unit" ("guid", "name", "abbreviation", "conversionRatio", "conversionUnitId")
VALUES ('Base Unit', 'Base Unit', 'bu', '1', NULL)
;