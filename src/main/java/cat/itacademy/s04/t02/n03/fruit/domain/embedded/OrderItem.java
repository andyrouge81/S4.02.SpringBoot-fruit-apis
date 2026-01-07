package cat.itacademy.s04.t02.n03.fruit.domain.embedded;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    private String fruitName;

    private Integer quantityInKilos;

    public OrderItem(String fruitName, Integer quantityInKilos){
        this.fruitName = fruitName;
        this.quantityInKilos = quantityInKilos;
    }


}
