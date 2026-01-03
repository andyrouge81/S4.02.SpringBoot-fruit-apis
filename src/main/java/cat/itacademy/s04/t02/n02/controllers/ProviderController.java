package cat.itacademy.s04.t02.n02.provider.controllers;

import cat.itacademy.s04.t02.n02.provider.model.dto.provider.ProviderCreateRequest;
import cat.itacademy.s04.t02.n02.provider.model.dto.provider.ProviderResponse;
import cat.itacademy.s04.t02.n02.provider.model.dto.provider.ProviderUpdateRequest;
import cat.itacademy.s04.t02.n02.provider.services.provider.ProviderService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/providers")
public class ProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService){
        this.providerService = providerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProviderResponse create(@Valid @RequestBody ProviderCreateRequest request){

        return providerService.create(request);
    }

    @GetMapping
    public List<ProviderResponse> getProviders(){

        return providerService.findAll();

    }

    @PutMapping("/{id}")
    public ProviderResponse updateProvider(@PathVariable Long id, @Valid @RequestBody  ProviderUpdateRequest request){
        return providerService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProvider(@PathVariable Long id){
        providerService.delete(id);
    }

}
