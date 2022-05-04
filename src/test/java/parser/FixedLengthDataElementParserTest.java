package parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;

import java.util.function.BiConsumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import model.Transaction.TransactionBuilder;

@ExtendWith(MockitoExtension.class)
class FixedLengthDataElementParserTest {

    @Mock
    private TransactionBuilder builder;
    @Mock
    private BiConsumer<TransactionBuilder, String> consumer;
    @Captor
    private ArgumentCaptor<String> elementCaptor;

    @Test
    void shouldParse() {
        String transaction = "01100112";
        FixedLengthDataElementParser fixture = new FixedLengthDataElementParser(4, consumer);
        assertEquals(4, fixture.parse(4, transaction, builder));
        then(consumer).should().accept(eq(builder), elementCaptor.capture());
        assertEquals("0112", elementCaptor.getValue());
    }
}
