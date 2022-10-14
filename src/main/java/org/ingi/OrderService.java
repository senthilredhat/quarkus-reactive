package org.ingi;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import org.ingi.repository.OrderRepository;
import org.ingi.repository.models.Order;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;

@ApplicationScoped
public class OrderService {
    OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }

    @ReactiveTransactional
    public Uni<Order> create(Order order){
        System.out.println("service" + order.getOrderNumber());
        return this.orderRepository.persist(order)
                .onFailure(PersistenceException.class)
                .recoverWithItem(ex -> {
                    Log.error(ex.getMessage(), ex);
                    return null;
                });
    }

    /**
     * Gets the next sequential order number for a customer
     *
     * @param customerId the customer id
     * @return the next sequential order id
     */
    private String getNextOrderNumber(String customerId) {
        var currentOrderNumberString = orderRepository.getLastOrderNumber(customerId);

        var currentOrderNumber =
                currentOrderNumberString == null
                        ? 0
                        : Integer.parseInt(currentOrderNumberString);

        var nextOrderNumber = currentOrderNumber + 1;
        return padLeft(Integer.toString(nextOrderNumber), 10, '0');
    }
    private String padLeft(String value, int length, char padChar) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(padChar);
        }

        return sb.substring(value.length()) + value;
    }

}
