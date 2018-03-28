package his.loadprofile.controller;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.springframework.http.ResponseEntity;

public class AboutControllerTest extends ControllerTest{
	
	@Test
    public void aboutPageContainsString() throws Exception {
        
        
        ResponseEntity<String> response = template.getForEntity(base.toString() + "about",
                String.class);
        assertThat(response.getBody(), containsString("Project Description go here"));
    }

}
