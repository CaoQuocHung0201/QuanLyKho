package com.mibo.quanlykho.Controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mibo.quanlykho.Models.TaiKhoan;
import com.mibo.quanlykho.R;
import com.mibo.quanlykho.Views.QuanLyTaiKhoan;


import java.util.List;

public class AdapterTaikhoan extends BaseAdapter {
    private QuanLyTaiKhoan context;
    List<TaiKhoan> taiKhoanList;
    private int layout;

    public AdapterTaikhoan(QuanLyTaiKhoan context,  int layout,List<TaiKhoan> taiKhoanList) {
        this.context = context;
        this.taiKhoanList = taiKhoanList;
        this.layout = layout;
    }

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
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);
            viewHolder.tvName = convertView.findViewById(R.id.tenTK);
            viewHolder.btnSua = convertView.findViewById(R.id.btnSuaTK);
            viewHolder.btnXoa = convertView.findViewById(R.id.btnXoaTK);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TaiKhoan taiKhoan = taiKhoanList.get(position);
        viewHolder.tvName.setText(taiKhoan.getName());
        viewHolder.btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogSua(taiKhoan.getUid());
            }
        });
        viewHolder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogXoa(taiKhoan.getUid());
            }
        });

        return convertView;
    }
    private class ViewHolder{
        TextView tvName, btnSua,btnXoa;
    }
}
