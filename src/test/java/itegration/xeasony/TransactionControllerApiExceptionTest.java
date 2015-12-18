package itegration.xeasony;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import xeasony.Application;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class TransactionControllerApiExceptionTest extends BaseControllerApiTest {
    protected JSONArray transactions;
    protected JSONArray ids;

    @Before
    public void setUp() {
        super.setUp();

        JSONObject obj = getJsonObjectFromFile("src/test/resources/transactionsException.json");
        transactions = obj.getJSONArray("transactions");
        ids = obj.getJSONArray("ids");
    }

    @Test
    public void putCannotConvertException() throws Exception {
        this.mvc.perform(put("/transactionservice/transaction/" + ids.get(0))
                .content(transactions.get(0).toString())
                .contentType(contentTypeJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(contentTypeJson));
    }

    @Test
    public void putValidationException() throws Exception {
        this.mvc.perform(put("/transactionservice/transaction/" + ids.get(1))
                .content(transactions.get(1).toString())
                .contentType(contentTypeJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(contentTypeJson))
                .andExpect(jsonPath("$.url", notNullValue()))
                .andExpect(jsonPath("$.code", is("VALIDATION_ERROR")))
                .andExpect(jsonPath("$.fieldErrors", hasSize(2)));

        this.mvc.perform(put("/transactionservice/transaction/" + ids.get(2))
                .content(transactions.get(2).toString())
                .contentType(contentTypeJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(contentTypeJson))
                .andExpect(jsonPath("$.url", notNullValue()))
                .andExpect(jsonPath("$.code", is("VALIDATION_ERROR")))
                .andExpect(jsonPath("$.fieldErrors", hasSize(1)));
    }

    @Test
    public void findOneByIdResourceNotFoundException() throws Exception {
        this.mvc.perform(get("/transactionservice/transaction/" + 1)
                .contentType(contentTypeJson))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(contentTypeJson))
                .andExpect(jsonPath("$.url", notNullValue()))
                .andExpect(jsonPath("$.code", is("RESOURCE_NOT_FOUND")))
                .andExpect(jsonPath("$.fieldErrors", isEmptyOrNullString()));
    }

    @Test
    public void getTotalAmountNotResourceFoundException() throws Exception {
        this.mvc.perform(get("/transactionservice/sum/" + 1)
                .contentType(contentTypeJson))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(contentTypeJson))
                .andExpect(jsonPath("$.url", notNullValue()))
                .andExpect(jsonPath("$.code", is("RESOURCE_NOT_FOUND")))
                .andExpect(jsonPath("$.fieldErrors", isEmptyOrNullString()));
    }
}
