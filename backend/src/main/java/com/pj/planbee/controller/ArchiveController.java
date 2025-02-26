package com.pj.planbee.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pj.planbee.dto.ArchiveDTO;
import com.pj.planbee.service.ArchiveService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders= "*", allowCredentials = "true")
@RequestMapping("/archive") // ���� �ٲ�
public class ArchiveController {

	@Autowired ArchiveService as;
	
	//���� ���� �޼ҵ�(�α��� ����� ���� ����)
	@PostMapping(value = "/makeSession", produces = "application/json; charset=utf-8")
	public String session(HttpSession se) {
		se.setAttribute("sessionId", "admin");
		return (String) se.getAttribute("sessionId");
	}
	
	// �α׾ƿ�(�α��� ����� ���� ����)
    @PostMapping(value = "/logout", produces = "application/json; charset=utf-8")
    public int logout(HttpSession se) {
        se.invalidate();
        return 1; // �α׾ƿ� ����
    }

    // �α��� ���� Ȯ��(�α��� ����� ���� ����)
    @GetMapping(value = "/checkSession", produces = "application/json; charset=utf-8")
    public int checkSession(HttpSession se) {
        return (se.getAttribute("sessionId") != null) ? 1 : 0; // 1: �α��ε� ����, 0: �α��ε��� ����
    }
    
    //���Ⱑ �׽�Ʈ ����
    // ó�� /archive �������� �����ϸ� ���� ��¥�� �����̷�Ʈ
    @GetMapping(value = "/", produces = "application/json; charset=utf-8")
    public void redirectToYesterday(HttpServletResponse res) throws IOException {
        // ���� ��¥ ���ϱ�
        LocalDate yesterday = LocalDate.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String yesterdayDate = yesterday.format(formatter);
        
        // ���� ��¥�� �����̷�Ʈ
        res.sendRedirect("/archive/" + yesterdayDate);
    }

    // ���� ��¥ �Ǵ� �˻��� ��¥�� ������ ��������
    @GetMapping(value = "/{date}", produces = "application/json; charset=utf-8")
    public List<ArchiveDTO> getArchivesByDate(@PathVariable String date, HttpSession se) {
        // ���ǿ��� userId ��������
        String userId = (String) se.getAttribute("sessionId");

        // ���� ��¥ �Ǵ� �˻��� ��¥�� ������ ��������
        List<ArchiveDTO> archives = as.getArchivesWithDetails(userId);

        return archives;
    }
    //���Ⱑ �׽�Ʈ ������
    
    // ��¥ �˻�
    @GetMapping(value = "/searchDate/{date}", produces = "application/json; charset=utf-8")
    public List<ArchiveDTO> searchArchives(@PathVariable("date") String date, HttpSession se) {
        // ���� ��������
        String userId = (String) se.getAttribute("sessionId"); 

        // �˻�
        List<ArchiveDTO> archives = as.searchArchivesByDate(userId, date);

        return archives;
    }
     
    // ���� �˻�
    @GetMapping(value = "/searchKeyword/{keyword}", produces = "application/json; charset=utf-8")
    public List<ArchiveDTO> searchByDetail(@PathVariable String keyword, HttpSession se) {
        // ���ǿ��� userId ��������
        String userId = (String) se.getAttribute("sessionId");
        return as.searchByDetail(userId, keyword);
    }
}
