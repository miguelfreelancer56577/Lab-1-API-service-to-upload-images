package com.github.mangelt.lab1.auth;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.github.mangelt.lab1.entity.AuthGroup;
import com.github.mangelt.lab1.entity.User;

public class ImageUserPrincipal implements UserDetails {
	
	private final User user;
	private final List<AuthGroup> authGroups;
	
	public ImageUserPrincipal(User user, List<AuthGroup> authGroups) {
		this.user = user;
		this.authGroups = authGroups;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(authGroups.isEmpty()) {
			return Collections.emptySet();
		}
		Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
		authGroups.forEach(group->{
           grantedAuthorities.add(new SimpleGrantedAuthority(group.getRoleGroup()));
        });
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUserId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return user.getIsAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return user.getIsAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return user.getIsCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return user.getIsEnabled();
	}

}
