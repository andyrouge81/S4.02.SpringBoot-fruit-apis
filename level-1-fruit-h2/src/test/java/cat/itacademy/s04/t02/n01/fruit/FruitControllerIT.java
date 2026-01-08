package cat.itacademy.s04.t02.n01.fruit;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FruitControllerIT {

    @Autowired
    private MockMvc mockMvc;

    //POST
    @Test
    void createFruit_whenValid_returnHTTP201AndFruit() throws Exception {

        String body = """
                {
                "name":"Orange",
                "weightInKilos" : 5
                }
                """;


        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))

                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name").value("Orange"))
                .andExpect(jsonPath("$.weightInKilos").value(5));


    }

    @Test
    void createFruit_whenFruitNameIsEmpty_returnHTTP400BadRequest() throws Exception {

        String body = """
                 {
                 "name" : " ",
                 "weightInKilos": 5
                 }
                """;

        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))

                .andExpect(status().isBadRequest());


    }

    @Test
    void createFruit_whenNoValidWeightFruit_returnHTTP400BadRequest() throws Exception {

        String body = """
                {
                "name" : "Orange",
                "weightInKilos": 0
                }
                """;

        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))

                .andExpect(status().isBadRequest());
    }

    //GET

    @Test
    void findAllFruits_whenInventoryExists_theReturnHTTP200RequestAndList() throws Exception {

        String body1 = """
                 {   
                    "name" : "Apple",
                    "weightInKilos" : 10
                 }
                 """;

        String body2 = """
                 {   
                    "name" : "Pear",
                    "weightInKilos" : 5
                 }
                 """;

        mockMvc.perform(post("/fruits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body1))
                .andExpect(status().isCreated());


        mockMvc.perform(post("/fruits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body2))
                .andExpect(status().isCreated());;


        mockMvc.perform(get("/fruits"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    void findAllFruits_whenFruitsInventoryEmpty_returnAnEmptyArrayAndHTTP200() throws Exception{

        mockMvc.perform(get("/fruits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getFruitById_whenExists_returnHTTP200andFruit() throws Exception{

        String body = """
                {
                "name" : "Banana",
                "weightInKilos" : 8
                }
                """;

        String response = mockMvc.perform(post("/fruits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Number id = JsonPath.read(response, "$.id");

        mockMvc.perform(get("/fruits/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Banana"));


    }

    @Test
    void getFruitById_whenNotExists_returnHTTP404NotFound() throws Exception{
        mockMvc.perform(get("/fruits/{id}", 555L))
                .andExpect(status().isNotFound());
    }

    //UPDATE
    @Test
    void updateFruit_whenIsValid_returnHTTP200AndUpdatedFruit() throws Exception{

        String body = """
                {
                "name" : "Apple",
                "weightInKilos" : 5
                }
                """;

        String response = mockMvc.perform(post("/fruits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Number id = JsonPath.read(response, "$.id");

        String updateBody = """
                {
                "name" : "Red Apple",
                "weightInKilos" : 7
                }
                """;

        mockMvc.perform(put("/fruits/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Red Apple"))
                .andExpect(jsonPath("$.weightInKilos").value(7));

    }

    @Test
    void updateFruit_whenIdNotExists_returnHTTP404NotFound() throws Exception{

        String body = """
                {
                "name" : "Banana",
                "weightInKilos" : 6
                }
               
                """;

        mockMvc.perform(put("/fruits/{id}", 666L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isNotFound());


    }

    @Test
    void updateFruit_whenDataNotValid_returnHTTP400BadRequest() throws Exception{

        String body = """
                {
                "name" : "Orange",
                "weightInKilos" : 5
                }
                """;

        String response = mockMvc.perform(post("/fruits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Number id = JsonPath.read(response, "$.id");


        String invalidResponse = """
                {
                "name" : " ",
                "weightInKilos" : -5
                }
                """;

        mockMvc.perform(put("/fruits/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidResponse))
                .andExpect(status().isBadRequest());

    }

    @Test
    void deleteFruit_whenIdExists_deleteFruitAndReturnHTTP204NoContent() throws Exception{

        String body = """
                {
                "name" : "Apple",
                "weightInKilos" : 4
                }
                """;

        String response = mockMvc.perform(post("/fruits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Number id = JsonPath.read(response, "$.id");

        mockMvc.perform(delete("/fruits/{id}", id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/fruits/{id}", id))
                .andExpect(status().isNotFound());

    }

    @Test
    void deleteFruit_whenIdNotExists_thenHTTP404NotFoundAndErrorMessage() throws Exception{

        mockMvc.perform(get("/fruits/{id}", 444L))
                .andExpect(status().isNotFound());

    }


}
