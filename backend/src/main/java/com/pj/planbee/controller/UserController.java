package com.pj.planbee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pj.planbee.dto.UserDTO;
import com.pj.planbee.service.UserService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/user")  //  User API �⺻ ��� ����
public class UserController {

    @Autowired
    private UserService userService;

    //  ȸ�� ����
    @PostMapping(value = "/register", produces = "application/json; charset=utf-8")
    public int registerUser(@RequestBody UserDTO user) {
        int result = userService.regiseterUser(user);

        if (result == -1) {
            //"ȸ������ ����: user_id �ߺ���";
            return result;
        } else if (result == -2) {
            //"ȸ������ ����: email �ߺ���";
        	return result;
        } else if (result == -3) {
            //"ȸ������ ����: ���� �ڵ� ����ġ";
        	return result;
        } else if (result == -4) {
            //"ȸ������ ����: ���� ���� ����ġ";
        	return result;
        } else if (result > 0) {
            //"ȸ������ ����!";
        	return result;
        } else {
            //"ȸ������ ����!";
        	return 0;
        }
    }


    //  Ư�� userId�� �����ϴ��� Ȯ��
    @GetMapping(value = "/check-id/{userId}", produces = "application/json; charset=utf-8")
    public boolean checkUserId(@PathVariable String userId) {
        return userService.isUserIdExists(userId);
    }

    //  Ư�� email�� �����ϴ��� Ȯ��
    @GetMapping(value = "/check-email/{email}", produces = "application/json; charset=utf-8")
    public boolean checkEmail(@PathVariable String email) {
        return userService.isEmailExists(email);
    }
}
