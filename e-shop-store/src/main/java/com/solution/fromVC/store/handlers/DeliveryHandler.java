package com.solution.fromVC.store.handlers;

import com.solution.fromVC.entities.CustomerOrder;
import com.solution.fromVC.event.OrderEvent;
import com.solution.fromVC.store.ejb.OrderBean;
import com.solution.fromVC.store.ejb.OrderJMSManager;
import com.solution.fromVC.store.qualifiers.Paid;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Влад on 21.11.2016.
 */
@Stateless
public class DeliveryHandler implements IOrderHandler, Serializable {


    private static final Logger logger = Logger.getLogger(DeliveryHandler.class.getCanonicalName());
    private static final long serialVersionUID = 4346750267714932851L;

    @EJB
    OrderBean orderBean;
    @EJB
    OrderJMSManager orderPublisher;

    @Asynchronous
    @Override
    public void onNewOrder(@Observes @Paid OrderEvent event) {

        logger.log(Level.FINEST, "{0} Event being processed by DeliveryHandler", Thread.currentThread().getName());

        try{
            logger.log(Level.INFO, "Order #{0} has been paid in the amount of {1}. Order is now ready for delivery!", new Object[]{event.getOrderID(), event.getAmount()});

            orderBean.setOrderStatus(event.getOrderID(), String.valueOf(OrderBean.Status.READY_TO_SHIP.getStatus()));
            CustomerOrder order = orderBean.getOrderById(event.getOrderID());
            if(order != null){
                orderPublisher.sendMessage(order);
            }else {
                throw new Exception("The order does not exist");
            }
        }catch (Exception e){
            logger.log(Level.SEVERE, null, e);
        }
    }
}
