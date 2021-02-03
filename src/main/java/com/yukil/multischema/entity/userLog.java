package com.yukil.multischema.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(schema = "logging")
@Getter
@Setter
public class userLog {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private LocalDateTime loginDateTime;
    private String behavior;

}
