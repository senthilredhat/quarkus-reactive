package org.ingi;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import org.ingi.repository.OrderRepository;
import org.ingi.repository.models.Order;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;

@ApplicationScoped
public class OrderService {
    OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }

    @ReactiveTransactional
    public Uni<Order> create(Order order){
        Log.info(Thread.currentThread().getName());
        return this.orderRepository.persist(order)
                .onFailure(PersistenceException.class)
                .recoverWithItem(ex -> {
                    Log.error(ex.getMessage(), ex);
                    return null;
                });
    }

}
