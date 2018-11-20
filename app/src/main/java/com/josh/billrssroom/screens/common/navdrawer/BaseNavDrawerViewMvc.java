package com.josh.billrssroom.screens.common.navdrawer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;
import com.josh.billrssroom.R;
import com.josh.billrssroom.screens.common.views.BaseObservableViewMvc;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public abstract class BaseNavDrawerViewMvc<ListenerType> extends BaseObservableViewMvc<ListenerType>
        implements NavDrawerViewMvc {

    private final DrawerLayout drawerLayout;
    private final FrameLayout frameLayout;
    private final NavigationView navigationView;

    public BaseNavDrawerViewMvc(LayoutInflater inflater, @Nullable ViewGroup parent) {
        super.setRootView(inflater.inflate(R.layout.layout_drawer, parent, false));

        drawerLayout = findViewById(R.id.drawer_layout);
        frameLayout = findViewById(R.id.frame_content);
        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            drawerLayout.closeDrawers();
            if (menuItem.getItemId() == R.id.drawer_menu_favorites) {
                onDrawerItemClicked(DrawerItems.FAVORITES_LIST);
            }
            return false;
        });
    }

    protected abstract void onDrawerItemClicked(DrawerItems item);

    @Override
    protected void setRootView(View view) {
        frameLayout.addView(view);
    }

    @Override
    public boolean isDrawerOpen() {
        return drawerLayout.isDrawerOpen(GravityCompat.START);
    }

    @Override
    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void closeDrawer() {
        drawerLayout.closeDrawers();
    }
}
