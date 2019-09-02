package com.vension.fastframe.app.ui.bus.rx;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.disposables.CompositeDisposable;

/**
 * ========================================================================
 * 作 者：Vension
 * 主 页：Github: https://github.com/Vension
 * 日 期：2019/9/2 11:25
 *   ______       _____ _______ /\/|_____ _____            __  __ ______
 *  |  ____/\    / ____|__   __|/\/  ____|  __ \     /\   |  \/  |  ____|
 *  | |__ /  \  | (___    | |     | |__  | |__) |   /  \  | \  / | |__
 *  |  __/ /\ \  \___ \   | |     |  __| |  _  /   / /\ \ | |\/| |  __|
 *  | | / ____ \ ____) |  | |     | |    | | \ \  / ____ \| |  | | |____
 *  |_|/_/    \_\_____/   |_|     |_|    |_|  \_\/_/    \_\_|  |_|______|
 *
 * Take advantage of youth and toss about !
 * ------------------------------------------------------------------------
 * 描述：事件复合
 * ========================================================================
 */
public class EventComposite extends EventBase {
    private CompositeDisposable compositeDisposable;
    private Object object;
    private Set<EventSubscriber> subscriberEvents;

    public final CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public final void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    public final Object getObject() {
        return object;
    }

    public final void setObject(Class<?> object) {
        this.object = object;
    }

    public final Set<EventSubscriber> getSubscriberEvents() {
        return subscriberEvents;
    }

    public final void setSubscriberEvents(Set<EventSubscriber> subscriberEvents) {
        this.subscriberEvents = subscriberEvents;
    }

    public EventComposite(CompositeDisposable compositeDisposable, Object object, Set<EventSubscriber> subscriberEvents) {
        this.compositeDisposable = compositeDisposable;
        this.object = object;
        this.subscriberEvents = subscriberEvents;
    }

    /**
     * 发送粘性事件
     *
     * @param objectMap
     */
    public final void subscriberSticky(Map<Class<?>, Object> objectMap) {
        List<Class> classes = new ArrayList<>();
        for (Map.Entry<Class<?>, Object> classObjectEntry : objectMap.entrySet()) {
            for (EventSubscriber subscriberEvent : subscriberEvents) {
                if (classObjectEntry.getKey() == subscriberEvent.getParameter()) {
                    try {
                        classes.add(classObjectEntry.getKey());
                        subscriberEvent.handleEvent(classObjectEntry.getValue());
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        stickyEventMapRemove(classes);
    }
}
