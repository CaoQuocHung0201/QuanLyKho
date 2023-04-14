package com.mibo.quanlykho.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mibo.quanlykho.Models.SQLite;
import com.mibo.quanlykho.Models.SanPham;
import com.mibo.quanlykho.Models.phieuNhap;
import com.mibo.quanlykho.Models.phieuXuat;
import com.mibo.quanlykho.Models.val;
import com.mibo.quanlykho.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class XuatKho extends AppCompatActivity {

    TextView idNhanvien, barCode,Ten, thoiGian,soLuongton, btnXuat, btnQuaylai;
    EditText Gia,soLuongxuat;

    String id_phieuXuat="",id_nv="",barcode="",thoigian="",ten_sp="",danh_muc="",hsd="",thuonghieu="",xuatxu="",yyyy="",MM="",dd="",hh="",mm="";
    int soluong=0,gia=0;
    final int[] inventory={0};

    DatabaseReference myData= val.databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuat_kho);
        Intent intent=getIntent();
        barcode = intent.getStringExtra("barcodexuat");
        anhxa();
//        select_sqlite();
        get_Time();
        get_sanpham();


        btnXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_Values();
                if (soluong>inventory[0]){
                    Toast.makeText(XuatKho.this, "Hàng tồn kho không đủ", Toast.LENGTH_SHORT).show();
                }else {
                    inventory[0]=inventory[0]-soluong;
                    up_realtime_slSP();
                    up_realtime_phieuXuat();
                    Toast.makeText(XuatKho.this, "Đã xuất kho", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        btnQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void up_realtime_slSP(){
        myData.child(val.Kho).child(danh_muc).child(barcode).child(SanPham.sl).setValue(inventory[0]);
        myData.child(val.Kho).child(val.Local_sp).child(danh_muc).setValue(barcode);
    }

    private void get_sanpham() {
        myData.child(val.Kho).child(val.Local_sp).child(barcode).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() != null){
                danh_muc=snapshot.getValue().toString();
                myData.child(val.Kho).child(snapshot.getValue().toString()).child(barcode).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue()!=null){
                            SanPham sp=snapshot.getValue(SanPham.class);
                            ten_sp=sp.getName();
                            gia=sp.getGiaNhap();
                            soluong=sp.getSoLuong();
                            inventory[0]=soluong;
                            set_Values();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void get_Values() {
        ten_sp=Ten.getText().toString().trim();
        gia=Integer.valueOf(Gia.getText().toString().trim());
        soluong=Integer.valueOf((String) soLuongxuat.getText().toString().trim());
//        hsd=HSD.getText().toString().trim();;
//        thuonghieu=thuongHieu.getText().toString().trim();
//        xuatxu=xuatXu.getText().toString().trim();
    }

    private void set_Values() {

        barCode.setText("Barcode: "+barcode);
        Ten.setText("Tên sản phẩm: "+ten_sp);
        Gia.setText(String.valueOf(gia));
        soLuongton.setText(String.valueOf("Số lượng tồn kho: "+soluong+" /Cái"));
//        HSD.setText(hsd);
//        thuongHieu.setText(thuonghieu);
//        xuatXu.setText(xuatxu);

    }

    private void up_realtime_phieuXuat(){
        UUID uuid = UUID.randomUUID();
        id_phieuXuat=uuid.toString();

        phieuXuat px=new phieuXuat(soluong,gia,thoigian,id_nv,barcode);
        myData.child(val.TT_Xuat).child(danh_muc).child(yyyy).child(MM).child(dd).child(id_phieuXuat).setValue(px);
        myData.child(val.Kho).child(danh_muc).child(barcode).child(SanPham.ngXuat).child(thoigian).setValue(id_phieuXuat);

    }

    private void anhxa(){
        idNhanvien = findViewById(R.id.idNhanvien_xuat);
        barCode = findViewById(R.id.barCode_xuat);
        Ten = findViewById(R.id.Ten_xuat);
        thoiGian = findViewById(R.id.thoiGian_xuat);
        soLuongton = findViewById(R.id.soLuongton_xuat);
        btnXuat = findViewById(R.id.btnXuat);
        Gia = findViewById(R.id.edtGia_xuat);
        Gia.addTextChangedListener(new PriceTextWatcher());
        soLuongxuat = findViewById(R.id.edtSoluong_xuat);
        btnQuaylai = findViewById(R.id.btnQuaylai_xuat);

        id_nv = DangNhap.uid;
        idNhanvien.setText("ID nhân viên: "+id_nv.substring(0,7));

        barcode="123";
        barCode.setText("Barcode: "+barcode);
    }
    private class PriceTextWatcher implements TextWatcher {
        private String current = "";
        private final DecimalFormat df = new DecimalFormat("#,###");
        private Editable s;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!s.toString().equals(current)) {
                Gia.removeTextChangedListener(this);

                String cleanString = s.toString().replaceAll("[^\\d]", "");
                if (!cleanString.isEmpty()) {
                    double parsed = Double.parseDouble(cleanString);
                    String formatted = df.format(parsed);
                    current = formatted;
                    Gia.setText(formatted);
                    Gia.setSelection(formatted.length());
                }

                Gia.addTextChangedListener(this);
            }
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable s) {}
    }

    private void get_Time(){

        yyyy = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
        MM = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
        dd = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
        hh = new SimpleDateFormat("hh", Locale.getDefault()).format(new Date());
        mm = new SimpleDateFormat("mm", Locale.getDefault()).format(new Date());
        thoigian=hh+":"+mm+":"+dd+":"+MM+":"+yyyy;
        thoiGian.setText("Thời gian: "+hh+":"+mm+" - "+dd+":"+MM+":"+yyyy);
    }

    public void select_sqlite(){
        SQLite sqLite = new SQLite(this, val.Name_databasae_sqlite, null, 1);
        sqLite.QueryData(val.Table_sqlite);
        Cursor getdata = sqLite.GetData("select * from "+val.Name_table_sqlite);
        while (getdata.moveToNext()){
            id_nv=getdata.getColumnName(2);
            Toast.makeText(this, ""+id_nv, Toast.LENGTH_SHORT).show();
            idNhanvien.setText("ID nhân viên:  "+id_nv);
        }
    }
}