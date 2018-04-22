package ir.zabetan.bankaccount.repository;

import ir.zabetan.bankaccount.domain.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account,String> {
    Account findAccountByPhoneNumber(String phoneNumber);
}
