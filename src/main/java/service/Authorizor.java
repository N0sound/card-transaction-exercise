package service;

import model.Transaction;

class Authorizor {

    private static final String RESPONSE_CODE_DECLINED = "DE";
    private static final String RESPONSE_CODE_ERROR = "ER";
    private static final String RESPONSE_CODE_OK = "OK";

    String getResponseCode(Transaction transaction) {
        if (!hasMandatoryFields(transaction)) {
            return RESPONSE_CODE_ERROR;
        }
        if (isAuthorizedForAmt(transaction) && transaction.getExpirationMillis() >= System.currentTimeMillis()) {
            return RESPONSE_CODE_OK;
        }
        return RESPONSE_CODE_DECLINED;
    }

    private static boolean hasMandatoryFields(Transaction transaction) {
        var dataElements = transaction.getBitMap().getDataElements();
        return dataElements[0] && dataElements[1] && dataElements[2] && transaction.hasAmount()
                && transaction.hasExpirationMillis();
    }

    private static boolean isAuthorizedForAmt(Transaction transaction) {
        if (transaction.getZipCode() != null) {
            return transaction.getAmount() < 20000;
        }
        return transaction.getAmount() < 10000;
    }
}
