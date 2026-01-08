package cat.itacademy.s04.t02.n01.fruit.model.mapper;

import cat.itacademy.s04.t02.n01.fruit.model.dto.FruitCreateRequest;
import cat.itacademy.s04.t02.n01.fruit.model.dto.FruitResponse;
import cat.itacademy.s04.t02.n01.fruit.model.entity.Fruit;

public class FruitMapper {

    private FruitMapper(){}

    public static Fruit toEntity(FruitCreateRequest request){
        return new Fruit(request.getName(), request.getWeightInKilos());
    }

    public static FruitResponse toResponse(Fruit fruit){
        return new FruitResponse(fruit.getId(), fruit.getName(), fruit.getWeightInKilos());
    }

}
