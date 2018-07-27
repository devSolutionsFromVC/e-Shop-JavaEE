package com.solution.fromVC.store.ejb;

import com.solution.fromVC.event.OrderEvent;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.inject.New;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Влад on 20.11.2016.
 */
@Named("EventDispatcherBean")
@Stateless
public class EventDispatcherBean{

    private static final Logger logger = Logger.getLogger(EventDispatcherBean.class.getCanonicalName());

    @Inject
    @New
    Event<OrderEvent> eventManager;

    @Asynchronous
    public void publish(OrderEvent event){
        logger.log(Level.FINEST, "{0} Sending event from EJB", Thread.currentThread().getName());
        eventManager.fire(event);
    }

}
