package com.yukil.multischema.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "setting")
@Setter
@Getter
public class member {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
}
