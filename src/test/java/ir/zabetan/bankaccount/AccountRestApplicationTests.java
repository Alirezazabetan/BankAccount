package ir.zabetan.bankaccount;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ir.zabetan.bankaccount.domain.Account;
import ir.zabetan.bankaccount.repository.AccountRepository;
import ir.zabetan.bankaccount.service.AccountService;
import ir.zabetan.bankaccount.service.dto.RequestModifyDTO;
import ir.zabetan.bankaccount.web.rest.AccountResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountRestApplicationTests {

    private MockMvc restAccountMockMvc;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountResource accountResource;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.restAccountMockMvc = MockMvcBuilders.standaloneSetup(accountResource)
                .setMessageConverters(jacksonMessageConverter).build();
        this.account = createEntity();
    }

    private Account account;

    public Account createEntity() {
        return new Account()
                .phoneNumber("09123334455")
                .balance(88888);
    }

    @Test
    public void createAccount() throws Exception {
        int databaseSizeBeforeCreate = accountRepository.findAll().size();

        restAccountMockMvc.perform(post("/api/create")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(account)))
                .andExpect(status().isCreated());

        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts).hasSize(databaseSizeBeforeCreate + 1);
        Account accountTest = accounts.get(accounts.size() - 1);
        assertThat(accountTest.getPhoneNumber()).isEqualTo("09123334455");
        assertThat(accountTest.getBalance().longValue()).isEqualTo(88888L);

        accountRepository.delete(account);
    }

    @Test
    public void increaseRuleGroup() throws Exception {
        accountRepository.save(account);
        RequestModifyDTO requestIncreaseDTO = new RequestModifyDTO();
        requestIncreaseDTO.setPhoneNumber(account.getPhoneNumber());
        requestIncreaseDTO.setBalance(11111);

        restAccountMockMvc.perform(post("/api/increase")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(requestIncreaseDTO)))
                .andExpect(status().isCreated());

        List<Account> accounts = accountRepository.findAll();
//		assertThat(accounts).hasSize(databaseSizeBeforeCreate + 1);
        Account accountTest = accounts.get(0);
        assertThat(accountTest.getPhoneNumber()).isEqualTo("09123334455");
        assertThat(accountTest.getBalance().longValue()).isEqualTo(99999L);

        accountRepository.delete(account);
    }


    @Test
    public void decreaseRuleGroup() throws Exception {
        accountRepository.save(account);
        RequestModifyDTO requestModifyDTO = new RequestModifyDTO();
        requestModifyDTO.setPhoneNumber(account.getPhoneNumber());
        requestModifyDTO.setBalance(11111);

        restAccountMockMvc.perform(post("/api/decrease")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(requestModifyDTO)))
                .andExpect(status().is(201));

        List<Account> accounts = accountRepository.findAll();
//		assertThat(accounts).hasSize(databaseSizeBeforeCreate + 1);
        Account accountTest = accounts.get(0);
        assertThat(accountTest.getPhoneNumber()).isEqualTo("09123334455");
        assertThat(accountTest.getBalance().longValue()).isEqualTo(77777L);

        accountRepository.delete(account);
    }

    public static byte[] convertObjectToJsonBytes(Object object)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);

        return mapper.writeValueAsBytes(object);
    }

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

}
