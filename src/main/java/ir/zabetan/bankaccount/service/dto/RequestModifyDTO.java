package ir.zabetan.bankaccount.service.dto;


import java.math.BigDecimal;

public class RequestModifyDTO {

    private String phoneNumber;
    private BigDecimal balance;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setBalance(long balance) {
        this.balance = new BigDecimal(balance);
    }

    @Override
    public String toString() {
        return "RequestModifyDTO{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", balance=" + balance +
                '}';
    }
}
