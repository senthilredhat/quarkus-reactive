package org.ingi.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import org.ingi.proto.CreateOrderResponseMessage;
import org.ingi.repository.models.Order;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {


    public Uni<Order> findByCustomerName(String name){
        return find("customer", name).firstResult();
    }

    public Uni<String> getLastOrderNumber(String customerId){
        return find("customerId","ORDER by creation_time DESC",customerId).firstResult()
                .onItem().ifNotNull().transform(o-> {
                    return o.getOrderNumber();
                } )
                .onItem().ifNull().continueWith(() ->{
                    return "0";
                });
    }
}
