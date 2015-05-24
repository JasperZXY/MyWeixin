package com.jasper.myweixin.activity;

import android.app.Activity;
import android.content.Context;
import android.nfc.Tag;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.jasper.myweixin.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主页面
 * @author Jasper
 */
public class MainActivity extends Activity {
    private Context context;

    private static final String TAG = "MainActivity";

    private ViewPager mTabPager;
    private ImageView mTabImgBottom;//tab下面的阴影
    private ImageView[] mTabViews;  //4个tab对应的图片
    private int[] imageNormalIds;
    private int[] imagePressedIds;
    private int lastIndex = 0;// 上一次tab index
    private int screenWidth;

    private ListView listViewWeixin;
    private ArrayList<View> views = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Display currDisplay = getWindowManager().getDefaultDisplay();
        screenWidth = currDisplay.getWidth();
        context = this;

        mTabPager = (ViewPager) findViewById(R.id.main_tab_pager);
        //添加监听器
        mTabPager.setOnPageChangeListener(new TabPagerChangerListener());

        int[] tabViewIds = new int[]{R.id.img_weixin, R.id.img_address, R.id.img_frends, R.id.img_settings};
        imageNormalIds = new int[]{R.drawable.tab_weixin_normal, R.drawable.tab_address_normal, R.drawable.tab_find_frd_normal, R.drawable.tab_settings_normal};
        imagePressedIds = new int[]{R.drawable.tab_weixin_pressed, R.drawable.tab_address_pressed, R.drawable.tab_find_frd_pressed, R.drawable.tab_settings_pressed};
        mTabImgBottom =  (ImageView) findViewById(R.id.main_tab_bottom);
        mTabViews = new ImageView[tabViewIds.length];
        for (int i=0; i<tabViewIds.length; i++) {
            ImageView view = (ImageView) findViewById(tabViewIds[i]);
            mTabViews[i] = view;
            view.setOnClickListener(new TabViewClickListener(i));
        }

        LayoutInflater lf = LayoutInflater.from(this);
        int[] tabIds = new int[]{R.layout.main_tab_weixin, R.layout.main_tab_address, R.layout.main_tab_friends, R.layout.main_tab_settingss};
       // final ArrayList<View> views = new ArrayList<>();
        for (int i=0; i<tabIds.length; i++) {
            views.add(lf.inflate(tabIds[i], null));
        }
        mTabPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
            @Override
            public Object instantiateItem(View container, int position) {
                ((ViewPager)container).addView(views.get(position));
                return views.get(position);
            }


            @Override
            public void destroyItem(View container, int position, Object object) {
                ((ViewPager)container).removeView(views.get(position));
            }
        });

        //FIXME 特别要注意一下，views.get(0)的写法要改一下
        listViewWeixin = (ListView) (views.get(0).findViewById(R.id.list_weixin_1));
        List<Map<String, Object>> listItems = new ArrayList<>();
        for (int i=0; i<20; i++) {
            Map<String, Object> map = new HashMap<>();
            if (i % 2 == 0) {
                map.put("icon", R.drawable.xiaohei);
                map.put("name", "小黑");
            } else {
                map.put("icon", R.drawable.renma);
                map.put("name", "人马");
            }
            map.put("say", "今晚12点开黑打dota，今晚12点开黑打dota，今晚12点开黑打dota，今晚12点开黑打dota");
            map.put("time", "昨晚12点");
            listItems.add(map);
        }
        listViewWeixin.setAdapter(new SimpleAdapter(this, listItems, R.layout.main_tab_weixin_item,
                new String[]{"icon", "name", "say", "time"},
                new int[]{R.id.img_icon, R.id.text_name, R.id.text_say, R.id.text_time}));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 顶部导航栏的点击事件
     */
    public class TabViewClickListener implements View.OnClickListener {
        private int index;

        public TabViewClickListener(int i) {
            this.index = i;
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "Tab click:" + index);
            mTabPager.setCurrentItem(index);
        }
    }

    public class TabPagerChangerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            if (position >= mTabViews.length || position < 0) {
                return;
            }

            Log.i(TAG, String.format("onPageSelected lastIndex:%s, curIndex:%s", lastIndex, position));
            mTabViews[lastIndex].setImageDrawable(getResources().getDrawable(imageNormalIds[lastIndex]));
            mTabViews[position].setImageDrawable(getResources().getDrawable(imagePressedIds[position]));

            int one = screenWidth / 4;

            Animation animation = new TranslateAnimation(one * lastIndex, one * position, 0, 0);
            animation.setFillAfter(true);
            animation.setDuration(300);
            mTabImgBottom.setAnimation(animation);
            lastIndex = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }
}
