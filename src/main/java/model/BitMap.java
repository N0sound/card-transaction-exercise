package model;

import lombok.Data;

@Data
public class BitMap {

    private final int decimalValue;
    private final boolean[] dataElements;
}
