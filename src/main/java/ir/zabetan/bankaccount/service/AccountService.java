package ir.zabetan.bankaccount.service;
import ir.zabetan.bankaccount.domain.Account;
import ir.zabetan.bankaccount.repository.AccountRepository;
import ir.zabetan.bankaccount.service.dto.RequestModifyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public int verify() {
        double randomValue = Math.random();
//        String url = String.format("https://api.github.com/users/%s", randomValue);
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject(url, String.class);
//        if (Double.valueOf(result) == randomValue){
//            return 1;
//        }else{
//            return 0;
//        }
        if (randomValue == randomValue){
            return 1;
        }else{
            return 0;
        }
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public Account findOne(String phoneNumber) {
        return accountRepository.findAccountByPhoneNumber(phoneNumber);
    }

    public Account increase(RequestModifyDTO requestModifyDTO) {
        Account result = findOne(requestModifyDTO.getPhoneNumber());
        result.setBalance(result.getBalance().add(requestModifyDTO.getBalance()));
        return update(result);
    }

    public Account decrease(RequestModifyDTO requestModifyDTO) throws Exception {
        Account result = findOne(requestModifyDTO.getPhoneNumber());
        if(verify()==1){
            result.setBalance(result.getBalance().subtract(requestModifyDTO.getBalance()));
        }else {
            throw new Exception("You dot have perimition to do the opration");
        }
        return update(result);
    }

    private Account update(Account account) {
        return accountRepository.save(account);
    }

    public void delete(Account result) {
        accountRepository.delete(result);
    }
}
