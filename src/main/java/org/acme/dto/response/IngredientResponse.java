package org.acme.dto.response;

import org.acme.models.Recipe;

public class IngredientResponse {

    private Long id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
