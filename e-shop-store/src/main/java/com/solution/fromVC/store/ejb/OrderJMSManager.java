package com.solution.fromVC.store.ejb;

import com.solution.fromVC.entities.CustomerOrder;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Влад on 20.11.2016.
 */
//@JMSDestinationDefinition(
//        name = "java:/jms/eShopOrderQueue",
//        interfaceName = "javax.jms.Queue",
//        destinationName = "eShopOrderQueue")
@Stateless
public class OrderJMSManager {

    private static final Logger logger = Logger.getLogger(OrderJMSManager.class.getCanonicalName());

    @Inject
    private JMSContext context;

    @Resource(mappedName = "java:/jms/eShopOrderQueue")
    private Queue queue;
    private QueueBrowser browser;

    public void sendMessage(CustomerOrder customerOrder){
        ObjectMessage msgObj = context.createObjectMessage();

        try{
            msgObj.setObject(customerOrder);
            msgObj.setObjectProperty("OrderID", String.valueOf(customerOrder.getId()));
        }catch (JMSException e){
            logger.log(Level.SEVERE, null, e);
        }
    }

    public void deleteMessage(int orderID) throws Exception {
        JMSConsumer consumer = context.createConsumer(queue, "OrderID='" + orderID + "'");
        CustomerOrder order = consumer.receiveBody(CustomerOrder.class, 1);

        if(order != null){
            logger.log(Level.INFO, "Order {0} removed from queue.", order.getId());
        }else {
            logger.log(Level.SEVERE, "Order {0} was not removed from queue!", orderID);
            throw new Exception("Order not removed from queue");
        }
    }
}
