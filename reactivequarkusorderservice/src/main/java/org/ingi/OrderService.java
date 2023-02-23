package org.ingi;

import com.fasterxml.jackson.databind.JsonNode;
//import io.debezium.outbox.reactive.quarkus.internal.DebeziumOutboxHandler;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.reactive.mutiny.Mutiny;
import org.ingi.repository.OrderRepository;
import org.ingi.repository.models.Order;
//import org.ingi.cdc.OrderCreatedEvent;


import java.util.Objects;
import java.util.UUID;
//import io.debezium.outbox.quarkus.ExportedEvent;
import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.narayana.jta.RunOptions;

@ApplicationScoped
public class OrderService {
//    @Inject
//    DebeziumOutboxHandler handler;
    @Inject
    Mutiny.SessionFactory sf;
    OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }

    @ReactiveTransactional
    public Uni<Order> create(Order order){
        Log.info(Thread.currentThread().getName());
        return this.orderRepository.persistAndFlush(order);
//                .invoke(()->this.beginExample(order));
//                .call(() -> handler.persistToOutbox(OrderCreatedEvent.of(order)));
    }

    public void beginExample(Order order) {
        QuarkusTransaction.begin(QuarkusTransaction.beginOptions()
                .timeout(10));
        QuarkusTransaction.rollback();
        //do work
        Log.info(order.getCustomer());
        if(Objects.equals(order.getCustomer(), "ingmar")){
            Log.info("I am here");
            QuarkusTransaction.commit();
        }
        else{
            Log.info("I am now here");
            QuarkusTransaction.rollback();
        }
//        QuarkusTransaction.rollback();
    }
    @ReactiveTransactional
    public Uni<Integer> cancel(String id){
        return this.orderRepository.update("status=4 where id=?1",UUID.fromString(id));
    }

//    public Uni<PanacheEntityBase> onExportedEvent(@Observes ExportedEvent event) {
//        OutboxEvent outboxEvent = new OutboxEvent(
//                event.getAggregateType(),
//                event.getAggregateId(),
//                event.getType(),
//                event.getPayload().
//        );
//
//       return outboxEvent.persist();
//    }

}
