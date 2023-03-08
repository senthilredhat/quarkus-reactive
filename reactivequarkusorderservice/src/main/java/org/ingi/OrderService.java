package org.ingi;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.ingi.repository.OrderRepository;
import org.ingi.repository.models.Order;

import java.util.UUID;


@ApplicationScoped
public class OrderService {


    OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }

    @WithTransaction
    public Uni<Order> create(Order order){
        Log.info(Thread.currentThread().getName());
        return this.orderRepository.persistAndFlush(order);
    }



    @WithTransaction
    public Uni<Integer> cancel(String id){
        return this.orderRepository.update("status=4 where id=?1",UUID.fromString(id));
    }

}
