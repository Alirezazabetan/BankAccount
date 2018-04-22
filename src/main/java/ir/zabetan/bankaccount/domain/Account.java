package ir.zabetan.bankaccount.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document
public class Account {

    @Id
    private String phoneNumber;

    private BigDecimal balance;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Account phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Account balance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public Account balance(long balance) {
        this.balance = new BigDecimal(balance);
        return this;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setBalance(long balance) {
        this.balance = new BigDecimal(balance);
    }

    @Override
    public String toString() {
        return "Account{" +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                ", balance=" + getBalance() +
                '}';
    }
}
