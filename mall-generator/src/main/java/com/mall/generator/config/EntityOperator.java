package com.mall.generator.config;

/**
 * EntityOperator
 *
 * @author youfu.wang
 * @date 2023/7/24
 **/
public enum EntityOperator {
    list("list"),
    get("get"),
    add("add"),
    update("update"),
    delete("delete");

    private final String operator;

    private EntityOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return this.operator;
    }


}
