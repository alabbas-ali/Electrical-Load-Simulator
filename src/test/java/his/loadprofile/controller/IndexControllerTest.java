package his.loadprofile.controller;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.http.ResponseEntity;

public class IndexControllerTest extends ControllerTest{
	
	@Test
    public void greetingShouldReturnMessageFromService() 
    		throws Exception {
		
        ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
        assertThat(response.getBody(), containsString("Project Description go here"));
    }
	
}
