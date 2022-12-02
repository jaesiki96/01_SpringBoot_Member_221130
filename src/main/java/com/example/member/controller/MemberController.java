package com.example.member.controller;

import com.example.member.dto.MemberDTO;
import com.example.member.repository.MemberRepository;
import com.example.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    //회원가입 페이지
    @GetMapping("/save")
    public String saveForm() {
        return "memberPages/memberSave";
    }

    //회원가입
    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        memberService.save(memberDTO);
        return "index";
    }

    //로그인 페이지
    @GetMapping("/login")
    public String loginForm() {
        return "memberPages/memberLogin";
    }

    //로그인
    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            session.setAttribute("loginEmail", memberDTO.getMemberEmail());
            return "memberPages/memberMain";
        } else {
            return "memberPages/memberLogin";
        }
    }

    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

    //회원목록
    @GetMapping("/member/")
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "memberPages/memberList";
    }

    //회원조회
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "memberPages/memberDetail";
    }

    //수정 페이지
    @GetMapping("/update")
    public String updateForm(Model model, HttpSession session) {
        String loginEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.findByMemberEmail(loginEmail);
        model.addAttribute("member", memberDTO);
        return "memberPages/memberUpdate";
    }

    //회원정보 수정
    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return "memberPages/memberMain";
    }

    //회원삭제
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        memberService.delete(id);
        return "redirect:/member/";
    }

    //이메일 중복체크
    @PostMapping("/dup-check")
//    public @ResponseBody String emailDuplicateCheck(@RequestParam("inputEmail") String memberEmail) {
    public ResponseEntity emailDuplicateCheck(@RequestParam("inputEmail") String memberEmail) {
        String checkResult = memberService.emailDuplicateCheck(memberEmail);
//        return checkResult;
        if (checkResult != null) {
            return new ResponseEntity<>("사용해도 됩니다", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("사용할 수 없습니다", HttpStatus.CONFLICT);
        }
    }

    //상세조회(ajax)
//    @GetMapping("/detail-ajax")
//    public @ResponseBody MemberDTO detailAjax(@RequestParam("id") Long id) {
//        return memberService.findById(id);
//    }

    //상세조회(axios)
    @GetMapping("/ajax/{id}")
    public ResponseEntity findByIdAxios(@PathVariable Long id) {
        System.out.println("id = " + id);
        MemberDTO memberDTO = memberService.findById(id);
        if (memberDTO != null) {
            return new ResponseEntity<>(memberDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*
        get: /member/{id}
        post: /member/{id}
        delete: /member/{id}
        put: /member/{id}

    */

    //삭제(axios)
    @DeleteMapping("/{id}")
    public ResponseEntity deleteByAxios(@PathVariable Long id) {
        System.out.println("id = " + id);
        memberService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }






















}
