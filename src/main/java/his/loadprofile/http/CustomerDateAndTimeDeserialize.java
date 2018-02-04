package his.loadprofile.http;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomerDateAndTimeDeserialize extends JsonDeserializer<Date> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm ");

    @SuppressWarnings("deprecation")
	@Override
    public Date deserialize(JsonParser paramJsonParser,
            DeserializationContext paramDeserializationContext)
            throws IOException, JsonProcessingException {
        String str = paramJsonParser.getText().trim();
        try {
        	Date t = dateFormat.parse(str);
        	System.out.println(t.toGMTString());
            return t;
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return paramDeserializationContext.parseDate(str);
    }

	
}
