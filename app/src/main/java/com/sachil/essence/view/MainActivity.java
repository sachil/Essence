package com.sachil.essence.view;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sachil.essence.R;
import com.sachil.essence.StatusBarUtils;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Toolbar mToolbar = null;
    private NavigationView mNavigation = null;
    private DrawerLayout mDrawer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtils.setColorForDrawerLayout(this, (DrawerLayout) findViewById(R.id.main_drawer), getPrimaryColor());
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mNavigation = (NavigationView) findViewById(R.id.main_navigation);
        mDrawer = (DrawerLayout) findViewById(R.id.main_drawer);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
    }
}
