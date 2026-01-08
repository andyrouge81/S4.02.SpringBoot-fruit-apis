package cat.itacademy.s04.t02.n03.fruit.dto.request;

import cat.itacademy.s04.t02.n03.fruit.domain.embedded.OrderItem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderCreateRequest {

    @NotBlank
    private String clientName;

    @NotNull
    private LocalDate deliveryDate;

    @NotEmpty
    @Valid
    private List<OrderItemRequest> items;


}
