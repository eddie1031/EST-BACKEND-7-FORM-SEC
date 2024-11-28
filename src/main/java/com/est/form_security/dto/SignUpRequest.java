package com.est.form_security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class SignUpRequest {
    private String username;
    private String password;
    private String email;
}
