package com.pj.planbee.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.planbee.dto.ArchiveDTO;
import com.pj.planbee.mapper.ArchiveMapper;

@Service
public class ArchiveServiceImpl implements ArchiveService {

    @Autowired ArchiveMapper mapper;

    @Override
    public List<ArchiveDTO> getArchivesWithDetails(String userId) {
        
        LocalDate today = LocalDate.now(); // ����
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String endDate = today.minusDays(1).format(formatter);   // ���� 
        String startDate = today.minusDays(6).format(formatter); // 6�� �� 
        
        // ���� ~ 6�� �� 
        List<ArchiveDTO> archives = mapper.findRecentArchives(
            userId, startDate.toString(), endDate.toString());

        // ���� ���ٸ�, ���� �ֽ��� �����͸� ������ 6�ϰ� ��������
        if (archives.isEmpty()) {
            String latestDate = mapper.findLatestDate(userId);
            if (latestDate != null) {
            	 LocalDate latest = LocalDate.parse(latestDate, formatter);
                 String sixDaysAgo = latest.minusDays(5).format(formatter);
                 archives = mapper.findLatestArchives(userId, sixDaysAgo, latestDate);
            }
        }

        return archives;
    }
    
    
    @Override
    public List<ArchiveDTO> searchArchivesByDate(String userId, String date) {
        // �˻� ��¥ �������� 3�� �� ~ 2�� �� ������
        return mapper.findArchives(userId, date);
    }
}
