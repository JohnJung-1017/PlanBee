package com.pj.planbee.service;

import java.util.List;

import com.pj.planbee.dto.TempUserDTO;

public interface TempUserService {
	public List<TempUserDTO> getTempUser();	//�׽�Ʈ ���
	
	public List<TempUserDTO> getTempUserData(String tempUserId); //�׽�Ʈ ���
	
	public int insertTempUser(TempUserDTO dto); // DB�� �ӽ� ���� ���
	
	public int deleteTempUser(String tempUserEmail); // DB�� �ӽ� ������ ���� ���� ���
	
	public void sendCode(String tempUserEmail, String tempUserCode) throws Exception; // �ڵ带 ������ �̸��Ϸ� ������ ���
	
}
