package his.loadprofile.controller;

//import static org.hamcrest.Matchers.containsString;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import org.springframework.test.web.servlet.MockMvc;


import java.net.URL;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
abstract class ControllerTest {
	
	//@Autowired
    //private MockMvc mvc;
	
	@LocalServerPort
    private int port;

    protected URL base;

    @Autowired
    protected TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
    }
    
    
    //when(service.greet()).thenReturn("Hello Mock");
    //this.mockMvc.perform(get("/about")).andDo(print()).andExpect(status().isOk())
    //        .andExpect(content().string(containsString("Project Description go here")));
}
