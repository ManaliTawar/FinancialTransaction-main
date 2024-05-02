import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAuthorizationTransaction() throws Exception {
        String requestBody = "{" +
                "\"messageId\": \"50e70c62-e480-49fc-bc1b-e991ac672173\"," +
                "\"userId\": \"8786e2f9-d472-46a8-958f-d659880e723d\"," +
                "\"responseCode\": \"APPROVED\"," +
                "\"balance\": {" +
                "\"amount\": \"0\"," +
                "\"currency\": \"USD\"," +
                "\"debitOrCredit\": \"CREDIT\"" +
                "}" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.put("/authorization/{messageId}", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testLoadFunds() throws Exception {
        String requestBody = "{" +
                "\"messageId\": \"55210c62-e480-asdf-bc1b-e991ac67FSAC\"," +
                "\"userId\": \"2226e2f9-ih09-46a8-958f-d659880asdfD\"," +
                "\"balance\": {" +
                "\"amount\": \"100.23\"," +
                "\"currency\": \"USD\"," +
                "\"debitOrCredit\": \"CREDIT\"" +
                "}" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.put("/load/{messageId}", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
