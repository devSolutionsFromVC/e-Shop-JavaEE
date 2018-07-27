package com.solution.fromVC.payment;

import com.solution.fromVC.event.OrderEvent;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;


@Path("/pay")
public class PaymentServices {

    private static final Logger logger = Logger.getLogger(String.valueOf(PaymentServices.class));

    public PaymentServices() {
    }

    @POST
    @Consumes("application/xml")
    public Response processPayment(OrderEvent order){
        logger.info("Amount: " + order.getAmount());
        if(order.getAmount() < 30000){
            return Response.ok(200).build();
        }else {
            return Response.status(401).build();
        }
    }

    @GET
    @Produces("text/html")
    public String getHtml(){
        return "PaymentServices";
    }
}
