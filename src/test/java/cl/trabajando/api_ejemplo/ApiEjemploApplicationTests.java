package cl.trabajando.api_ejemplo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cl.trabajando.api_ejemplo.config.GlobalConfiguration;
import cl.trabajando.api_ejemplo.config.SecurityConfiguration;
import cl.trabajando.api_ejemplo.config.SwaggerConfiguracion;
import cl.trabajando.api_ejemplo.model.HolaMundo;

@Test
@SpringBootTest(properties = { "logstash.host=localhost", "logstash.port=4560",
	"spring.active.profiles=native" }, classes = { GlobalConfiguration.class, SecurityConfiguration.class,
		SwaggerConfiguracion.class })
@Rollback()
public class ApiEjemploApplicationTests extends AbstractTestNGSpringContextTests {

    private final String GOOGLE_CHROME_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @BeforeClass
    public void setup() throws Exception {
	this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
		.defaultRequest(get("/").header("User-Agent", GOOGLE_CHROME_AGENT)).build();
    }

    /**
     * Esta es una prueba unitaria ocupando TestNG y mock de ejemplo
     * @throws IOException
     * @throws Exception
     */
    @Test
    public void pruebaUnitariaEjemplo() throws IOException, Exception {
	//mockMvc.perform(get("/v1/mensaje")).andExpect(status().isOk());
    }
    
    @Test
    public void pruebaUnitariaEjemplo2() throws IOException, Exception {
	//mockMvc.perform(get("/v1/status")).andExpect(status().isBadGateway());
    }

   
}
