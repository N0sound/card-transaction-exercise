package parser.bitmap;

import java.util.List;
import java.util.function.BiFunction;

import model.Transaction;
import parser.DataElementParser;

public interface BitMapStrategy {
	
	public int getSize();
	
	public boolean hasMandatoryFields(boolean[] dataElements);
	
	public List<DataElementParser> getDataElementParsers(boolean[] dataElements);
	
	public BiFunction<Transaction,String, String> getTransactionResponseFormatter();
}
