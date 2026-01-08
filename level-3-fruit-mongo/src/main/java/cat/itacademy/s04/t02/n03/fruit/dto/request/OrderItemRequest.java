package cat.itacademy.s04.t02.n03.fruit.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OrderItemRequest {
    @NotBlank(message = "Fruit name must not be blank")
    private String fruitName;

    @NotNull(message = "Quantity must not be null")
    @Positive(message = "Quantity must not be negative")
    private Integer quantityInKilos;
}
