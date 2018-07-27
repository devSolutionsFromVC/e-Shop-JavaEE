package com.solution.fromVC.store.ejb;

import com.solution.fromVC.entities.OrderDetail;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Влад on 20.11.2016.
 */
@Stateless
public class OrderDetailBean extends AbstractFacade<OrderDetail>{

    @PersistenceContext(unitName = "e-ShopU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderDetailBean() {
        super(OrderDetail.class);
    }

    public List<OrderDetail> findOrderDetailByOrder(int orderId){
        Query query = getEntityManager().createNamedQuery("OrderDetail.findByOrderId");
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }
}
