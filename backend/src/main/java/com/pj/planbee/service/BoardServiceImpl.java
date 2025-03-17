package com.pj.planbee.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.planbee.dto.BoardDTO;
import com.pj.planbee.mapper.BoardMapper;
@Service
public class BoardServiceImpl implements BoardService{
	@Autowired BoardMapper btMap;

	@Override
	public List<BoardDTO> getAllList() {
		List<BoardDTO> totalBoard = new ArrayList<BoardDTO>();
		
		try {
			totalBoard = btMap.getAllList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalBoard;
	}

	@Override
	public BoardDTO getView(int postId) {
		
		BoardDTO dto = null;
		try {
			dto = btMap.getView(postId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	@Override
	public int writePost(BoardDTO dto) {
		int result = 0;
		try {
			result = btMap.writePost(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int boardModify(BoardDTO dto, String sessionId, int postId) {
		int result = 0;
		String writer = btMap.getWriter(postId);
		dto.setPostId(postId);
		if(writer.equals(sessionId)) {
			try {
				result = btMap.boardModify(dto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			result = -1;
		}
		
		return result;
	}

	@Override
	public int boardDel(int postId, String sessionId) {
		String writer = btMap.getWriter(postId);
		int result = 0; 
		if(writer.equals(sessionId)) {
			try {
				result = btMap.boardDel(postId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			result = -1;
		}
		return result;
	}

	@Override
	public int boardHit(int postId) {
		int result = 0;
		try {
			result = btMap.boardHit(postId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<BoardDTO> boardGroup(int groupId) {
		List<BoardDTO> group = new ArrayList<BoardDTO>();
		try {
			group = btMap.boardGroup(groupId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return group;
	}

	@Override
	public List<BoardDTO> boardUser(String userId) {
		List<BoardDTO> user = new ArrayList<BoardDTO>();
		try {
			user = btMap.boardUser(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	@Override
	public List<BoardDTO> maxHit(int groupId) {
		List<BoardDTO> maxHit = new ArrayList<BoardDTO>();
		try {
			maxHit = btMap.maxHit(groupId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maxHit;
	}
	
	@Override
	public List<BoardDTO> newestSort(int groupId){
		List<BoardDTO> newestSort = new ArrayList<BoardDTO>();
		try {
			newestSort = btMap.newestSort(groupId);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return newestSort;
	}
	
	@Override
	public List<BoardDTO> oldestSort(int groupId){
		List<BoardDTO> oldestSort = new ArrayList<BoardDTO>();
		try {
			oldestSort = btMap.oldestSort(groupId);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return oldestSort;
	}

	@Override
	public int groupSearch(String sessionId) {
		int groupId = btMap.groupSearch(sessionId);
		return groupId;
	}

	@Override
	public List<BoardDTO> contentSearch(int groupId, String content) {
		List<BoardDTO> contents = new ArrayList<BoardDTO>();
		try {
			contents = btMap.contentSearch(groupId, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contents;
	}

	@Override
	public List<BoardDTO> titleSearch(int groupId, String content) {
		List<BoardDTO> contents = new ArrayList<BoardDTO>();
		try {
			contents = btMap.titleSearch(groupId, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contents;
	}

}
