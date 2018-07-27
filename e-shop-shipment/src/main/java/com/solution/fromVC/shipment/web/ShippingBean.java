package com.solution.fromVC.shipment.web;

import com.solution.fromVC.entities.CustomerOrder;
import com.solution.fromVC.shipment.ejb.OrderBrowser;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


@Named
@RequestScoped
public class ShippingBean implements Serializable{

    private static final Logger logger = Logger.getLogger(ShippingBean.class.getCanonicalName());

    private static final String SERVICE_ENDPOINT = "http://localhost:8080/e-Shop/services/order";

    private static final String MEDIA_TYPE = MediaType.APPLICATION_JSON;

    private static final long serialVersionUID = -2526289536313985021L;

    protected Client client;

    @EJB
    private OrderBrowser orderBrowser;

    @PostConstruct
    private void init(){
        client = ClientBuilder.newClient();
    }

    @PreDestroy
    private void destroy(){
        client.close();
    }

    private Map<String, CustomerOrder> orders;

    public Map<String, CustomerOrder> getOrders(){
        return orders;
    }

    public void setOrders(Map<String, CustomerOrder> orders){
        this.orders = orders;
    }

    public enum Status{

        PENDING_PAYMENT(2),
        READY_TO_SHIP(3),
        SHIPPED(4),
        CANCELLED_PAYMENT(5),
        CANCELLED_MANUAL(6);
        private int status;

        private Status(final int pStatus){
            status = pStatus;
        }

        public int getStatus(){
            return status;
        }
    }

    public String getEndpoint(){
        return SERVICE_ENDPOINT;
    }

    public List<CustomerOrder> listByStatus(final Status status){
        List<CustomerOrder> entity = (List<CustomerOrder>) client.target(SERVICE_ENDPOINT)
                .queryParam("status", String.valueOf(status.getStatus()))
                .request(MEDIA_TYPE)
                .get(new GenericType<List<CustomerOrder>>(){});
        return entity;
    }

    public void updateOrderStatus(final String messageID, final Status status) throws JMSException {
        CustomerOrder order = orderBrowser.processOrder(messageID);
        Response response = client.target(SERVICE_ENDPOINT)
                .path("/" + order.getId())
                .request(MEDIA_TYPE)
                .put(Entity.text(String.valueOf(status)));

        logger.log(Level.FINEST, "PUT Status response: {0}", response.getStatus());
    }

    public List<String> getPendingOrders() throws JMSException {
        Map<String, CustomerOrder> pendingOrders = orderBrowser.getOrders();

        if(pendingOrders == null){
            return null;
        } else {
            setOrders(pendingOrders);
            return new ArrayList<String>(getOrders().keySet());
        }
    }

    public List<CustomerOrder> getCompletedOrders(){
        return listByStatus(Status.SHIPPED);
    }
}
