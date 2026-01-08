package cat.itacademy.s04.t02.n01.fruit.services;

import cat.itacademy.s04.t02.n01.fruit.model.dto.FruitCreateRequest;
import cat.itacademy.s04.t02.n01.fruit.model.dto.FruitResponse;
import cat.itacademy.s04.t02.n01.fruit.model.dto.FruitUpdateRequest;

import java.util.List;

public interface FruitService {

    FruitResponse create(FruitCreateRequest request);
    List<FruitResponse> findAll();
    FruitResponse findById(Long id);
    FruitResponse update(Long id, FruitUpdateRequest request);
    void deleteById(Long id);
}
