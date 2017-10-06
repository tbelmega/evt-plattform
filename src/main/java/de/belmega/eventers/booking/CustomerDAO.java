package de.belmega.eventers.booking;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class CustomerDAO {

    @PersistenceContext
    EntityManager em;


    public CustomerTO persist(CustomerTO customer) {

        CustomerEntity customerEntity = loadOrCreateCustomerEntity(customer);

        customerEntity.setCompany(customer.getCompany());
        customerEntity.setGreeting(customer.getGreeting());
        customerEntity.setFirstName(customer.getFirstname());
        customerEntity.setLastName(customer.getLastname());
        customerEntity.setEmailadress(customer.getEmailadress());
        customerEntity.setMobile(customer.getMobile());
        customerEntity.setHotelAdress(customer.getHotelAdress());

        customer.setId(customerEntity.getId());
        return customer;
    }

    private CustomerEntity loadOrCreateCustomerEntity(CustomerTO customer) {
        CustomerEntity customerEntity = getBookingEntity(customer);
        if (customerEntity == null) {
            customerEntity = new CustomerEntity();
            em.persist(customerEntity);
        }
        return customerEntity;
    }

    private CustomerEntity getBookingEntity(CustomerTO customer) {
        if (customer.getId() != null) {
            return em.find(CustomerEntity.class, customer.getId());
        } else return null;
    }

    public CustomerEntity findById(Long customerId) {
        return em.find(CustomerEntity.class, customerId);
    }
}
