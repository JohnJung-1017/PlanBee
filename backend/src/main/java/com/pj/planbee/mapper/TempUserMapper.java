package com.pj.planbee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pj.planbee.dto.TempUserDTO;

@Mapper
public interface TempUserMapper {
	public List<TempUserDTO> getTempUser(); //�׽�Ʈ ��� 
	public List<TempUserDTO> getTempUserData(@Param("tempUserId") String tempUserId); //�׽�Ʈ ���
	public int insertTempUser(TempUserDTO dto); //TempUser DB�� ����(insertorupdate�� �۵� �� ����)
	public int deleteTempUser(@Param("tempUserEmail") String tempUserEmail);
	public String getTempUserCode(String tempUserEmail); //TempUser DB�� �ӽ� ������ ���� ����
	public int countUserId(String tempUserId);
	public int countUserEmail(String tempUserEmail);
	public TempUserDTO getTempUserByEmail(String tempUserEmail);
	public int updateVerifyStatus(@Param("tempUserEmail") String tempUserEmail); // �̸��� ���� �� verify_status ����
	public Integer getVerifyStatus(@Param("tempUserEmail") String tempUserEmail);
	public Integer checkTempUserExists(String tempUserEmail);
	public int updateTempUser(TempUserDTO dto);
	public void deleteExpiredTempUsers();
}