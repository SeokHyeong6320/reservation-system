package com.project.userservice.client;

import com.project.domain.dto.UserDto;
import com.project.domain.dto.UserLoginDto;
import com.project.domain.model.SignDomainForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "security-service")
public interface SecurityServiceClient {

    @PostMapping("/security-service/register")
    ResponseEntity<UserDto> userRegister(@RequestBody SignDomainForm.SignUpForm form);


    @PostMapping("/security-service/login")
    ResponseEntity<UserLoginDto> login(@RequestBody SignDomainForm.SignInForm form);


}
