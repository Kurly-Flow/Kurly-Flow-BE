package com.detailretail.kurlyflow.admin.command.domain;

import com.detailretail.kurlyflow.worker.command.domain.Worker;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "regionTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegionTO {
    @Id
    @GeneratedValue
    @Column(name = "region")
    private String region;

    @Column(name = "workingNumber")
    private Long workingNumber;

    @Column(name="workingTeam")
    private String workingTeam;

    @Column(name="workingDate")
    private LocalDateTime workingDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "region")
    private List<Admin> admins = new ArrayList<>();

//    public RegionTO(String region, Long workingNumber, String workingTeam, LocalDateTime workingDate){
//        Objects.requireNonNull(region, "region must not be null");
//        Objects.requireNonNull(workingNumber, "TO must not be null");
//        Objects.requireNonNull(workingTeam, "team must not be null");
//        this.region = region;
//        this.workingNumber = workingNumber;
//        this.workingTeam = workingTeam;
//        this.workingDate = workingDate;
//    }
//
//    public void assignWorkingDate(LocalDateTime workingDate){
//        this.workingDate = workingDate;
//    }
//    public void assignWorkingTeam(String workingTeam){
//        this.workingTeam = workingTeam;
//    }
//    public void assignWorkingNumbers(Long workingNumber){
//        this.workingNumber = workingNumber;
//    }


}