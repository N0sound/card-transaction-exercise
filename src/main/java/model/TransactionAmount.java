package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TransactionAmount {

	private final String unformattedString;
	private final long amount;

	public static TransactionAmount of(String str) {
		return new TransactionAmount(str, Long.parseLong(str));
	}
}