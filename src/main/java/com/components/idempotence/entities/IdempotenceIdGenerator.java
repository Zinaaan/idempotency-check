package com.components.idempotence.entities;

import java.util.UUID;

/**
 * @author lzn
 * @date 2023/01/10 15:49
 * @description 幂等号生成类
 */
public class IdempotenceIdGenerator {

    public String generateId() {
        return UUID.randomUUID().toString();
    }
}
