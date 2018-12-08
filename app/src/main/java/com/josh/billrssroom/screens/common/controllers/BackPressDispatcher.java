package com.josh.billrssroom.screens.common.controllers;

public interface BackPressDispatcher {

    void registerListener(BackPressedListener listener);

    void unregisterListener(BackPressedListener listener);
}
