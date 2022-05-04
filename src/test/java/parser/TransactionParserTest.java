package parser;

import java.util.function.BiConsumer;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import model.BitMap;
import model.Transaction.TransactionBuilder;

@ExtendWith(MockitoExtension.class)
class TransactionParserTest {

    @Mock
    private BitMapParser bitMapParser;
    @Mock
    private BitMap bitMap;
    @Mock
    private BiConsumer<TransactionBuilder, String> consumer1, consumer2, consumer3, consumer4;
    @Captor
    private ArgumentCaptor<String> elementCaptor1, elementCaptor2, elementCaptor3, elementCaptor4;
    @InjectMocks
    private TransactionParser fixture;

    // @Test
    // void shouldReturnTransaction() {
    // String transaction = "0110c1041212111212EDWARD SMITH12345";
    // given(bitMapParser.parse(transaction)).willReturn(bitMap);
    // given(bitMap.getDataElementParsers()).willReturn(List.of(new VariableLengthDataElementParser(consumer1),
    // new FixedLengthDataElementParser(4, consumer2), new VariableLengthDataElementParser(consumer3),
    // new FixedLengthDataElementParser(5, consumer4)));
    // fixture.parse(transaction);
    // then(consumer1).should().accept(any(), elementCaptor1.capture());
    // then(consumer2).should().accept(any(), elementCaptor2.capture());
    // then(consumer3).should().accept(any(), elementCaptor3.capture());
    // then(consumer4).should().accept(any(), elementCaptor4.capture());
    // assertEquals("041212", elementCaptor1.getValue());
    // assertEquals("1112", elementCaptor2.getValue());
    // assertEquals("12EDWARD SMITH", elementCaptor3.getValue());
    // assertEquals("12345", elementCaptor4.getValue());
    // }
}
