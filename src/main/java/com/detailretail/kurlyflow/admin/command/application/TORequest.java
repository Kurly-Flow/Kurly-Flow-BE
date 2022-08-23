package com.detailretail.kurlyflow.admin.command.application;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TORequest {
    private LocalDate workingDate;
    private String workingTeam;
    private Integer workingNumbers;
}
