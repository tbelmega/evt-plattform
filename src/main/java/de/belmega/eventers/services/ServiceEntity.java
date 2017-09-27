package de.belmega.eventers.services;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ServiceEntity {

    @Id
    @GeneratedValue
    private long id;

    private String serviceName;

    @ManyToOne // each service has One category. each category has Many services. --> Many to One
    private CategoryEntity categoryEntity;

    public ServiceEntity(CategoryEntity categoryEntity, String serviceName) {
        this.serviceName = serviceName;
        this.categoryEntity = categoryEntity;
    }

    public ServiceEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }
}
