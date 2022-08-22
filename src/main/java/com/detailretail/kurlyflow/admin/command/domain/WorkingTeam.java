package com.detailretail.kurlyflow.admin.command.domain;

import com.detailretail.kurlyflow.admin.exception.WorkingTeamNotMatchException;
import java.time.LocalTime;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorkingTeam {
  주간조(LocalTime.of(10, 00), LocalTime.of(19, 00)), 점심조(LocalTime.of(13, 00),
      LocalTime.of(22, 00)), 풀타임(LocalTime.of(15, 30), LocalTime.of(12, 50)), 미들(
      LocalTime.of(17, 00), LocalTime.of(12, 50)), 파트(LocalTime.of(19, 30), LocalTime.of(12, 50));

  private final LocalTime start;
  private final LocalTime end;

  public static WorkingTeam of(String team) {
    String teams = team.split("\\(")[0];
    return Arrays.stream(WorkingTeam.values())
        .filter(workingTeam -> workingTeam.name().equals(teams)).findFirst()
        .orElseThrow(WorkingTeamNotMatchException::new);
  }
}
