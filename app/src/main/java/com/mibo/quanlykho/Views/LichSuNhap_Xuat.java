package com.mibo.quanlykho.Views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.mibo.quanlykho.Models.val;
import com.mibo.quanlykho.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LichSuNhap_Xuat extends AppCompatActivity {
    TextView tieude, btnLoc, btnXuatAll, btnQuaylai;
    EditText tuNgay, denNgay; String tu_yyyy="",tu_MM="",tu_dd="",den_yyyy="",den_MM="",den_dd="";

    AdapterLichSu adapterLichSu;
    ListView listLichsu;
    ArrayList<Thong_tin_lich_su_sp> listTT;

    String barcode=ThongTinChiTiet.bc,tensp=ThongTinChiTiet.tsp,yyyy="",MM="",dd="",hh="",mm="";

    String name="Tên sản phẩm: ",time="Ngày nhập: ", sl="Số lượng nhập: ",gia="Giá nhập: ",nv="Nhân viên nhập: ",danhMuc="ĐT";

    DatabaseReference myData= val.databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_nhap_xuat);
        anhxa();
        get_Time();
        load_all_lich_su_nhap();


        listLichsu.setAdapter(adapterLichSu);

//        Toast.makeText(this, tensp+"   "+barcode, Toast.LENGTH_SHORT).show();

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
                if (tuNgay.getText().toString().isEmpty()&&denNgay.getText().toString().isEmpty())
                    Toast.makeText(LichSuNhap_Xuat.this, "Dữ liệu không để trống", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(LichSuNhap_Xuat.this, tu_dd+"/"+tu_MM+"/"+tu_yyyy+"\n"+den_dd+"/"+den_MM+"/"+den_yyyy, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LichSuNhap_Xuat.this,ThongTinChiTiet.class));
                finish();
            }
        });
    }

    // lấy ngày tháng
    private void set_time(String str) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
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
                }
                else {
                    denNgay.setText(str_dd + "/" + str_MM + "/" + str_yyyy);
                    den_yyyy=str_yyyy;
                    den_MM=str_MM;
                    den_dd=str_dd;
                }
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
    }

    private void load_all_lich_su_nhap(){
        myData.child(val.TT_Nhap).child(danhMuc).child(yyyy).child(MM).child(dd).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                myData.child(val.TT_Nhap).child(danhMuc).child(yyyy).child(MM).child(dd).child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue()!=null){
                            phieuNhap pN=snapshot.getValue(phieuNhap.class);
                            if (pN.getBarCode().equals(barcode)){
                                final String[] nameNV={""};
                                //lay ten nv
                                myData.child(val.TT_Tai_Khoan).child(pN.getNhanVien()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        TaiKhoan tk=snapshot.getValue(TaiKhoan.class);
                                        listTT.add(new Thong_tin_lich_su_sp(name+tensp,gia+pN.getGiaNhap()+"  VND",sl+pN.getSoLuong(),time+pN.getNgayNhap(),nv+tk.getName()));
                                        adapterLichSu.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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

    private void get_Time(){
        yyyy = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
        MM = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
        dd = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
        hh = new SimpleDateFormat("hh", Locale.getDefault()).format(new Date());
        mm = new SimpleDateFormat("mm", Locale.getDefault()).format(new Date());
//        thoigian=hh+":"+mm+":"+dd+":"+MM+":"+yyyy;
//        thoiGian.setText("Thời gian: "+hh+":"+mm+"  "+dd+":"+MM+":"+yyyy);
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