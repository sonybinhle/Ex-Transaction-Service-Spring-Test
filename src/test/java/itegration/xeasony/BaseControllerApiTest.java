package itegration.xeasony;

import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BaseControllerApiTest {
    protected MediaType contentTypeJson = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    protected MockMvc mvc;

    protected JSONObject statusOk;

    @Autowired
    private WebApplicationContext context;

    protected void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();

        statusOk = new JSONObject();
        statusOk.put("status", "ok");
    }

    protected void assertEquals(String expected, ResultActions mvcResult, boolean strict) throws Exception {
        String actual = mvcResult.andReturn().getResponse().getContentAsString();
        JSONAssert.assertEquals(expected, actual, strict);
    }

    protected JSONObject getJsonObjectFromFile(String path) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(path)));
            return (JSONObject) JSONParser.parseJSON(content);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
