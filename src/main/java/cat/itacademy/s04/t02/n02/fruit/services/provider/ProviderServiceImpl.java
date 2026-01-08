package cat.itacademy.s04.t02.n02.fruit.services.provider;


import cat.itacademy.s04.t02.n02.fruit.exception.ConflictException;
import cat.itacademy.s04.t02.n02.fruit.exception.DuplicateRequestException;
import cat.itacademy.s04.t02.n02.fruit.exception.ResourceNotFoundException;
import cat.itacademy.s04.t02.n02.fruit.model.dto.provider.ProviderCreateRequest;
import cat.itacademy.s04.t02.n02.fruit.model.dto.provider.ProviderResponse;
import cat.itacademy.s04.t02.n02.fruit.model.dto.provider.ProviderUpdateRequest;
import cat.itacademy.s04.t02.n02.fruit.model.entity.Provider;
import cat.itacademy.s04.t02.n02.fruit.model.mapper.ProviderMapper;
import cat.itacademy.s04.t02.n02.fruit.repositories.FruitRepository;
import cat.itacademy.s04.t02.n02.fruit.repositories.ProviderRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService{

    private final ProviderRepository providerRepo;
    private final FruitRepository fruitRepo;

    public ProviderServiceImpl(ProviderRepository providerRepo, FruitRepository fruitRepo){
        this.providerRepo = providerRepo;
        this.fruitRepo = fruitRepo;
    }

    @Override
    public ProviderResponse create(ProviderCreateRequest request){

        if(providerRepo.existsByName(request.getName())){
            throw new DuplicateRequestException("Provider already exists");
        }

        Provider provider = new Provider(
        request.getName(),
        request.getCountry());

        Provider saved = providerRepo.save(provider);

        return ProviderMapper.toResponse(saved);
    }

    @Override
    public List<ProviderResponse> findAll(){
        return ProviderMapper.toResponseList(providerRepo.findAll());

    }



    @Override
    public ProviderResponse update(Long id, ProviderUpdateRequest request){

        Provider provider = providerRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Provider not found"));

        if(providerRepo.existsByNameAndIdNot(request.getName(), id)){
            throw new DuplicateRequestException("Provider name already exists");

        }

        provider.setName(request.getName());
        provider.setCountry(request.getCountry());

        Provider saved = providerRepo.save(provider);

        return ProviderMapper.toResponse(saved);
    }

    @Override
    public void delete(Long id){

        if(!providerRepo.existsById(id)){
            throw new ResourceNotFoundException("Provider not found");
        }

        if(fruitRepo.existsByProviderId(id)){
            throw new ConflictException("Provider has associate fruits and cannot be deleted");
        }

        providerRepo.deleteById(id);

    }
}
