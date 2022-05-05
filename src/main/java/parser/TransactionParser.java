package parser;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

import model.BitMap;
import model.ExpirationDate;
import model.Transaction;
import model.Transaction.TransactionBuilder;
import model.TransactionAmount;

@RequiredArgsConstructor
public class TransactionParser {

    // According to the documentation the transaction will be in the correct format for parsing
    private static final int STARTING_POINTER = 6;
    private static final DataElementParser CREDIT_CARD_NUMBER_PARSER = new VariableLengthDataElementParser(
            (builder, str) -> builder.creditCardNumber(str));
    private static final DataElementParser EXPIRATION_DATE_PARSER = new FixedLengthDataElementParser(4,
            (builder, str) -> builder.expirationDate(ExpirationDate.of(str)));
    private static final DataElementParser TRANSACTION_AMT_PARSER = new FixedLengthDataElementParser(10,
            (builder, str) -> builder.transactionAmount(TransactionAmount.of(str)));
    private static final DataElementParser CARDHOLDER_NAME_PARSER = new VariableLengthDataElementParser(
            (builder, str) -> builder.cardholderName(str));
    private static final DataElementParser ZIP_CODE_PARSER = new FixedLengthDataElementParser(5,
            (builder, str) -> builder.zipCode(str));

    private final BitMapParser bitMapParser;

    public Transaction parse(String transaction) {
        TransactionBuilder builder = Transaction.builder();

        var bitMap = bitMapParser.parse(transaction);
        builder.bitMap(bitMap);

        int ptr = STARTING_POINTER;
        for (var parser : selectDataElementParsers(bitMap)) {
            ptr += parser.parse(ptr, transaction, builder);
        }
        return builder.build();
    }

    private static List<DataElementParser> selectDataElementParsers(BitMap bitMap) {
        boolean[] dataElements = bitMap.getDataElements();
        List<DataElementParser> parsers = new ArrayList<>();
        if (dataElements[0]) {
            parsers.add(CREDIT_CARD_NUMBER_PARSER);
        }
        if (dataElements[1]) {
            parsers.add(EXPIRATION_DATE_PARSER);
        }
        if (dataElements[2]) {
            parsers.add(TRANSACTION_AMT_PARSER);
        }
        if (dataElements[4]) {
            parsers.add(CARDHOLDER_NAME_PARSER);
        }
        if (dataElements[5]) {
            parsers.add(ZIP_CODE_PARSER);
        }
        return parsers;
    }
}
