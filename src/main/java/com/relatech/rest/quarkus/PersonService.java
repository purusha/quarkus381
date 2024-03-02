package com.relatech.rest.quarkus;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PersonService {

    @Inject
    PersonRepository personRepository;

    @Transactional
    public Person create(PersonRequest request) {
        final Person person = new Person();

        person.setFirstname(request.firstname());
        person.setLastname(request.lastname());
        person.setAddress(request.address());
        personRepository.persist(person);
        
        return person;
    }

    public Person getById(Long id) {
        return personRepository
    		.findByIdOptional(id)
            .orElseThrow(() -> new IllegalArgumentException("Person with id " + id + " not found"));
    }

    public List<Person> getAll() {
        return personRepository.listAll();
    }

    @Transactional
    public void update(Long id, PersonRequest request) {
        personRepository
        	.findByIdOptional(id)
            .ifPresent(person -> {
                person.setFirstname(request.firstname());
                person.setLastname(request.lastname());
                person.setAddress(request.address());
                personRepository.persist(person);
            });
    }

    @Transactional
    public void delete(Long id) {
        personRepository.deleteById(id);
    }
}