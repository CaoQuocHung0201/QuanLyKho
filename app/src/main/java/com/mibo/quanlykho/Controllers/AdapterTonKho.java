package com.mibo.quanlykho.Controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.mibo.quanlykho.Models.SanPham;
import com.mibo.quanlykho.R;
import com.mibo.quanlykho.Views.TonKho;

import java.util.List;

public class AdapterTonKho extends BaseAdapter {

    private TonKho context;
    private int layout;
    private List<SanPham> sanPhamList;

    public AdapterTonKho(TonKho context, int layout, List<SanPham> sanPhamList) {
        this.context = context;
        this.layout = layout;
        this.sanPhamList = sanPhamList;
    }


    @Override
    public int getCount() {
        return sanPhamList.size();
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
            viewHolder.tenSP = convertView.findViewById(R.id.tenSP_dongtonkho);
            viewHolder.soLuong = convertView.findViewById(R.id.soLuong_dongtonkho);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SanPham sanPham = sanPhamList.get(position);
        viewHolder.tenSP.setText(sanPham.getName());
        //viewHolder.ngayNhapDauTien.setText(sanPham.getNgayNhap());
        viewHolder.soLuong.setText(String.valueOf(sanPham.getSoLuong()));
        return convertView;
    }

    private class ViewHolder{
        TextView tenSP, soLuong;
    }
}
