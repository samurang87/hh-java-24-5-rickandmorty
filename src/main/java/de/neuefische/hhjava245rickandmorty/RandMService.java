package de.neuefische.hhjava245rickandmorty;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

@Service
public class RandMService {

    private RestClient randmClient = null;

    public RandMService(RestClient.Builder restClientBuilder) {
        this.randmClient = restClientBuilder.baseUrl("https://rickandmortyapi.com/api/character").build();
    }

    public Optional<List<RandMCharacter>> getAllCharacters() {
        RandMCharResponse response = randmClient.get().retrieve().body(RandMCharResponse.class);
        return Optional.ofNullable(response).map(RandMCharResponse::results);
    }
}
