package cat.itacademy.s04.t02.n02.fruit.controllers;

import cat.itacademy.s04.t02.n02.fruit.model.dto.fruit.FruitCreateRequest;
import cat.itacademy.s04.t02.n02.fruit.model.dto.fruit.FruitResponse;
import cat.itacademy.s04.t02.n02.fruit.model.dto.fruit.FruitUpdateRequest;
import cat.itacademy.s04.t02.n02.fruit.services.fruit.FruitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public List<FruitResponse> findAll(@RequestParam(required = false) Long providerId){

        if(providerId != null){
            return service.findByProviderId(providerId);
        }

        return service.findAll();
    }


    @GetMapping("/{id}")
    public FruitResponse findById(@PathVariable Long id){
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public FruitResponse update(@PathVariable Long id,
                                @Valid @RequestBody FruitUpdateRequest request){

        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        service.deleteById(id);
    }


}
