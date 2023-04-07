package com.mibo.quanlykho.Views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mibo.quanlykho.MainActivity;
import com.mibo.quanlykho.Models.TaiKhoan;
import com.mibo.quanlykho.R;

import java.util.ArrayList;

public class DangNhap extends AppCompatActivity {
    TextView btnDangNhap;
    EditText edtTK,edtMK;
    CheckBox luuDN;
    SharedPreferences sharedPreferences;
    DatabaseReference mydata;

    SharedPreferences.Editor editor;
    ArrayList<TaiKhoan> arrayUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        anhxa();
        arrayUser = new ArrayList<TaiKhoan>();
        //initPreferences();\
        TaiKhoan taiKhoan=new TaiKhoan("hung","123",true);
        TaiKhoan taiKhoan2=new TaiKhoan("hung1","321",true);
        mydata.child("user").child("2").setValue(taiKhoan);
        mydata.child("user").child("3").setValue(taiKhoan2);


        mydata.child("user").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TaiKhoan taiKhoan = snapshot.getValue(TaiKhoan.class);
                arrayUser.add(taiKhoan);
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


        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtMK.getText().toString().trim().isEmpty() || edtTK.getText().toString().trim().isEmpty()){
                    Toast.makeText(DangNhap.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    String userName = edtTK.getText().toString().trim();
                    String passWord = edtMK.getText().toString().trim();

                    for(int i=0;i<arrayUser.size();i++){
                        if(userName.equals(arrayUser.get(i).getUsername()) && passWord.equals(arrayUser.get(i).getPassword())){
                            Toast.makeText(DangNhap.this, "ok", Toast.LENGTH_SHORT).show();
                            break;
                        }else Toast.makeText(DangNhap.this, "sai", Toast.LENGTH_SHORT).show();
                    }

                    if (luuDN.isChecked()){
                        editor.putString("username", userName);
                        editor.putString("pass", passWord);
                        editor.putBoolean("check",true);
                        editor.commit();
                    }else {
//                        editor.clear();
//                        editor.commit();
                    }
                }
            }
        });

    }
    private void anhxa(){
        btnDangNhap = findViewById(R.id.btnDangNhap);
        edtTK = findViewById(R.id.edtTK);
        edtMK = findViewById(R.id.edtMK);
        luuDN = findViewById(R.id.checkBox);
        mydata= FirebaseDatabase.getInstance().getReference();
    }
    private void initPreferences() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        String savedUsername = sharedPreferences.getString("username", "");
        edtTK.setText(savedUsername);
        String savedPass = sharedPreferences.getString("pass", "");
        edtMK.setText(savedPass);
        boolean savedCheck = sharedPreferences.getBoolean("check", false);
        luuDN.setChecked(savedCheck);

        if(!savedUsername.isEmpty() && !savedPass.isEmpty()){
            Intent intent = new Intent(DangNhap.this, MainActivity.class);
            intent.putExtra("username",savedUsername);
            startActivity(intent);
        }
    }
}