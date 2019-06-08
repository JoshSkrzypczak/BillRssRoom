package com.josh.billrssroom.common;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseObservable<LISTENER_CLASS> {

    private final Set<LISTENER_CLASS> listeners = Collections.newSetFromMap(
            new ConcurrentHashMap<LISTENER_CLASS, Boolean>(1));

    public final void registerListener(LISTENER_CLASS listener){
        listeners.add(listener);
    }

    public final void unregisterListener(LISTENER_CLASS listener){
        listeners.remove(listener);
    }

    protected final Set<LISTENER_CLASS> getListeners(){
        return Collections.unmodifiableSet(listeners);
    }
}