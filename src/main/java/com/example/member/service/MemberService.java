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
//            MemberDTO memberDTO = MemberDTO.toDTO(memberEntity);
//            memberDTOList.add(memberDTO);
            // 한 줄 표현
            memberDTOList.add(MemberDTO.toDTO(memberEntity));
        }
        return memberDTOList;
    }

    //회원조회
    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
//            MemberEntity memberEntity = optionalMemberEntity.get();
//            MemberDTO memberDTO = MemberDTO.toDTO(memberEntity);
//            return memberDTO;
            // 한 줄 표현
            return MemberDTO.toDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    //수정 페이지
    public MemberDTO findByMemberEmail(String loginEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(loginEmail);
        if (optionalMemberEntity.isPresent()) {
            return MemberDTO.toDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    //회원정보 수정
    //update 도 save 메서드 사용
    //Entity 에  값이 있으면 update
    //Entity 에  값이 없으면 insert
    public void update(MemberDTO memberDTO) {
        MemberEntity updateEntity = MemberEntity.toUpdateEntity(memberDTO);
        memberRepository.save(updateEntity);
    }

    //회원삭제
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    //이메일 중복체크
    public String emailDuplicateCheck(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if (optionalMemberEntity.isEmpty()) {
            return "ok";
        } else {
            return null;
        }
    }
}
