package de.neuefische.hhjava245rickandmorty;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RandMController {

    private final RandMService rms;

    @GetMapping("/characters")
    public RandMCharResponse getCharacters() {
        return new RandMCharResponse(rms.getAllCharacters().orElseThrow());
    }

    @GetMapping("characters/{id}")
    public RandMCharacter getCharacterById(@PathVariable String id) {
        return rms.getCharacterById(id);
    }
}
