package cat.itacademy.s04.t02.n02;

import cat.itacademy.s04.t02.n02.controllers.FruitController;
import cat.itacademy.s04.t02.n02.exception.GlobalExceptionHandler;
import cat.itacademy.s04.t02.n02.exception.ResourceNotFoundException;
import cat.itacademy.s04.t02.n02.model.dto.fruit.FruitResponse;
import cat.itacademy.s04.t02.n02.services.fruit.FruitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FruitController.class)
@Import(GlobalExceptionHandler.class)
public class FruitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FruitService fruitService;

    //POST
    @Test
    void createFruit_whenProviderExists_returnHTTP201() throws Exception {

        FruitResponse response = new FruitResponse(10L, "Orange", 5,1L );

        when(fruitService.create(any())).thenReturn(response);

        String body = """
                {
                "name" : "Orange",
                "weightInKilos" : 5,
                "providerId" : 1
                }
                """;


        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.name").value("Orange"))
                .andExpect(jsonPath("$.weightInKilos").value(5))
                .andExpect(jsonPath("$.providerId").value(1L));


    }

    @Test
    void createFruit_whenInvalidRequest_returnHTTP400BadRequest() throws Exception {

        String body = """
                 {
                 "name" : " ",
                 "weightInKilos": -1
                 }
                """;

        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))

                .andExpect(status().isBadRequest());


    }


    //GET

    @Test
    void findFruitsByProvider_whenProviderExists_returnHTTP200AndList() throws Exception {

        when(fruitService.findByProviderId(1L)).thenReturn(List.of(
                new FruitResponse(1L,"Apple",5 ,1L),
                new FruitResponse(2L, "Orange", 7, 1L)

        ));

        mockMvc.perform(get("/fruits")
                .param("providerId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Apple"))
                .andExpect(jsonPath("$[1].name").value("Orange"));

    }



    @Test
    void findFruitByProvider_whenProviderNotExists_returnHTTP404NotFound() throws Exception{

        when(fruitService.findByProviderId(99L))
                .thenThrow(new ResourceNotFoundException("Provider not found"));

        mockMvc.perform(get("/fruits")
                .param("providerId", "99"))
                .andExpect(status().isNotFound());


    }

    @Test
    void findAllFruits_whenNoProviderId_returnHTTp200AndList() throws Exception{

        when(fruitService.findAll()).thenReturn(List.of(
                new FruitResponse(1L, "Apple",4, 1L),
                new FruitResponse(2L, "Orange",6,2L)
        ));

        mockMvc.perform(get("/fruits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Apple"))
                .andExpect(jsonPath("$[1].name").value("Orange"));

    }

}
