package cat.itacademy.s04.t02.n01.fruitapih2.services;

import cat.itacademy.s04.t02.n01.fruitapih2.exception.FruitNotFoundException;
import cat.itacademy.s04.t02.n01.fruitapih2.model.dto.FruitCreateRequest;
import cat.itacademy.s04.t02.n01.fruitapih2.model.dto.FruitResponse;
import cat.itacademy.s04.t02.n01.fruitapih2.model.dto.FruitUpdateRequest;
import cat.itacademy.s04.t02.n01.fruitapih2.model.entity.Fruit;
import cat.itacademy.s04.t02.n01.fruitapih2.model.mapper.FruitMapper;
import cat.itacademy.s04.t02.n01.fruitapih2.repositories.FruitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FruitServiceImpl implements FruitService{

        private final FruitRepository repository;

        public FruitServiceImpl(FruitRepository repository){

            this.repository = repository;
        }

        @Override
        public FruitResponse create(FruitCreateRequest request){
            Fruit fruit = FruitMapper.toEntity(request);
            Fruit saved = repository.save(fruit);

            return FruitMapper.toResponse(saved);
        }


        @Override
        public List<FruitResponse> findAll(){
            return repository.findAll().stream()
                    .map(FruitMapper::toResponse)
                    .toList();

        }

        @Override
        public FruitResponse findById(Long id){
            Fruit fruit = repository.findById(id)
                    .orElseThrow(()->new FruitNotFoundException(id));

            return FruitMapper.toResponse(fruit);
        }

        @Override
        public FruitResponse update(Long id, FruitUpdateRequest request){

            Fruit fruit = repository.findById(id)
                    .orElseThrow(()-> new FruitNotFoundException(id));
            fruit.setName(request.getName());
            fruit.setWeightInKilos(request.getWeightInKilos());

            Fruit updatedFruit = repository.save(fruit);

            return FruitMapper.toResponse(updatedFruit);
        }

        @Override
        public void deleteById(Long id){

            Fruit fruit = repository.findById(id)
                    .orElseThrow(()-> new FruitNotFoundException(id));

            repository.delete(fruit);
        }



}
