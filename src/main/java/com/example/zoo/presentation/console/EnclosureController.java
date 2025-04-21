package com.example.zoo.presentation.console;

import com.example.zoo.domain.model.Enclosure;
import com.example.zoo.domain.valueobjects.EnclosureType;
import com.example.zoo.infrastructure.persistence.InMemoryEnclosureRepository;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class EnclosureController {
    private final InMemoryEnclosureRepository enclosureRepository;

    public EnclosureController(InMemoryEnclosureRepository enclosureRepository) {
        this.enclosureRepository = enclosureRepository;
    }

    @ShellMethod(key = "enclosure add", value = "Add a new enclosure")
    public String addEnclosure(String type, double size, int maxCapacity) {
        Enclosure enclosure = new Enclosure(null, 
            EnclosureType.valueOf(type.toUpperCase()), size, maxCapacity);
        enclosureRepository.save(enclosure);
        return "Enclosure added with ID: " + enclosure.getId();
    }

    @ShellMethod(key = "enclosure list", value = "List all enclosures")
    public List<String> listEnclosures() {
        return enclosureRepository.findAll().stream()
            .map(enclosure -> String.format(
                "ID: %d | Type: %s | Size: %.1f | Animals: %d/%d | Clean: %s",
                enclosure.getId(),
                enclosure.getType(),
                enclosure.getSize(),
                enclosure.getAnimalIds().size(),
                enclosure.getMaxCapacity(),
                enclosure.isClean() ? "Yes" : "No"
            ))
            .collect(Collectors.toList());
    }

    @ShellMethod(key = "enclosure delete", value = "Delete an enclosure")
    public String deleteEnclosure(int id) {
        enclosureRepository.delete(id);
        return "Enclosure deleted";
    }

    @ShellMethod(key = "enclosure clean", value = "Mark enclosure as clean")
    public String cleanEnclosure(int id) {
        Enclosure enclosure = enclosureRepository.findById(id);
        if (enclosure != null) {
            enclosure.clean();
            return "Enclosure cleaned";
        }
        return "Enclosure not found";
    }
}