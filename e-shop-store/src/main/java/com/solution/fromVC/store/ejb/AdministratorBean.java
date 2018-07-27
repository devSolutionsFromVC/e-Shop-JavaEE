package com.solution.fromVC.store.ejb;

import com.solution.fromVC.entities.Administrator;
import com.solution.fromVC.entities.Groups;
import com.solution.fromVC.entities.Person;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by Влад on 17.11.2016.
 */
@Stateless
public class AdministratorBean extends AbstractFacade<Administrator> {

    private boolean lastAdministrator;

    @PersistenceContext(unitName = "e-ShopU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdministratorBean(){
        super(Administrator.class);
    }

    public Person getAdministratorByEmail(String email){
        Query query = getEntityManager().createNamedQuery("Person.findByEmail");
        query.setParameter("email", email);

        if(query.getResultList().size() > 0){
            return (Person) query.getSingleResult();
        }else {
            return null;
        }
    }

    public boolean isLastAdministrator(){
        return lastAdministrator;
    }

    @Override
    public void create(Administrator admin) {
        Groups adminGroup = (Groups) em.createNamedQuery("Groups.findByName")
                        .setParameter("name", "ADMINS")
                        .getSingleResult();
        admin.getGroupsList().add(adminGroup);
        adminGroup.getPersonList().add(admin);
        em.persist(admin);
        em.merge(adminGroup);
    }

    @Override
    public void remove(Administrator admin) {
        Groups adminGroup = (Groups) em.createNamedQuery("Groups.findByName")
                .setParameter("name", "ADMINS")
                .getSingleResult();
        if(adminGroup.getPersonList().size() > 0){
            adminGroup.getPersonList().remove(admin);
            em.remove(admin);
            em.merge(adminGroup);
            lastAdministrator = false;
        }else {
            lastAdministrator = true;
        }

    }
}
