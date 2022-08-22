package com.detailretail.kurlyflow.admin.command.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkersResponse {
    private Long id;
    private String name;
    private String region;
}
