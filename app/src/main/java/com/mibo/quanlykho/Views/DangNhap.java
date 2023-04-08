package com.mibo.quanlykho.Views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mibo.quanlykho.MainActivity;
import com.mibo.quanlykho.Models.SQLite;
import com.mibo.quanlykho.Models.TaiKhoan;
import com.mibo.quanlykho.Models.val;
import com.mibo.quanlykho.R;

import java.util.ArrayList;

public class DangNhap extends AppCompatActivity {

    SQLite sqLite = new SQLite(this, val.Name_databasae_sqlite, null, 1);

    TextView btnDangNhap;
    EditText edtTK,edtMK;
    CheckBox luuDN;
    SharedPreferences sharedPreferences;


    SharedPreferences.Editor editor;
    ArrayList<TaiKhoan> arrayUser;

    String user="",pass="",uid="",check="";

    //FirebaseAuth myAuth=val.firebaseAuth;
    DatabaseReference myData=val.databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        sqLite.QueryData(val.Table_sqlite);
        anhxa();
        select_rename();


        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = edtTK.getText().toString().trim();
                pass = edtMK.getText().toString().trim();
                check=String.valueOf(luuDN.isChecked());
                if(edtMK.getText().toString().trim().isEmpty() || edtTK.getText().toString().trim().isEmpty()){
                    Toast.makeText(DangNhap.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {

                    myData.child(val.TT_Tai_Khoan).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            myData.child(val.TT_Tai_Khoan).child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    TaiKhoan tk=snapshot.getValue(TaiKhoan.class);
                                    if (user.equals(tk.getUser())&&pass.equals(tk.getPass())){
                                        uid=snapshot.getKey();
                                        if (luuDN.isChecked()){
                                            insert_sqlite();
                                        }
                                        else if (!luuDN.isChecked()) {
                                            delete_rename();
                                        }
                                        if (tk.getQuen().equals(val.admin)){
                                            startActivity(new Intent(DangNhap.this,MainActivity.class));
                                            finish();
                                        }else {
                                            startActivity(new Intent(DangNhap.this,MainActivity.class));
                                            finish();
                                        }

                                    }
                                    //else Toast.makeText(DangNhap.this, "Không tồn tại tài khoản hoặc mật khẩu này.", Toast.LENGTH_SHORT).show();
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

//                    myAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                Toast.makeText(DangNhap.this, "Đăng nhập tthanh cong"+check, Toast.LENGTH_SHORT).show();
//
//                                if (luuDN.isChecked()){
//                                    insert_sqlite();
//                                }
//                                else if (!luuDN.isChecked()) {
//                                    delete_rename();
//                                    finish();
//                                }
//                            }
//                            else
//                                Toast.makeText(DangNhap.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
//                        }
//                    });
                }
            }
        });

    }
    private void anhxa(){
        btnDangNhap = findViewById(R.id.btnDangNhap);
        edtTK = findViewById(R.id.edtTK);
        edtMK = findViewById(R.id.edtMK);
        luuDN = findViewById(R.id.checkBox);
    }

    public void insert_sqlite(){
        delete_rename();
        sqLite.QueryData("Insert into "+val.Name_table_sqlite+" values('"+user+"','"+pass+"','"+uid+"','"+check+"')");
    }

    // add Nho mat khau vao txt
    //-------------------------------------------------------------------------------------
    public void select_rename(){
        Cursor getdata = sqLite.GetData("select * from "+val.Name_table_sqlite);
        while (getdata.moveToNext()){
            edtTK.setText(getdata.getString(0));
            edtMK.setText(getdata.getString(1));
            luuDN.setChecked(Boolean.valueOf(getdata.getString(3)));
//            Toast.makeText(this, ""+getdata.getString(3), Toast.LENGTH_SHORT).show();
        }
    }
    //-------------------------------------------------------------------------------------

    // Khong Nho mat khau
    //-------------------------------------------------------------------------------------
    public void delete_rename(){
        sqLite.QueryData("delete from "+val.Name_table_sqlite);
    }
    //-------------------------------------------------------------------------------------

}