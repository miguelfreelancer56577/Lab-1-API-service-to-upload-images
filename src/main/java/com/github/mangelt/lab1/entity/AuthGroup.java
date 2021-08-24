package com.github.mangelt.lab1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="AUTH_USER_GROUP")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthGroup {
	@Id
    @Column(name="AUTH_USER_GROUP_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="USERID")
    private String userId;
    @Column(name="AUTH_GROUP")
    private String roleGroup;
}
