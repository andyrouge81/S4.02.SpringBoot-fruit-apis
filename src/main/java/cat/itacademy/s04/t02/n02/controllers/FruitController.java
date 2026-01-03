package cat.itacademy.s04.t02.n02.provider.controllers;

import cat.itacademy.s04.t02.n02.provider.model.dto.fruit.FruitCreateRequest;
import cat.itacademy.s04.t02.n02.provider.model.dto.fruit.FruitResponse;
import cat.itacademy.s04.t02.n02.provider.model.dto.fruit.FruitUpdateRequest;
import cat.itacademy.s04.t02.n02.provider.services.fruit.FruitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    @GetMapping("/{id}")
    public ResponseEntity<FruitResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FruitResponse> updateFruit(@PathVariable Long id,
                                                     @Valid @RequestBody FruitUpdateRequest request){
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deleteById(id);

        return ResponseEntity.noContent().build();

    }


}
