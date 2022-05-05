package service;

import java.util.Optional;

import model.ExpirationDate;
import model.ResponseCode;
import model.Transaction;
import model.TransactionAmount;

class ResponseGenerator {

    private static final String RESPONSE_MESSAGE_TYPE = "0110";

    String generate(Transaction transaction, ResponseCode responseCode) {
        return RESPONSE_MESSAGE_TYPE + getBitMapResponse(transaction)
                + Optional.ofNullable(transaction.getCreditCardNumber()).orElse("")
                + Optional.ofNullable(transaction.getExpirationDate()).map(ExpirationDate::getUnformattedString)
                        .orElse("")
                + Optional.ofNullable(transaction.getTransactionAmount()).map(TransactionAmount::getUnformattedString)
                        .orElse("")
                + responseCode.getCode() + Optional.ofNullable(transaction.getCardholderName()).orElse("")
                + Optional.ofNullable(transaction.getZipCode()).orElse("");
    }

    // This modifies the binary by adding 16; corresponds to adding a response code at index 3 of the bitmap
    private static String getBitMapResponse(Transaction transaction) {
        return Integer.toHexString(transaction.getBitMap().getDecimalValue() + 16);
    }
}
