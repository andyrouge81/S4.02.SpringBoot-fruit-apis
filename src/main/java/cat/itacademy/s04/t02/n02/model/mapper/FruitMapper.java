package cat.itacademy.s04.t02.n02.provider.model.mapper;

import cat.itacademy.s04.t02.n02.provider.model.dto.fruit.FruitCreateRequest;
import cat.itacademy.s04.t02.n02.provider.model.dto.fruit.FruitResponse;
import cat.itacademy.s04.t02.n02.provider.model.entity.Fruit;

import java.util.List;

public final class FruitMapper {

    private FruitMapper(){}

    public static FruitResponse toResponse(Fruit fruit){
        return new FruitResponse(
                fruit.getId(),
                fruit.getName(),
                fruit.getWeightInKilos(),
                fruit.getProvider().getId()
        );

    }

    public static List<FruitResponse> toResponseList(List<Fruit> fruits){
        return fruits.stream()
                .map(FruitMapper::toResponse)
                .toList();
    }

}
