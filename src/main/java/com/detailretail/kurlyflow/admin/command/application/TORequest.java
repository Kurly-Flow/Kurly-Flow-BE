package com.detailretail.kurlyflow.admin.command.application;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TORequest {
    private LocalDateTime workingDate;
    private String workingTeam;
    private Long workingNumbers;
}
