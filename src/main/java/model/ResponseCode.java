package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ResponseCode {
    DECLINED("DE"),
    ERROR("ER"),
    OK("OK");

    public final String code;
}
