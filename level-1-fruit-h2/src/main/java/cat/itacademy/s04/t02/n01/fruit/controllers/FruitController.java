package cat.itacademy.s04.t02.n01.fruit.controllers;

import cat.itacademy.s04.t02.n01.fruit.model.dto.FruitCreateRequest;
import cat.itacademy.s04.t02.n01.fruit.model.dto.FruitResponse;
import cat.itacademy.s04.t02.n01.fruit.model.dto.FruitUpdateRequest;
import cat.itacademy.s04.t02.n01.fruit.services.FruitService;
import jakarta.validation.Valid;
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
    public ResponseEntity<FruitResponse> create(@Valid @RequestBody FruitCreateRequest request){
        FruitResponse created = service.create(request);
        return ResponseEntity
                .created(URI.create("/fruits/" + created.id()))
                .body(created);
    }

    @GetMapping
    public ResponseEntity<List<FruitResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
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
