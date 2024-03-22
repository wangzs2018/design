package com.book.utils;

import com.book.pay.strategy.factory.MyEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
public class EnumUtils {
    private static final String MESSAGE = "INVALID ENUM CODE , %s , %s";

    /**
     * 根据枚举code获取枚举对象
     *
     * @param enumClass 枚举对象
     * @param code      code值
     * @param <T>
     * @return
     */
    public synchronized static <T extends MyEnum> T getEnumByCode(String code, Class<T> enumClass) {
        Optional<T> enumOptional = Stream.of(enumClass.getEnumConstants()).filter(item -> item.code().equals(code)).findAny();
        if (!enumOptional.isPresent()) {
            log.info(String.format(MESSAGE, enumClass.getName(), code));
            throw new RuntimeException(String.format(MESSAGE, enumClass.getName(), code));
        }
        return enumOptional.get();
    }
}