package org.ingi.repository.converters;

import org.ingi.repository.models.Order;
import org.ingi.proto.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

/**
 * Converter for orders grpc models -from GRPC to domain model
 */
public class OrderMessageConverter {

    private OrderMessageConverter() {
    }

    /**
     * Converts a order domain model to a grpc CreateOrderResponse
     *
     * @param source the order domain model
     * @return the CreateOrderResponse grpc model
     */
    public static CreateOrderResponseMessage toCreateOrderResponseMessage(Order source) {
        if (source == null) {
            return null;
        }
        var orderMessageBuilder = CreateOrderResponseMessage.newBuilder()
                .setOrderNumber(source.getOrderNumber());
        return orderMessageBuilder.build();
    }

    /**
     * Converts a OrderRequestMessage grpc model to a order domain model
     *
     * @param source the CreateOrderRequest grpc model
     * @return the order domains model
     */
    public static Order toOrder(OrderRequestMessage source, String customerId) {
        if (source == null) {
            return null;
        }

        return new Order()
                .setCustomer(source.getCustomer())
                .setCreationTime(OffsetDateTime.now(ZoneOffset.UTC))
                .setCost(source.getCost())
                .setStatus(source.getStatus().getNumber())
                .setCustomerId(customerId)
                .setOrderItems(
                        source.getItemsList().stream()
                                .map(OrderItemMessageConverter::toOrderItem)
                                .collect(Collectors.toList())
                );
    }

    /**
     * Converts a order model to a Order grpc model
     *
     * @param source the order model
     * @return the order grpc message
     */
    public static OrderResponseMessage toOrderResponseMessage(Order source) {
        if (source == null) {
            return null;
        }

        var orderMessageBuilder = OrderResponseMessage.newBuilder()
                .setCustomer(source.getCustomer());
//                .setCost(source.getCost())
//                .setOrderNumber(source.getOrderNumber())
//                .setStatus(OrderStatusEnumConverter.toOrderStatusEnum(source.getStatus()))
//                .setCreationTime(convertToTimeStamp(source.getCreationTime()));

//        source.getItems().stream()
//                .map(OrderItemMessageConverter::toOrderItemMessage)
//                .collect(Collectors.toList())
//                .forEach(orderMessageBuilder::addItems);

        return orderMessageBuilder.build();
    }
}
