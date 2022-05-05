package service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import model.BitMap;
import model.ExpirationDate;
import model.ResponseCode;
import model.Transaction;
import model.TransactionAmount;

class ResponseGeneratorTest {

    private static final BitMap BIT_MAP = new BitMap(161, new boolean[8]);

    // Do not mock DTOs; purpose of mocking is to make relationship and interactions between objects visible
    private final ResponseGenerator fixture = new ResponseGenerator();

    @Test
    void shouldCreateResponseWithoutDataElements() {
        var transaction = Transaction.builder().bitMap(BIT_MAP).build();
        assertEquals("0110b1ER", fixture.generate(transaction, ResponseCode.ERROR));
    }

    @Test
    void shouldCreateResponseWithDataElementsMissingUnformattedString() {
        var transaction = Transaction.builder().bitMap(BIT_MAP).cardholderName("XXXXXX").zipCode("12345")
                .creditCardNumber("YYYYYYYYY").build();
        assertEquals("0110b1YYYYYYYYYERXXXXXX12345", fixture.generate(transaction, ResponseCode.ERROR));
    }

    @Test
    void shouldCreateResponse() {
        var transaction = Transaction.builder().bitMap(BIT_MAP).cardholderName("XXXXXX").zipCode("12345")
                .creditCardNumber("YYYYYYYYY").transactionAmount(TransactionAmount.of("0000000012"))
                .expirationDate(ExpirationDate.of("1121")).build();
        assertEquals("0110b1YYYYYYYYY11210000000012ERXXXXXX12345", fixture.generate(transaction, ResponseCode.ERROR));
    }
}
