package service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import model.BitMap;
import model.ExpirationDate;
import model.ResponseCode;
import model.Transaction;
import model.TransactionAmount;

class AuthorizorTest {

    private static final boolean[] INVALID_DATA_ELEMENTS = new boolean[8];
    private static final String EXPIRATION_DATE_IN_FUTURE = "1299";
    private static final String EXPIRED_DATE = "0100";
    private static final String ZIP_CODE = "12345";
    private static final BitMap VALID_BIT_MAP = new BitMap(0,
            new boolean[] { true, true, true, false, false, false, false, false });

    // Do not mock DTOs; purpose of mocking is to make relationship and interactions between objects visible
    private final Authorizor fixture = new Authorizor();

    @Test
    void shouldReturnErrorIfMandatoryFieldsAreMising() {
        var bitMap = new BitMap(0, INVALID_DATA_ELEMENTS);
        var transaction = Transaction.builder().bitMap(bitMap).build();
        assertEquals(ResponseCode.ERROR, fixture.getResponseCode(transaction));
    }

    @Test
    void shouldReturnErrorIfAmountMissing() {
        var transaction = Transaction.builder().bitMap(VALID_BIT_MAP).build();
        assertEquals(ResponseCode.ERROR, fixture.getResponseCode(transaction));
    }

    @Test
    void shouldReturnErrorIfExpirationMissing() {
        var transaction = Transaction.builder().bitMap(VALID_BIT_MAP)
                .transactionAmount(TransactionAmount.of("0000010000")).build();
        assertEquals(ResponseCode.ERROR, fixture.getResponseCode(transaction));
    }

    @Test
    void shouldReturnDeclineIfZipMissingAndAmountGreaterThan99() {
        var transaction = Transaction.builder().bitMap(VALID_BIT_MAP)
                .expirationDate(ExpirationDate.of(EXPIRATION_DATE_IN_FUTURE))
                .transactionAmount(TransactionAmount.of("0000010000")).build();
        assertEquals(ResponseCode.DECLINED, fixture.getResponseCode(transaction));
    }

    @Test
    void shouldReturnDeclineIfZipPresentAndAmountGreaterThan199() {
        var transaction = Transaction.builder().bitMap(VALID_BIT_MAP)
                .expirationDate(ExpirationDate.of(EXPIRATION_DATE_IN_FUTURE))
                .transactionAmount(TransactionAmount.of("0000020000")).zipCode(ZIP_CODE).build();
        assertEquals(ResponseCode.DECLINED, fixture.getResponseCode(transaction));
    }

    @Test
    void shouldReturnDeclineIfZipMissingAndAmountValidButExpired() {
        var transaction = Transaction.builder().bitMap(VALID_BIT_MAP).expirationDate(ExpirationDate.of(EXPIRED_DATE))
                .transactionAmount(TransactionAmount.of("0000009900")).build();
        assertEquals(ResponseCode.DECLINED, fixture.getResponseCode(transaction));
    }

    @Test
    void shouldReturnDeclineIfZipPresentAndAmountValidButExpired() {
        var transaction = Transaction.builder().bitMap(VALID_BIT_MAP).expirationDate(ExpirationDate.of(EXPIRED_DATE))
                .transactionAmount(TransactionAmount.of("0000019900")).zipCode(ZIP_CODE).build();
        assertEquals(ResponseCode.DECLINED, fixture.getResponseCode(transaction));
    }

    @Test
    void shouldReturnOkIfZipMissingAndAmountValid() {
        var transaction = Transaction.builder().bitMap(VALID_BIT_MAP)
                .expirationDate(ExpirationDate.of(EXPIRATION_DATE_IN_FUTURE))
                .transactionAmount(TransactionAmount.of("0000009900")).build();
        assertEquals(ResponseCode.OK, fixture.getResponseCode(transaction));
    }

    @Test
    void shouldReturnOkIfZipPresentAndAmountValid() {
        var transaction = Transaction.builder().bitMap(VALID_BIT_MAP)
                .expirationDate(ExpirationDate.of(EXPIRATION_DATE_IN_FUTURE))
                .transactionAmount(TransactionAmount.of("0000019900")).zipCode(ZIP_CODE).build();
        assertEquals(ResponseCode.OK, fixture.getResponseCode(transaction));
    }
}
