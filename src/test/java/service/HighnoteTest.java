package service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.BDDMockito.willReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import model.BitMap;
import model.ResponseCode;
import model.Transaction;
import parser.TransactionParser;

@ExtendWith(MockitoExtension.class)
class HighnoteTest {

    private static final String TRANSACTION_STR_1 = "test1";
    private static final String TRANSACTION_STR_2 = "test2";
    private static final String RESPONSE_1 = "response1";
    private static final String RESPONSE_2 = "response2";
    private static final Transaction TRANSACTION_1 = Transaction.builder().bitMap(new BitMap(0, new boolean[1]))
            .build();
    private static final Transaction TRANSACTION_2 = Transaction.builder().bitMap(new BitMap(2, new boolean[1]))
            .build();

    @Mock
    private TransactionParser parser;
    @Mock
    private Authorizor authorizer;
    @Mock
    private ResponseGenerator responseGenerator;

    @InjectMocks
    private Highnote fixture;

    @Test
    void shouldReturnResponse() {
        willReturn(TRANSACTION_1).given(parser).parse(TRANSACTION_STR_1);
        willReturn(TRANSACTION_2).given(parser).parse(TRANSACTION_STR_2);
        willReturn(ResponseCode.ERROR).given(authorizer).getResponseCode(TRANSACTION_1);
        willReturn(ResponseCode.OK).given(authorizer).getResponseCode(TRANSACTION_2);
        willReturn(RESPONSE_1).given(responseGenerator).generate(TRANSACTION_1, ResponseCode.ERROR);
        willReturn(RESPONSE_2).given(responseGenerator).generate(TRANSACTION_2, ResponseCode.OK);
        assertArrayEquals(new String[] { RESPONSE_1, RESPONSE_2 },
                fixture.processTransactions(new String[] { TRANSACTION_STR_1, TRANSACTION_STR_2 }));
    }
}
