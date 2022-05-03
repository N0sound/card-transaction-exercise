package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TransactionAmountTest {

	@Test
	void shouldReturnOriginalString() {
		assertEquals("0000000001", TransactionAmount.of("0000000001").getUnformattedString());
		assertEquals("1000000000", TransactionAmount.of("1000000000").getUnformattedString());
		assertEquals("9999999999", TransactionAmount.of("9999999999").getUnformattedString());
	}

	@Test
	void shouldReturnAmount() {
		assertEquals(1L, TransactionAmount.of("0000000001").getAmount());
		assertEquals(1000000000L, TransactionAmount.of("1000000000").getAmount());
		assertEquals(9999999999L, TransactionAmount.of("9999999999").getAmount());

	}
}
