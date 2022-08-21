package com.detailretail.kurlyflow.admin.command.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminSignUpRequest {

    private String name;
    private String employeeNumber;
    private String password;
}

