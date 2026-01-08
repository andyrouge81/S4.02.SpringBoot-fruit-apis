package cat.itacademy.s04.t02.n02.fruit.model.dto.provider;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProviderCreateRequest {

    @NotBlank(message = "Provider name must not be blank")
    @Size(max = 100)
    private String name;


    @NotBlank(message = "Country must not be blank")
    @Size(max = 60)
    private String country;


}
