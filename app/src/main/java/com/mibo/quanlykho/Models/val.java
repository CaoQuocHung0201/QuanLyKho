package com.mibo.quanlykho.Models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class val {
    public static String Name_databasae_sqlite="Thong_tin_user.sqlite";
    public static String Name_table_sqlite="Thong_tin";
    // tạo bảng
    public static String Table_sqlite="CREATE TABLE IF NOT EXISTS "+Name_table_sqlite+"(user nvarchar(100), pass nvarchar(100),uid nvarchar(100), boolean nvarchar(10))";

    public static FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    public static DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    public static StorageReference storageReference= FirebaseStorage.getInstance().getReferenceFromUrl("gs://app-quanl-ly-kho.appspot.com");


    //Realtime
    public static String TT_Tai_Khoan="TT_Tai_Khoan";
    public static String TT_DanhMuc="TT_DanhMuc";

    public static String TT_Nhap="TT_Nhap";
    public static String TT_Xuat="TT_Xuat";

    public static String Kho="Kho";
    public static String Local_sp="Local";


    public static String admin="admin";
    public static String nv="nv";
}
