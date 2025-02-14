package com.pj.planbee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pj.planbee.dto.TempUserDTO;

@Mapper
public interface TempUserMapper {
	public List<TempUserDTO> getTempUser(); //�׽�Ʈ ��� 
	public List<TempUserDTO> getTempUserData(@Param("tempUserId") String tempUserId); //�׽�Ʈ ���
	public int insertTempUser(TempUserDTO dto); //TempUser DB�� ����
	public int deleteTempUser(@Param("tempUserEmail") String tempUserEmail);
	public String getTempUserCode(String tempUserEmail); //TempUser DB�� �ӽ� ������ ���� ����
}