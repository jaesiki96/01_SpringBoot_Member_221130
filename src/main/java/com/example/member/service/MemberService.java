package com.example.member.service;

import com.example.member.dto.MemberDTO;
import com.example.member.entity.MemberEntity;
import com.example.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    //회원가입
    public Long save(MemberDTO memberDTO) {
        Long savedId = memberRepository.save(MemberEntity.toSaveEntity(memberDTO)).getId();
        return savedId;
    }
}
