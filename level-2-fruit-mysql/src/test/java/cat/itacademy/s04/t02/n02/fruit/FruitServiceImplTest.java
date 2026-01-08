package cat.itacademy.s04.t02.n02.fruit;

import cat.itacademy.s04.t02.n02.fruit.exception.ResourceNotFoundException;
import cat.itacademy.s04.t02.n02.fruit.model.dto.fruit.FruitCreateRequest;
import cat.itacademy.s04.t02.n02.fruit.model.dto.fruit.FruitResponse;
import cat.itacademy.s04.t02.n02.fruit.model.entity.Fruit;
import cat.itacademy.s04.t02.n02.fruit.model.entity.Provider;
import cat.itacademy.s04.t02.n02.fruit.repositories.FruitRepository;
import cat.itacademy.s04.t02.n02.fruit.repositories.ProviderRepository;
import cat.itacademy.s04.t02.n02.fruit.services.fruit.FruitServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FruitServiceImplTest {

    @Mock
    private FruitRepository fruitRepo;

    @Mock
    private ProviderRepository providerRepo;

    @InjectMocks
    private FruitServiceImpl service;

    @Test
    void createFruit_whenProviderExists_createFruitAndReturnResponse(){

        Provider provider = new Provider("ProviderA", "Portugal");
        provider.setId(1L);

        FruitCreateRequest request = new FruitCreateRequest();
        request.setName("Orange");
        request.setWeightInKilos(6);
        request.setProviderId(1L);


        when(providerRepo.findById(1L)).thenReturn(Optional.of(provider));
        when(fruitRepo.save(any(Fruit.class))).thenAnswer(invocation ->{
            Fruit fruit = invocation.getArgument(0);
            fruit.setId(10L);
            return fruit;
        });

        FruitResponse response = service.create(request);

        assertEquals(10L, response.id());
        assertEquals("Orange",response.name());
        assertEquals(6,response.weightInKilos());
        assertEquals(1L,response.providerId());
    }

    @Test
    void createFruit_whenProviderNotFound_throws404NotFound(){

        FruitCreateRequest request = new FruitCreateRequest();
                request.setName("Apple");
                request.setWeightInKilos(5);
                request.setProviderId(99L);

                when(providerRepo.findById(99L))
                        .thenReturn(Optional.empty());

                assertThrows(ResourceNotFoundException.class,
                        ()->service.create(request));

                verify(fruitRepo, never()).save(any());


    }

    @Test
    void findFruitByProvider_whenProviderExists_returnProviderList(){

       Provider provider = new Provider("ProviderB", "Spain");
       provider.setId(1L);

       when(providerRepo.existsById(1L)).thenReturn(true);

       when(fruitRepo.findByProviderId(1L)).thenReturn(List.of(
               new Fruit("Orange",10, provider),
               new Fruit("Banana", 5,provider)
       ));

       List<FruitResponse> result = service.findByProviderId(1L);

       assertEquals(2, result.size());
       assertEquals(1L, result.get(0).providerId());
    }

    @Test
    void findFruitsByProvider_whenProviderNotExists_return404NotFound(){

        when(providerRepo.existsById(55L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, ()->service.findByProviderId(55L));


        verify(fruitRepo, never()).findByProviderId(any());
    }

    @Test
    void findAllFruits_returnAllFruits(){

        Provider provider1 = new Provider("Fruits&Co","Spain");
        provider1.setId(1L);

        Provider provider2 = new Provider("Tropical Fruits", "Brazil");
        provider2.setId(2L);

        when(fruitRepo.findAll()).thenReturn(List.of(
                new Fruit("Apple", 5 , provider1),
                new Fruit("Mango", 6, provider2)
        ));

        List<FruitResponse> result = service.findAll();

        assertEquals(2, result.size());
        assertEquals("Apple", result.get(0).name());
        assertEquals(1L, result.get(0).providerId());
        assertEquals("Mango", result.get(1).name());
        assertEquals(2L, result.get(1).providerId());
    }


}
