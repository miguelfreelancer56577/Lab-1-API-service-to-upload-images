package com.github.mangelt.lab1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "USER")
@Data
public class User {
	@Id
    @Column(name="ID")
    private long id;
	@Column(name="USERID", nullable = false, unique = true)
	private String userId;
    @Column(name="NAME", nullable = false)
    private String name;
    @Column(name="LASTNAME", nullable = false)
    private String lastName;
    @Column(name="SECONDNAME", nullable = false)
    private String secondName;
    @Column(name="PASSWORD", nullable = false)
    private String password;
    @Column(name="EDGE", nullable = false)
    private String edge;
    @Column(name="ISACCOUNTNONEXPIRED", nullable = false, columnDefinition = "boolean default true")
    private boolean isAccountNonExpired;
    @Column(name="ISACCOUNTNONLOCKED", nullable = false, columnDefinition = "boolean default true")
    private boolean isAccountNonLocked;
    @Column(name="ISCREDENTIALSNONEXPIRED", nullable = false, columnDefinition = "boolean default true")
    private boolean isCredentialsNonExpired;
    @Column(name="ISENABLED", nullable = false, columnDefinition = "boolean default true")
    private boolean isEnabled;
}
