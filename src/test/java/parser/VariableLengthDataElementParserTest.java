package parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;

import java.util.function.BiConsumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import model.Transaction.TransactionBuilder;

@ExtendWith(MockitoExtension.class)
class VariableLengthDataElementParserTest {

    @Mock
    private TransactionBuilder builder;
    @Mock
    private BiConsumer<TransactionBuilder, String> consumer;
    @Captor
    private ArgumentCaptor<String> elementCaptor;

    @InjectMocks
    private VariableLengthDataElementParser fixture;

    @Test
    void shouldParse() {
        String transaction = "01101000000000012345";
        assertEquals(12, fixture.parse(4, transaction, builder));
        then(consumer).should().accept(eq(builder), elementCaptor.capture());
        assertEquals("100000000001", elementCaptor.getValue());
    }

    @Test
    void shouldParseOnlyNumericaValuesIfNoVariableAlphaValues() {
        String transaction = "01100000000000012345";
        assertEquals(2, fixture.parse(4, transaction, builder));
        then(consumer).should().accept(eq(builder), elementCaptor.capture());
        assertEquals("00", elementCaptor.getValue());
    }
}
