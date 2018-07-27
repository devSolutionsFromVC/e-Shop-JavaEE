package com.solution.fromVC.store.ejb;

import com.solution.fromVC.entities.CustomerOrder;
import com.solution.fromVC.entities.OrderStatus;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.*;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Влад on 20.11.2016.
 */
@Stateless
@Path("/orders")
public class OrderBean  extends AbstractFacade<CustomerOrder> implements Serializable{

    private static final Logger logger = Logger.getLogger(OrderBean.class.getCanonicalName());

    private static final long serialVersionUID = -2407971550575800416L;

    @PersistenceContext(unitName = "e-ShopU")
    private EntityManager em;

    CustomerOrder order;

    @EJB
    OrderStatusBean statusBean;

    public OrderBean() {
        super(CustomerOrder.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<CustomerOrder> getOrderByCustomerId(Integer id){
        Query query = (Query) getEntityManager().createNamedQuery("CustomerOrder.findByCustomerId");
        query.setParameter("id", id);
        return query.getResultList();
    }

    public CustomerOrder getOrderById(Integer id){
        Query query = getEntityManager().createNamedQuery("CustomerOrder.findById");
        query.setParameter("id", id);
        return (CustomerOrder) query.getSingleResult();
    }

    @GET
    @Produces({"application/json", "application/xml"})
    public List<CustomerOrder> getOrderByStatus(@QueryParam("status") int status){
        Query query = getEntityManager().createNamedQuery("CustomerOrder.findByStatusId");
        OrderStatus result = statusBean.find(status);
        query.setParameter("status", result.getStatus());
        return query.getResultList();
    }

    @PUT
    @Path("{orderId}")
    @Produces({"application/json", "application/xml"})
    public void setOrderStatus(@PathParam("orderId") int orderId, String newStatus){
        logger.log(Level.INFO, "Order id:{0} - Status:{1}", new Object[]{orderId, newStatus});

        try{
            order = this.find(orderId);

            if(order != null){
                logger.log(Level.FINEST, "Updating order {0} status to {1}", new Object[]{order.getId(), newStatus});
                OrderStatus oStatus = statusBean.find(new Integer(newStatus));
                order.setOrderStatus(oStatus);
                em.merge(order);

                logger.info("Order Updated!");
            }
        }catch (Exception e){
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    public enum Status {

        PENDING_PAYMENT(2),
        READY_TO_SHIP(3),
        SHIPPED(4),
        CANCELLED_PAYMENT(5),
        CANCELLED_MANUAL(6);
        private int status;

        private Status(int pStatus) {
            status = pStatus;
        }

        public int getStatus() {
            return status;
        }
    }

}
