package org.almondiz.almondiz.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommonResult {

    private boolean success;

    private String msg;
}
