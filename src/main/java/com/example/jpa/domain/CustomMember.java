package com.example.jpa.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
public class CustomMember extends User {    // UserDetails --> User(username, password, authorities)

    private Member member;

    public CustomMember(Member member) {    // Set<Role> -> Collection<GrantedAuthority>
        super(member.getUsername(), member.getPassword(), getToGrantedAuthorities(member.getRoles()));
        this.member = member;
    }

    // Set<Role> -> Collection<GrantedAuthority> --> SimpleGrantedAuthority
    private static Collection<? extends GrantedAuthority> getToGrantedAuthorities(Set<Role> roleSet) {
        // USER, MANAGER -> ROLE_USER, ROLE_MANAGER
        return roleSet.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())).collect(Collectors.toList());
    }
}
