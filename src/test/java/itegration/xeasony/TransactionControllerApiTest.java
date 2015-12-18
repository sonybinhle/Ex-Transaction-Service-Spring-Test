package itegration.xeasony;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.junit.runners.MethodSorters;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import xeasony.Application;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class TransactionControllerApiTest extends BaseControllerApiTest {
    protected JSONArray transactions;
    protected JSONArray ids;
    protected JSONArray carIds;
    protected JSONArray shoppingIds;
    protected JSONArray totalAmountsSimple;
    protected JSONArray totalAmountsComplex;
    protected JSONObject updatedTransaction;
    protected Long updatedId;

    @Before
    public void setUp() {
        super.setUp();

        JSONObject obj = getJsonObjectFromFile("src/test/resources/transactions.json");
        transactions = obj.getJSONArray("transactions");
        totalAmountsSimple = obj.getJSONArray("totalAmountsSimple");
        totalAmountsComplex = obj.getJSONArray("totalAmountsComplex");
        ids = obj.getJSONArray("ids");
        carIds = obj.getJSONArray("carIds");
        shoppingIds = obj.getJSONArray("shoppingIds");
        updatedTransaction = obj.getJSONObject("updatedTransaction");
        updatedId = obj.getLong("updatedId");
    }

    @Test
    public void a_putCreateWithoutParentId() throws Exception {
        testPutCreate(0);
    }

    @Test
    public void b_putCreateWithParentId() throws Exception {
        testPutCreate(1);
        testPutCreate(2);
    }

    @Test
    public void c_findOneById() throws Exception {
        testFindOneById(ids.getLong(0), transactions.getJSONObject(0));
        testFindOneById(ids.getLong(1), transactions.getJSONObject(1));
    }

    @Test
    public void d_findByType() throws Exception {
        testFindByType("cars", carIds);
        testFindByType("shopping", shoppingIds);
        testFindByType("NotExistType", new JSONArray());
    }

    @Test
    public void e_getTotalAmountById() throws Exception {
        for (int i = 0; i < 3; ++i) {
            testGetTotalAmountById(ids.getLong(i), totalAmountsSimple.get(i));
        }
    }

    @Test
    public void f_getTotalAmountByIdComplex() throws Exception {
        for (int i = 3; i < transactions.length(); ++i) {
            testPutCreate(i);
        }
        for (int i = 0; i < transactions.length(); ++i) {
            testGetTotalAmountById(ids.getLong(i), totalAmountsComplex.get(i));
        }
    }

    @Test
    public void g_findAll() throws Exception {
        this.mvc.perform(get("/transactionservice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(transactions.length())));
    }

    @Test
    public void f_putUpdate() throws Exception {
        ResultActions result = this.mvc.perform(put("/transactionservice/transaction/" + updatedId)
                .content(updatedTransaction.toString())
                .contentType(contentTypeJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentTypeJson));

        assertEquals(statusOk.toString(), result, true);

        testFindOneById(updatedId, updatedTransaction);
    }

    private void testPutCreate(int index) throws Exception {
        JSONObject transaction = transactions.getJSONObject(index);
        Long id = ids.getLong(index);
        ResultActions result = this.mvc.perform(put("/transactionservice/transaction/" + id)
                .content(transaction.toString())
                .contentType(contentTypeJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(contentTypeJson));

        assertEquals(statusOk.toString(), result, true);
    }

    private void testFindOneById(Long id, JSONObject transaction) throws Exception {
        ResultActions result = this.mvc.perform(get("/transactionservice/transaction/" + id)
                .contentType(contentTypeJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentTypeJson));

        assertEquals(transaction.toString(), result, false);
    }

    private void testFindByType(String type, JSONArray actual) throws Exception {
        ResultActions result = this.mvc.perform(get("/transactionservice/types/" + type)
                .contentType(contentTypeJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentTypeJson));

        assertEquals(actual.toString(), result, true);
    }

    private void testGetTotalAmountById(Long id, Object totalAmount) throws Exception {
        JSONObject actual = new JSONObject();
        actual.put("amount", totalAmount);

        ResultActions result = this.mvc.perform(get("/transactionservice/sum/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentTypeJson));

        assertEquals(actual.toString(), result, true);
    }
}
