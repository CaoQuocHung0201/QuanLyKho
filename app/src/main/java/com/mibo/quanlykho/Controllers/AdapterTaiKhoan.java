package com.mibo.quanlykho.Controllers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.mibo.quanlykho.Models.TaiKhoan;

import java.util.List;

public class AdapterTaiKhoan extends BaseAdapter {
    Context context;
    private int layout;
    List<TaiKhoan> taiKhoanList;
    @Override
    public int getCount() {
        return taiKhoanList.size();
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

        return convertView;
    }

    public class ViewHolder {

    }
}
