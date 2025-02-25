DDL for Db tables creation:
-----------------------------------------------------------------------------------------------------------------
CREATE TABLE recipes (
id SERIAL PRIMARY KEY,
title VARCHAR(255) NOT NULL,
description TEXT NOT NULL,
ingredients JSONB NOT NULL,  -- Guardar los ingredientes como un array JSON
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_recipes_title ON recipes(title);

App description:
---------------------------------------------------------------------------------------------------------
The app is a simple recipe manager. It allows users to create, read, update and delete recipes. 
A recipe consists of a title, a description and a list of ingredients. The ingredients are stored as a JSON array in the database.
The app also has a search functionality that allows users to search for recipes by title.
