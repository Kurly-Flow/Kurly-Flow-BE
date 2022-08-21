package com.detailretail.kurlyflow.admin.command.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginRequest {

    private String employeeNumber;
    private String password;
}