package com.xingcloud;

import com.xingcloud.model.Action;
import com.xingcloud.model.Update;

/**
 * 所有的方法都会立即返回。后台会有一个队列来进行发送操作。
 */
public interface IXA {

    /**
     * setup uid
     * @param uid
     */
    void setUid(String uid);

    /**
     * 发送一个事件。
     * @param action
     */
    void action(Action action);

    /**
     * 按照给定的时间戳，发送一个事件。
     * @param action
     * @param timestamp
     */
    void action(Action action, long timestamp);

    /**
     * 发送一条属性更新信息。
     * @param update
     */
    void update(Update update);

    /**
     * 关闭这个XA对象。
     */
    void close();
}
