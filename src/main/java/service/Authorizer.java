package service;

import lombok.RequiredArgsConstructor;
import model.Transaction;
import parser.TransactionParser;

@RequiredArgsConstructor
public class Authorizer {

	private static final String RESPONSE_MESSAGE_TYPE = "0110";
	private static final String RESPONSE_CODE_DECLINED = "DE";
	private static final String RESPONSE_CODE_ERROR = "ER";
	private static final String RESPONSE_CODE_OK = "OK";

	private final TransactionParser parser;

	public String[] authorize(String[] transactions) {
		String[] response = new String[transactions.length];
		for (int i = 0; i < transactions.length; i++) {
			var transaction = parser.build(transactions[i]);
			String responseCode = getResponseCode(transaction);
			response[i] = transaction.getResponseString(RESPONSE_MESSAGE_TYPE, responseCode);
		}
		return response;
	}

	private String getResponseCode(Transaction transaction) {
		if (!transaction.hasMandatoryFields()) {
			return RESPONSE_CODE_ERROR;
		}
		if (isAuthorizedForAmt(transaction) && transaction.getExpirationMillis() >= System.currentTimeMillis()) {
			return RESPONSE_CODE_OK;
		}
		return RESPONSE_CODE_DECLINED;
	}

	private boolean isAuthorizedForAmt(Transaction transaction) {
		if (transaction.getZipCode() != null) {
			return transaction.getAmount() < 200;
		}
		return transaction.getAmount() < 100;
	}
}
