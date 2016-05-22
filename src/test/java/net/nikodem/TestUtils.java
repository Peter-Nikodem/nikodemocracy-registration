package net.nikodem;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;
import org.springframework.http.*;

import java.io.*;
import java.nio.charset.*;

public class TestUtils {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
