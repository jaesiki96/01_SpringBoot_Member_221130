package com.example.member.service;

import com.example.member.dto.MemberDTO;
import com.example.member.entity.MemberEntity;
import com.example.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    //회원가입
    public Long save(MemberDTO memberDTO) {
        Long savedId = memberRepository.save(MemberEntity.toSaveEntity(memberDTO)).getId();
        return savedId;
    }

    //로그인
    public MemberDTO login(MemberDTO memberDTO) {
        /*
            email 로 DB 에서 조회를 하고
            사용자가 입력한 비번과 DB 에서 조회한 비번이 일치하는지를 판단해서
            로그인 성공, 실패 여부를 리턴
            단, email 조회결과가 없을 때도 실패
         */
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                //memberEntity.getMemberPassword() : DB에 있는 비밀번호 = memberDTO.getMemberPassword() : 입력 받은 비밀번호
                return MemberDTO.toDTO(memberEntity);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    //회원목록
    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity memberEntity : memberEntityList) {
            MemberDTO memberDTO = MemberDTO.toDTO(memberEntity);
            memberDTOList.add(memberDTO);
        }
        return memberDTOList;
    }

    //회원조회
    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            MemberDTO memberDTO = MemberDTO.toDTO(memberEntity);
            return memberDTO;
        } else {
            return null;
        }
    }
}
