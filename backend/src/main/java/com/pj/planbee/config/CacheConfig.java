package com.pj.planbee.config;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Component;

import com.pj.planbee.dto.ArchiveDTO;

@Component
public class CacheConfig {

    public static final int CACHE_SIZE = 100;

    // ���ü� �ذ�
    public static final Map<String, List<ArchiveDTO>> archiveCache = new ConcurrentHashMap<>();

    // ��Ƽ������ ȯ�濡�� ����ȭ ����
    private static final ReentrantLock cacheLock = new ReentrantLock();

    // ��¥���� ĳ�� ���� (��Ƽ������ ȯ�� ����)
    public static void putCache(String userId, String date, List<ArchiveDTO> value) {
        String cacheKey = userId + "_" + date;

        cacheLock.lock(); // �� ȹ��
        try {
            if (archiveCache.size() >= CACHE_SIZE) {
                // ���� ������ �׸� ���� (LRU ���)
                String eldestKey = archiveCache.keySet().iterator().next();
                archiveCache.remove(eldestKey);
                System.out.println("ĳ�� ����: " + eldestKey);
            }
            archiveCache.put(cacheKey, value);
            System.out.println("ĳ�� �߰�: " + cacheKey);
        } finally {
            cacheLock.unlock(); // �� ����
        }
    }

    // ��¥���� ĳ�� ��ȸ (�б� �۾��� ����ȭ �ʿ� ����)
    public static List<ArchiveDTO> getCache(String userId, String date) {
        return archiveCache.get(userId + "_" + date);
    }

    // ĳ�� ���� ��� (�ݺ� �� ĳ�ð� �������� �ʵ��� ����ȭ �߰�)
    public static void printCacheStatus() {
        cacheLock.lock();
        try {
            System.out.println("���� ĳ�õ� ������ ���:");
            archiveCache.forEach((key, value) -> 
                System.out.println(" - " + key + " �� ������ : " + value.size() + " ��")
            );
        } finally {
            cacheLock.unlock(); // �� ����
        }
    }
}
