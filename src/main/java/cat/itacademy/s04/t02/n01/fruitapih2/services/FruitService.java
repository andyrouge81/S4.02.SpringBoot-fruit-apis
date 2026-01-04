package cat.itacademy.s04.t02.n01.fruitapih2.services;

import cat.itacademy.s04.t02.n01.fruitapih2.model.dto.FruitCreateRequest;
import cat.itacademy.s04.t02.n01.fruitapih2.model.dto.FruitResponse;
import cat.itacademy.s04.t02.n01.fruitapih2.model.dto.FruitUpdateRequest;

import java.util.List;

public interface FruitService {

    FruitResponse create(FruitCreateRequest request);
    List<FruitResponse> findAll();
    FruitResponse findById(Long id);
    FruitResponse update(Long id, FruitUpdateRequest request);
    void deleteById(Long id);
}
