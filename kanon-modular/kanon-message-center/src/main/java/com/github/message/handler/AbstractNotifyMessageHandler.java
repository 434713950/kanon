package com.github.message.handler;

import com.github.kanon.common.notify.NotifyTemplate;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/11/1
 */
public abstract class AbstractNotifyMessageHandler<T extends NotifyTemplate> implements NotifyMessageHandler<T>{
    @Override
    public void excute(T notifyTemplate) {
        check(notifyTemplate);
        if (!process(notifyTemplate)){
            fail(notifyTemplate);
        }
        success(notifyTemplate);
    }

    @Override
    public abstract void check(T notifyTemplate);

    @Override
    public abstract Boolean process(T notifyTemplate);

    @Override
    public void success(T notifyTemplate){};

    @Override
    public void fail(T notifyTemplate) {}
}
