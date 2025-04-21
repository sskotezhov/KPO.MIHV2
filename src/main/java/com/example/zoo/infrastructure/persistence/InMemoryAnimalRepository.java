package com.example.zoo.infrastructure.persistence;

import com.example.zoo.domain.model.Animal;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryAnimalRepository {
    private final Map<Integer, Animal> animals = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public Animal save(Animal animal) {
        if (animal.getId() == null) {
            animal.setId(idGenerator.getAndIncrement());
        }
        animals.put(animal.getId(), animal);
        return animal;
    }

    public Animal findById(Integer id) {
        if (id == null) {
            return null;
        }
        return animals.get(id);
    }

    public List<Animal> findAll() {
        return new ArrayList<>(animals.values());
    }

    public void delete(Integer id) {
        animals.remove(id);
    }
}