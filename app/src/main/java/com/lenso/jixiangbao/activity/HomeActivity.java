package com.lenso.jixiangbao.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.lenso.jixiangbao.R;
import com.lenso.jixiangbao.adapter.HomeViewPageAdapter;
import com.lenso.jixiangbao.fragment.TestFragment;
import com.lenso.jixiangbao.view.MenuItemView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by king on 2016/5/10.
 */
public class HomeActivity extends BaseActivity {
    private static final String TAG="HomeActivity";
    @Bind(R.id.menu_item_1)
    MenuItemView menuItem1;
    @Bind(R.id.menu_item_2)
    MenuItemView menuItem2;
    @Bind(R.id.menu_item_3)
    MenuItemView menuItem3;
    @Bind(R.id.menu_item_4)
    MenuItemView menuItem4;
    @Bind(R.id.ll_home)
    LinearLayout llHome;
    @Bind(R.id.vp_home)
    ViewPager vpHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android_home);
        ButterKnife.bind(this);
        initView();
    }
private void select(int index){
    menuItem1.setSelected(false);
    menuItem2.setSelected(false);
    menuItem3.setSelected(false);
    menuItem4.setSelected(false);
    switch (index){
        case 0:
            menuItem1.setSelected(true);
            break;
        case 1:
            menuItem2.setSelected(true);
            break;
        case 2:
            menuItem3.setSelected(true);
            break;
        case 3:
            menuItem4.setSelected(true);
            break;
    }
}
    private void initView() {
        List<Fragment> fragments=new ArrayList<>();
        fragments.add(new TestFragment());
        fragments.add(new TestFragment());
        fragments.add(new TestFragment());
        fragments.add(new TestFragment());

        HomeViewPageAdapter adapter = new HomeViewPageAdapter(getSupportFragmentManager(), fragments);
        vpHome.setAdapter(adapter);
        vpHome.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                select(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        select(0);
        menuItem1.setMenuItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vpHome.setCurrentItem(0);
            }
        });
        menuItem2.setMenuItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vpHome.setCurrentItem(1);
            }
        });
        menuItem3.setMenuItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vpHome.setCurrentItem(2);
            }
        });
        menuItem4.setMenuItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vpHome.setCurrentItem(3);
            }
        });
    }

}