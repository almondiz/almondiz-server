package org.almondiz.almondiz.config.security;

import org.almondiz.almondiz.exception.exception.AccessDeniedException;
import org.almondiz.almondiz.exception.exception.AuthenticationEntryPointException;
import org.almondiz.almondiz.response.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {

    @GetMapping(value = "/entrypoint")
    public CommonResult entryPointException() {
        throw new AuthenticationEntryPointException();
    }

    @GetMapping(value = "/accessdenied")
    public CommonResult accessDeniedException() {
        throw new AccessDeniedException();
    }
}
