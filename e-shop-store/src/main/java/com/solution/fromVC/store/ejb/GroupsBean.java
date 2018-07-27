package com.solution.fromVC.store.ejb;

import com.solution.fromVC.entities.Groups;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Влад on 20.11.2016.
 */
@Stateless
public class GroupsBean extends AbstractFacade<Groups>{

    @PersistenceContext(unitName = "e-ShopU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GroupsBean() {
        super(Groups.class);
    }

}
