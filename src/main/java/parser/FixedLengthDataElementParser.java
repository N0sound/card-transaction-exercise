package parser;

import java.util.function.BiConsumer;

import lombok.RequiredArgsConstructor;
import model.Transaction.TransactionBuilder;

@RequiredArgsConstructor
public class FixedLengthDataElementParser implements DataElementParser {

	private final int length;
	private final BiConsumer<TransactionBuilder, String> consumer;

	@Override
	public int parse(int index, String transaction, TransactionBuilder transactionBuilder) {
		String element = transaction.substring(index, index + length);
		consumer.accept(transactionBuilder, element);
		return element.length();
	}
}
