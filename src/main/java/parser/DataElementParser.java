package parser;

import model.Transaction.TransactionBuilder;;

public interface DataElementParser {

	int parse(int index, String transaction, TransactionBuilder builder);
}
