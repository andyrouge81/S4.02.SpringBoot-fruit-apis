package cat.itacademy.s04.t02.n02.model.dto.fruit;


public record FruitResponse (

    Long id,
    String name,
    int weightInKilos,
    Long providerId

) {}
