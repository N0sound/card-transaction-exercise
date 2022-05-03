package parser.bitmap;

import java.util.List;

import lombok.RequiredArgsConstructor;
import model.BitMap;
import parser.DataElementParser;

@RequiredArgsConstructor
public class BitMapParser {

	private final BitMapStrategy strategy;

	public BitMap parse(String transaction) {
		int request = Integer.parseInt(transaction.substring(4, 6), 16);
		boolean[] dataElements = getDataElements(getBinary(request));
		boolean hasMandatoryFields = strategy.hasMandatoryFields(dataElements);
		List<DataElementParser> dataElementParsers = strategy.getDataElementParsers(dataElements);
		return BitMap.builder().dataElementParsers(dataElementParsers).hasMandatoryFields(hasMandatoryFields)
				.response(Integer.toHexString(request + 16))
				.responseFormatter(strategy.getTransactionResponseFormatter()).build();
	}

	private String getBinary(int value) {
		return String.format("%" + strategy.getSize() + "s", Integer.toBinaryString(value)).replace(' ', '0');
	}

	private boolean[] getDataElements(String binary) {
		boolean[] dataElements = new boolean[strategy.getSize()];
		int i = 0;
		for (char bit : binary.toCharArray()) {
			if (bit == '1') {
				dataElements[i] = true;
			}
			i++;
		}
		return dataElements;
	}
}
