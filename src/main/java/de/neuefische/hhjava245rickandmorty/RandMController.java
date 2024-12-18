package de.neuefische.hhjava245rickandmorty;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RandMController {

    private final RandMService rms;

    @GetMapping("/characters")
    public RandMCharResponse getCharacters(@RequestParam(required = false) String status) {
        if (status != null) {
            return new RandMCharResponse(rms.getAllCharactersByStatus(status));
        }
        return new RandMCharResponse(rms.getAllCharacters());
    }

    @GetMapping("characters/{id}")
    public RandMCharacter getCharacterById(@PathVariable String id) {
        return rms.getCharacterById(id);
    }

    @GetMapping("/species-statistic")
    public long getSpeciesStatistic(@RequestParam String species) {
        return rms.getSpeciesStatistic(species);
    }
}
