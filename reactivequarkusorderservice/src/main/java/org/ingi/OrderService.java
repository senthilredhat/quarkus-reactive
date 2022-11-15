package org.ingi;

import com.fasterxml.jackson.databind.JsonNode;
import io.debezium.outbox.quarkus.internal.DebeziumOutboxHandler;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import org.ingi.repository.OrderRepository;
import org.ingi.repository.models.Order;
import org.ingi.cdc.OrderCreatedEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.UUID;
import io.debezium.outbox.quarkus.ExportedEvent;


@ApplicationScoped
public class OrderService {
    @Inject
    DebeziumOutboxHandler handler;
    OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }

    @ReactiveTransactional
    public Uni<Order> create(Order order){
        Log.info(Thread.currentThread().getName());
        return handler.persistToOutbox(OrderCreatedEvent.of(order)).chain(()->this.orderRepository.persistAndFlush(order));
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
