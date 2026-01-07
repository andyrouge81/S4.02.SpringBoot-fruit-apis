package cat.itacademy.s04.t02.n03.fruit.service;

import cat.itacademy.s04.t02.n03.fruit.dto.request.OrderCreateRequest;
import cat.itacademy.s04.t02.n03.fruit.dto.request.OrderUpdateRequest;
import cat.itacademy.s04.t02.n03.fruit.dto.response.OrderResponse;


import java.util.List;

public interface OrderService {

    OrderResponse create(OrderCreateRequest request);
    List<OrderResponse> findAllOrders();
    OrderResponse findById(String id);
    OrderResponse update(String id, OrderUpdateRequest request);
    void deleteById(String id);

}


