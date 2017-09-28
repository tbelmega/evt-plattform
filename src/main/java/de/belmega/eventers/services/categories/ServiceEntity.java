package de.belmega.eventers.services.categories;

import javax.persistence.*;

@Entity
public class ServiceEntity {

    @Id
    @Column(unique = true)
    private String serviceName;

    @ManyToOne // each service has One category. each category has Many services. --> Many to One
    private CategoryEntity category;

    public ServiceEntity(CategoryEntity category, String serviceName) {
        this.serviceName = serviceName;
        this.category = category;
    }

    public ServiceEntity() {
    }

    public String getServiceName() {
        return serviceName;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }
}
