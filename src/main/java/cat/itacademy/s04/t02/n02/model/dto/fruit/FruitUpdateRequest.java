package cat.itacademy.s04.t02.n02.provider.model.dto.fruit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FruitUpdateRequest {

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotNull(message = "Weight must not be null")
    @Positive(message = "Weight must be positive")
    private int weightInKilos;

}
