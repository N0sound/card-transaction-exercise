package model;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class Transaction {

    @NonNull
    private final BitMap bitMap;
    private final String creditCard;
    private final ExpirationDate expirationDate;
    private final TransactionAmount transactionAmount;
    private final String cardholderName;
    private final String zipCode;

    public boolean hasExpirationMillis() {
        return Optional.ofNullable(expirationDate).map(ExpirationDate::getExpirationMillis).isPresent();
    }

    public boolean hasAmount() {
        return Optional.ofNullable(transactionAmount).map(TransactionAmount::getAmount).isPresent();
    }

    public long getExpirationMillis() {
        return expirationDate.getExpirationMillis();
    }

    public long getAmount() {
        return transactionAmount.getAmount();
    }
}
