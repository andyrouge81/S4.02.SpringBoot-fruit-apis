package cat.itacademy.s04.t02.n03.fruit.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequestDto {
    @NotBlank
    private String fruitName;

    @NotNull
    @Positive
    private Integer quantityInKilos;
}
