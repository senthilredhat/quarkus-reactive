package org.ingi.repository.converters;

import org.ingi.repository.models.OrderStatus;
import org.ingi.proto.*;

public class OrderStatusEnumConverter {
    private OrderStatusEnumConverter() {
    }

    /**
     * Converts a grpc order status to a domain status
     *
     * @param source the grpc order status
     * @return the domain status
     */
    public static OrderStatus toOrderStatus(OrderStatusEnum source) {
        return OrderStatus.fromId(source.getNumber());
    }

    /**
     * Converts a domain order status to a grpc status
     *
     * @param source the domain order status
     * @return the grpc status
     */
    public static OrderStatusEnum toOrderStatusEnum(OrderStatus source) {
        return OrderStatusEnum.forNumber(source.getid());
    }
}
