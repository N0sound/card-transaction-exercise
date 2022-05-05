package service;

import model.ResponseCode;
import model.Transaction;

class Authorizor {

    ResponseCode getResponseCode(Transaction transaction) {
        if (!hasMandatoryFields(transaction)) {
            return ResponseCode.ERROR;
        }
        if (isAuthorizedForAmt(transaction) && transaction.getExpirationMillis() > System.currentTimeMillis()) {
            return ResponseCode.OK;
        }
        return ResponseCode.DECLINED;
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
