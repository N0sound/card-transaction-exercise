package parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import model.BitMap;
import model.ExpirationDate;
import model.Transaction;
import model.TransactionAmount;

@ExtendWith(MockitoExtension.class)
class TransactionParserTest {

    // Do not mock DTOs; purpose of mocking is to make relationship and interactions between objects visible
    @Mock
    private BitMapParser bitMapParser;

    @InjectMocks
    private TransactionParser fixture;

    @Test
    void shouldReturnTransactionWithoutDataElements() {
        var bitMap = new BitMap(0, new boolean[] { false, false, false, false, false, false, false, false });
        Transaction expected = Transaction.builder().bitMap(bitMap).build();
        String transaction = "011070";
        given(bitMapParser.parse(transaction)).willReturn(bitMap);
        assertEquals(expected, fixture.parse(transaction));
    }

    @Test
    void shouldReturnTransactionWithSomeDataElements() {
        var bitMap = new BitMap(0, new boolean[] { true, false, false, false, true, true, false, false });
        Transaction expected = Transaction.builder().bitMap(bitMap).cardholderName("11MASTER YODA")
                .creditCardNumber("165105105105105100").zipCode("90089").build();
        String transaction = "0100ec16510510510510510011MASTER YODA90089";
        given(bitMapParser.parse(transaction)).willReturn(bitMap);
        assertEquals(expected, fixture.parse(transaction));
    }

    @Test
    void shouldReturnTransaction() {
        var bitMap = new BitMap(0, new boolean[] { true, true, true, false, true, true, false, false });
        Transaction expected = Transaction.builder().bitMap(bitMap).cardholderName("11MASTER YODA")
                .creditCardNumber("165105105105105100").expirationDate(ExpirationDate.of("1225"))
                .transactionAmount(TransactionAmount.of("0000011000")).zipCode("90089").build();
        String transaction = "0100ec1651051051051051001225000001100011MASTER YODA90089";
        given(bitMapParser.parse(transaction)).willReturn(bitMap);
        assertEquals(expected, fixture.parse(transaction));
    }
}
