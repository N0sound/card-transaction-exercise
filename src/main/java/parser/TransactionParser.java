package parser;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

import model.ExpirationDate;
import model.Transaction;
import model.Transaction.TransactionBuilder;
import model.TransactionAmount;

@RequiredArgsConstructor
public class TransactionParser {

    // According to the documentation the transaction will be in the correct format for parsing
    private static final int STARTING_POINTER = 6;

    private final BitMapParser bitMapParser;

    public Transaction parse(String transaction) {
        TransactionBuilder builder = Transaction.builder();

        var bitMap = bitMapParser.parse(transaction);
        builder.bitMap(bitMap);

        int ptr = STARTING_POINTER;
        for (var parser : getDataElementParsers(bitMap.getDataElements())) {
            ptr += parser.parse(ptr, transaction, builder);
        }
        return builder.build();
    }

    private List<DataElementParser> getDataElementParsers(boolean[] dataElements) {
        List<DataElementParser> parsers = new ArrayList<>();
        if (dataElements[0]) {
            parsers.add(new VariableLengthDataElementParser((builder, str) -> builder.creditCard(str)));
        }
        if (dataElements[1]) {
            parsers.add(new FixedLengthDataElementParser(4,
                    (builder, str) -> builder.expirationDate(ExpirationDate.of(str))));
        }
        if (dataElements[2]) {
            parsers.add(new FixedLengthDataElementParser(10,
                    (builder, str) -> builder.transactionAmount(TransactionAmount.of(str))));
        }
        if (dataElements[4]) {
            parsers.add(new VariableLengthDataElementParser((builder, str) -> builder.cardholderName(str)));
        }
        if (dataElements[5]) {
            parsers.add(new FixedLengthDataElementParser(5, (builder, str) -> builder.zipCode(str)));
        }
        return parsers;
    }
}
