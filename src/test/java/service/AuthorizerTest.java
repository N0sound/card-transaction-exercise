package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import model.Transaction;
import parser.TransactionParser;

@ExtendWith(MockitoExtension.class)
class AuthorizerTest {

	private static final String RESPONSE_MESSAGE_TYPE = "0110";
	private static final String RESPONSE_CODE_DECLINED = "DE";
	private static final String RESPONSE_CODE_ERROR = "ER";
	private static final String RESPONSE_CODE_OK = "OK";
	private static final String TRANSACTION = "test";

	@Mock
	private TransactionParser parser;
	@Mock
	private Transaction transaction;
	@Captor
	private ArgumentCaptor<String> messageType;
	@Captor
	private ArgumentCaptor<String> responseCode;
	@InjectMocks
	private Authorizer fixture;

//	@BeforeAll
//	void init() {
//		given(transaction.getResponseString(any(), any())).willReturn("");
//	}

	@Test
	void shouldReturnResponseWithErrorCodeIfMandatoryFieldsAreMissing() {
		given(parser.build(TRANSACTION)).willReturn(transaction);
		fixture.authorize(new String[] { TRANSACTION });
		then(transaction).should().getResponseString(messageType.capture(), responseCode.capture());
		assertEquals(RESPONSE_MESSAGE_TYPE, messageType.getValue());
		assertEquals(RESPONSE_CODE_ERROR, responseCode.getValue());
	}

	@Test
	void shouldReturnResponseWithDeclineCodeIfZipCodeIsAbsentButAmountGreaterThan99() {
		given(parser.build(TRANSACTION)).willReturn(transaction);
		given(transaction.hasMandatoryFields()).willReturn(true);
		given(transaction.getAmount()).willReturn(100L);
		fixture.authorize(new String[] { TRANSACTION });
		then(transaction).should().getResponseString(messageType.capture(), responseCode.capture());
		assertEquals(RESPONSE_MESSAGE_TYPE, messageType.getValue());
		assertEquals(RESPONSE_CODE_DECLINED, responseCode.getValue());
	}

	@Test
	void shouldReturnResponseWithDeclineCodeIfZipCodeIsPresentButAmountGreaterThan199() {
		given(parser.build(TRANSACTION)).willReturn(transaction);
		given(transaction.hasMandatoryFields()).willReturn(true);
		given(transaction.getZipCode()).willReturn("zip");
		given(transaction.getAmount()).willReturn(200L);
		fixture.authorize(new String[] { TRANSACTION });
		then(transaction).should().getResponseString(messageType.capture(), responseCode.capture());
		assertEquals(RESPONSE_MESSAGE_TYPE, messageType.getValue());
		assertEquals(RESPONSE_CODE_DECLINED, responseCode.getValue());
	}

	@Test
	void shouldReturnResponseWithDeclineCodeIfExpirationDateLessThanCurrentTimeIfZipPresent() {
		given(parser.build(TRANSACTION)).willReturn(transaction);
		given(transaction.hasMandatoryFields()).willReturn(true);
		given(transaction.getZipCode()).willReturn("zip");
		given(transaction.getAmount()).willReturn(199L);
		given(transaction.getExpirationMillis()).willReturn(0L);
		fixture.authorize(new String[] { TRANSACTION });
		then(transaction).should().getResponseString(messageType.capture(), responseCode.capture());
		assertEquals(RESPONSE_MESSAGE_TYPE, messageType.getValue());
		assertEquals(RESPONSE_CODE_DECLINED, responseCode.getValue());
	}

	@Test
	void shouldReturnResponseWithDeclineCodeIfExpirationDateLessThanCurrentTimeIfZipAbsent() {
		given(parser.build(TRANSACTION)).willReturn(transaction);
		given(transaction.hasMandatoryFields()).willReturn(true);
		given(transaction.getAmount()).willReturn(99L);
		given(transaction.getExpirationMillis()).willReturn(0L);
		fixture.authorize(new String[] { TRANSACTION });
		then(transaction).should().getResponseString(messageType.capture(), responseCode.capture());
		assertEquals(RESPONSE_MESSAGE_TYPE, messageType.getValue());
		assertEquals(RESPONSE_CODE_DECLINED, responseCode.getValue());
	}

	@Test
	void shouldReturnResponseWithOkayCodeIfZipCodePresentAndAbountLessThan199() {
		given(parser.build(TRANSACTION)).willReturn(transaction);
		given(transaction.hasMandatoryFields()).willReturn(true);
		given(transaction.getZipCode()).willReturn("zip");
		given(transaction.getAmount()).willReturn(199L);
		given(transaction.getExpirationMillis()).willReturn(System.currentTimeMillis()+10000L);
		fixture.authorize(new String[] { TRANSACTION });
		then(transaction).should().getResponseString(messageType.capture(), responseCode.capture());
		assertEquals(RESPONSE_MESSAGE_TYPE, messageType.getValue());
		assertEquals(RESPONSE_CODE_OK, responseCode.getValue());
	}

	@Test
	void shouldReturnResponseWithOkayCodeIfZipCodeAbsentAndAbountLessThan99() {
		given(parser.build(TRANSACTION)).willReturn(transaction);
		given(transaction.hasMandatoryFields()).willReturn(true);
		given(transaction.getAmount()).willReturn(99L);
		given(transaction.getExpirationMillis()).willReturn(System.currentTimeMillis()+10000L);
		fixture.authorize(new String[] { TRANSACTION });
		then(transaction).should().getResponseString(messageType.capture(), responseCode.capture());
		assertEquals(RESPONSE_MESSAGE_TYPE, messageType.getValue());
		assertEquals(RESPONSE_CODE_OK, responseCode.getValue());
	}
}
