package com.sachil.essence.ui;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sachil.essence.R;
import com.sachil.essence.StatusBarUtils;
import com.sachil.essence.presenter.GankPresenter;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Toolbar mToolbar = null;
    private NavigationView mNavigation = null;
    private DrawerLayout mDrawer = null;
    private SwipeRefreshLayout mRefreshLayout = null;
    private RecyclerView mRecyclerView = null;
    private ViewGroup mNavigationHeader = null;
    private ViewGroup mCategoryAll = null;
    private ViewGroup mCategoryAndroid = null;
    private ViewGroup mCategoryIos = null;
    private ViewGroup mCategoryWeb = null;
    private ViewGroup mCategoryResource = null;
    private ViewGroup mCategoryApp = null;
    private ViewGroup mCategoryPhoto = null;
    private ViewGroup mCategoryVideo = null;
    private ViewGroup mCategoryBookmark = null;
    private ViewGroup mSelectedCategory = null;
    private NavigationView mNavigationView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        new GankPresenter().listHistory();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(Gravity.LEFT);
                break;
            case R.id.action_about:
                break;
            case R.id.action_settings:
                break;
            case R.id.action_exit:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtils.setColorForDrawerLayout(this,
                (DrawerLayout) findViewById(R.id.main_drawer), getThemeColor());
    }

    @Override
    protected void changeTheme(int color) {
        super.changeTheme(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            ((ViewGroup) mDrawer.getChildAt(0)).removeViewAt(0);
        StatusBarUtils.setColorForDrawerLayout(this,
                mDrawer, color);
        mToolbar.setBackgroundColor(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setNavigationBarColor(color);
        mNavigationHeader.setBackgroundColor(color);
        setCategoryColor(mSelectedCategory, color);
    }

    @Override
    public void onClick(View v) {
        resetCategories();
        switch (v.getId()) {
            case R.id.category_all:
                setCategoryColor(mCategoryAll, getThemeColor());
                getSupportActionBar().setTitle(R.string.category_all);
                break;
            case R.id.category_android:
                setCategoryColor(mCategoryAndroid, getThemeColor());
                getSupportActionBar().setTitle(R.string.category_android);
                break;
            case R.id.category_ios:
                setCategoryColor(mCategoryIos, getThemeColor());
                getSupportActionBar().setTitle(R.string.category_ios);
                break;
            case R.id.category_web:
                setCategoryColor(mCategoryWeb, getThemeColor());
                getSupportActionBar().setTitle(R.string.category_web);
                break;
            case R.id.category_resource:
                setCategoryColor(mCategoryResource, getThemeColor());
                getSupportActionBar().setTitle(R.string.category_resource);
                break;
            case R.id.category_app:
                setCategoryColor(mCategoryApp, getThemeColor());
                getSupportActionBar().setTitle(R.string.category_app);
                break;
            case R.id.category_photo:
                setCategoryColor(mCategoryPhoto, getThemeColor());
                getSupportActionBar().setTitle(R.string.category_photo);
                break;
            case R.id.category_video:
                setCategoryColor(mCategoryVideo, getThemeColor());
                getSupportActionBar().setTitle(R.string.category_video);
                break;
            case R.id.category_bookmark:
                setCategoryColor(mCategoryBookmark, getThemeColor());
                getSupportActionBar().setTitle(R.string.category_bookmark);
                break;
        }
        mDrawer.closeDrawer(Gravity.LEFT);
    }

    private void setCategoryColor(View category, int color) {
        if (category instanceof ViewGroup) {
            mSelectedCategory = (ViewGroup) category;
            View image = mSelectedCategory.getChildAt(0);
            if (image instanceof ImageView) {
                Drawable drawable = ((ImageView) image).getDrawable();
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            }
            View text = mSelectedCategory.getChildAt(1);
            if (text instanceof TextView)
                ((TextView) text).setTextColor(color);
        }
    }

    private void resetCategories() {
        setCategoryColor(mCategoryAll, getNormalTextColor());
        setCategoryColor(mCategoryAndroid, getNormalTextColor());
        setCategoryColor(mCategoryIos, getNormalTextColor());
        setCategoryColor(mCategoryWeb, getNormalTextColor());
        setCategoryColor(mCategoryResource, getNormalTextColor());
        setCategoryColor(mCategoryApp, getNormalTextColor());
        setCategoryColor(mCategoryPhoto, getNormalTextColor());
        setCategoryColor(mCategoryVideo, getNormalTextColor());
        setCategoryColor(mCategoryBookmark, getNormalTextColor());
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mNavigation = (NavigationView) findViewById(R.id.main_navigation);
        mDrawer = (DrawerLayout) findViewById(R.id.main_drawer);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.category_all);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_refresh_layout);
        mRefreshLayout.setColorSchemeColors(getThemeColor());
        mRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.white);

        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        mRecyclerView.setOnClickListener(this);
        mNavigationHeader = (ViewGroup) findViewById(R.id.navigation_header);
        mCategoryAll = (ViewGroup) findViewById(R.id.category_all);
        mCategoryAll.setOnClickListener(this);
        setCategoryColor(mCategoryAll, getThemeColor());
        mCategoryAndroid = (ViewGroup) findViewById(R.id.category_android);
        mCategoryAndroid.setOnClickListener(this);
        mCategoryIos = (ViewGroup) findViewById(R.id.category_ios);
        mCategoryIos.setOnClickListener(this);
        mCategoryWeb = (ViewGroup) findViewById(R.id.category_web);
        mCategoryWeb.setOnClickListener(this);
        mCategoryResource = (ViewGroup) findViewById(R.id.category_resource);
        mCategoryResource.setOnClickListener(this);
        mCategoryApp = (ViewGroup) findViewById(R.id.category_app);
        mCategoryApp.setOnClickListener(this);
        mCategoryPhoto = (ViewGroup) findViewById(R.id.category_photo);
        mCategoryPhoto.setOnClickListener(this);
        mCategoryVideo = (ViewGroup) findViewById(R.id.category_video);
        mCategoryVideo.setOnClickListener(this);
        mCategoryBookmark = (ViewGroup) findViewById(R.id.category_bookmark);
        mCategoryBookmark.setOnClickListener(this);
        mNavigationView = (NavigationView) findViewById(R.id.main_navigation);
        mNavigationView.setOnClickListener(this);
    }
}
