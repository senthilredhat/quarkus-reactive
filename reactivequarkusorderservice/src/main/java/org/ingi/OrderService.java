package org.ingi;
import com.fasterxml.jackson.databind.JsonNode;
//import io.debezium.outbox.reactive.quarkus.internal.DebeziumOutboxHandler;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.logging.Log;
import io.quarkus.vault.runtime.client.Shared;
import io.quarkus.vault.runtime.client.VaultClient;
import io.smallrye.mutiny.Uni;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.reactive.mutiny.Mutiny;
import org.ingi.repository.OrderRepository;
import org.ingi.repository.models.Order;
//import org.ingi.cdc.OrderCreatedEvent;


import java.util.Objects;
import java.util.UUID;
//import io.debezium.outbox.quarkus.ExportedEvent;


@ApplicationScoped
public class OrderService {
//    @Inject
//    DebeziumOutboxHandler handler;
    @Inject @Shared
    VaultClient client;

    OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }

    @WithTransaction
    public Uni<Order> create(Order order){
        Log.info(Thread.currentThread().getName());
        return this.orderRepository.persistAndFlush(order);
//                .call(() -> handler.persistToOutbox(OrderCreatedEvent.of(order)));
    }



    @WithTransaction
    public Uni<Integer> cancel(String id){
        return this.orderRepository.update("status=4 where id=?1",UUID.fromString(id));
    }

}
