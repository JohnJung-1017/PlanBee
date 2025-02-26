package com.pj.planbee.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.pj.planbee.dto.ArchiveDTO;

@Mapper
public interface ArchiveMapper {
    
    // ���� ~ 6�� ��
    List<ArchiveDTO> findRecentArchives(
            @Param("userId") String userId, 
            @Param("startDate") String startDate, 
            @Param("endDate") String endDate);

    // ���� �ֽ� �����͸� ������ 6�ϰ��� ������
    List<ArchiveDTO> findLatestArchives(
            @Param("userId") String userId, 
            @Param("sixDaysAgo") String sixDaysAgo, 
            @Param("latestDate") String latestDate);

    // �ֽ� ��¥
    String findLatestDate(@Param("userId") String userId);
    
    // ��¥ �˻�
    List<ArchiveDTO> findArchives(
            @Param("userId") String userId, 
            @Param("searchDate") String searchDate);
    
    // ���� �˻�
    List<ArchiveDTO> searchByDetail(
    		@Param("userId") String userId, 
            @Param("keyword") String keyword);
    
 // Ư�� ��¥�� ������ ��������
    List<ArchiveDTO> findArchivesByDate(
            @Param("userId") String userId, 
            @Param("date") String date);
}
