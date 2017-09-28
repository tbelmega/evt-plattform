package de.belmega.eventers.services.categories;

import javax.persistence.*;

@Entity
public class CategoryEntity {

    @Id
    @Column(unique = true)
    private String categoryName;

    public CategoryEntity(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryEntity() {
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return categoryName;
    }
}
