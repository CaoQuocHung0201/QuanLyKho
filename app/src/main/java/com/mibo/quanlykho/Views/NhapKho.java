package com.mibo.quanlykho.Views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
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

import org.jetbrains.annotations.Contract;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class NhapKho extends AppCompatActivity {
    TextView idNhanvien, barCode, thoiGian, btnChupanh, btnNhap,btnThemdanhmuc, btnQuaylai, HSD;
    EditText Ten,Gia,soLuong,thuongHieu,xuatXu;
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
    int iSuccessful=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_kho);

        anhxa();
        get_DanhMuc();
        get_Time();
        //get data nếu trùng barcode
        get_sanpham();

        HSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_time("");
            }
        });
        btnQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                get_Values();
                if (exist==false) {
                    up_realtime_SP();
                    for (int i = 0; i < arr_img.size(); i++) {
                        upload(i);
                    }
                }
                else {
                    final int[] count={0};
                    myData.child(val.Kho).child(danh_muc).child(barcode).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            SanPham sp=snapshot.getValue(SanPham.class);
                            count[0]=count[0]+1;
                            if (count[0]==1) {
                                inventory=sp.getSoLuong()+soluong;
                                up_realtime_slSP();
                                Toast.makeText(NhapKho.this, "Đã nhập kho", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                up_realtime_phieuNhap();

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
        bitmap.compress(Bitmap.CompressFormat.WEBP,100,baos);

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
                    myData.child(val.Kho).child(danh_muc).child(barcode).child(SanPham.Img).child(String.valueOf(i)).setValue(String.valueOf(dowloadUri)).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                iSuccessful=iSuccessful+1;//lấy sô để đủ số lượng ảnh để tb up thành công
                                if (iSuccessful==arr_img.size()){
                                    Toast.makeText(NhapKho.this, "Nhập thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                };
                            }
                        }
                    });
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
            bitmap.compress(Bitmap.CompressFormat.WEBP, 100, fos);
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
                if (snapshot.getValue()!=null) {
                    danh_muc = snapshot.getValue().toString();
//                    Toast.makeText(NhapKho.this, "" + snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                    myData.child(val.Kho).child(snapshot.getValue().toString()).child(barcode).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue() != null) {
                                SanPham sp = snapshot.getValue(SanPham.class);
                                ten_sp = sp.getName();
                                gia = sp.getGiaNhap();
                                hsd = sp.getHSD();
                                thuonghieu = sp.getThuongHieu();
                                xuatxu = sp.getXuatXu();
                                soluong = sp.getSoLuong();
                                exist = true;
                                btnChupanh.setEnabled(false);
                                Drawable drawable = ContextCompat.getDrawable(NhapKho.this, R.drawable.rouded_corne_disable);
                                btnChupanh.setBackground(drawable);
                                set_Values();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    myData.child(val.Kho).child(snapshot.getValue().toString()).child(barcode).child(SanPham.Img).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            if (snapshot.getValue() != null) {
                                String link = snapshot.getValue().toString();
                                imageFilePath = link;
                                setAnh();
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void get_Values() {
        ten_sp=Ten.getText().toString().trim();
        //gia=Integer.valueOf(Gia.getText().toString().trim().replace(",",""));
        gia=Integer.valueOf(Gia.getText().toString().trim().replace(".",""));
        soluong=Integer.valueOf((String) soLuong.getText().toString().trim());
        hsd=HSD.getText().toString().trim();;
        thuonghieu=thuongHieu.getText().toString().trim();
        xuatxu=xuatXu.getText().toString().trim();
    }

    private void set_Values() {
        barCode.setText("Mã barcode: "+barcode);
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
        myData.child(val.Kho).child(val.Local_sp).child(barcode).setValue(danh_muc);
    }

    private void up_realtime_slSP(){
        myData.child(val.Kho).child(danh_muc).child(barcode).child(SanPham.sl).setValue(inventory);
//        myData.child(val.Kho).child(val.Local_sp).child(danh_muc).setValue(barcode);
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


    private void anhxa(){
        idNhanvien = findViewById(R.id.idNhanvien_nhap);
        barCode = findViewById(R.id.barCode_nhap);
        thoiGian = findViewById(R.id.thoiGian_nhap);
        btnChupanh = findViewById(R.id.btnChupanh_nhap);
        btnNhap = findViewById(R.id.btnNhap);
        Ten = findViewById(R.id.edtTenSp_nhap);
        Gia = findViewById(R.id.edtGia_nhap);
        Gia.addTextChangedListener(new PriceTextWatcher());
        soLuong = findViewById(R.id.edtSoluong_nhap);
        HSD = findViewById(R.id.edtHSD_nhap);
        thuongHieu = findViewById(R.id.edtThuonghieu_nhap);
        xuatXu = findViewById(R.id.edtXuatxu_nhap);
        danhmuc = findViewById(R.id.danhMuc_nhap);
        listAnh = findViewById(R.id.listAnh);
        btnThemdanhmuc=findViewById(R.id.textView10);
        btnQuaylai =findViewById(R.id.btnQuaylai_nhap);

        arr_img = new ArrayList<>();
        imageList = new ArrayList<>();
        arr_MaDanhMuc=new ArrayList<>();
        arr_DanhMuc=new ArrayList<>();
        arr_img=new ArrayList<>();
        arrayAdapter_DanhMuc=new ArrayAdapter<>(getApplication(), android.R.layout.simple_list_item_1,arr_DanhMuc);
        arrayAdapter_DanhMuc.setDropDownViewResource(android.R.layout.simple_list_item_1);
        danhmuc.setAdapter(arrayAdapter_DanhMuc);

        Intent intent = getIntent();
        barcode = intent.getStringExtra("barcode");
        barcode ="111";
        barCode.setText("Mã barcode: "+barcode);

        id_nv = DangNhap.uid;
        idNhanvien.setText("ID nhân viên: "+id_nv.substring(0,7));

        Drawable drawable = ContextCompat.getDrawable(NhapKho.this, R.drawable.rouded_blue);
        btnChupanh.setBackground(drawable);

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

                HSD.setText(str_dd+"/"+str_MM+"/"+str_yyyy);
//                Toast.makeText(NhapKho.this, str_dd+"/"+str_MM+"/"+str_yyyy, Toast.LENGTH_SHORT).show();

//                if (str.isEmpty()) {
//                    tuNgay.setText(str_dd + "/" + str_MM + "/" + str_yyyy);
//                    tu_yyyy=str_yyyy;
//                    tu_MM=str_MM;
//                    tu_dd=str_dd;
//
//                    i_tu_yyyy=year;
//                    i_tu_MM=month;
//                    i_tu_dd=dayOfMonth;
//
//                }
//                else {
//                    denNgay.setText(str_dd + "/" + str_MM + "/" + str_yyyy);
//                    den_yyyy=str_yyyy;
//                    den_MM=str_MM;
//                    den_dd=str_dd;
//
//                    i_den_yyyy=year;
//                    i_den_MM=month;
//                    i_den_dd=dayOfMonth;
//                }
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
    }
}