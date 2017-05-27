package com.kisen.plugframelib.utils.eventbus;

/**
 * 标题：EventBus 传递的数据模型
 * 作者：kisen
 * 版本：
 * 创建时间：on 2017/5/16 10:43.
 */
public class EventBusEntity {
    private int status;

    private Object result;

    public EventBusEntity(int status, Object result) {
        this.status = status;
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public int getStatus() {
        return status;
    }
}
