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
	        if (isUserIdExists(user.getUserId())) {
	            System.out.println("ȸ�� ���� ���� : �̹� ���� �Ϸ� �� ID �Դϴ�.");
	            return -1;
	        }
	        if (isEmailExists(user.getUserEmail())) {
	            System.out.println("ȸ�� ���� ���� : �̹� ������ �Ϸ�� email �Դϴ�.");
	            return -2;
	        }

	        // �Է��� ���� �ڵ尡 Temp_User �����ͺ��̽��� tempuser_code�� ��ġ�ϴ��� Ȯ��
	        String storedCode = tus.getTempUserCode(user.getUserEmail()); // DB���� ����� �ڵ� ��������
	        if (storedCode == null || !storedCode.equals(user.getTempUserCode())) {
	            System.out.println("ȸ�� ���� ���� : ���� �ڵ� ����ġ");
	            return -3; // ���� �ڵ� ����ġ
	        }

	        // RealUser ���̺� ���� ����
	        result = mapper.insertUser(user);

	        // ȸ������ ���� �� TempUser ����
	        if (result > 0) {
	            tus.deleteTempUser(user.getUserEmail());
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
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
