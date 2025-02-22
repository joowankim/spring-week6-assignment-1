package com.codesoom.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
public class LoginData {

    @NotBlank
    @Email
    @Size(min = 3)
    private String email;

    @NotBlank
    @Size(min = 4, max = 1024)
    private String password;
}
