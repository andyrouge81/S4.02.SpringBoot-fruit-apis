package cat.itacademy.s04.t02.n02;


import cat.itacademy.s04.t02.n02.exception.ConflictException;
import cat.itacademy.s04.t02.n02.exception.ResourceNotFoundException;
import cat.itacademy.s04.t02.n02.repositories.FruitRepository;
import cat.itacademy.s04.t02.n02.repositories.ProviderRepository;
import cat.itacademy.s04.t02.n02.services.provider.ProviderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProviderServiceImplDeleteTest {

    @Mock
    ProviderRepository providerRepo;

    @Mock
    FruitRepository fruitRepo;

    @InjectMocks
    ProviderServiceImpl service;

    @Test
    void delete_whenProviderExistsAndFruitNotExist_thenDeleteProvider(){

        Long id = 1L;

        when(providerRepo.existsById(id)).thenReturn(true);
        when(fruitRepo.existsByProviderId(id)).thenReturn(false);

        service.delete(id);

        verify(providerRepo).deleteById(id);

    }

    @Test
    void delete_whenProviderNotExist_throw404ErrorNotFound(){
        Long id = 55L;

        when(providerRepo.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, ()->service.delete(id));

        verify(providerRepo, never()).deleteById(anyLong());
        verify(fruitRepo, never()).existsByProviderId(anyLong());

    }

    @Test
    void delete_whenProviderExistsAndHasFruits_throw409ErrorAndCanNotDelete(){

        Long id = 1L;

        when(providerRepo.existsById(id)).thenReturn(true);
        when(fruitRepo.existsByProviderId(id)).thenReturn(true);

        assertThrows(ConflictException.class ,()->service.delete(id));

        verify(providerRepo, never()).deleteById(anyLong());



    }



}
