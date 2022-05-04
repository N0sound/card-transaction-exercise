package parser;

import model.Transaction.TransactionBuilder;;

interface DataElementParser {

    int parse(int index, String transaction, TransactionBuilder builder);
}
