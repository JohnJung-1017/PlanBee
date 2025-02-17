package com.pj.planbee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

import javax.mail.internet.MimeMessage;

import com.pj.planbee.dto.TempUserDTO;
import com.pj.planbee.mapper.TempUserMapper;

@Service
public class TempUserServiceImpl implements TempUserService{
    
    @Autowired TempUserMapper mapper;
    @Autowired JavaMailSender mailSender;

    @Transactional
    public int insertOrUpdateTempUser(TempUserDTO dto) {
        int result = 0;

        try {
        	//������ �� 5�� ���� ������ ����
        	mapper.deleteExpiredTempUsers();
        	 
            // ID �ߺ� �˻� (Real_User ���̺��� Ȯ��)
            if (mapper.countUserId(dto.getTempUserId()) > 0) {
                return -1; // �̹� ���Ե� ID
            }

            // Email �ߺ� �˻� (Real_User ���̺��� Ȯ��)
            if (mapper.countUserEmail(dto.getTempUserEmail()) > 0) {
                return -2; // �̹� ���Ե� �̸���
            }

            // ���� temp_user ���̺��� �̸����� �����ϴ��� Ȯ��
            Integer existingUser = mapper.checkTempUserExists(dto.getTempUserEmail());

            // 6�ڸ� ���� �ڵ� ����
            String verificationCode = generateVerificationCode();
            dto.setTempUserCode(verificationCode);
            dto.setVerifyStatus(0); // �⺻��: ���� �̿Ϸ� (false)

            // �̸��� ���� (�ڵ� ���� �� �ٷ� ����)
            sendCode(dto.getTempUserEmail(), verificationCode);

            if (existingUser != null && existingUser > 0) {
                // ���� �����Ͱ� �ִٸ� UPDATE ����
                result = mapper.updateTempUser(dto);
            } else {
                // ���� �����Ͱ� ������ INSERT ����
                result = mapper.insertTempUser(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("�̸��� ���� ����: " + e.getMessage());
        }
        return result;
    }


    public String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public int sendCode(String recipientEmail, String code) {
        int result = 0;
        
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(recipientEmail);
            helper.setSubject("PlanBee ȸ������ ���� �ڵ�");

            // �̸��� ���� ����
            String htmlContent = "<html><body style='margin: 0; padding: 0;'>"
                + "<table role='presentation' width='100%' cellspacing='0' cellpadding='0' border='0' style='background-color: #000000; width: 100%; padding: 20px 0;'>"
                + "<tr><td align='center'>"
                + "<table role='presentation' width='500' cellspacing='0' cellpadding='0' border='0' style='background-color: #000000; color: #ffffff; border-radius: 10px; padding: 20px; text-align: center;'>"
                + "<tr><td>"
                + "<h1 style='color: #FFD700;'>PlanBee ȸ������ ���� �ڵ�</h1>"
                + "<p style='font-size: 16px; margin-top: 20px;'>�Ʒ��� ���� �ڵ带 �Է��Ͽ� ȸ�������� �Ϸ��ϼ���.</p>"
                + "<div style='background-color: #FFD700; margin-top:10px; color: #000000; font-size: 24px; font-weight: bold; padding: 10px; border-radius: 5px; display: inline-block; margin: 10px 0;'>"
                + code + "</div>"
                + "<p style='margin-top: 20px;'>���� �ð����κ��� 5�� �̳��� ��Ȯ�� �Է��� �ּ���.</p>"
                + "<p style='margin-top: 30px; font-size: 12px; color: #aaaaaa;'>�� �̸����� �ڵ� �߼� �����Դϴ�. ȸ������ ������.</p>"
                + "</td></tr></table>"
                + "</td></tr></table>"
                + "</body></html>";

            helper.setText(htmlContent, true);
            
            mailSender.send(message);
            result = 1; // �̸��� ���� ����

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public int deleteTempUser(String tempUserEmail) {
        return mapper.deleteTempUser(tempUserEmail);
    }

    public String getTempUserCode(String tempUserEmail) {
        return mapper.getTempUserCode(tempUserEmail);
    }

	@Override
	public List<TempUserDTO> getTempUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TempUserDTO> getTempUserData(String tempUserId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public TempUserDTO getTempUserByEmail(String tempUserEmail) {
	    return mapper.getTempUserByEmail(tempUserEmail);
	}

	@Override
	public int insertTempUser(TempUserDTO dto) {
		// TODO Auto-generated method stub
		return mapper.insertTempUser(dto);
	}

	@Override
	public int updateVerifyStatus(String tempUserEmail) {
		// TODO Auto-generated method stub
		return mapper.updateVerifyStatus(tempUserEmail);
	}
	
	public Integer getVerifyStatus(String tempUserEmail) {
	    Integer status = mapper.getVerifyStatus(tempUserEmail);
	    return (status != null) ? status : 0; // NULL�̸� �⺻�� 0 ��ȯ
	}
	
}