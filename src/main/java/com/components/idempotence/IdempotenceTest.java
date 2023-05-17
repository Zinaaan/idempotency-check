package com.components.idempotence;

import com.components.idempotence.annotations.IdempotenceRequired;
import com.components.idempotence.entities.IdempotenceIdGenerator;
import com.components.idempotence.entities.Order;

/**
 * @author lzn
 * @date 2023/01/10 16:05
 * @description
 */
public class IdempotenceTest {

    /**
     * Act as the interface caller(clients or any other services)
     */
    public static void main(String[] args) {
        IdempotenceIdGenerator idempotence = new IdempotenceIdGenerator();
        String idempotenceId = idempotence.generateId();
        //...通过feign框架将幂等号添加到http header中...

    }

    /**
     * Act as the interface implementer
     */
    public class OrderController {

        /**
         * Add the custom annotation IdempotenceRequired means this method need interface idempotence verification
         *
         * @return order
         */
        @IdempotenceRequired
        public Order createOrder() {
            // ...

            //此处为了不报错这样写，实际不会这样返回
            return new Order();
        }
    }

    //幂等校验逻辑在AOP进行操作，见Aop->IdempotenceSupportAdvice
}
