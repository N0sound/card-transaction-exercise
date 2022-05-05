package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ResponseCodeTest {

    @Test
    void shouldReturnNumberOfResponseCodes() {
        assertEquals(3, ResponseCode.values().length);
    }

    @ParameterizedTest
    @MethodSource("provideForShouldReturnResponseCode")
    void shouldReturnResponseCode(String code, ResponseCode responseCode) {
        assertEquals(code, responseCode.getCode());
    }

    private static Stream<Arguments> provideForShouldReturnResponseCode() {
        return Stream.of(Arguments.of("OK", ResponseCode.OK), Arguments.of("DE", ResponseCode.DECLINED),
                Arguments.of("ER", ResponseCode.ERROR));
    }
}
