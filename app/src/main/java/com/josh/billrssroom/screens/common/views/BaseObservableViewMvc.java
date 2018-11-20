package com.josh.billrssroom.screens.common.views;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseObservableViewMvc <ListenerType> extends BaseViewMvc implements
        ObservableViewMvc<ListenerType> {

    private Set<ListenerType> listeners = new HashSet<>();

    @Override
    public void registerListener(ListenerType listenerType) {
        listeners.add(listenerType);
    }

    @Override
    public void unregisterListener(ListenerType listenerType) {
        listeners.remove(listenerType);
    }

    protected Set<ListenerType> getListeners(){
        return Collections.unmodifiableSet(listeners);
    }

    protected void openDrawer() {

    }
}
