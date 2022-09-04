package org.almondiz.almondiz.config.security;

import io.swagger.annotations.Api;
import org.almondiz.almondiz.exception.exception.CAccessDeniedException;
import org.almondiz.almondiz.exception.exception.CAuthenticationEntryPointException;
import org.almondiz.almondiz.response.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Spring Security 관련 컨트롤러"})
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {

    @GetMapping(value = "/entrypoint")
    public CommonResult entryPointException() {
        throw new CAuthenticationEntryPointException();
    }

    @GetMapping(value = "/accessdenied")
    public CommonResult accessDeniedException() {
        throw new CAccessDeniedException();
    }
}
