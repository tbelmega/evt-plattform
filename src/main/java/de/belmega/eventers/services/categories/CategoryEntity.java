package de.belmega.eventers.services.categories;

import javax.persistence.*;

@Entity
public class CategoryEntity {

    @Id
    private String id;

    private String categoryName;

    public CategoryEntity(String id, String categoryName) {
        this.id = id;
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

    public String getId() {
        return id;
    }
}
