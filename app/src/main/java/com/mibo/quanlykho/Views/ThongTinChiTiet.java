package com.mibo.quanlykho.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mibo.quanlykho.Controllers.ImageAdapter;
import com.mibo.quanlykho.Models.SanPham;
import com.mibo.quanlykho.Models.val;
import com.mibo.quanlykho.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ThongTinChiTiet extends AppCompatActivity {

    TextView barCode, tenSP, gia1Sp, HSD, soLuong, thuongHieu,xuatXu, btnLSNhap, btnLSXuat,btnQuaylai;
    DatabaseReference myData= val.databaseReference;

    String barcode="",imageFilePath="";
    RecyclerView listAnh;
    List<String> imageList;

//    final String[] tensp={""};

    public static String mdm="",tsp="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_chi_tiet);
        anhxa();
        load_sanpham();

        btnQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ThongTinChiTiet.this,TonKho.class));
                LichSuNhap_Xuat.str_retrun="";
                finish();
            }
        });
        btnLSNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThongTinChiTiet.this,LichSuNhap_Xuat.class);
                intent.putExtra(val.barcode_ttct_lsnx,barcode);
                intent.putExtra(val.tsp_ttct_lsnx,tsp);
                intent.putExtra(val.madm_ttct_lsnx,mdm);
                intent.putExtra(val.nhap_xuat_ttct_lsnx,true);
                startActivity(intent);
                finish();
            }
        });

        btnLSXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThongTinChiTiet.this,LichSuNhap_Xuat.class);
                intent.putExtra(val.barcode_ttct_lsnx,barcode);
                intent.putExtra(val.tsp_ttct_lsnx,tsp);
                intent.putExtra(val.madm_ttct_lsnx,mdm);
                intent.putExtra(val.nhap_xuat_ttct_lsnx,false);
                startActivity(intent);
                finish();
            }
        });

    }
    private void setAnh(){
        imageList.add(imageFilePath);
        ImageAdapter adapter = new ImageAdapter(this, imageList);
        listAnh.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter.notifyDataSetChanged();
        listAnh.setAdapter(adapter);
    }

    private void load_sanpham(){
        Intent intent=getIntent();
        if (LichSuNhap_Xuat.str_retrun!=null) {
            if (!LichSuNhap_Xuat.str_retrun.isEmpty()) {
                barcode = LichSuNhap_Xuat.str_retrun;
            }
            else {
                barcode=intent.getStringExtra(val.tonkho_ttct);
            }
        }
        else {
            barcode=intent.getStringExtra(val.tonkho_ttct);
        }

        myData.child(val.Kho).child(val.Local_sp).child(barcode).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue()!=null) {
                    mdm=snapshot.getValue().toString();
                    myData.child(val.Kho).child(snapshot.getValue().toString()).child(barcode).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue() != null) {
                                SanPham sanPham = snapshot.getValue(SanPham.class);
                                barCode.setText("Barcode: " + barcode);
                                tenSP.setText("Tên sản phẩm: " + sanPham.getName());
                                gia1Sp.setText("Giá: " + sanPham.getGiaNhap()+"  VNĐ");
                                HSD.setText("Hạn sử dụng: " + sanPham.getHSD());
                                soLuong.setText("Số lượng: " + sanPham.getSoLuong()+" Cái");
                                thuongHieu.setText("Thương hiệu: " + sanPham.getThuongHieu());
                                xuatXu.setText("Xuất xứ: " + sanPham.getXuatXu());
                                tsp=sanPham.getName();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    myData.child(val.Kho).child(snapshot.getValue().toString()).child(barcode).child(SanPham.Img).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue()!=null){
                                String link=snapshot.getValue().toString();
                                int l=link.length();
                                String str=link.substring(1,l-1);
                                imageFilePath=str;
                                setAnh();
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


    private void anhxa(){
        barCode = findViewById(R.id.barCode_ton);
        tenSP = findViewById(R.id.tenSP_ton);
        gia1Sp = findViewById(R.id.gia1SP_ton);
        gia1Sp.addTextChangedListener(new PriceTextWatcher());
        HSD = findViewById(R.id.HSD_ton);
        soLuong = findViewById(R.id.soluong_ton);
        thuongHieu = findViewById(R.id.thuonghieu_ton);
        xuatXu = findViewById(R.id.xuatxu_ton);
        btnLSNhap= findViewById(R.id.btnLSNhap_ton);
        btnLSXuat= findViewById(R.id.btnLSXuat_ton);
        btnQuaylai= findViewById(R.id.btnQuaylai_ton);
        listAnh = findViewById(R.id.listAnh_thongtinchitiet);
        imageList = new ArrayList<>();
    }
    private class PriceTextWatcher implements TextWatcher {
        private String current = "";
        private final DecimalFormat df = new DecimalFormat("#,###");
        private Editable s;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!s.toString().equals(current)) {
                gia1Sp.removeTextChangedListener(this);

                String cleanString = s.toString().replaceAll("[^\\d]", "");
                if (!cleanString.isEmpty()) {
                    double parsed = Double.parseDouble(cleanString);
                    String formatted = df.format(parsed);
                    current = formatted;
                    gia1Sp.setText(formatted);
                    //gia1Sp.setSelection(formatted.length());
                }

                gia1Sp.addTextChangedListener(this);
            }
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable s) {}
    }
}