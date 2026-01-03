package cat.itacademy.s04.t02.n02;

import cat.itacademy.s04.t02.n02.controllers.ProviderController;
import cat.itacademy.s04.t02.n02.exception.ConflictException;
import cat.itacademy.s04.t02.n02.exception.DuplicateRequestException;
import cat.itacademy.s04.t02.n02.exception.GlobalExceptionHandler;
import cat.itacademy.s04.t02.n02.exception.ResourceNotFoundException;
import cat.itacademy.s04.t02.n02.model.dto.provider.ProviderResponse;
import cat.itacademy.s04.t02.n02.services.provider.ProviderService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

import org.springframework.http.MediaType;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProviderController.class)
@Import(GlobalExceptionHandler.class)
public class ProviderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProviderService providerService;

    // POST
    @Test
    void createProvider_whenIsValid_returnHTTP201Created() throws Exception{

        ProviderResponse response = new ProviderResponse(1L,"Fruits&CO", "Spain");


        when(providerService.create(any())).thenReturn(response);

        String body= """
                {
                "name" : "Fruits&CO",
                "country" : "Spain"
                }
                """;

        mockMvc.perform(post("/providers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Fruits&CO"))
                .andExpect(jsonPath("$.country").value("Spain"));


    }

    @Test
    void createProvider_whenNameAlreadyExists_returnHTTP409Conflict()throws Exception {

        when(providerService.create(any())).thenThrow(new DuplicateRequestException("Provider already exists"));

        String body = """
                {
                "name" : "Tropical Fruits",
                "country" : "Brazil"
                }
                
                """;

        mockMvc.perform(post("/providers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.message").value("Provider already exists"));
    }
    // GET
    @Test
    void getProviders_whenProviderExist_returnListAndHTTP200()throws Exception{

        when(providerService.findAll()).thenReturn(List.of(new ProviderResponse(1L, "Tropical Fruits", "Brazil"),
                                                            new ProviderResponse(2L, "Fruits&CO", "Spain")));

        mockMvc.perform(get("/providers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Tropical Fruits"))
                .andExpect(jsonPath("$[1].country").value("Spain"));


    }
    // UPDATE
    @Test
    void updateProvider_whenIsValid_returnHTTP200AndUpdateProvider() throws Exception{

        ProviderResponse response = new ProviderResponse(1L,"FruityLoops", "England" );

        when(providerService.update(eq(1L), any())).thenReturn(response);

        String body = """
                {
                "name" : "FruityLoops",
                "country" : "England"
                }
                """;

        mockMvc.perform(put("/providers/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("FruityLoops"))
                .andExpect(jsonPath("$.country").value("England"));



    }

    @Test
    void updateProvider_whenProviderNotExists_returnHTTP404ProviderNotFound() throws Exception{

        when(providerService.update(eq(55L),any())).thenThrow(new ResourceNotFoundException("Provider not found"));

        String body = """
                {
                "name" : "LocoFruits",
                "country" : "NoCountry"
                }
                """;

        mockMvc.perform(put("/providers/{id}", 55L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isNotFound());

    }

    @Test
    void updateProvider_whenProviderNameIsBlank_returnHTTP400()throws Exception{

        String body= """
                {
                "name" : " ",
                "country" : "Spain"
                }
                """;

        mockMvc.perform(put("/providers/{id}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body))
                .andExpect(status().isBadRequest());

    }

    @Test
    void updateProvider_whenNameAlreadyExits_returnHTTP409Conflict() throws Exception{

        when(providerService.update(eq(1L), any())).thenThrow(new DuplicateRequestException("Provider already exists"));

        String body= """
                {
                "name" : "Fruits&CO",
                "country" : "Spain"
                }
                """;

        mockMvc.perform(put("/providers/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isConflict());
    }

    // DELETE
    @Test
    void deleteProvider_whenProviderExistsAndHasNotFruits_returnHTTP204NoContent() throws Exception{

        doNothing().when(providerService).delete(1L);

        mockMvc.perform(delete("/providers/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteProvider_whenIdNotExist_returnHTTP404NotFound() throws Exception{

        doThrow(new ResourceNotFoundException("Provider not found"))
                .when(providerService).delete(99L);

        mockMvc.perform(delete("/providers/{id}",99L))
                .andExpect(status().isNotFound());

    }

    @Test
    void deleteProvider_whenProviderHasFruits_returnHTTp409Conflict() throws Exception{

        doThrow(new ConflictException("Provider has fruits and cannot be deleted"))
                .when(providerService).delete(1L);

        mockMvc.perform(delete("/providers/{id}", 1L))
                .andExpect(status().isConflict());
    }

}
