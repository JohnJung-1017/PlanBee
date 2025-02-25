package com.pj.planbee.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public int logout(HttpSession session) {
        session.invalidate();
        return 1; // �α׾ƿ� ����
    }

    // �α��� ���� Ȯ��(�α��� ����� ���� ����)
    @GetMapping(value = "/checkSession", produces = "application/json; charset=utf-8")
    public int checkSession(HttpSession session) {
        return (session.getAttribute("sessionId") != null) ? 1 : 0; // 1: �α��ε� ����, 0: �α��ε��� ����
    }
    

    @GetMapping(value = "/archives", produces = "application/json; charset=utf-8")
    public List<ArchiveDTO> getArchives(HttpSession session, Model model) {
    	
    	// ���ǿ��� userId ��������
        String userId = (String) session.getAttribute("sessionId");

        // ������ ��������
        List<ArchiveDTO> archives = as.getArchivesWithDetails(userId);

        return archives;
    }
    
    // ��¥ �˻�
    @GetMapping(value = "/search/{date}", produces = "application/json; charset=utf-8")
    public List<ArchiveDTO> searchArchives(@PathVariable("date") String date, HttpSession session) {
        // ���� ��������
        String userId = (String) session.getAttribute("sessionId"); 

        // �˻�
        List<ArchiveDTO> archives = as.searchArchivesByDate(userId, date);

        return archives;
    }
}
