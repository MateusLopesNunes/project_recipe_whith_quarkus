package org.acme.dto.response;

import org.acme.models.Recipe;

public class IngredientResponse {

    private Long id;
    private String name;
    private Recipe recipe;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
