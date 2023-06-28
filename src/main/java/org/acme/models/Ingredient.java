package org.acme.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.acme.dto.request.IngredientRequest;

import javax.persistence.*;

@Entity
public class Ingredient extends PanacheEntityBase  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    public Ingredient() {
    }

    public Ingredient(IngredientRequest request) {
        this.name = request.getName();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
