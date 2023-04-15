package com.mibo.quanlykho.Controllers;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mibo.quanlykho.Models.SanPham;
import com.mibo.quanlykho.Models.Thong_tin_lich_su_sp;
import com.mibo.quanlykho.R;
import com.mibo.quanlykho.Views.LichSuNhap_Xuat;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterLichSu extends BaseAdapter {

    private LichSuNhap_Xuat context;
    private int layout;
    private List<Thong_tin_lich_su_sp> listTT;

    public AdapterLichSu(LichSuNhap_Xuat context, int layout, List<Thong_tin_lich_su_sp> listTT) {
        this.context = context;
        this.layout = layout;
        this.listTT = listTT;
    }

    @Override
    public int getCount() {
        return listTT.size();
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
            viewHolder = new AdapterLichSu.ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);

            viewHolder.ngay = convertView.findViewById(R.id.ngay_donglichsu);
            viewHolder.tenSP = convertView.findViewById(R.id.tenSP_donglichsu);
            viewHolder.soLuong = convertView.findViewById(R.id.soluong_donglichsu);
            viewHolder.gia = convertView.findViewById(R.id.gia_donglichsu);

            viewHolder.nguoi = convertView.findViewById(R.id.nv_donglichsu);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (AdapterLichSu.ViewHolder) convertView.getTag();
        }
        Thong_tin_lich_su_sp tt=listTT.get(position);
        viewHolder.tenSP.setText(tt.getNameSP());
        viewHolder.ngay.setText(tt.getTimeSP());
        viewHolder.soLuong.setText(String.valueOf(tt.getSoLuongSP()));
        viewHolder.gia.setText(String.valueOf(tt.getGiaSP()));
        viewHolder.nguoi.setText(tt.getNhanVien());
        return convertView;
    }
    private class ViewHolder{
        TextView ngay, tenSP, soLuong,gia,nguoi;
    }

}
