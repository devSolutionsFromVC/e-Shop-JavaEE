package com.solution.fromVC.shipment.ejb;

import com.solution.fromVC.entities.CustomerOrder;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;
import javax.jms.Queue;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


@Stateless
public class OrderBrowser {

    private static final Logger logger = Logger.getLogger(OrderBrowser.class.getCanonicalName());

    @Inject
    private JMSContext context;

    @Resource(mappedName = "java:/jms/eShopOrderQueue")
    private Queue queue;
    private QueueBrowser browser;

    public Map<String, CustomerOrder> getOrders() throws JMSException {
        browser = context.createBrowser(queue);

        Enumeration msgs;
        try{
            msgs = browser.getEnumeration();

            if(!msgs.hasMoreElements()){
                logger.log(Level.INFO, "No messages on the queue");
            }else {
                Map<String, CustomerOrder> result = new LinkedHashMap<String, CustomerOrder>();
                while (msgs.hasMoreElements()){
                    Message msg = (Message) msgs.nextElement();

                    logger.log(Level.INFO, "Message ID: {0}",msg.getJMSMessageID());
                    CustomerOrder order = msg.getBody(CustomerOrder.class);
                    result.put(msg.getJMSMessageID(), order);
                }
                return result;
            }

        }catch (JMSException e){
            Logger.getLogger(OrderBrowser.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public CustomerOrder processOrder(String OrderMessageID) throws JMSException {
        logger.log(Level.INFO, "ProcessingOrder: {0}", OrderMessageID);
        JMSConsumer consumer = context.createConsumer(queue, "JMSMessageID='" + OrderMessageID + "'");
        CustomerOrder order = consumer.receiveBody(CustomerOrder.class, 1);
        return order;
    }
}
