package application;

import lombok.RequiredArgsConstructor;
import service.Authorizer;

@RequiredArgsConstructor
public class Highnote {
	
	private final Authorizer authorizer;
	
	public String[] processTransactions(String[] transactions) {
		return authorizer.authorize(transactions);
	}
}
