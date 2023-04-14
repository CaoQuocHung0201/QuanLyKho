package com.mibo.quanlykho.Views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mibo.quanlykho.Controllers.AdapterLichSu;
import com.mibo.quanlykho.Models.SanPham;
import com.mibo.quanlykho.Models.TaiKhoan;
import com.mibo.quanlykho.Models.Thong_tin_lich_su_sp;
import com.mibo.quanlykho.Models.phieuNhap;
import com.mibo.quanlykho.Models.phieuXuat;
import com.mibo.quanlykho.Models.val;
import com.mibo.quanlykho.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LichSuNhap_Xuat extends AppCompatActivity {
    TextView tieude, btnLoc, btnXuatAll, btnQuaylai;
    TextView tuNgay, denNgay;
    String tu_yyyy="",tu_MM="",tu_dd="",den_yyyy="",den_MM="",den_dd="";

    AdapterLichSu adapterLichSu;
    ListView listLichsu;
    ArrayList<Thong_tin_lich_su_sp> listTT;

    String barcode="",tensp="",yyyy="",MM="",dd="",hh="",mm="";
    static String str_retrun;

    String name="Tên sản phẩm: ",time="", sl="",gia="",nv="",danhMuc="";

    DatabaseReference myData= val.databaseReference;

    int i_tu_yyyy=0,i_tu_MM=0,i_tu_dd=0,i_den_yyyy=0,i_den_MM=0,i_den_dd=0;

    Boolean trangThai;
    String nhapxuat="",idNhapXuat="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_nhap_xuat);
        anhxa();
        load_intent();
        get_Time();
        listLichsu.setAdapter(adapterLichSu);

        tuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_time("");
            }
        });
        denNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_time("1");
            }
        });

        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tuNgay.getText().toString().isEmpty()||denNgay.getText().toString().isEmpty())
                    Toast.makeText(LichSuNhap_Xuat.this, "Dữ liệu không để trống", Toast.LENGTH_SHORT).show();
                else if (i_tu_dd>i_den_dd && i_tu_MM==i_den_MM && i_tu_yyyy==i_den_yyyy)
                    Toast.makeText(LichSuNhap_Xuat.this, "Dữ liệu không hợp lệ", Toast.LENGTH_SHORT).show();
                else {
                    listTT.clear();
                    adapterLichSu.notifyDataSetChanged();
                    get_idPhieuNhap();
                }
            }
        });

        btnQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LichSuNhap_Xuat.this,ThongTinChiTiet.class);
                str_retrun=barcode;
                startActivity(intent);
                finish();
            }
        });

    }

    private void load_intent() {

        Intent intent=getIntent();
        barcode=intent.getStringExtra(val.barcode_ttct_lsnx);
        tensp=intent.getStringExtra(val.tsp_ttct_lsnx);
        danhMuc=intent.getStringExtra(val.madm_ttct_lsnx);
        trangThai=intent.getBooleanExtra(val.nhap_xuat_ttct_lsnx,true);

        if (trangThai) {
            nhapxuat=val.TT_Nhap;
            idNhapXuat=SanPham.ngNhap;
            tieude.setText("LỊCH SỬ NHẬP");
            time="Ngày nhập: ";
            sl="Số lượng nhập: ";
            gia="Giá nhập: ";
            nv="Nhân viên nhập: ";
        }
        else{
            nhapxuat=val.TT_Xuat;
            idNhapXuat=SanPham.ngXuat;
            tieude.setText("LỊCH SỬ XUẤT");
            time="Ngày xuất: ";
            sl="Số lượng xuất: ";
            gia="Giá xuất: ";
            nv="Nhân viên xuất: ";
        }

    }

    private void get_idPhieuNhap(){
//        tu_yyyy="2023";tu_MM="04";tu_dd="08";den_yyyy="2025";den_MM="06";den_dd="31";
        myData.child(val.Kho).child(danhMuc).child(barcode).child(idNhapXuat).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String key_yyyy="",key_MM="",key_dd="";

                key_dd=snapshot.getKey().substring(6,8);
                key_MM=snapshot.getKey().substring(9,11);
                key_yyyy=snapshot.getKey().substring(12,snapshot.getKey().length());

                int i_key_dd=Integer.valueOf(snapshot.getKey().substring(6,8));
                int i_key_MM=Integer.valueOf(snapshot.getKey().substring(9,11));
                int i_key_yyyy=Integer.valueOf(snapshot.getKey().substring(12,snapshot.getKey().length()));

                if (i_tu_dd==i_den_dd && i_tu_MM==i_den_MM && i_tu_yyyy==i_den_yyyy) {// trong ngày
                    load_ls(tu_yyyy,tu_MM,tu_dd,snapshot.getValue().toString());
                }
                else if ((i_key_dd>=i_tu_dd && i_key_MM==i_tu_MM && i_key_yyyy==i_tu_yyyy)//ngày bắt đầu
                        ||(i_key_dd>=i_tu_dd && i_key_dd<=i_den_dd && i_key_MM<=i_den_MM && i_key_yyyy<=i_den_yyyy)){//ngày kết thúc
                    load_ls(key_yyyy,key_MM,key_dd,snapshot.getValue().toString());
                }

                if (yyyy.equals(key_yyyy) && MM.equals(key_MM) && dd.equals(key_dd) && i_tu_dd==0){//load theo ngày khi mới vào
                    load_ls(key_yyyy,key_MM,key_dd,snapshot.getValue().toString());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // lấy ngày tháng
    private void set_time(String str) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String str_yyyy=String.valueOf(year);
                String str_MM=String.valueOf(month);
                if (month<10)
                    str_MM="0"+month;

                String str_dd=String.valueOf(dayOfMonth);
                if (dayOfMonth<10)
                    str_dd="0"+dayOfMonth;

                if (str.isEmpty()) {
                    tuNgay.setText(str_dd + "/" + str_MM + "/" + str_yyyy);
                    tu_yyyy=str_yyyy;
                    tu_MM=str_MM;
                    tu_dd=str_dd;

                    i_tu_yyyy=year;
                    i_tu_MM=month;
                    i_tu_dd=dayOfMonth;

                }
                else {
                    denNgay.setText(str_dd + "/" + str_MM + "/" + str_yyyy);
                    den_yyyy=str_yyyy;
                    den_MM=str_MM;
                    den_dd=str_dd;

                    i_den_yyyy=year;
                    i_den_MM=month;
                    i_den_dd=dayOfMonth;
                }
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
    }


    private void load_ls(String stryyyy,String strMM,String strdd,String id){
//        snapshot.getKey()
        final String[] namenv={""};
        myData.child(nhapxuat).child(danhMuc).child(stryyyy).child(strMM).child(strdd).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue()!=null){
                    if (trangThai){//nhap
                        phieuNhap pN=snapshot.getValue(phieuNhap.class);
                        if (pN.getBarCode().equals(barcode)){
                            //lay ten nv
                            myData.child(val.TT_Tai_Khoan).child(pN.getNhanVien()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    TaiKhoan tk=snapshot.getValue(TaiKhoan.class);
                                    namenv[0]= tk.getName();
                                    listTT.add(new Thong_tin_lich_su_sp(name+tensp,gia+pN.getGiaNhap()+"  VND",sl+pN.getSoLuong(),time+pN.getNgayNhap(),nv+namenv[0]));
                                    adapterLichSu.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                    else {//xuat
                        phieuXuat pX=snapshot.getValue(phieuXuat.class);
                        if (pX.getBarCode().equals(barcode)){
                            //lay ten nv
                            myData.child(val.TT_Tai_Khoan).child(pX.getNhanVien()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    TaiKhoan tk=snapshot.getValue(TaiKhoan.class);
                                    namenv[0]= tk.getName();
                                    listTT.add(new Thong_tin_lich_su_sp(name+tensp,gia+pX.getGiaXuat()+"  VND",sl+pX.getSoLuong(),time+pX.getNgayXuat(),nv+namenv[0]));
                                    adapterLichSu.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }





                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void get_Time(){
        yyyy = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
        MM = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
        dd = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
        hh = new SimpleDateFormat("hh", Locale.getDefault()).format(new Date());
        mm = new SimpleDateFormat("mm", Locale.getDefault()).format(new Date());
//        thoigian=hh+":"+mm+":"+dd+":"+MM+":"+yyyy;
//        thoiGian.setText("Thời gian: "+hh+":"+mm+"  "+dd+":"+MM+":"+yyyy);
        get_idPhieuNhap();
    }

    private void anhxa(){
        tieude = findViewById(R.id.tieuDe);
        btnLoc = findViewById(R.id.btnLocNgay);
        btnXuatAll = findViewById(R.id.btnXuatAll);
        btnQuaylai = findViewById(R.id.btnQuaylai_lichsu);
        tuNgay = findViewById(R.id.tuNgay);
        denNgay= findViewById(R.id.denNgay);
        listLichsu = findViewById(R.id.listLichSu);
        listTT=new ArrayList<>();
        adapterLichSu=new AdapterLichSu(this,R.layout.dong_lich_su,listTT);
    }
}