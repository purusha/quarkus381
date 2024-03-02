package com.relatech.rest.quarkus;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.extern.slf4j.Slf4j;

@ExecutionTime
@Slf4j
@Path("/fruits")
public class FruitResource {

    private final Set<Fruit> fruits = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public FruitResource() {
        fruits.add(new Fruit("Apple", "Winter fruit"));
        fruits.add(new Fruit("Pineapple", "Tropical fruit"));
        
        log.info("installed fruits: {}", fruits);
    }

    @GET
    public Set<Fruit> list() {
        return fruits;
    }

    @POST
    public Set<Fruit> add(@Valid Fruit fruit) {
        fruits.add(fruit);
        return fruits;
    }

    @DELETE
    public Set<Fruit> delete(Fruit fruit) {
        fruits.removeIf(
    		existingFruit -> StringUtils.equals(existingFruit.getName(), fruit.getName())
		);
        
        return fruits;
    }
}
