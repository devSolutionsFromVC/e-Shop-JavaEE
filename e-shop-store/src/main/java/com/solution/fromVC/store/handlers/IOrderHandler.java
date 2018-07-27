package com.solution.fromVC.store.handlers;

import com.solution.fromVC.event.OrderEvent;

/**
 * Created by Влад on 21.11.2016.
 */
public interface IOrderHandler {

    void onNewOrder(OrderEvent event);
}
