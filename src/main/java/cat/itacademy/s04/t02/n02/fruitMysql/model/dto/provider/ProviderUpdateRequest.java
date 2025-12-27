package cat.itacademy.s04.t02.n02.fruitMysql.model.dto.provider;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProviderUpdateRequest {

    @NotBlank(message = "Name must not be Blank")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Country must not be Blank")
    @Size(max = 60)
    private String country;

}
