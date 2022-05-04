package service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import parser.BitMapParser;
import parser.TransactionParser;

class HighnoteIT {

    private static final String TRANSACTION_1 = "0100e016411111111111111112250000001000";
    private static final String TRANSACTION_2 = "0100e016401288888888188112250000011000";
    private static final String TRANSACTION_3 = "0100ec1651051051051051001225000001100011MASTER YODA90089";
    private static final String TRANSACTION_4 = "0100e016411111111111111112180000001000";
    private static final String TRANSACTION_5 = "01006012250000001000";

    private static final String RESPONSE_1 = "0110f016411111111111111112250000001000OK";
    private static final String RESPONSE_2 = "0110f016401288888888188112250000011000DE";
    private static final String RESPONSE_3 = "0110fc16510510510510510012250000011000OK11MASTER YODA90089";
    private static final String RESPONSE_4 = "0110f016411111111111111112180000001000DE";
    private static final String RESPONSE_5 = "01107012250000001000ER";

    private Highnote fixture;

    @BeforeEach
    void init() {
        fixture = new Highnote(new TransactionParser(new BitMapParser()), new Authorizor(), new ResponseGenerator());
    }

    @Test
    void shouldEvaluate() {
        var expected = new String[] { RESPONSE_1, RESPONSE_2, RESPONSE_3, RESPONSE_4, RESPONSE_5 };
        var actual = fixture.processTransactions(
                new String[] { TRANSACTION_1, TRANSACTION_2, TRANSACTION_3, TRANSACTION_4, TRANSACTION_5 });
        assertArrayEquals(expected, actual);
    }
}
