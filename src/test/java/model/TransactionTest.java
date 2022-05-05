package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionTest {

    private static final String CREDIT_CARD_NUMBER = "cc";
    private static final String CARDHOLDER_NAME = "name";
    private static final String ZIP_CODE = "zipCode";
    private static final BitMap BIT_MAP = new BitMap(0, new boolean[1]);

    private Transaction fixture;

    @BeforeEach
    void init() {
        fixture = Transaction.builder().creditCardNumber(CREDIT_CARD_NUMBER).cardholderName(CARDHOLDER_NAME)
                .zipCode(ZIP_CODE).bitMap(BIT_MAP).expirationDate(ExpirationDate.of("1122"))
                .transactionAmount(TransactionAmount.of("00000000200")).build();
    }

    @Test
    void shouldReturnFalseIfTransactionAmountMissing() {
        fixture = Transaction.builder().bitMap(BIT_MAP).build();
        assertFalse(fixture.hasAmount());
    }

    @Test
    void shouldReturnFalseIfExpirationMillisMissing() {
        fixture = Transaction.builder().bitMap(BIT_MAP).build();
        assertFalse(fixture.hasExpirationMillis());
    }

    @Test
    void shouldReturnAmount() {
        assertEquals(200L, fixture.getAmount());
    }

    @Test
    void shouldReturnExpirationMillis() {
        assertEquals(1669852800000L, fixture.getExpirationMillis());
    }
}
