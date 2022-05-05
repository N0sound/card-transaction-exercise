package parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import model.BitMap;

class BitMapParserTest {

    private final BitMapParser fixture = new BitMapParser();

    @Test
    void parseFullBitMap() {
        var expected = new BitMap(255, new boolean[] { true, true, true, true, true, true, true, true });
        assertEquals(expected, fixture.parse("0100ff"));
    }

    @Test
    void parseEmptyBitMap() {
        var expected = new BitMap(0, new boolean[8]);
        assertEquals(expected, fixture.parse("010000"));
    }

    @Test
    void parseBitMap() {
        var expected = new BitMap(225, new boolean[] { true, true, true, false, false, false, false, true });
        assertEquals(expected, fixture.parse("0100e1"));
    }
}
