package com.solution.fromVC.store.handlers;

import com.solution.fromVC.event.OrderEvent;
import com.solution.fromVC.store.ejb.OrderBean;
import com.solution.fromVC.store.qualifiers.New;
import com.solution.fromVC.store.qualifiers.Paid;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Влад on 21.11.2016.
 */
public class PaymentHandler implements IOrderHandler, Serializable{


    private static final Logger logger = Logger.getLogger(PaymentHandler.class.getCanonicalName());
    private static final long serialVersionUID = 4979287107039479577L;
    private static final String ENDPOINT = "http://localhost:8080/e-shop-payment/payment/pay";

    @Inject
    @Paid
    Event<OrderEvent> eventManager;

    @EJB
    OrderBean orderBean;

    @Asynchronous
    @Override
    public void onNewOrder(@Observes @New OrderEvent event) {

        logger.log(Level.FINEST, "{0} Event being processed by PaymentHandler", Thread.currentThread().getName());

        if(processPayment(event)){
            orderBean.setOrderStatus(event.getOrderID(), String.valueOf(OrderBean.Status.PENDING_PAYMENT.getStatus()));
            logger.info("Payment Approved");
            eventManager.fire(event);
        }else {
            orderBean.setOrderStatus(event.getOrderID(), String.valueOf(OrderBean.Status.CANCELLED_PAYMENT.getStatus()));
            logger.info("Payment Denied");
        }
    }

    private boolean processPayment(OrderEvent order) {

        boolean success = false;
        Client client = ClientBuilder.newClient();
        client.register(new AuthClientRequestFilter("paymentuser@example.com", "1234"));
        Response response = client.target(ENDPOINT)
                .request(MediaType.APPLICATION_XML)
                .post(Entity.entity(order, MediaType.APPLICATION_XML), Response.class);
        int status = response.getStatus();
        if(status == 200){
            success = true;
        }
        logger.log(Level.INFO, "[PaymentHandler] Response status {0}", status);
        client.close();
        return success;
    }

    private class AuthClientRequestFilter implements ClientRequestFilter{

        private final String user;
        private final String password;

        public AuthClientRequestFilter(String user, String password) {
            this.user = user;
            this.password = password;
        }

        @Override
        public void filter(ClientRequestContext requestContext) throws IOException {
            try{
                requestContext.getHeaders().add(
                        "Authorization",
                        "BASIC" + DatatypeConverter.printBase64Binary(
                                (user + ":" + password).getBytes("UTF-8"))
                );
            }catch (UnsupportedEncodingException e){

            }
        }
    }
}
