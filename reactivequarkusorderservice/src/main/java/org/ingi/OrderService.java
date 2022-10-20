package org.ingi;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.protobuf.Empty;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import net.bytebuddy.pool.TypePool;
import org.ingi.repository.OrderRepository;
import org.ingi.repository.models.Order;
import org.ingi.debezium.OrderCreatedEvent;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
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
    public Uni<Order> create(Order order){
        Log.info(Thread.currentThread().getName());
        Uni<Order> ord = this.orderRepository.persist(order)
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(()->event.fire(OrderCreatedEvent.of(order)));
//                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool());

//        Uni.createFrom()
//                .nullItem()
//                .invoke(()->event.fire(OrderCreatedEvent.of(order)))
//                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
//                .subscribe();

        return ord;

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

}
