package com.solution.fromVC.store.ejb;

import com.solution.fromVC.entities.Customer;
import com.solution.fromVC.entities.Groups;
import com.solution.fromVC.entities.Person;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by Влад on 21.11.2016.
 */
@Stateless
public class UserBean extends AbstractFacade<Customer>{

    @PersistenceContext(unitName = "e-ShopU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserBean() {
        super(Customer.class);
    }

    @Override
    public void create(Customer user) {
        Groups userGroup = (Groups) getEntityManager().createNamedQuery("Groups.findByName")
                            .setParameter("name", "USERS")
                            .getSingleResult();
        user.getGroupsList().add(userGroup);
        userGroup.getPersonList().add(user);
        em.persist(user);
        em.merge(userGroup);
    }

    @Override
    public void remove(Customer user) {
        Groups userGroup = (Groups) getEntityManager().createNamedQuery("Groups.findByName")
                .setParameter("name", "USERS")
                .getSingleResult();
        user.getGroupsList().remove(userGroup);
        userGroup.getPersonList().remove(user);
        em.remove(em.merge(user));
        em.merge(userGroup);
    }

    public Person getUserByEmail(String email){
       Query query = (Query) getEntityManager().createNamedQuery("Person.findByEmail")
                        .setParameter("email", email);
        if(query.getResultList().size() > 0){
            return (Person) query.getSingleResult();
        }else {
            return null;
        }
    }
}
