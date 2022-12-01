package com.example.member;

import com.example.member.dto.MemberDTO;
import com.example.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MemberTest {
    @Autowired
    private MemberService memberService;

    //@Rollback(value = true) -> DB에 저장 X

    //테스트는 독립적이어야 한다★★★
    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("회원가입 테스트")
    public void memberSaveTest() {
        //1. 회원가입 테스트
        //2. 신규회원 데이터 생성(DTO)
        //3. 회원가입 기능 실행
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail("testEmail");
        memberDTO.setMemberPassword("testPassword");
        memberDTO.setMemberName("testName");
        memberDTO.setMemberAge(1);
        memberDTO.setMemberPhone("111-1111-1111");
        Long saveId = memberService.save(memberDTO);
        //4. 가입 성공 후 가져온 id 값으로 DB 조회를 하고
        //5. 가입시 사용한 email 과 DB 에서 조회한 email 이 일치하는지를 판단
        //가입 시 사용한 email 은 DTO 에 email 이고
        //DB 에서 조회한 email 은 ↓
        MemberDTO savedMember = memberService.findById(saveId);
        assertThat(memberDTO.getMemberEmail()).isEqualTo(savedMember.getMemberEmail());
    }

    //테스트는 독립적이어야 한다★★★
    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("로그인 테스트")
    public void loginTest() {
        String loginEmail = "loginEmail";
        String loginPassword = "loginPassword";
        //1. 회원가입
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail(loginEmail);
        memberDTO.setMemberPassword(loginPassword);
        memberDTO.setMemberName("loginName");
        memberDTO.setMemberAge(2);
        memberDTO.setMemberPhone("222-2222-2222");
        memberService.save(memberDTO);
        //2. 로그인 수행
        MemberDTO loginDTO = new MemberDTO();
        loginDTO.setMemberEmail(loginEmail);
        loginDTO.setMemberPassword(loginPassword);
        MemberDTO loginResult = memberService.login(loginDTO);
        //3. 로그인 결과가 null 이 아니면 테스트 통과
        assertThat(loginResult).isNotNull();
    }

    //테스트는 독립적이어야 한다★★★

    // newMember 수정, 삭제
    public MemberDTO newMember() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail("testEmail");
        memberDTO.setMemberPassword("testPassword");
        memberDTO.setMemberName("testName");
        memberDTO.setMemberAge(0);
        memberDTO.setMemberPhone("000-0000-0000");
        return memberDTO;
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("수정 테스트")
    public void updateTest() {
        MemberDTO memberDTO = newMember();
        Long savedId = memberService.save(memberDTO);

        //수정용 MemberDTO
        memberDTO.setId(savedId);
        memberDTO.setMemberName("수정이름");

        //수정처리
        memberService.update(memberDTO);

        //DB 에서 조회한 이름이 수정할 때 사용한 이름과 같은지 확인
        MemberDTO memberDB = memberService.findById(savedId);
        assertThat(memberDB.getMemberName()).isEqualTo(memberDTO.getMemberName());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("삭제 테스트")
    public void deleteTest() {
        //회원가입
        MemberDTO memberDTO = newMember();
        Long savedId = memberService.save(memberDTO);

        //삭제처리
        memberService.delete(savedId);
    }
}
