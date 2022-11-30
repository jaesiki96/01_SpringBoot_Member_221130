package com.example.member;

import com.example.member.dto.MemberDTO;
import com.example.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberTest {
    @Autowired
    private MemberService memberService;

    //1. 회원가입 테스트
    //2. 신규회원 데이터 생성(DTO)
    //3. 회원가입 기능 실행
    //4. 가입 성공 후 가져온 id 값으로 DB 조회를 하고
    //5. 가입시 사용한 email 과 DB 에서 조회한 email 이 일치하는지를 판단

    @Test
    public void memberSaveTest() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail("testEmail");
        memberDTO.setMemberPassword("testPassword");
        memberDTO.setMemberName("testName");
        memberDTO.setMemberAge(20);
        memberDTO.setMemberPhone("010-0000-0000");

        Long saveId = memberService.save(memberDTO);
    }
}
