package cat.itacademy.s04.t02.n03.fruit;


import cat.itacademy.s04.t02.n03.fruit.controller.OrderController;
import cat.itacademy.s04.t02.n03.fruit.dto.request.OrderCreateRequest;
import cat.itacademy.s04.t02.n03.fruit.dto.request.OrderUpdateRequest;
import cat.itacademy.s04.t02.n03.fruit.dto.response.OrderItemResponse;
import cat.itacademy.s04.t02.n03.fruit.dto.response.OrderResponse;
import cat.itacademy.s04.t02.n03.fruit.exception.BadRequestException;
import cat.itacademy.s04.t02.n03.fruit.exception.GlobalExceptionHandler;
import cat.itacademy.s04.t02.n03.fruit.exception.ResourceNotFoundException;
import cat.itacademy.s04.t02.n03.fruit.service.OrderService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
@Import(GlobalExceptionHandler.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    //POST

    @Test
    void createOrder_whenIsValid_returnHTTP201AndOrder() throws Exception{

        OrderResponse response = new OrderResponse(
                "123bca",
                "Paco Mer",
                LocalDate.now().plusDays(2),
                List.of(new OrderItemResponse("Mango", 7)));

        when(orderService.create(any(OrderCreateRequest.class))).thenReturn(response);

        String body = """
                {
                "clientName" : "Paco Mer",
                "deliveryDate" : "%s",
                "items" : [
                    {
                    "fruitName" : "Mango",
                    "quantityInKilos" : 7
                    }
                    ]
                }
                """.formatted(LocalDate.now().plusDays(2));

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clientName").value("Paco Mer"))
                .andExpect(jsonPath("$.items[0].fruitName").value("Mango"))
                .andExpect(jsonPath("$.items[0].quantityInKilos").value(7))
                .andExpect(jsonPath("$.id").value("123bca"));

    }

    @Test
    void createOrder_whenInvalidRequest_returnHTTP400() throws Exception{


        String body = """
                {
                "clientName" : "Paco Mer",
                "deliveryDate" : "%s",
                "items" : [ ]
                }
                """.formatted(LocalDate.now());

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest());


    }

    //GET

    @Test
    void findAllOrders_whenOrdersExist_returnHTTP200AndList() throws Exception{

        OrderResponse response = new OrderResponse(
                "123ABC",
                "Juan Penas",
                LocalDate.now().plusDays(1),
                List.of(new OrderItemResponse("Apple", 5)));

        when(orderService.findAllOrders()).thenReturn(List.of(response));



        mockMvc.perform(get("/orders"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value("123ABC"))
                .andExpect(jsonPath("$[0].items[0].fruitName").value("Apple"))
                .andExpect(jsonPath("$[0].items[0].quantityInKilos").value(5));


    }

    @Test
    void findAllOrders_whenNoOrdersExist_returnEmptyList() throws Exception{

        when(orderService.findAllOrders()).thenReturn(List.of());

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

    }

    @Test
    void findById_whenExists_returnHTTP200AndOrder() throws Exception{

        OrderResponse response = new OrderResponse(
                "abc123",
                "Jose Moto",LocalDate.now().plusDays(2),
                List.of(new OrderItemResponse("Banana", 5),
                        new OrderItemResponse("Avocado", 3)));

        when(orderService.findById("abc123")).thenReturn(response);

        mockMvc.perform(get("/orders/{id}", "abc123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("abc123"))
                .andExpect(jsonPath("$.clientName").value("Jose Moto"))
                .andExpect(jsonPath("$.items[0].fruitName").value("Banana"))
                .andExpect(jsonPath("$.items[1].quantityInKilos").value(3));

    }

    @Test
    void findById_whenNoExists_returnHTTP404NotFound() throws Exception{

        when(orderService.findById("345bca")).thenThrow(new ResourceNotFoundException("Order not found"));

        mockMvc.perform(get("/orders/{id}", "345bca"))
                .andExpect(status().isNotFound());
    }

    @Test
    void update_whenExists_returnHTTP200AndOrder() throws Exception{

        OrderResponse response = new OrderResponse(
                "abc123",
                "Juan Tamayo",
                LocalDate.now().plusDays(1),
                List.of(new OrderItemResponse("Mango", 5))
        );

        when(orderService.update(eq("abc123"),any(OrderUpdateRequest.class))).thenReturn(response);

        String body = """
                {
                "clientName" : "Juan Tamayo",
                "deliveryDate" : "%s",
                "items" : [
                    {
                    "fruitName" : "Mango",
                    "quantityInKilos" : 5
                    }
                  ]
                }
                
                """.formatted(LocalDate.now().plusDays(1));

        mockMvc.perform(put("/orders/{id}", "abc123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))

                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.clientName").value("Juan Tamayo"))
                .andExpect(jsonPath("$.items[0].fruitName").value("Mango"))
                .andExpect(jsonPath("$.items[0].quantityInKilos").value(5));

    }

    @Test
    void update_whenNotExists_throws404NotFoundException() throws Exception{

        when(orderService.update(eq("123abc"), any(OrderUpdateRequest.class)))
                .thenThrow(new ResourceNotFoundException("Order not found"));

        String body = """
                {
                "clientName" : "Juan Tamayo",
                "deliveryDate" : "%s",
                "items" : [
                    {
                    "fruitName" : "Mango",
                    "quantityInKilos" : 5
                    }
                  ]
                }
                
                """.formatted(LocalDate.now().plusDays(1));

        mockMvc.perform(put("/orders/{id}", "123abc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound());

    }

    @Test
    void update_whenRequestInvalid_throwsHTTP400BadRequest() throws Exception{

        when(orderService.update(eq("123abc"), any(OrderUpdateRequest.class)))
                .thenThrow(new BadRequestException("Invalid data"));

        String body = """
                {
                "clientName" : "Juan Tamayo",
                "deliveryDate" : "%s",
                "items" : [
                    {
                    "fruitName" : "Mango",
                    "quantityInKilos" : 5
                    }
                  ]
                }
                
                """.formatted(LocalDate.now());

        mockMvc.perform(put("/orders/{id}", "123abc")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest());

    }

    @Test
    void deleteById_whenExists_deleteOrderAndReturnHTTP204NoContent() throws Exception{


        doNothing().when(orderService).deleteById("bca456");

        mockMvc.perform(delete("/orders/{id}", "bca456"))
                .andExpect(status().isNoContent());

        verify(orderService).deleteById("bca456");

    }

    @Test
    void deleteById_whenNotExists_returnHTTP404NotFound() throws Exception{

        doThrow(new ResourceNotFoundException("Order not found")).when(orderService).deleteById("abc123");

        mockMvc.perform(delete("/orders/{id}", "abc123"))
                .andExpect(status().isNotFound());


    }




}
