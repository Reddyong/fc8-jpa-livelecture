package com.example.jpa.service;

import com.example.jpa.domain.CustomMember;
import com.example.jpa.domain.Member;
import com.example.jpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> optionalMember = memberRepository.findByUsername(username);

        if (optionalMember.isEmpty()) {
            throw new UsernameNotFoundException("user not found");
        }

        Member member = optionalMember.get();

        return new CustomMember(member);    // 패스워드 체크 -> SecurityContextHolder
    }
}
