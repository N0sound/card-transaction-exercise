package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ExpirationDateTest {

	@Test
	void shouldReturnOriginalString() {
		assertEquals("1101", ExpirationDate.of("1101").getUnformattedString());
	}

	@Test
	void shouldReturnExpirationDateMillisOfLastMillisOfTheMonth() {
		assertEquals(1009843199999L, ExpirationDate.of("1201").getExpirationMillis());
	}
}
