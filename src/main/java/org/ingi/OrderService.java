package org.ingi;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;
import org.ingi.repository.OrderRepository;
import org.ingi.repository.models.Order;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderService {
    OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }

    @ReactiveTransactional
    public Uni<Order> create(Order order){
        return this.orderRepository.persist(order);
    }

}
