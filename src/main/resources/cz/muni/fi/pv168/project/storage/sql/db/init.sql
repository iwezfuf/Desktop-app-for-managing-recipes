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
-- Recipe table definition
--
CREATE TABLE IF NOT EXISTS "Recipe"
(
    `id`               BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `guid`             VARCHAR      NOT NULL UNIQUE,
    `name`             VARCHAR(150) NOT NULL,
    `description`      VARCHAR(1500) NOT NULL,
    `preparationTime`  INT NOT NULL,
    `numOfServings`    INT NOT NULL,
    `instructions`     VARCHAR(2500) NOT NULL,
    `recipeCategoryId` BIGINT REFERENCES "RecipeCategory"(`id`),
);

--
-- RecipeCategory table definition
--
CREATE TABLE IF NOT EXISTS "RecipeCategory"
(
    `id`        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `guid`      VARCHAR      NOT NULL UNIQUE,
    `name`      VARCHAR(150) NOT NULL,
    `color`     INT          NOT NULL,
);

