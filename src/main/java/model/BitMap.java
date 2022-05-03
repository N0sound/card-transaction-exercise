package model;

import java.util.List;
import java.util.function.BiFunction;

import lombok.Builder;
import lombok.Getter;
import parser.DataElementParser;

@Builder
@Getter
public class BitMap {

	private final String response;
	private final boolean hasMandatoryFields;
	private final List<DataElementParser> dataElementParsers;
	private final BiFunction<Transaction, String, String> responseFormatter;
}
