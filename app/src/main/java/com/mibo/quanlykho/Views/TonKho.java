package com.mibo.quanlykho.Views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    ArrayList<String> arr_DanhMuc,arr_MaDanhMuc;
    ArrayAdapter arrayAdapter_DanhMuc;

    DatabaseReference myData = val.databaseReference;
    ArrayList<SanPham> listSanpham;
    AdapterTonKho adapterTonKho;

    String[] maDM={""},Barcode={""};
    String str="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ton_kho);
        anhxa();
        get_DanhMuc();
        AddDataList();
        adapterTonKho = new AdapterTonKho(this, R.layout.dong_ton_kho, listSanpham);
        listTonkho.setAdapter(adapterTonKho);

        danhMuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (str.isEmpty()){
                    str="1";
                }else {
                    AddDataList_DanhMuc(String.valueOf(arr_MaDanhMuc.get(i)));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        listTonkho.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                startActivity(new Intent(TonKho.this,ThongTinChiTiet.class));

            }
        });

        btnQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void AddDataList_DanhMuc(String madm){
        listSanpham.clear();
        adapterTonKho.notifyDataSetChanged();
        myData.child(val.Kho).child(madm).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Barcode[0] = snapshot.getKey();
                myData.child(val.Kho).child(maDM[0]).child(Barcode[0]).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        SanPham sanPham = snapshot.getValue(SanPham.class);
                        listSanpham.add(new SanPham(sanPham.getName(),sanPham.getSoLuong()));
                        adapterTonKho.notifyDataSetChanged();
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

    public void AddDataList(){
        myData.child(val.Kho).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                maDM[0]=snapshot.getKey();
                // loại local
                if (!maDM[0].equals(val.Local_sp)) {
                    myData.child(val.Kho).child(maDM[0]).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            Barcode[0] = snapshot.getKey();
                            myData.child(val.Kho).child(maDM[0]).child(Barcode[0]).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    SanPham sanPham = snapshot.getValue(SanPham.class);
                                    listSanpham.add(new SanPham(sanPham.getName(),sanPham.getSoLuong()));
                                    adapterTonKho.notifyDataSetChanged();
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

    private void get_DanhMuc(){
        myData.child(val.TT_DanhMuc).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                arr_DanhMuc.add(snapshot.getValue().toString());
                arr_MaDanhMuc.add(snapshot.getKey());
                arrayAdapter_DanhMuc.notifyDataSetChanged();
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

        arr_DanhMuc=new ArrayList();
        arr_MaDanhMuc=new ArrayList<>();
        arrayAdapter_DanhMuc=new ArrayAdapter<>(getApplication(), android.R.layout.simple_list_item_1,arr_DanhMuc);
        arrayAdapter_DanhMuc.setDropDownViewResource(android.R.layout.simple_list_item_1);
        danhMuc.setAdapter(arrayAdapter_DanhMuc);

    }
}