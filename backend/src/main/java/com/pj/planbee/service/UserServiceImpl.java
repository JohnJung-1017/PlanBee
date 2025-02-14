package com.pj.planbee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pj.planbee.dto.TempUserDTO;
import com.pj.planbee.dto.UserDTO;
import com.pj.planbee.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired UserMapper mapper;
	
	@Autowired UserService us;
	
	@Autowired TempUserService tus;
	
	@Transactional
	@Override
	public int regiseterUser(UserDTO user) {
	    int result = 0;

	    try {
	        // ���� �ߺ� �˻� (Real_User ���̺��� Ȯ��)
	        if (isUserIdExists(user.getUserId())) {
	            return -1; // �ߺ��� ID
	        }
	        if (isEmailExists(user.getUserEmail())) {
	            return -2; // �ߺ��� Email
	        }

	        // Temp_User ���̺��� ����� ������ ��������
	        TempUserDTO tempUser = tus.getTempUserByEmail(user.getUserEmail());

	        // ����� ���� �ڵ� �� (���� �ڵ� ����ġ �� ���� �Ұ�)
	        if (tempUser == null || !tempUser.getTempUserCode().equals(user.getTempUserCode())) {
	            return -3; // ���� �ڵ� ����ġ
	        }

	        // ����� Temp_User ������ �Է��� ���� �� (��� ���� ��ġ�ؾ� ���� ����)
	        if (!tempUser.getTempUserId().equals(user.getUserId()) ||
	            !tempUser.getTempUserPw().equals(user.getUserPw()) ||
	            !tempUser.getTempUserName().equals(user.getUserName()) ||
	            !tempUser.getTempUserPhone().equals(user.getUserPhone())) {
	            return -4; // Temp_User ���� ����ġ
	        }

	        // ��� ������ ��ġ�ϸ� Real_User ���̺� ���� ����
	        result = mapper.insertUser(user);

	        // ȸ������ ���� �� TempUser ����
	        if (result > 0) {
	            tus.deleteTempUser(user.getUserEmail());
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("ȸ������ ����");
	    }
	    return result;
	}



	
    @Override
    public boolean isUserIdExists(String userId) {
        return mapper.countUserId(userId) > 0;
    }

    @Override
    public boolean isEmailExists(String userEmail) {
        return mapper.countUserEmail(userEmail) > 0;
    }
	
}
