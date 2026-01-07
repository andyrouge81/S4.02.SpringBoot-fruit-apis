package cat.itacademy.s04.t02.n03.fruit.mapper;

import cat.itacademy.s04.t02.n03.fruit.domain.document.Order;
import cat.itacademy.s04.t02.n03.fruit.domain.embedded.OrderItem;
import cat.itacademy.s04.t02.n03.fruit.dto.request.OrderCreateRequest;
import cat.itacademy.s04.t02.n03.fruit.dto.response.OrderItemResponse;
import cat.itacademy.s04.t02.n03.fruit.dto.response.OrderResponse;

import java.util.List;

public class OrderMapper {

    private OrderMapper(){}

    public static Order toDocument(OrderCreateRequest request){

        List<OrderItem> items = request.getItems().stream()
                .map(i->new OrderItem(i.getFruitName(),i.getQuantityInKilos()))
                .toList();

        return new Order(
                request.getClientName(),
                request.getDeliveryDate(),
                items
        );
    }

    public static OrderResponse toResponse(Order order){

        List<OrderItemResponse> items = order.getItems().stream()
                .map(i->new OrderItemResponse(i.getFruitName(),i.getQuantityInKilos()))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getClientName(),
                order.getDeliveryDate(),
                items
        );

    }

    public static List<OrderResponse> toResponseList(List<Order> orders){

        return orders.stream()
                .map(OrderMapper::toResponse)
                .toList();
    }
}
