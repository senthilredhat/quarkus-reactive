package org.ingi;

import com.fasterxml.jackson.databind.JsonNode;
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
    OrderRepository orderRepository;

    @Inject
    Event<ExportedEvent<String, JsonNode>> event;
    public OrderService(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }

    @ReactiveTransactional
//    @Transactional
    public Uni<Order> create(Order order){
        Log.info(Thread.currentThread().getName());
//        Uni<Order> ord = this.orderRepository.persist(order)
//                .emitOn(Infrastructure.getDefaultWorkerPool())
//                .invoke(()->event.fire(OrderCreatedEvent.of(order)));
//                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool());

//            Log.info(Thread.currentThread().getName());
//        try{event.fire(OrderCreatedEvent.of(order));}finally {
//            return this.orderRepository.persistAndFlush(order);
//        }
        var hello = Uni.createFrom().completionStage(
                event.fireAsync(OrderCreatedEvent.of(order)));

        //return this.orderRepository.persistAndFlush(order);

        return this.orderRepository.persistAndFlush(order)
               .invoke(()->event.fireAsync(OrderCreatedEvent.of(order)));
//            return Panache.withTransaction(() -> this.orderRepository.persist(order))
//                    .invoke(()->event.fire(OrderCreatedEvent.of(order)));

//                    .chain(event.fire(OrderCreatedEvent.of(order)));

//            return Panache.withTransaction(() -> this.orderRepository.persist(order))
//                    .invoke(o -> Log.infof("Persisted order: %s", o))
//                    .emitOn(Infrastructure.getDefaultWorkerPool())
//                    .invoke(o -> event.fire(OrderCreatedEvent.of(o)));

//        Uni.createFrom()
//                .nullItem()
//                .invoke(()->event.fire(OrderCreatedEvent.of(order)))
//                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
//                .subscribe();

//        return ord;

//        return this.orderRepository.persist(order)
//                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool()).invoke(()->event.fire(OrderCreatedEvent.of(order)))
//                .onFailure(PersistenceException.class)
//                .recoverWithItem(ex -> {
//                    Log.error(ex.getMessage(), ex);
//                    return null;
//                });
//                .invoke(()->event.fire(OrderCreatedEvent.of(order)));
    }
//.runSubscriptionOn(Infrastructure.getDefaultWorkerPool()).invoke(()->event.fire(OrderCreatedEvent.of(order)))
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
