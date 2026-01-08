package cat.itacademy.s04.t02.n02.fruit;

import cat.itacademy.s04.t02.n02.fruit.exception.DuplicateRequestException;
import cat.itacademy.s04.t02.n02.fruit.model.dto.provider.ProviderCreateRequest;
import cat.itacademy.s04.t02.n02.fruit.model.dto.provider.ProviderResponse;
import cat.itacademy.s04.t02.n02.fruit.model.entity.Provider;
import cat.itacademy.s04.t02.n02.fruit.repositories.ProviderRepository;
import cat.itacademy.s04.t02.n02.fruit.services.provider.ProviderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProviderServiceImplTest {

    @Mock
    private ProviderRepository repo;

    @InjectMocks
    private ProviderServiceImpl service;

    @Test
    void createProvider_whenNameDoesNotExist_thenSaveProvider(){

        ProviderCreateRequest request = new ProviderCreateRequest();
        request.setName("Tropical Fruits");
        request.setCountry("Brazil");

        when(repo.existsByName("Tropical Fruits")).thenReturn(false);

        when(repo.save(any(Provider.class)))

                .thenAnswer(invocation -> {
                    Provider provider = invocation.getArgument(0);
                    provider.setId(1L);
                    return provider;
                });

        ProviderResponse result = service.create(request);


        assertEquals("Tropical Fruits", result.name());
        assertEquals("Brazil", result.country());

    }

    @Test
    void createProvider_whenNameAlreadyExist_thenThrowException(){

        ProviderCreateRequest request = new ProviderCreateRequest();
        request.setName("Tropical Fruits");
        request.setCountry("Brazil");

        when(repo.existsByName("Tropical Fruits")).thenReturn(true);

        assertThrows(DuplicateRequestException.class, ()-> service.create(request));

        verify(repo, never()).save(any(Provider.class));

    }



}
