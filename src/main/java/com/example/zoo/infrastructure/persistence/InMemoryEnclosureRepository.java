package com.example.zoo.infrastructure.persistence;

import com.example.zoo.domain.model.Enclosure;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryEnclosureRepository {
    private final Map<Integer, Enclosure> enclosures = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public Enclosure save(Enclosure enclosure) {
        if (enclosure.getId() == null) {
            enclosure.setId(idGenerator.getAndIncrement());
        }
        enclosures.put(enclosure.getId(), enclosure);
        return enclosure;
    }

    public Enclosure findById(Integer id) {
        if (id == null) {
            return null;
        }
        return enclosures.get(id);
    }
    
    public List<Enclosure> findAll() {
        return new ArrayList<>(enclosures.values());
    }

    public void delete(Integer id) {
        enclosures.remove(id);
    }
}