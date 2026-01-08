package cat.itacademy.s04.t02.n01.fruit.service;

import cat.itacademy.s04.t02.n01.fruit.exception.FruitNotFoundException;
import cat.itacademy.s04.t02.n01.fruit.model.dto.FruitCreateRequest;
import cat.itacademy.s04.t02.n01.fruit.model.dto.FruitUpdateRequest;
import cat.itacademy.s04.t02.n01.fruit.model.entity.Fruit;
import cat.itacademy.s04.t02.n01.fruit.repositories.FruitRepository;
import cat.itacademy.s04.t02.n01.fruit.services.FruitServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FruitServiceImplTest {

    @Mock
    private FruitRepository repo;

    @InjectMocks
    private FruitServiceImpl service;

    @Test
    void create_whenValid_returnFruitIsSaved(){
        FruitCreateRequest request = new FruitCreateRequest();
        request.setName("Orange");
        request.setWeightInKilos(6);

        Fruit savedFruit = new Fruit("Orange", 6);
        savedFruit.setId(1L);

        when(repo.save(any(Fruit.class))).thenReturn(savedFruit);

        var response = service.create(request);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Orange",response.name());
        assertEquals(6, response.weightInKilos());

        verify(repo, times(1)).save(any(Fruit.class));
    }

    @Test
    void findById_whenExists_returnFruit(){

        Fruit fruit = new Fruit("Banana", 5);
        fruit.setId(2L);

        when(repo.findById(2L)).thenReturn(Optional.of(fruit));

        var response = service.findById(2L);

        assertEquals(2L, response.id());
        assertEquals("Banana", response.name());
    }

    @Test
    void findById_whenNotExists_thenThrowException(){

        when(repo.findById(3L)).thenReturn(Optional.empty());

        assertThrows(FruitNotFoundException.class, ()-> service.findById(3L));
    }

    @Test
    void update_whenExists_thenUploadFruit(){

        Fruit fruit = new Fruit("Red Apple", 3);
        fruit.setId(2L);

        FruitUpdateRequest request = new FruitUpdateRequest();
        request.setName("Apple");
        request.setWeightInKilos(4);

        when(repo.findById(2L)).thenReturn(Optional.of(fruit));
        when(repo.save(any(Fruit.class))).thenReturn(fruit);

        var response = service.update(2L,request);

        assertEquals("Apple", response.name());
        assertEquals(4,response.weightInKilos());

        verify(repo).save(fruit);
    }

    @Test
    void delete_whenNoExits_thenThrowException(){

        when(repo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(FruitNotFoundException.class, ()->service.deleteById(1L));

        verify(repo, never()).delete(any());


    }
}
