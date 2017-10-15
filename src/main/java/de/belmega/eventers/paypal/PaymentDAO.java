package de.belmega.eventers.paypal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class PaymentDAO {

    @PersistenceContext
    EntityManager em;

    public void persist(PaymentEntity paymentEntity) {
        em.persist(paymentEntity);
    }
}
