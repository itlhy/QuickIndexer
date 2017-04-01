package com.lhy.quickindexer;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 创 建 人: 路好营
 * 创建日期: 2017/3/29 15:35
 * 添加备注:
 */
public class QuickIndexAdapter extends BaseAdapter {
    private ArrayList<Person> personList = new ArrayList<>();
    private Context context;

    public QuickIndexAdapter(ArrayList<Person> personList, Context context) {
        this.personList = personList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return personList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = View.inflate(context, R.layout.item_person, null);//这个布局是每个名字上面都有一个首字母
        } else {
            view = convertView;
        }
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        TextView tvIndex = (TextView) view.findViewById(R.id.tv_index);
        Person person = personList.get(position);
        String currentStr = person.getPinyin().charAt(0) + "";
        String index = null;
        if (position == 0) {
            index = currentStr;
        } else {
            String lastStr = personList.get(position - 1).getPinyin().charAt(0) + "";
            if (!TextUtils.equals(lastStr, currentStr)) {
                index = currentStr;
            }
        }
        //首字母相同的姓氏只显示第一个名字上面的首字母,其他的隐藏掉
        tvIndex.setVisibility(index == null ? View.GONE : View.VISIBLE);
        tvIndex.setText(currentStr);
        tvName.setText(person.getName());
        return view;
    }
}
