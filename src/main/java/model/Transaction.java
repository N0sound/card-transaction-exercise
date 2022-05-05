package model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class Transaction {

    @NonNull
    private final BitMap bitMap;
    private final String creditCardNumber;
    private final ExpirationDate expirationDate;
    private final TransactionAmount transactionAmount;
    private final String cardholderName;
    private final String zipCode;

    public boolean hasExpirationMillis() {
        return expirationDate != null;
    }

    public boolean hasAmount() {
        return transactionAmount != null;
    }

    public long getExpirationMillis() {
        return expirationDate.getExpirationMillis();
    }

    public long getAmount() {
        return transactionAmount.getAmount();
    }
}
