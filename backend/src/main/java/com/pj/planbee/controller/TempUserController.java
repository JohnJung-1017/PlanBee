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
@CrossOrigin(origins="*")
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
    @PostMapping(value = "/sendCode", produces = "application/json; charset=utf-8")
    public int sendVerificationCode(@RequestBody TempUserDTO dto) {
        int result = tempUserService.insertOrUpdateTempUser(dto);

        if (result == -1) {
            return result; // �̹� ���Ե� ID
        } else if (result == -2) {
            return result; // �̹� ���Ե� �̸���
        } else if (result > 0) {
            return result; // �̸��� ���� �ڵ� �߼� ����
        } else {
            return 0; // �� �� ���� ����
        }
    }

    @PostMapping(value = "/verifyCode", produces = "application/json; charset=utf-8")
    public int verifyUserCode(@RequestBody TempUserDTO dto) {
    	int result = 0;
        String storedCode = tempUserService.getTempUserCode(dto.getTempUserEmail());

        if (storedCode != null && storedCode.equals(dto.getTempUserCode())) {
            // ���� ���� �� 1�� ������Ʈ
        	result = tempUserService.updateVerifyStatus(dto.getTempUserEmail());
            return result;
        }else {
        return -1; // ���� �ڵ� ����ġ
        }
    }
}