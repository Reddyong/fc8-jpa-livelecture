package com.example.jpa.service;

import com.example.jpa.domain.Member;
import com.example.jpa.domain.Role;
import com.example.jpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    // 회원가입(패스워드 암호화, 권한부여)
    public Member register(Member member) {
        // 1. 패스워드 암호화
        String hashedPwd = passwordEncoder.encode(member.getPassword());
        member.setPassword(hashedPwd);

        // 2. 권한부여
        Set<Role> roleSet = new HashSet<>();

        Role userRole = roleService.findByRole("USER");
        roleSet.add(userRole);

//        Role managerRole = roleService.findByRole("MANAGER");
//        roleSet.add(managerRole);
//
//        Role adminRole = roleService.findByRole("ADMIN");
//        roleSet.add(adminRole);

        member.setRoles(roleSet);

        return memberRepository.save(member);
    }
}
