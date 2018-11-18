package com.josh.billrssroom.screens.common.toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.josh.billrssroom.R;
import com.josh.billrssroom.screens.common.views.BaseViewMvc;

/**
 * This would be a good way to separate Toolbar logic.
 * Todo: Explore how this might work with a SearchView
 */
public class ToolbarViewMvc extends BaseViewMvc {

    public interface NavigateUpClickListener {
        void onNavigateUpClicked();
    }

    private final TextView textTitle;
    private final ImageButton btnBack;

    private NavigateUpClickListener navigateUpClickListener;

    public ToolbarViewMvc(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.layout_toolbar, parent, false));
        textTitle = findViewById(R.id.text_toolbar_title);
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            navigateUpClickListener.onNavigateUpClicked();
        });
    }

    public void setTitle(String title){
        textTitle.setText(title);
    }

    public void enableUpButtonAndListen(NavigateUpClickListener navigateUpClickListener){
        this.navigateUpClickListener = navigateUpClickListener;
        btnBack.setVisibility(View.VISIBLE);
    }
}
