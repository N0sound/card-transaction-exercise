package model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BitMap {

    private final int decimalValue;
    private final boolean[] dataElements;
}
