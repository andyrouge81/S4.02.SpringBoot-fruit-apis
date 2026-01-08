package cat.itacademy.s04.t02.n03.fruit;

import cat.itacademy.s04.t02.n03.fruit.domain.document.Order;
import cat.itacademy.s04.t02.n03.fruit.domain.embedded.OrderItem;
import cat.itacademy.s04.t02.n03.fruit.dto.request.OrderCreateRequest;
import cat.itacademy.s04.t02.n03.fruit.dto.request.OrderItemRequest;
import cat.itacademy.s04.t02.n03.fruit.dto.request.OrderUpdateRequest;
import cat.itacademy.s04.t02.n03.fruit.dto.response.OrderItemResponse;
import cat.itacademy.s04.t02.n03.fruit.dto.response.OrderResponse;
import cat.itacademy.s04.t02.n03.fruit.exception.BadRequestException;
import cat.itacademy.s04.t02.n03.fruit.exception.ResourceNotFoundException;
import cat.itacademy.s04.t02.n03.fruit.repository.OrderRepository;
import cat.itacademy.s04.t02.n03.fruit.service.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void createOrder_whenValid_returnOrderResponse(){

        OrderItemRequest itemRequest = new OrderItemRequest();
        itemRequest.setFruitName("Apple");
        itemRequest.setQuantityInKilos(5);

        OrderCreateRequest request = new OrderCreateRequest();
        request.setClientName("Carlos Martin");
        request.setDeliveryDate(LocalDate.now().plusDays(1));
        request.setItems(List.of(itemRequest));

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation->{
                    Order order = invocation.getArgument(0);
                    order.setId("123abc");
                    return order;
                });

        OrderResponse response = orderService.create(request);

        assertNotNull(response);
        assertEquals("123abc", response.id());
        assertEquals("Apple", response.items().get(0).fruitName());
        assertEquals(5, response.items().get(0).quantityInKilos());
        assertEquals("Carlos Martin", response.clientName());
        assertEquals(1,response.items().size());

        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void createOrder_whenDateIsInvalid_return400BadRequest(){

        OrderItemRequest itemRequest = new OrderItemRequest();
        itemRequest.setFruitName("Apple");
        itemRequest.setQuantityInKilos(5);

        OrderCreateRequest request = new OrderCreateRequest();
        request.setClientName("Carlos Martin");
        request.setDeliveryDate(LocalDate.now());
        request.setItems(List.of(itemRequest));

        assertThrows(BadRequestException.class, ()->orderService.create(request));

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void findAllOrders_whenOrderExists_return200AndAllOrders(){

        OrderItem orderItem = new OrderItem("Orange", 5);

        Order order = new Order("Ramon Cocinas",LocalDate.now().plusDays(1), List.of(orderItem));
        order.setId("abc123");

        when(orderRepository.findAll()).thenReturn(List.of(order));

        List<OrderResponse> result = orderService.findAllOrders();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(5, result.get(0).items().get(0).quantityInKilos());
        assertEquals("Orange", result.get(0).items().get(0).fruitName());
        assertEquals("abc123", result.get(0).id());
        assertEquals("Ramon Cocinas", result.get(0).clientName());

        verify(orderRepository).findAll();


    }

    @Test
    void findAllOrders_whenNoOrder_returnEmptyList(){

        when(orderRepository.findAll()).thenReturn(List.of());

        List<OrderResponse> result = orderService.findAllOrders();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(orderRepository).findAll();

    }

    @Test
    void findById_whenOrderIdExists_return200AndOrder(){

        OrderItem orderItem = new OrderItem("Apple", 7);

        Order order = new Order("Pedro Marmol", LocalDate.now().plusDays(1),List.of(orderItem));
        order.setId("456XYZ");

        when(orderRepository.findById("456XYZ")).thenReturn(Optional.of(order));

        OrderResponse response = orderService.findById("456XYZ");

        assertNotNull(response);
        assertEquals(1, response.items().size());
        assertEquals("456XYZ", response.id());
        assertEquals("Pedro Marmol", response.clientName());
        assertEquals("Apple", response.items().get(0).fruitName());
        assertEquals(7, response.items().get(0).quantityInKilos());

        verify(orderRepository).findById("456XYZ");
    }

    @Test
    void findById_whenOrderNotExists_throw404NotFound(){

        when(orderRepository.findById("99L")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class , ()-> orderService.findById("99L"));

        verify(orderRepository).findById("99L");


    }

    @Test
    void updateOrder_whenIsValid_return200AndUpdate(){

        OrderItem orderItem = new OrderItem("Banana", 9);

        Order order = new Order("Paco Soria",LocalDate.now().plusDays(1), List.of(orderItem));
        order.setId("abc456");

        OrderItemRequest itemRequest = new OrderItemRequest();
        itemRequest.setQuantityInKilos(6);
        itemRequest.setFruitName("Mango");

        OrderUpdateRequest updateRequest = new OrderUpdateRequest();
        updateRequest.setClientName("Ramon Perez");
        updateRequest.setDeliveryDate(LocalDate.now().plusDays(2));
        updateRequest.setItems(List.of(itemRequest));

        when(orderRepository.findById("abc456")).thenReturn(Optional.of(order));

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation->invocation.getArgument(0));

        OrderResponse response = orderService.update("abc456", updateRequest);

        assertEquals(6, response.items().get(0).quantityInKilos());
        assertEquals("Ramon Perez", response.clientName());
        assertEquals("abc456", response.id());
        assertEquals("Mango", response.items().get(0).fruitName());

        verify(orderRepository).save(any(Order.class));
        verify(orderRepository).findById("abc456");


    }

    @Test
    void updateOrder_whenIdNotExists_throw404NotFound(){

        OrderUpdateRequest request = new OrderUpdateRequest();
        request.setClientName("Ramon Mendez");
        request.setDeliveryDate(LocalDate.now().plusDays(2));
        request.setItems(List.of());

        when(orderRepository.findById("abc123"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, ()->orderService.update("abc123", request));

        verify(orderRepository, never()).save(any());
        verify(orderRepository).findById("abc123");


    }

    @Test
    void updateOrder_whenDateIsInvalid_throws400BadRequest(){

        Order order = new Order(
                "Pedro Ramirez",
                LocalDate.now().plusDays(2),
                List.of(new OrderItem("Mango", 5)));
        order.setId("456XYZ");

        OrderUpdateRequest updateRequest = new OrderUpdateRequest();
        updateRequest.setClientName("Manolo Civantos");
        updateRequest.setDeliveryDate(LocalDate.now());
        updateRequest.setItems(List.of(new OrderItemRequest(){{
            setFruitName("Banana");
            setQuantityInKilos(3);
        }}));

        when(orderRepository.findById("456XYZ")).thenReturn(Optional.of(order));

        assertThrows(BadRequestException.class, ()->orderService.update("456XYZ", updateRequest));

        verify(orderRepository,never()).save(any());

    }

    @Test
    void deleteOrder_whenIdExist_deleteOrderAndReturn204NoContent(){

        when(orderRepository.existsById("abc123")).thenReturn(true);

        orderService.deleteById("abc123");

        verify(orderRepository).deleteById("abc123");
        verify(orderRepository).existsById("abc123");


    }

    @Test
    void deleteOrder_whenNoExists_throw404NotFound(){

        when(orderRepository.existsById("abc123")).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, ()->orderService.deleteById("abc123"));

        verify(orderRepository).existsById("abc123");
        verify(orderRepository, never()).deleteById(any());

    }

}
