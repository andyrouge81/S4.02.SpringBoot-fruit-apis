package cat.itacademy.s04.t02.n02.services.fruit;

import cat.itacademy.s04.t02.n02.exception.FruitNotFoundException;
import cat.itacademy.s04.t02.n02.exception.ResourceNotFoundException;
import cat.itacademy.s04.t02.n02.model.dto.fruit.FruitCreateRequest;
import cat.itacademy.s04.t02.n02.model.dto.fruit.FruitResponse;
import cat.itacademy.s04.t02.n02.model.dto.fruit.FruitUpdateRequest;
import cat.itacademy.s04.t02.n02.model.entity.Fruit;
import cat.itacademy.s04.t02.n02.model.entity.Provider;
import cat.itacademy.s04.t02.n02.model.mapper.FruitMapper;
import cat.itacademy.s04.t02.n02.repositories.FruitRepository;
import cat.itacademy.s04.t02.n02.repositories.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FruitServiceImpl implements FruitService{

        private final FruitRepository fruitRepo;
        private final ProviderRepository providerRepo;

        public FruitServiceImpl(FruitRepository fruitRepo, ProviderRepository providerRepo){

            this.fruitRepo = fruitRepo;
            this.providerRepo = providerRepo;
        }

        @Override
        public FruitResponse create(FruitCreateRequest request){

            Provider provider = providerRepo.findById(request.getProviderId())
                    .orElseThrow(()-> new ResourceNotFoundException("Provider not found"));

            Fruit fruit = new Fruit(
                    request.getName(),
                    request.getWeightInKilos(),
                    provider
            );


            return FruitMapper.toResponse(fruitRepo.save(fruit));
        }


        @Override
        public List<FruitResponse> findAll(){
            return fruitRepo.findAll().stream()
                    .map(FruitMapper::toResponse)
                    .toList();

        }

        @Override
        public FruitResponse findById(Long id){
            Fruit fruit = fruitRepo.findById(id)
                    .orElseThrow(()->new FruitNotFoundException(id));

            return FruitMapper.toResponse(fruit);
        }

        @Override
        public List<FruitResponse> findByProviderId(Long providerId){

            if(!providerRepo.existsById(providerId)){
                throw new ResourceNotFoundException("Provider not found");
            }

            return FruitMapper.toResponseList(fruitRepo.findByProviderId(providerId));
        }

        @Override
        public FruitResponse update(Long id, FruitUpdateRequest request){

            Fruit fruit = fruitRepo.findById(id)
                    .orElseThrow(()-> new FruitNotFoundException(id));
            fruit.setName(request.getName());
            fruit.setWeightInKilos(request.getWeightInKilos());

            Fruit updatedFruit = fruitRepo.save(fruit);

            return FruitMapper.toResponse(updatedFruit);
        }

        @Override
        public void deleteById(Long id){

            Fruit fruit = fruitRepo.findById(id)
                    .orElseThrow(()-> new FruitNotFoundException(id));

            fruitRepo.delete(fruit);
        }



}
