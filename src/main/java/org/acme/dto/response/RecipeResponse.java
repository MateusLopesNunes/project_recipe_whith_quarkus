package org.acme.dto.response;

import org.acme.models.Category;
import org.acme.models.User;

import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class RecipeResponse {

    public Long id;
    public String title;
    public String description;
    public Long numberOfPortion;
    public String preparationMethod;
    public LocalDate PreparationTime = LocalDate.now();
    public LocalDateTime createdAt = LocalDateTime.now();
    public LocalDateTime updatedAt = LocalDateTime.now();
    public String image;
    public User author;
    public Category category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDate getPreparationTime() {
        return PreparationTime;
    }

    public void setPreparationTime(LocalDate preparationTime) {
        PreparationTime = preparationTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
