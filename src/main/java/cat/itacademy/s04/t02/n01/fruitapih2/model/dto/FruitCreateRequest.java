package cat.itacademy.s04.t02.n01.fruitapih2.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FruitCreateRequest {

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotNull(message = "Weight must not be null")
    @Positive(message = "Weight must be positive")
    private Integer weightInKilos;



}
