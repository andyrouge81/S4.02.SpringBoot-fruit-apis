package cat.itacademy.s04.t02.n02.services.fruit;

import cat.itacademy.s04.t02.n02.model.dto.fruit.FruitCreateRequest;
import cat.itacademy.s04.t02.n02.model.dto.fruit.FruitResponse;
import cat.itacademy.s04.t02.n02.model.dto.fruit.FruitUpdateRequest;

import java.util.List;

public interface FruitService {

    FruitResponse create(FruitCreateRequest request);
    List<FruitResponse> findAll();
    FruitResponse findById(Long id);
    FruitResponse update(Long id, FruitUpdateRequest request);
    void deleteById(Long id);

    List<FruitResponse> findByProviderId(Long providerId);
}
