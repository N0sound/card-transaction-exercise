package parser.bitmap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import model.ExpirationDate;
import model.Transaction;
import model.TransactionAmount;
import parser.DataElementParser;
import parser.FixedLengthDataElementParser;
import parser.VariableLengthDataElementParser;

public class BitMapStrategyIso8583 implements BitMapStrategy {

	@Override
	public int getSize() {
		return 8;
	}

	@Override
	public boolean hasMandatoryFields(boolean[] dataElements) {
		return dataElements[0] && dataElements[1] && dataElements[2];
	}

	@Override
	public List<DataElementParser> getDataElementParsers(boolean[] dataElements) {
		List<DataElementParser> parsers = new ArrayList<>();
		if (dataElements[0]) {
			parsers.add(new VariableLengthDataElementParser((builder, str) -> {
				builder.creditCard(str);
			}));
		}
		if (dataElements[1]) {
			parsers.add(new FixedLengthDataElementParser(4, (builder, str) -> {
				builder.expirationDate(ExpirationDate.of(str));
			}));
		}
		if (dataElements[2]) {
			parsers.add(new FixedLengthDataElementParser(10, (builder, str) -> {
				builder.transactionAmount(TransactionAmount.of(str));
			}));
		}
		if (dataElements[4]) {
			parsers.add(new VariableLengthDataElementParser((builder, str) -> {
				builder.cardholderName(str);
			}));
		}
		if (dataElements[5]) {
			parsers.add(new FixedLengthDataElementParser(5, (builder, str) -> {
				builder.zipCode(str);
			}));
		}
		return parsers;
	}

	@Override
	public BiFunction<Transaction, String, String> getTransactionResponseFormatter() {
		return (transaction, responseCode) -> responseFormatter(transaction, responseCode);
	}

	private static String responseFormatter(Transaction transaction, String responseCode) {
		return transaction.getBitMap().getResponse() + Optional.ofNullable(transaction.getCreditCard()).orElse("")
				+ Optional.ofNullable(transaction.getExpirationDate()).map(ExpirationDate::getUnformattedString)
						.orElse("")
				+ Optional.ofNullable(transaction.getTransactionAmount()).map(TransactionAmount::getUnformattedString)
						.orElse("")
				+ responseCode + Optional.ofNullable(transaction.getCardholderName()).orElse("")
				+ Optional.ofNullable(transaction.getZipCode()).orElse("");
	}
}
