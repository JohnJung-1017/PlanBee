package com.pj.planbee.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pj.planbee.dto.LoginDTO;
import com.pj.planbee.dto.TempUserDTO;
import com.pj.planbee.dto.UserDTO;
import com.pj.planbee.service.LoginService;
import com.pj.planbee.service.TempUserService;
import com.pj.planbee.service.UserService;

@RestController
@CrossOrigin(origins = "*") // CSR�� ���� CORS ���
@RequestMapping("/auth")
public class AuthController {

    @Autowired TempUserService tempUserService;
    
    @Autowired UserService userService;

    @Autowired LoginService loginService;

    // �̸��� ���� �ڵ� �߼�
    @PostMapping(value = "/email/send", produces = "application/json; charset=utf-8")
    public int sendVerificationCode(@RequestBody TempUserDTO dto) {
        int result = tempUserService.insertOrUpdateTempUser(dto);

        if (result == -1) return result; // �̹� ���Ե� ID
        else if (result == -2) return result; // �̹� ���Ե� �̸���
        else if (result > 0) return result; // �̸��� ���� �ڵ� �߼� ����
        else return 0; // �� �� ���� ����
    }

    // �̸��� ���� �ڵ� Ȯ��
    @PostMapping(value = "/email/verify", produces = "application/json; charset=utf-8")
    public int verifyUserCode(@RequestBody TempUserDTO dto) {
        String storedCode = tempUserService.getTempUserCode(dto.getTempUserEmail());

        if (storedCode != null && storedCode.equals(dto.getTempUserCode())) {
            return tempUserService.updateVerifyStatus(dto.getTempUserEmail()); // ���� ����
        } else {
            return -1; // ���� �ڵ� ����ġ
        }
    }

    // ȸ������
    @PostMapping(value = "/register", produces = "application/json; charset=utf-8")
    public int registerUser(@RequestBody UserDTO user) {
        int result = userService.regiseterUser(user);

        if (result == -1) return result; // "ȸ������ ����: user_id �ߺ���"
        else if (result == -2) return result; // "ȸ������ ����: email �ߺ���"
        else if (result == -3) return result; // "ȸ������ ����: ���� �ڵ� ����ġ"
        else if (result == -4) return result; // "ȸ������ ����: ���� �Ϸ���� ����"
        else if (result > 0) return result; // "ȸ������ ����!"
        else return 0; // "ȸ������ ����!"
    }

    // (�׽�Ʈ) Ư�� userId�� �����ϴ��� Ȯ��
    @GetMapping(value = "/check-id/{userId}", produces = "application/json; charset=utf-8")
    public boolean checkUserId(@PathVariable String userId) {
        return userService.isUserIdExists(userId);
    }

    // (�׽�Ʈ) Ư�� email�� �����ϴ��� Ȯ��
    @GetMapping(value = "/check-email/{email}", produces = "application/json; charset=utf-8")
    public boolean checkEmail(@PathVariable String email) {
        return userService.isEmailExists(email);
    }

 // �α���
    @PostMapping(value = "/login", produces = "application/json; charset=utf-8")
    public int login(@RequestBody LoginDTO loginDTO, HttpSession session) {
        String userId = loginDTO.getUserId();
        String userPw = loginDTO.getUserPw();

        if (userId == null || userPw == null) {
            return -2; // �Է°��� null�̸� �α��� ���� (-2)
        }

        // �Ķ���͸� Map���� ����
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("userPw", userPw);

        LoginDTO user = loginService.login(paramMap);

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
