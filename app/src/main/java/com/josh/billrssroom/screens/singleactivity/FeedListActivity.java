package com.josh.billrssroom.screens.singleactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.josh.billrssroom.R;
import com.josh.billrssroom.screens.common.ScreensNavigator;
import com.josh.billrssroom.screens.common.controllers.BackPressDispatcher;
import com.josh.billrssroom.screens.common.controllers.BackPressedListener;
import com.josh.billrssroom.screens.common.controllers.BaseActivity;
import com.josh.billrssroom.screens.common.fragmentframehelper.FragmentFrameWrapper;

import java.util.HashSet;
import java.util.Set;

import androidx.fragment.app.FragmentTransaction;

public class FeedListActivity extends BaseActivity implements BackPressDispatcher, FragmentFrameWrapper {

    public static void startClearTop(Context context){
        Intent intent = new Intent(context, FeedListActivity.class);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    private final Set<BackPressedListener> backPressedListeners = new HashSet<>();
    private ScreensNavigator screensNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_content_frame);
        screensNavigator = getCompositionRoot().getScreensNavigator();

        if (savedInstanceState == null){
            screensNavigator.toFeedList();
        }
    }

    @Override
    public void registerListener(BackPressedListener listener) {
        backPressedListeners.add(listener);
    }

    @Override
    public void unregisterListener(BackPressedListener listener) {
        backPressedListeners.remove(listener);
    }

    @Override
    public void onBackPressed() {
        boolean isBackPressedConsumedByAnyListener = false;
        for (BackPressedListener listener : backPressedListeners){
            if (listener.onBackPressed()){
                isBackPressedConsumedByAnyListener = true;
            }
        }
        if (!isBackPressedConsumedByAnyListener){
            super.onBackPressed();
        }
    }

    @Override
    public FrameLayout getFragmentFrame() {
        return findViewById(R.id.frame_content);
    }
}
