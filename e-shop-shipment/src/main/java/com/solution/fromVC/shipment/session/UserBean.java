package com.solution.fromVC.shipment.session;

import com.solution.fromVC.entities.Customer;
import com.solution.fromVC.entities.Person;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class UserBean extends AbstractFacade<Customer>{

    @PersistenceContext(unitName = "e-ShopU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Person getPersonByEmail(String email){
        Query query = getEntityManager().createNamedQuery("Person.findByEmail");
        query.setParameter("email", email);
        return (Person)query.getSingleResult();
    }

    public UserBean() {
        super(Customer.class);
    }
}
