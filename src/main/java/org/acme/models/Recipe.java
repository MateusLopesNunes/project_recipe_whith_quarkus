package org.acme.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
public class Recipe extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String title;
    public Long numberOfPortion;
    public String preparationMethod;
    public Long preparationTime;
    public LocalDateTime createdAt = LocalDateTime.now();
    public LocalDateTime updatedAt;
    public String image;
    @ManyToOne
    public User author;
    @ManyToOne
    public Category category;

    public static PanacheQuery<Recipe> findByCategory(Long categoryId){
          return find("category = " + categoryId);
    }

    public static PanacheQuery<Recipe> findByUser(Long userId){
        return find("author = " + userId);
    }

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

    public Long getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Long preparationTime) {
        this.preparationTime = preparationTime;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
