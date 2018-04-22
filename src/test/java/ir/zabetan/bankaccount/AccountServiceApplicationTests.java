package ir.zabetan.bankaccount;

import ir.zabetan.bankaccount.domain.Account;
import ir.zabetan.bankaccount.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceApplicationTests {

	@Autowired
	private AccountService accountService;

	private Account account;

	public Account createEntity(){
		return new Account()
				.phoneNumber("09123334455")
				.balance(99999);
	}

	@Before
	public void run(){
		this.account = createEntity();
	}

	@Test
	public void save() {
		Account result = accountService.save(this.account);
		assertEquals(result.getPhoneNumber(),account.getPhoneNumber());
		assertEquals(result.getBalance().longValue(),account.getBalance().longValue());

		accountService.delete(result);
	}

	@Test
	public void find_one() {
//		accountService.save(this.account);
//
//		accountService.findOne(this.account.getPhoneNumber());
//		assertEquals(result.getPhoneNumber(),account.getPhoneNumber());
//		assertEquals(result.getBalance().longValue(),account.getBalance().longValue());
//
//		accountService.delete(result);
	}



}
