package com.mibo.quanlykho.Views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mibo.quanlykho.Controllers.ImageAdapter;
import com.mibo.quanlykho.Controllers.RandomStringExmple;
import com.mibo.quanlykho.Models.SQLite;
import com.mibo.quanlykho.Models.SanPham;
import com.mibo.quanlykho.Models.phieuNhap;
import com.mibo.quanlykho.Models.val;
import com.mibo.quanlykho.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class NhapKho extends AppCompatActivity {
    TextView idNhanvien, barCode, thoiGian, btnChupanh, btnNhap,btnThemdanhmuc;
    EditText Ten,Gia,soLuong,HSD,thuongHieu,xuatXu;
    Spinner danhmuc;
    RecyclerView listAnh;

    ArrayList<String> arr_DanhMuc,arr_MaDanhMuc;
    ArrayList<Bitmap> arr_img;
    ArrayAdapter arrayAdapter_DanhMuc;

    String id_phieuNhap="",id_nv="",barcode="",thoigian="",ten_sp="",danh_muc="",hsd="",thuonghieu="",xuatxu="",yyyy="",MM="",dd="",hh="",mm="";
    int soluong=0,gia=0,inventory=0;
    String imageFilePath="";
    Boolean exist=false;

    DatabaseReference myData= val.databaseReference;
    StorageReference storageReference= val.storageReference;
    int Requet_code=123;
    List<String> imageList;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_kho);
        barcode="yuedfrghjkl";
        anhxa();
        get_DanhMuc();
        select_sqlite();
        get_Time();

        //get data nếu trùng barcode
        get_sanpham();

        danhmuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (danh_muc.isEmpty()){
                    danh_muc=arr_MaDanhMuc.get(i);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnChupanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                ActivityCompat.requestPermissions(NhapKho.this,new String[]{Manifest.permission.CAMERA},Requet_code);
                startActivityForResult(intent,Requet_code);
            }
        });

        btnThemdanhmuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.dialog_add_danhmuc, null);
                AlertDialog.Builder alert = new AlertDialog.Builder(NhapKho.this);
                alert.setCancelable(false);
                alert.setTitle("Thêm danh mục mới");
                alert.setView(alertLayout);

                dialog = alert.create();
                EditText txt_name=alertLayout.findViewById(R.id.txt_add_name_dm);
                Button btn_add_dm_xn=alertLayout.findViewById(R.id.btn_add_dm_xn);
                Button btn_add_dm_huy=alertLayout.findViewById(R.id.btn_add_dm_huy);

                btn_add_dm_xn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str_name=txt_name.getText().toString().trim();
                        if (str_name.isEmpty()){
                            Toast.makeText(NhapKho.this, "Yêu cầu nhập tên danh mục", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            String maDM=RandomStringExmple.randomAlphaNumeric(5);
                            myData.child(val.TT_DanhMuc).child(maDM).setValue(str_name).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(NhapKho.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    }
                                }
                            });
                        }
                    }
                });


                btn_add_dm_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });

        btnNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                get_Values();
//                if (exist==false) {
//                    up_realtime_SP();
//                }
//                else {
//                    final int[] count={0};
//                    myData.child(val.Kho).child(danh_muc).child(barcode).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            SanPham sp=snapshot.getValue(SanPham.class);
//
//                            count[0]=count[0]+1;
//                            if (count[0]==1) {
//                                inventory=sp.getSoLuong()+soluong;
//                                up_realtime_slSP();
//                            }
//
//                            Log.d("AAA",count[0]+"");
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//
//                }
//                up_realtime_phieuNhap();
                for (int i = 0; i < arr_img.size(); i++) {
                    upload(i);
                }
            }
        });
    }

    //  upload hinh
    //-------------------------------------------------------------------------------------

    private void setAnh(){
        imageList.add(imageFilePath);
//        imageList.add("https://firebasestorage.googleapis.com/v0/b/app-quanl-ly-kho.appspot.com/o/yuedfrghjkl%2F1680944204890.webp?alt=media&token=df081e0d-9b69-4120-a201-511c83490a7f");
        ImageAdapter adapter = new ImageAdapter(this, imageList);
        listAnh.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter.notifyDataSetChanged();
        listAnh.setAdapter(adapter);
    }
    private void upload(int i) {
        Calendar calendar = Calendar.getInstance();
        final StorageReference imgUp=storageReference.child(barcode+"/"+calendar.getTimeInMillis()+".webp");

        Bitmap bitmap = arr_img.get(i);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.WEBP,50,baos);

        byte[] data_img = baos.toByteArray();

        UploadTask uploadTask = imgUp.putBytes(data_img);

        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful())
                    throw task.getException();
                return imgUp.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    Uri dowloadUri = task.getResult();
                    myData.child(val.Kho).child(danh_muc).child(barcode).child(SanPham.Img).child(String.valueOf(i)).setValue(String.valueOf(dowloadUri));
                }
                else
                    Toast.makeText(NhapKho.this, "Upload lỗi", Toast.LENGTH_SHORT).show();
            }
        });


    }

    //-------------------------------------------------------------------------------------

    //  Chup hinh
    //-------------------------------------------------------------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==Requet_code || requestCode==RESULT_OK && data!=null ){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            Toast.makeText(this, ""+bitmap.toString(), Toast.LENGTH_SHORT).show();
            arr_img.add(bitmap);

            imageFilePath = saveImageToStorage(bitmap);
            setAnh();
            //Toast.makeText(this, ""+imageFilePath, Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    //-------------------------------------------------------------------------------------
    private String saveImageToStorage(Bitmap bitmap) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + ".webp";

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(storageDir, imageFileName);

        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.WEBP, 50, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageFile.getAbsolutePath();
    }

    private void get_sanpham() {
        myData.child(val.Kho).child(val.Local_sp).child(barcode).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                danh_muc=snapshot.getValue().toString();
                myData.child(val.Kho).child(snapshot.getValue().toString()).child(barcode).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue()!=null){
                            SanPham sp=snapshot.getValue(SanPham.class);
                            ten_sp=sp.getName();
                            gia=sp.getGiaNhap();
                            hsd=sp.getHSD();
                            thuonghieu=sp.getThuongHieu();
                            xuatxu=sp.getXuatXu();
                            exist=true;
                            set_Values();
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void get_Values() {
        ten_sp=Ten.getText().toString().trim();
        gia=Integer.valueOf(Gia.getText().toString().trim());
        soluong=Integer.valueOf((String) soLuong.getText().toString().trim());
        hsd=HSD.getText().toString().trim();;
        thuonghieu=thuongHieu.getText().toString().trim();
        xuatxu=xuatXu.getText().toString().trim();
    }

    private void set_Values() {
        barCode.setText(barcode);
        Ten.setText(ten_sp);
        Gia.setText(String.valueOf(gia));
        soLuong.setText(String.valueOf(soluong));
        HSD.setText(hsd);
        thuongHieu.setText(thuonghieu);
        xuatXu.setText(xuatxu);

        for (int i=0;i<=arr_MaDanhMuc.size();i++){
            if (arr_MaDanhMuc.get(i).equals(danh_muc)){
                danhmuc.setSelection(i);
                break;
            }
        }
    }

    private void up_realtime_phieuNhap(){
        UUID uuid = UUID.randomUUID();
        id_phieuNhap=uuid.toString();

        phieuNhap pn=new phieuNhap(soluong,gia,thoigian,id_nv,barcode);
        myData.child(val.TT_Nhap).child(danh_muc).child(yyyy).child(MM).child(dd).child(id_phieuNhap).setValue(pn);
        myData.child(val.Kho).child(danh_muc).child(barcode).child(SanPham.ngNhap).child(thoigian).setValue(id_phieuNhap);

    }

    private void up_realtime_SP(){
        SanPham sp=new SanPham(ten_sp,gia,soluong,hsd,thuonghieu,xuatxu);
        myData.child(val.Kho).child(danh_muc).child(barcode).setValue(sp);
        myData.child(val.Kho).child(val.Local_sp).child(danh_muc).setValue(barcode);
    }

    private void up_realtime_slSP(){
        myData.child(val.Kho).child(danh_muc).child(barcode).child(SanPham.sl).setValue(inventory);
        myData.child(val.Kho).child(val.Local_sp).child(danh_muc).setValue(barcode);
    }

    private void get_Time(){

        yyyy = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
        MM = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
        dd = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
        hh = new SimpleDateFormat("hh", Locale.getDefault()).format(new Date());
        mm = new SimpleDateFormat("mm", Locale.getDefault()).format(new Date());
        thoigian=hh+":"+mm+":"+dd+":"+MM+":"+yyyy;
        thoiGian.setText("Thời gian: "+hh+":"+mm+"  "+dd+":"+MM+":"+yyyy);
    }


    private void anhxa(){
        idNhanvien = findViewById(R.id.idNhanvien_nhap);
        barCode = findViewById(R.id.barCode_nhap);
        thoiGian = findViewById(R.id.thoiGian_nhap);
        btnChupanh = findViewById(R.id.btnChupanh_nhap);
        btnNhap = findViewById(R.id.btnNhap);
        Ten = findViewById(R.id.edtTenSp_nhap);
        Gia = findViewById(R.id.edtGia_nhap);
        soLuong = findViewById(R.id.edtSoluong_nhap);
        HSD = findViewById(R.id.edtHSD_nhap);
        thuongHieu = findViewById(R.id.edtThuonghieu_nhap);
        xuatXu = findViewById(R.id.edtXuatxu_nhap);
        danhmuc = findViewById(R.id.danhMuc_nhap);
        listAnh = findViewById(R.id.listAnh);
        btnThemdanhmuc=findViewById(R.id.textView10);

        arr_img = new ArrayList<>();
        imageList = new ArrayList<>();
        arr_MaDanhMuc=new ArrayList<>();
        arr_DanhMuc=new ArrayList<>();
        arr_img=new ArrayList<>();
        arrayAdapter_DanhMuc=new ArrayAdapter<>(getApplication(), android.R.layout.simple_list_item_1,arr_DanhMuc);
        arrayAdapter_DanhMuc.setDropDownViewResource(android.R.layout.simple_list_item_1);
        danhmuc.setAdapter(arrayAdapter_DanhMuc);
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

    //lấy uid nv
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