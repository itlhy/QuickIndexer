package com.lhy.quickindexer;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends Activity {

    private QuickIndexBar quickIndexBar;
    private ListView listView;
    private TextView tvLetterTip;
    private ArrayList<Person> personArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_view);
        tvLetterTip = (TextView) findViewById(R.id.tv_letter_center_tip);
        personArrayList = new ArrayList<>();
        fillAndSortData(personArrayList);
        listView.setAdapter(new QuickIndexAdapter(personArrayList, MainActivity.this));

        this.quickIndexBar = (QuickIndexBar) findViewById(R.id.quick_index_bar);
        quickIndexBar.setOnLetterUpdateListener(new QuickIndexBar.OnLetterUpdateListener() {
            @Override
            public void onLetterUpdate(String letter) {
                tvLetterTip.setText(letter);
                tvLetterTip.setVisibility(View.VISIBLE);//屏幕中间给出提示当前触摸的字母
                for (int i = 0; i < personArrayList.size(); i++) {
                    String c = personArrayList.get(i).getPinyin().charAt(0) + "";//遍历所有item的第一个汉字,并获取拼音的首字母大写
                    if (TextUtils.equals(c, letter)) {//当前的首字母和触摸字母的一致,把当前的条目移动到屏幕第一个条目位置
                        listView.smoothScrollToPositionFromTop(i, 0, 100);//平滑移动
//                        listView.setSelection(i);
                        break;
                    }
                }

            }

            @Override
            public void onHiddenLetter() {
                tvLetterTip.setVisibility(View.GONE);//隐藏屏幕中间给出提示当前触摸的字母
            }
        });

    }

    /**
     * 把所有名字添加到集合里,并根据拼音首字母排序
     * @param personArrayList
     */
    private void fillAndSortData(ArrayList<Person> personArrayList) {
        for (int i = 0; i < Cheeses.NAMES.length; i++) {
            String name = Cheeses.NAMES[i];
            personArrayList.add(new Person(name));
        }
        Collections.sort(personArrayList);
    }
}
