package com.mhealth.sample;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.ext.sidemenu.interfaces.Resourceble;
import android.view.ext.sidemenu.interfaces.ScreenShotable;
import android.view.ext.sidemenu.model.SlideMenuItem;
import android.view.ext.sidemenu.util.ViewAnimator;
import android.widget.LinearLayout;
import com.mhealth.sample.fragment.CircleProgressFragment;
import com.mhealth.sample.fragment.ContentFragment;
import com.walkingposture.R;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import mhealthapp.*;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements ViewAnimator.ViewAnimatorListener {

    public static final String CLOSE = "Close";
    public static final String BUILDING = "Building";
    public static final String BOOK = "Book";
    public static final String PAINT = "Paint";
    public static final String CASE = "Case";
    public static final String SHOP = "Shop";
    public static final String PARTY = "Party";
    public static final String MOVIE = "Movie";


    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ViewAnimator viewAnimator;
    private int res = R.drawable.content_music2;
    private LinearLayout linearLayout;
    private ContentFragment contentFragment;



/**
 *   cx
 *   修改主题背景
 *   R.drawable.splash2
 * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sildemenu_activity_main);

        contentFragment = ContentFragment.newInstance(R.drawable.content_music2);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, contentFragment)
                .commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });


        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(this, list, contentFragment, drawerLayout, this);
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(CLOSE, R.drawable.icn_close);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(BUILDING, R.drawable.icn_1);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(BOOK, R.drawable.icn_2);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(PAINT, R.drawable.icn_3);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(CASE, R.drawable.icn_4);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(SHOP, R.drawable.icn_5);
        list.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem(PARTY, R.drawable.icn_6);
        list.add(menuItem6);
        SlideMenuItem menuItem7 = new SlideMenuItem(MOVIE, R.drawable.icn_7);
        list.add(menuItem7);
    }


    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
/**
 * cx
 * 新增了更多标签中的id
 * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.main_feedback:
                return true;
            case R.id.main_praise:
                return true;
            case R.id.main_problem:
                return true;
            case R.id.main_software:
                return true;
            case R.id.main_version:
                return true;
            case R.id.main_us:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition) {
        this.res = this.res == R.drawable.content_music ? R.drawable.content_films : R.drawable.content_music;
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
        findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        ContentFragment contentFragment = ContentFragment.newInstance(this.res);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        return contentFragment;
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case CLOSE:
                return screenShotable;
            case BUILDING: //设备
                ConnectFragment connectFragment = new ConnectFragment ();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, connectFragment)
                        .commit();
                return connectFragment;


            case BOOK: // 采集

                CircleProgressFragment circleProgressFragment=new CircleProgressFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, circleProgressFragment).commit();
                return circleProgressFragment;

            case PAINT: //报告
                RemoteStorageFragment remoteStorageFragment = new RemoteStorageFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, remoteStorageFragment).commit();
                return remoteStorageFragment;

            case CASE: //行为识别
                ActivityRecognitionFragment recongnitionFragment = new ActivityRecognitionFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, recongnitionFragment).commit();
                return recongnitionFragment;

            case SHOP: //通知
                NotificationsFragment notificationsFragment = new NotificationsFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, notificationsFragment).commit();
                return notificationsFragment;
            case PARTY:// 视频
                GuidelinesFragment guidelinesFragment = new GuidelinesFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, guidelinesFragment).commit();
                return guidelinesFragment;
            case MOVIE: //数据可视化
                VisualizationFragment visualizationFragment = new VisualizationFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, visualizationFragment).commit();
                return visualizationFragment;

            default:
                return replaceFragment(screenShotable, position);
        }
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }
}
