package service;

import lombok.RequiredArgsConstructor;

import parser.TransactionParser;

@RequiredArgsConstructor
public class Highnote {

    private final TransactionParser parser;
    private final Authorizor authorizor;
    private final ResponseGenerator responseGenerator;

    public String[] processTransactions(String[] transactions) {
        String[] response = new String[transactions.length];
        for (int i = 0; i < transactions.length; i++) {
            var transaction = parser.parse(transactions[i]);
            String responseCode = authorizor.getResponseCode(transaction);
            response[i] = responseGenerator.generate(transaction, responseCode);
        }
        return response;
    }
}
