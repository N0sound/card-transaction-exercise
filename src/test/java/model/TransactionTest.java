package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionTest {

    private static final String CREDIT_CARD = "cc";
    private static final String CARDHOLDER_NAME = "name";
    private static final String ZIP_CODE = "zipCode";

    @Mock
    private BitMap bitMap;
    @Mock
    private ExpirationDate expirationDate;
    @Mock
    private TransactionAmount transactionAmount;

    private Transaction fixture;

    @BeforeEach
    void init() {
        fixture = Transaction.builder().creditCard(CREDIT_CARD).cardholderName(CARDHOLDER_NAME).zipCode(ZIP_CODE)
                .bitMap(bitMap).expirationDate(expirationDate).transactionAmount(transactionAmount).build();
    }

    @Test
    void shouldReturnAmount() {
        given(transactionAmount.getAmount()).willReturn(2L);
        assertEquals(2L, fixture.getAmount());
    }

    @Test
    void shouldReturnExpirationMillis() {
        given(expirationDate.getExpirationMillis()).willReturn(4L);
        assertEquals(4L, fixture.getExpirationMillis());
    }
}
