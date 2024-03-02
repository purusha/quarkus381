package com.relatech.rest.quarkus;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Fruit {

	@NotEmpty
	String name;
	
	@NotEmpty
    String description;
    
}
