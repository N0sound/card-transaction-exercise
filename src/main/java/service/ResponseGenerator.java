package service;

import java.util.Optional;

import model.BitMap;
import model.ExpirationDate;
import model.Transaction;
import model.TransactionAmount;

class ResponseGenerator {

    private static final String RESPONSE_MESSAGE_TYPE = "0110";

    String generate(Transaction transaction, String responseCode) {
        return RESPONSE_MESSAGE_TYPE + getBitMapResponse(transaction.getBitMap())
                + Optional.ofNullable(transaction.getCreditCard()).orElse("")
                + Optional.ofNullable(transaction.getExpirationDate()).map(ExpirationDate::getUnformattedString)
                        .orElse("")
                + Optional.ofNullable(transaction.getTransactionAmount()).map(TransactionAmount::getUnformattedString)
                        .orElse("")
                + responseCode + Optional.ofNullable(transaction.getCardholderName()).orElse("")
                + Optional.ofNullable(transaction.getZipCode()).orElse("");
    }

    private static String getBitMapResponse(BitMap bitMap) {
        return Integer.toHexString(bitMap.getDecimalValue() + 16);
    }
}
