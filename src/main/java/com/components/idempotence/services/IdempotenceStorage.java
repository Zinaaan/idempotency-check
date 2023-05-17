package com.components.idempotence.services;

/**
 * @author lzn
 * @date 2023/01/10 15:44
 * @description 接口：用来读写幂等号
 */
public interface IdempotenceStorage {

    /**
     * save idempotenceId if it does not exists
     *
     * @param idempotenceId: the idempotence id
     * @return true if idempotenceId saved, otherwise return false
     */
    boolean saveIfAbsent(String idempotenceId);

    /**
     * delete idempotenceId
     *
     * @param idempotenceId: the idempotence id
     */
    void delete(String idempotenceId);

    /**
     * check idempotence is exist or not
     *
     * @param idempotenceId: the idempotence id
     * @return true if idempotenceId exist, otherwise return false
     */
    boolean check(String idempotenceId);
}
