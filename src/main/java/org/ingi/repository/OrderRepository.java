package org.ingi.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;
import org.ingi.repository.models.Order;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {

//    @ReactiveTransactional
//    public Uni<String> createOrder(Order order){
//        System.out.println("2nd" + order);
//        persistAndFlush(order);
//        return Uni.createFrom().item(order.getOrderNumber());
//        //return persist(order);
//        //return order.onItem()
//        //return persist(order).onItem().transform(inserted -> inserted.getOrderNumber());
//
//    }


    public Uni<Order> findByCustomerName(String name){
        return find("customer", name).firstResult();
    }
}
