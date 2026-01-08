package cat.itacademy.s04.t02.n02.fruit.services.provider;

import cat.itacademy.s04.t02.n02.fruit.model.dto.provider.ProviderCreateRequest;
import cat.itacademy.s04.t02.n02.fruit.model.dto.provider.ProviderResponse;
import cat.itacademy.s04.t02.n02.fruit.model.dto.provider.ProviderUpdateRequest;

import java.util.List;


public interface ProviderService {

    ProviderResponse create(ProviderCreateRequest request);
    List<ProviderResponse> findAll();
    ProviderResponse update(Long id, ProviderUpdateRequest request);
    void delete(Long id);

}
