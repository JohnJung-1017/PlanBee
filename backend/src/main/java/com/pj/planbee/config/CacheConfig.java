package com.pj.planbee.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.pj.planbee.dto.ArchiveDTO;

public class CacheConfig {

    // LRU ĳ�� (�ִ� 30�� ����, �ʰ��Ǹ� ���� ������ �����ͺ��� ����)
    public static final int CACHE_SIZE = 30;
    
    public static final Map<String, List<ArchiveDTO>> archiveCache =
    	    new LinkedHashMap<String, List<ArchiveDTO>>(CACHE_SIZE, 0.75f, true) {
    	        @Override
    	        protected boolean removeEldestEntry(Map.Entry<String, List<ArchiveDTO>> eldest) {
    	            return size() > CACHE_SIZE;  // ĳ�� ������ �ʰ� �� ���� ������ ������ ����
    	        }
    	    };

}
