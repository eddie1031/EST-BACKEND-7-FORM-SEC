package com.est.form_security.view;

import com.est.form_security.dto.SignUpRequest;
import com.est.form_security.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ViewController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "sign_in";
    }

    @GetMapping("/sign-up")
    public String showSignUpPage() {
        return "sign_up";
    }

    @PostMapping("/sign-up")
    public String doSignUp(SignUpRequest request) {
        memberService.save(request);
        return "redirect:index";
    }

}
