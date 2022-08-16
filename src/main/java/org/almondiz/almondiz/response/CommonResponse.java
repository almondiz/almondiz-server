package org.almondiz.almondiz.response;

import lombok.Getter;

@Getter
public enum CommonResponse {
    SUCCESS("성공"),
    FAIL("실패");

    String msg;

    CommonResponse(String msg) {
        this.msg = msg;
    }
}
