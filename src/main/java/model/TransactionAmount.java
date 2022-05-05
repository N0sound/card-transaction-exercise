package model;

import lombok.Data;

@Data
public class TransactionAmount {

    private final String unformattedString;
    private final long amount;

    public static TransactionAmount of(String str) {
        return new TransactionAmount(str, Long.parseLong(str));
    }
}
