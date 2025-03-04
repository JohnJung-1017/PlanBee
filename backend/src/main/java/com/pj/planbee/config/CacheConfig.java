package com.pj.planbee.config;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.pj.planbee.dto.ArchiveDTO;

public class CacheConfig {

    // LRU ĳ�� (�ִ� 30�� ����, �ʰ��Ǹ� ���� ������ �����ͺ��� ����)
    public static final int CACHE_SIZE = 30;
    
    // ���� ������ ���� ó��
    public static final Map<String, List<ArchiveDTO>> archiveCache =
        new ConcurrentHashMap<>();
    
    //ĳ�� �߰� �� LRU ó��
    public static synchronized void putCache(String key, List<ArchiveDTO> value) {
        if (archiveCache.size() >= CACHE_SIZE) {
            // ���� ������ �׸� ����
            String eldestKey = archiveCache.keySet().iterator().next();
            archiveCache.remove(eldestKey);
            System.out.println("ĳ�� ����: " + eldestKey);
        }
        archiveCache.put(key, value);
        System.out.println("ĳ�� �߰�: " + key);
    }

    //ĳ�� ���� ��� (����ȭ �߰�)
    public static void printCacheStatus() {
        System.out.println("���� ĳ�õ� ������ ���:");
        archiveCache.forEach((key, value) -> 
            System.out.println(" - " + key + " �� ������ : " + value.size() + " ��")
        );
    }
}
