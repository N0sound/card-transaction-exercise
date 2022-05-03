package parser;

import lombok.RequiredArgsConstructor;
import model.Transaction;
import model.Transaction.TransactionBuilder;
import parser.bitmap.BitMapParser;

@RequiredArgsConstructor
public class TransactionParser {

	// According to the documentation the transaction will be in the correct format for parsing
	private static final int STARTING_POINTER = 6;
	
	private final BitMapParser bitMapParser;

	public Transaction build(String transaction) {
		TransactionBuilder builder = Transaction.builder();

		var bitMap = bitMapParser.parse(transaction);
		builder.bitMap(bitMap);

		int ptr = STARTING_POINTER;
		for (var parser : bitMap.getDataElementParsers()) {
			ptr += parser.parse(ptr, transaction, builder);
		}
		return builder.build();
	}
}
