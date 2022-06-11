package com.lebrwcd.msmservice.service;

import java.util.Map;

/**
 * @author lebrwcd
 * @date 2022/5/12
 * @note
 */
public interface MsmService {

    boolean send(String phone, Map<String, Object> param);
}
