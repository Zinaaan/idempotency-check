package com.components.idempotence.entities;

import com.components.idempotence.services.IdempotenceStorage;
import org.springframework.stereotype.Component;

/**
 * @author lzn
 * @date 2023/01/10 15:44
 * @description 接口幂等实体
 */
@Component
public class Idempotence {

    private final IdempotenceStorage storage;

    public Idempotence(IdempotenceStorage storage) {
        this.storage = storage;
    }

    public boolean saveIfAbsent(String idempotenceId) {
        return storage.saveIfAbsent(idempotenceId);
    }

    public void delete(String idempotenceId) {
        storage.delete(idempotenceId);
    }

    public boolean check(String idempotenceId) {
        return storage.check(idempotenceId);
    }
}
