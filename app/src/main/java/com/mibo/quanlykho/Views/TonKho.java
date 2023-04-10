package com.mibo.quanlykho.Views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mibo.quanlykho.Controllers.AdapterTonKho;
import com.mibo.quanlykho.Models.SanPham;
import com.mibo.quanlykho.Models.TaiKhoan;
import com.mibo.quanlykho.Models.val;
import com.mibo.quanlykho.R;

import java.util.ArrayList;

public class TonKho extends AppCompatActivity {

    TextView btnQuaylai;
    ListView listTonkho;
    Spinner danhMuc;
    DatabaseReference myData = val.databaseReference;
    ArrayList<SanPham> listSanpham;
    AdapterTonKho adapterTonKho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ton_kho);
        anhxa();
        AddDataList();
        adapterTonKho = new AdapterTonKho(this, R.layout.dong_ton_kho, listSanpham);
        listTonkho.setAdapter(adapterTonKho);

        btnQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void AddDataList(){
        myData.child(val.Kho).child("ĐT").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                myData.child(val.Kho).child("ĐT").child(snapshot.getKey()).child("name").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        String ten = snapshot.getValue().toString();
//                        listSanpham.add(sanPham);
//                        adapterTonKho.notifyDataSetChanged();
//                        Log.d("AAA",""+ten);
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

    private void anhxa(){
        btnQuaylai = findViewById(R.id.btnQuaylai_tonkho);
        listTonkho =findViewById(R.id.listTonkho);
        danhMuc = findViewById(R.id.danhmuctonkho);
        listSanpham = new ArrayList<>();
    }
}