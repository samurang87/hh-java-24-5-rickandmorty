package de.neuefische.hhjava245rickandmorty;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@RestClientTest(RandMService.class)
class RandMServiceTest {

    @Autowired
    private MockRestServiceServer ms;

    @Autowired
    private RandMService rms;

    @Test
    void getAllCharacters() {
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

        var result = rms.getAllCharacters();
        RandMCharacter expectedCharacter = new RandMCharacter("1", "Rick Sanchez", "Human");
        List<RandMCharacter> expectedResponse = List.of(expectedCharacter);
        Assertions.assertEquals(expectedResponse, result);
    }

    @Test
    void getCharacterById() {
        ms.expect(requestTo("https://rickandmortyapi.com/api/character/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("""
                                {
                                    "id": 1,
                                    "name": "Rick Sanchez",
                                    "status": "Alive",
                                    "species": "Human"
                                }
                                """,
                        MediaType.APPLICATION_JSON));

        var result = rms.getCharacterById("1");
        RandMCharacter expectedCharacter = new RandMCharacter("1", "Rick Sanchez", "Human");
        Assertions.assertEquals(expectedCharacter, result);
    }

    @Test
    void getAllCharactersByStatus() {
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
                                        }
                                    ]
                                }
                                """,
                        MediaType.APPLICATION_JSON));

        var result = rms.getAllCharactersByStatus("Alive");
        RandMCharacter expectedCharacter1 = new RandMCharacter("1", "Rick Sanchez", "Human");
        RandMCharacter expectedCharacter2 = new RandMCharacter("2", "Rick Sanchez", "Human");
        List<RandMCharacter> expectedResponse = List.of(expectedCharacter1, expectedCharacter2);
        Assertions.assertEquals(expectedResponse, result);
    }

    @Test
    void getSpeciesStatistic() {
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
                                        }
                                    ]
                                }
                                """,
                        MediaType.APPLICATION_JSON));

        var result = rms.getSpeciesStatistic("Human");
        Assertions.assertEquals(2, result);
    }
}