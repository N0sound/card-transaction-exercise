package service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import model.Transaction;
import parser.TransactionParser;

@ExtendWith(MockitoExtension.class)
class HighnoteTest {

    private static final String RESPONSE_CODE_ERROR = "ER";
    private static final String TRANSACTION = "test";
    private static final String RESPONSE = "response";

    @Mock
    private TransactionParser parser;
    @Mock
    private Transaction transaction;
    @Mock
    private Authorizor authorizer;
    @Mock
    private ResponseGenerator responseGenerator;
    @InjectMocks
    private Highnote fixture;

    @Test
    void shouldReturnResponse() {
        given(parser.parse(TRANSACTION)).willReturn(transaction);
        given(authorizer.getResponseCode(transaction)).willReturn(RESPONSE_CODE_ERROR);
        given(responseGenerator.generate(transaction, RESPONSE_CODE_ERROR)).willReturn(RESPONSE);
        assertArrayEquals(new String[] { RESPONSE }, fixture.processTransactions(new String[] { TRANSACTION }));
    }
}
