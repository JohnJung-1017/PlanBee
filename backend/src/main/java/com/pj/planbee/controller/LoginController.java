package com.pj.planbee.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pj.planbee.dto.LoginDTO;
import com.pj.planbee.dto.UserDTO;
import com.pj.planbee.service.LoginService;

@RestController
@CrossOrigin(origins = "*") // CSR�� ���� CORS ���
@RequestMapping("/auth")
public class LoginController {
	
	@Autowired LoginService service;
	
	@PostMapping(value="/login", produces = "application/json; charset=utf-8")
	public int login(@RequestBody UserDTO userDto, HttpSession session) {
        String userId = userDto.getUserId();
        String userPw = userDto.getUserPw();

        LoginDTO user = service.login(userId, userPw);

        if (user != null) {
            session.setAttribute("user", user); // ���� ����
            return 1; // �α��� ����
        } else {
            return -1; // �α��� ���� (���̵� �Ǵ� ��й�ȣ ����ġ)
        }
    }

    // �α׾ƿ�
    @PostMapping(value = "/logout", produces = "application/json; charset=utf-8")
    public int logout(HttpSession session) {
        session.invalidate();
        return 1; // �α׾ƿ� ����
    }
    

    // �α��� ���� Ȯ��
    @GetMapping(value = "/session", produces = "application/json; charset=utf-8")
    public int checkSession(HttpSession session) {
        return (session.getAttribute("user") != null) ? 1 : 0; // 1: �α��ε� ����, 0: �α��ε��� ����
    }
}
