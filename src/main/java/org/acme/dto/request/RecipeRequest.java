package org.acme.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RecipeRequest {

    @NotBlank(message="Title may not be blank")
    private String title;
    @NotNull
    private Long numberOfPortion;
    @NotNull
    private Long preparationTime;
    @NotBlank(message="Preparation method may not be blank")
    private String preparationMethod;
    @NotNull
    private Long author;
    @NotNull
    private Long category;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getNumberOfPortion() {
        return numberOfPortion;
    }

    public void setNumberOfPortion(Long numberOfPortion) {
        this.numberOfPortion = numberOfPortion;
    }

    public String getPreparationMethod() {
        return preparationMethod;
    }

    public void setPreparationMethod(String preparationMethod) {
        this.preparationMethod = preparationMethod;
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Long getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Long preparationTime) {
        this.preparationTime = preparationTime;
    }
}
