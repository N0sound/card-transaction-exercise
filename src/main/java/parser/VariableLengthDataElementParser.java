package parser;

import java.util.function.BiConsumer;

import lombok.RequiredArgsConstructor;

import model.Transaction.TransactionBuilder;

@RequiredArgsConstructor
class VariableLengthDataElementParser implements DataElementParser {

    private static final int NUMERIC_LENGTH = 2;

    private final BiConsumer<TransactionBuilder, String> consumer;

    @Override
    public int parse(int index, String transaction, TransactionBuilder transactionBuilder) {
        int length = getLengthOfAlpha(index, transaction);
        String element = transaction.substring(index, index + NUMERIC_LENGTH + length);
        consumer.accept(transactionBuilder, element);
        return element.length();
    }

    private static int getLengthOfAlpha(int index, String transaction) {
        return Integer.parseInt(transaction.substring(index, index + NUMERIC_LENGTH));
    }
}
