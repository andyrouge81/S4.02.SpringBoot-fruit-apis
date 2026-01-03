package cat.itacademy.s04.t02.n02.controllers;

import cat.itacademy.s04.t02.n02.model.dto.fruit.FruitCreateRequest;
import cat.itacademy.s04.t02.n02.model.dto.fruit.FruitResponse;
import cat.itacademy.s04.t02.n02.model.dto.fruit.FruitUpdateRequest;
import cat.itacademy.s04.t02.n02.services.fruit.FruitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fruits")
public class FruitController {

    private final FruitService service;


    public FruitController(FruitService service){
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FruitResponse create(@Valid @RequestBody FruitCreateRequest request){

        return service.create(request);
    }

    @GetMapping
    public List<FruitResponse> findByProvider(@RequestParam Long providerId){
        return service.findByProviderId(providerId);
    }


}
