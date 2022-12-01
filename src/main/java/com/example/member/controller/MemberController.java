package com.example.member.controller;

import com.example.member.dto.MemberDTO;
import com.example.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

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
}
