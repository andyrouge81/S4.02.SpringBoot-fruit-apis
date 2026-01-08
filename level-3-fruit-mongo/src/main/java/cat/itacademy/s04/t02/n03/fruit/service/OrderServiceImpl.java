package cat.itacademy.s04.t02.n03.fruit.service;

import cat.itacademy.s04.t02.n03.fruit.domain.document.Order;
import cat.itacademy.s04.t02.n03.fruit.domain.embedded.OrderItem;
import cat.itacademy.s04.t02.n03.fruit.dto.request.OrderCreateRequest;
import cat.itacademy.s04.t02.n03.fruit.dto.request.OrderUpdateRequest;
import cat.itacademy.s04.t02.n03.fruit.dto.response.OrderResponse;
import cat.itacademy.s04.t02.n03.fruit.exception.BadRequestException;
import cat.itacademy.s04.t02.n03.fruit.exception.ResourceNotFoundException;
import cat.itacademy.s04.t02.n03.fruit.mapper.OrderMapper;
import cat.itacademy.s04.t02.n03.fruit.repository.OrderRepository;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository){
        this.repository = repository;
    }

    @Override
    public OrderResponse create(OrderCreateRequest request){

        if(request.getDeliveryDate().isBefore(LocalDate.now().plusDays(1))){
            throw new BadRequestException("Invalid date");

        }

        Order order = OrderMapper.toDocument(request);

        Order saved = repository.save(order);

        return OrderMapper.toResponse(saved);
    }

    @Override
    public List<OrderResponse> findAllOrders(){
        return repository.findAll().stream()
                .map(OrderMapper::toResponse)
                .toList();
    }

    @Override
    public OrderResponse findById(String id){

        Order order = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Order not found"));

        return OrderMapper.toResponse(order);
    }

    @Override
    public OrderResponse update(String id, OrderUpdateRequest request){

    Order order = repository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Order not found"));

    if(request.getDeliveryDate().isBefore(LocalDate.now().plusDays(1))){
        throw new BadRequestException("Invalid date");
        }

    order.setClientName(request.getClientName());
    order.setDeliveryDate(request.getDeliveryDate());

    List<OrderItem> items = request.getItems().stream()
            .map(i-> new OrderItem(i.getFruitName(), i.getQuantityInKilos()))
            .toList();

    order.setItems(items);

    Order updated = repository.save(order);

    return OrderMapper.toResponse(updated);

    }

    @Override
    public void deleteById(String id){

        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Order not found");
        }

        repository.deleteById(id);

    }


}
