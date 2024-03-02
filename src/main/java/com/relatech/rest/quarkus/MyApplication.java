package com.relatech.rest.quarkus;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import io.quarkus.runtime.Startup;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@OpenAPIDefinition(
    tags = {
        @Tag(name="widget", description="Widget operations."),
        @Tag(name="gasket", description="Operations related to gaskets")
    },
    info = @Info(
        title="Example API",
        version = "1.0.0-SNAPSHOT",
        contact = @Contact(
            name = "Example API Support",
            url = "http://exampleurl.com/contact",
            email = "techsupport@example.com"),
        license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0.html")
    )
)
@ApplicationPath("/api")
public class MyApplication extends Application {
	
	@Startup
	public void printConfig() {
		final Config config = ConfigProvider.getConfig();
		
		for (String property : config.getPropertyNames()) {
			log.info("{} = {}", property, config.getConfigValue(property).getValue());
		}		
	}
	
}