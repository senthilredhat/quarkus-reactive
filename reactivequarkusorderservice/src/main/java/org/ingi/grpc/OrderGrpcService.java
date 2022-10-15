package org.ingi.grpc;
import io.quarkus.grpc.GrpcService;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import org.ingi.OrderService;
import org.ingi.proto.*;

import org.ingi.repository.OrderRepository;
import org.ingi.repository.converters.OrderMessageConverter;

import javax.inject.Inject;

@GrpcService
public class OrderGrpcService extends MutinyOrderGrpc.OrderImplBase{
    OrderService orderService;
    public OrderGrpcService(OrderService orderService){
        this.orderService=orderService;
    }
    public Uni<CreateOrderResponseMessage> createOrder(CreateOrderRequestMessage request) {
        Log.info(Thread.currentThread().getName());
        return this.orderService.create(OrderMessageConverter.toOrder(request.getOrder(), request.getCustomerId()))
                .map(o -> {
                    Log.info(Thread.currentThread().getName());
                    var createdOrder = CreateOrderResponseMessage
                            .newBuilder()
                            .setOrderNumber("thanks for your order "+ o.getCustomer()+ " " +o.getOrderNumber()).build();
                    return createdOrder;
                });

    }
}
