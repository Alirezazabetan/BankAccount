package ir.zabetan.bankaccount.web.rest;

import ir.zabetan.bankaccount.domain.Account;
import ir.zabetan.bankaccount.service.AccountService;
import ir.zabetan.bankaccount.service.dto.RequestModifyDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/api")
public class AccountResource {


    private final AccountService accountService;

    public AccountResource(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@Valid @RequestBody Account account) throws Exception {
        if (account.getPhoneNumber()== null) {
            throw new Exception("A new account must have an phoneNumber");
        }
        if (null == accountService.findOne(account.getPhoneNumber())){
            Account result = accountService.save(account);
            return ResponseEntity.created(new URI("/api/create/" + result.getPhoneNumber()))
                    .headers(new HttpHeaders())
                    .body(result);
        }else {
            throw new Exception("The account is exist");
        }
    }

    @PostMapping("/increase")
    public ResponseEntity<Account> increaseAccount(@Valid @RequestBody RequestModifyDTO requestModifyDTO) throws Exception {
        if (requestModifyDTO.getPhoneNumber()== null) {
            throw new Exception("The account is not exist");
        }
        Account result = accountService.increase(requestModifyDTO);
        return ResponseEntity.created(new URI("/api/increase/" + result.getPhoneNumber()))
                .headers(new HttpHeaders())
                .body(result);
    }

    @PostMapping("/decrease")
    public ResponseEntity<Account> decreaseAccount(@Valid @RequestBody RequestModifyDTO RequestModifyDTO) throws Exception {
        if (RequestModifyDTO.getPhoneNumber()== null) {
            throw new Exception("The account is not exist");
        }
        Account result = accountService.decrease(RequestModifyDTO);
        return ResponseEntity.created(new URI("/api/decrease/" + result.getPhoneNumber()))
                .headers(new HttpHeaders())
                .body(result);
    }
}
