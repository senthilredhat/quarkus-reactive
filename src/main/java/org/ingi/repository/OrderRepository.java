package org.ingi.repository;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;
import org.ingi.repository.models.Order;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {

    @ReactiveTransactional
    public Uni<String> createOrder(Order order){
        return persist(order).onItem().transform(inserted -> inserted.getOrderNumber());

    }


    public Uni<Order> findByCustomerName(String name){
        return find("customer", name).firstResult();
    }
}
