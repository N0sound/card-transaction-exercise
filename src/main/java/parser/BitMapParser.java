package parser;

import lombok.RequiredArgsConstructor;

import model.BitMap;

@RequiredArgsConstructor
public class BitMapParser {

    private static final int SIZE = 8;

    BitMap parse(String transaction) {
        int request = Integer.parseInt(transaction.substring(4, 6), 16);
        boolean[] dataElements = getDataElements(getBinary(request));
        return BitMap.builder().dataElements(dataElements).decimalValue(request).build();
    }

    private static String getBinary(int value) {
        String format = "%" + SIZE + "s";
        return String.format(format, Integer.toBinaryString(value)).replace(' ', '0');
    }

    private static boolean[] getDataElements(String binary) {
        boolean[] dataElements = new boolean[SIZE];
        int i = 0;
        for (char bit : binary.toCharArray()) {
            if (bit == '1') {
                dataElements[i] = true;
            }
            i++;
        }
        return dataElements;
    }
}
