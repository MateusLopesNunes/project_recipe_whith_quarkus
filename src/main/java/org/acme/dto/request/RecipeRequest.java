package org.acme.dto.request;

public class RecipeRequest {

    private String title;
    private String description;
    private Long numberOfPortion;
    private String preparationMethod;
    private String image;
    private Long author;
    private Long category;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
