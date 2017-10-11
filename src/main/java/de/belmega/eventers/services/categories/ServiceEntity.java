package de.belmega.eventers.services.categories;

import javax.persistence.*;

@Entity
public class ServiceEntity {


    @Id
    private String serviceId;

    private  int hourlyRate;

    private String serviceName;

    @ManyToOne // each service has One category. each category has Many services. --> Many to One
    private CategoryEntity category;

    public ServiceEntity(String serviceId, CategoryEntity category, String serviceName, int hourlyRate) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.category = category;
        this.hourlyRate = hourlyRate;
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

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return serviceName;
    }
}
