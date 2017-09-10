package com.gp.qq2017_gp.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.gp.qq2017_gp.R;
import com.gp.qq2017_gp.utils.FragmentFactory;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    @InjectView(R.id.fl_content)
    FrameLayout flContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        init();

    }

    private void init() {
        initToolbar();
        initBottomNavigation();
    }

    private void initToolbar() {
        toolbar.setTitle("");//不设置的话title还是会有，且要在setsupportactionbar之前
        tvTitle.setText("我就是标题了怎么滴");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initBottomNavigation() {
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.conversation_selected_2, "消息"))
                .addItem(new BottomNavigationItem(R.mipmap.contact_selected_2, "好友"))
                .addItem(new BottomNavigationItem(R.mipmap.plugin_selected_2, "动态"));

        bottomNavigationBar.setActiveColor(R.color.btn_normal);
        bottomNavigationBar.setInActiveColor(R.color.inActive);

        bottomNavigationBar.initialise();

        bottomNavigationBar.setTabSelectedListener(mOnTabSelectedListener);
    }

    private BottomNavigationBar.OnTabSelectedListener mOnTabSelectedListener = new BottomNavigationBar.SimpleOnTabSelectedListener() {
        @Override
        public void onTabSelected(int position) {
            addFragment(position);
        }

        @Override
        public void onTabUnselected(int position) {
            hideFragment(position);
        }
    };

    private void addFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = FragmentFactory.getFragment(position);

        if (!fragment.isAdded())
            transaction.add(R.id.fl_content, fragment, "tag" + position);

        transaction.show(fragment);
        transaction.commit();
    }

    private void hideFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = FragmentFactory.getFragment(position);
        transaction.hide(fragment);

        transaction.commit();
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuBuilder menuBuilder = (MenuBuilder) menu;
        menuBuilder.setOptionalIconsVisible(true);//设置菜单的图标可见
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);//inflate菜单
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        switch (menuId) {
            case R.id.menu_add_friend:
                showToast("添加炮友");
                break;
            case R.id.menu_scan:
                showToast("扫一个炮友");
                break;
            case R.id.menu_about:
                showToast("关于约炮");
                break;
            case android.R.id.home://这个id应该是返回键
                finish();
                break;
        }

        return true;//返回true 消费掉事件
    }
}
