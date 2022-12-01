package com.example.member;

import com.example.member.dto.MemberDTO;
import com.example.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
// ↓ Assertions 클래스의 기능들을 사용할 수 있음 / assertj = test 를 위한 라이브러리 / static 을 써주면 간편하게 호출 가능
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

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
    //Test 가 끝나면 모든 흔적? 을 지워야 한다 ★★★
    @Transactional
    @Rollback(value = true)
    public void memberSaveTest() {
        //1,2,3
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail("testEmail1");
        memberDTO.setMemberPassword("testPassword");
        memberDTO.setMemberName("testName");
        memberDTO.setMemberAge(0);
        memberDTO.setMemberPhone("000-0000-0000");

        Long saveId = memberService.save(memberDTO);

        //4,5
        //가입 시 사용한 email 은 DTO 에 email 이고
        //DB 에서 조회한 email 은 ↓
        MemberDTO savedMember = memberService.findById(saveId);

        assertThat(memberDTO.getMemberEmail()).isEqualTo(savedMember.getMemberEmail());
    }
}
