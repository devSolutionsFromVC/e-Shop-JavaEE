package com.solution.fromVC.store.ejb;

import com.solution.fromVC.entities.OrderStatus;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;

/**
 * Created by Влад on 20.11.2016.
 */
@Stateless
public class OrderStatusBean extends AbstractFacade<OrderStatus> implements Serializable{

    private static final long serialVersionUID = 5199196331433553237L;

    @PersistenceContext(unitName = "e-ShopU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderStatusBean(){
        super(OrderStatus.class);
    }

    public OrderStatus getStatusByName(String status){
        Query query = getEntityManager().createNamedQuery("OrderStatus.findByStatus");
        query.setParameter("status", status);
        return (OrderStatus) query.getSingleResult();
    }
}
