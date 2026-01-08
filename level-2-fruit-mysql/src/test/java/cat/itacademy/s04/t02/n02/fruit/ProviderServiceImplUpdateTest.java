package cat.itacademy.s04.t02.n02.fruit;

import cat.itacademy.s04.t02.n02.fruit.exception.DuplicateRequestException;
import cat.itacademy.s04.t02.n02.fruit.exception.ResourceNotFoundException;
import cat.itacademy.s04.t02.n02.fruit.model.dto.provider.ProviderResponse;
import cat.itacademy.s04.t02.n02.fruit.model.dto.provider.ProviderUpdateRequest;
import cat.itacademy.s04.t02.n02.fruit.model.entity.Provider;
import cat.itacademy.s04.t02.n02.fruit.repositories.ProviderRepository;
import cat.itacademy.s04.t02.n02.fruit.services.provider.ProviderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProviderServiceImplUpdateTest {

    @Mock
    private ProviderRepository repo;

    @InjectMocks
    private ProviderServiceImpl service;

    @Test
    void updateProvider_whenProviderExistsAndNameNotDuplicate_thenUpdateAndReturnResponse(){

        Provider existing = new Provider("Fruits", "Brussels");
        existing.setId(1L);

        ProviderUpdateRequest request = new ProviderUpdateRequest();
        request.setName("NewFruits");
        request.setCountry("Denmark");

        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        when(repo.existsByNameAndIdNot("NewFruits",1L)).thenReturn(false);
        when(repo.save(any(Provider.class))).thenAnswer(invocation-> invocation.getArgument(0));

        ProviderResponse result = service.update(1L, request);

        assertEquals(1L, result.id());
        assertEquals("NewFruits", result.name());
        assertEquals("Denmark", result.country());

        ArgumentCaptor<Provider> captor = ArgumentCaptor.forClass(Provider.class);
        verify(repo).save(captor.capture());
        assertEquals("NewFruits", captor.getValue().getName());
        assertEquals("Denmark", captor.getValue().getCountry());


    }

    @Test
    void updateProvider_whenProviderNotFound_throwNotFoundException(){

        Long id = 99L;

        ProviderUpdateRequest request = new ProviderUpdateRequest();
        request.setName("Frutes");
        request.setCountry("Catalonia");

        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                ()->service.update(id, request));

        verify(repo , never()).save(any());
        verify(repo , never()).existsByNameAndIdNot(anyString(), anyLong());


    }

    @Test
    void updateProvider_whenProviderAlreadyExists_throwDuplicateException(){
        Long id = 1L;

        Provider provider = new Provider("Fruits", "Portugal");
        provider.setId(id);

        ProviderUpdateRequest request = new ProviderUpdateRequest();
        request.setName("FruitProvider");
        request.setCountry("ProviderCountry");


        when(repo.findById(id)).thenReturn(Optional.of(provider));
        when(repo.existsByNameAndIdNot(request.getName(),id)).thenReturn(true);

        assertThrows(DuplicateRequestException.class,
                ()-> service.update(id, request));

        verify(repo, never()).save(any());

    }


}
