package com.detailretail.kurlyflow.admin.command.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "region")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Region {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;


    @Column(name = "region")
    private String region;

    @Column(name = "to")
    private Long to;

    public Region(String region, Long to){
        Objects.requireNonNull(region, "region must not be null");
        Objects.requireNonNull(to, "TO must not be null");
        this.region = region;
        this.to = to;
    }


}
