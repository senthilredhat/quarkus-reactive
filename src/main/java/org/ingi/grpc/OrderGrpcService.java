package org.ingi.grpc;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import org.ingi.proto.*;
import org.ingi.repository.OrderRepository;
import org.ingi.repository.converters.OrderMessageConverter;

import javax.inject.Inject;

@GrpcService
public class OrderGrpcService extends MutinyOrderGrpc.OrderImplBase{
    @Inject
    OrderRepository orderRepository;
    public Uni<CreateOrderResponseMessage> createOrder(CreateOrderRequestMessage request) {
        var order = OrderMessageConverter.toOrder(request.getOrder(), request.getCustomerId());

        var createdOrderNumber = orderRepository.createOrder(order);
        var createOrderResponse = CreateOrderResponseMessage.newBuilder()
                .setOrderNumber(String.valueOf(createdOrderNumber))
                .build();

        return Uni.createFrom().item(createOrderResponse);
    }
}
