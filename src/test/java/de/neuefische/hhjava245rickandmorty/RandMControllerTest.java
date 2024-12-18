package de.neuefische.hhjava245rickandmorty;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
class RandMControllerTest {

    @Autowired
    private MockMvc mm;

    @Autowired
    private MockRestServiceServer ms;

    @Test
    void getCharacters() throws Exception {
        ms.expect(requestTo("https://rickandmortyapi.com/api/character"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("""
                                {
                                    "info": {
                                        "count": 32,
                                        "pages": 42
                                    },
                                    "results": [
                                        {
                                            "id": 1,
                                            "name": "Rick Sanchez",
                                            "status": "Alive",
                                            "species": "Human"
                                        }
                                    ]
                                }
                                """,
                        MediaType.APPLICATION_JSON));

        mm.perform(get("/api/characters"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {"results":[{"id":"1","name":"Rick Sanchez","species":"Human"}]}
                        """));
    }

    @Test
    void getCharacterById() throws Exception {
        ms.expect(requestTo("https://rickandmortyapi.com/api/character/3"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("""
                    {
                        "id": "3",
                        "name": "Summer Smith",
                        "status": "Alive",
                        "species": "Human",
                        "type": "",
                        "gender": "Female"
                    }
                    """,
                        MediaType.APPLICATION_JSON));

        mm.perform(get("/api/characters/3"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    {"id":"3","name":"Summer Smith","species":"Human"}
                    """));
    }

    @Test
    void getSpeciesStatistic() throws Exception {
        ms.expect(requestTo("https://rickandmortyapi.com/api/character?status=Alive"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("""
                                {
                                    "info": {
                                        "count": 32,
                                        "pages": 42
                                    },
                                    "results": [
                                        {
                                            "id": 1,
                                            "name": "Rick Sanchez",
                                            "status": "Alive",
                                            "species": "Human"
                                        },
                                        {
                                            "id": 2,
                                            "name": "Rick Sanchez",
                                            "status": "Alive",
                                            "species": "Human"
                                        },
                                        {
                                            "id": 3,
                                            "name": "Rick Sanchez",
                                            "status": "Alive",
                                            "species": "Human"
                                        }
                                    ]
                                }
                                """,
                        MediaType.APPLICATION_JSON));

        mm.perform(get("/api/species-statistic?species=Human"))
                .andExpect(status().isOk())
                .andExpect(content().string("3"));
    }
}
