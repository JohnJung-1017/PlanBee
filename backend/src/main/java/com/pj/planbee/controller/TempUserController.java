package com.pj.planbee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pj.planbee.dto.TempUserDTO;
import com.pj.planbee.service.TempUserService;

@RestController
@CrossOrigin
@RequestMapping("/tempuser")
public class TempUserController {

    @Autowired
    TempUserService tempUserService;

    // �׽�Ʈ ��ü TempUser ��ȸ
    @GetMapping(value = "/all", produces = "application/json; charset=utf-8")
    public List<TempUserDTO> getAllTempUsers() {
        return tempUserService.getTempUser();
    }

    // �׽�Ʈ�� Ư�� TempUser ��ȸ
    @GetMapping(value = "/{tempUserId}", produces = "application/json; charset=utf-8")
    public List<TempUserDTO> getTempUserData(@PathVariable String tempUserId) {
        return tempUserService.getTempUserData(tempUserId);
    }

    // �̸��� ���� �ڵ� �߼� + TempUser ����
    @PostMapping(value = "/sendcode", produces = "application/json; charset=utf-8")
    public String sendVerificationCode(@RequestBody TempUserDTO dto) {
        try {
            tempUserService.insertTempUser(dto);
            return "���� �ڵ尡 �̸��Ϸ� �߼۵Ǿ����ϴ�.";
        } catch (Exception e) {
            e.printStackTrace();
            return "���� �ڵ� �߼ۿ� �����߽��ϴ�.";
        }
    }

    // TempUser ����
    @DeleteMapping(value = "/delete/{tempUserEmail}", produces = "application/json; charset=utf-8")
    public String deleteTempUser(@PathVariable String tempUserEmail) {
        int result = tempUserService.deleteTempUser(tempUserEmail);
        if (result > 0) {
            return "�ӽ� ����� �����Ͱ� �����Ǿ����ϴ�.";
        } else {
            return "���� ����: �ش� �̸����� �������� �ʽ��ϴ�.";
        }
    }
}