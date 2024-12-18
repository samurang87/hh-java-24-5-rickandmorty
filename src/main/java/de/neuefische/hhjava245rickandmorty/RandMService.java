package de.neuefische.hhjava245rickandmorty;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class RandMService {

    private RestClient randmClient = null;

    public RandMService(RestClient.Builder restClientBuilder) {
        this.randmClient = restClientBuilder.baseUrl("https://rickandmortyapi.com/api/character").build();
    }

    public List<RandMCharacter> getAllCharacters() {
        RandMCharResponse response = randmClient.get().retrieve().body(RandMCharResponse.class);
        return response != null && response.results() != null ? response.results() : List.of();
    }

    public RandMCharacter getCharacterById(String id) {
        return randmClient.get().uri("/" + id).retrieve().body(RandMCharacter.class);
    }

    public List<RandMCharacter> getAllCharactersByStatus(String status) {
        RandMCharResponse response = randmClient.get().uri("?status=" + status).retrieve().body(RandMCharResponse.class);
        return response != null && response.results() != null ? response.results() : List.of();
    }

    public long getSpeciesStatistic(String species) {
        List<RandMCharacter> characters = getAllCharactersByStatus("Alive");
        return characters.stream()
                .filter(ch -> ch.species().equals(species))
                .count();
    }
}
