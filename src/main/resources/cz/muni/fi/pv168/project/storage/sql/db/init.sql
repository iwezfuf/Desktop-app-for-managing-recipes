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
    `id`               BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
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
