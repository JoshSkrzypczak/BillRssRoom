package com.josh.billrssroom.screens.common.views;

public interface ObservableViewMvc<ListenerType> extends ViewMvc {

    void registerListener(ListenerType listenerType);

    void unregisterListener(ListenerType listenerType);
}