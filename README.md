DDL for Db tables creation:
-----------------------------------------------------------------------------------------------------------------
CREATE TABLE recipes (
id SERIAL PRIMARY KEY,
title VARCHAR(255) NOT NULL,
description TEXT NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ingredients (
id SERIAL PRIMARY KEY,
recipe_id INT NOT NULL,
name VARCHAR(255) NOT NULL,
FOREIGN KEY (recipe_id) REFERENCES recipes(id) ON DELETE CASCADE
);

CREATE INDEX idx_recipes_title ON recipes(title);

Introduction de la application:
---------------------------------------------------------------------------------------------------------