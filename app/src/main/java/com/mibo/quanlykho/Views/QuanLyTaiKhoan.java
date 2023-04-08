package com.mibo.quanlykho.Views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
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
import com.google.firebase.database.ValueEventListener;
import com.mibo.quanlykho.Controllers.AdapterTaikhoan;
import com.mibo.quanlykho.Controllers.RandomStringExmple;
import com.mibo.quanlykho.Models.TaiKhoan;
import com.mibo.quanlykho.Models.val;
import com.mibo.quanlykho.R;

import java.util.ArrayList;
import java.util.UUID;

public class QuanLyTaiKhoan extends AppCompatActivity {
    TextView btnThemTK, btnTailai;
    ListView listTK;
    AlertDialog dialog;

    public ArrayList<TaiKhoan> arrTaikhoan;
    public AdapterTaikhoan adapterTaikhoan;

    //    FirebaseAuth myAuth= val.firebaseAuth;
    DatabaseReference myData = val.databaseReference;

    String sign_name = "", sign_user = "", sign_pass = "", sign_old = "", sign_local = "", sign_quyen = "", uid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_tai_khoan);
        anhxa();


        AddDataList();
        adapterTaikhoan = new AdapterTaikhoan(this, R.layout.dong_tai_khoan, arrTaikhoan);
        listTK.setAdapter(adapterTaikhoan);
        btnThemTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThem();
            }
        });
        btnTailai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(QuanLyTaiKhoan.this, ""+arrTaikhoan.size(), Toast.LENGTH_SHORT).show();
                //arrTaikhoan.clear();
                //AddDataList();
                adapterTaikhoan.notifyDataSetChanged();
            }
        });
    }

    public void AddDataList(){
        myData.child(val.TT_Tai_Khoan).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                myData.child(val.TT_Tai_Khoan).child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        TaiKhoan taiKhoan = snapshot.getValue(TaiKhoan.class);
                        arrTaikhoan.add(taiKhoan);
                        adapterTaikhoan.notifyDataSetChanged();
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
    public void DialogXoa(String uid) {
        myData.child(val.TT_Tai_Khoan).child(uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    AddDataList();
                    Toast.makeText(QuanLyTaiKhoan.this, "Xóa tài khoản thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void DialogThem() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_dangky, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(QuanLyTaiKhoan.this);
        alert.setCancelable(false);
        alert.setTitle("Thêm tài khoản");
        alert.setView(alertLayout);

        dialog = alert.create();

        Button btn_sign_huy = alertLayout.findViewById(R.id.btn_signin_huy);
        Button btn_sign_xn = alertLayout.findViewById(R.id.btn_signin_xn);
        EditText txt_sign_name = alertLayout.findViewById(R.id.txt_signin_tenngdung);
        EditText txt_sign_email = alertLayout.findViewById(R.id.txt_signin_email);
        EditText txt_sign_pass = alertLayout.findViewById(R.id.txt_signin_pass);
        EditText txt_sign_lai_pass = alertLayout.findViewById(R.id.txt_signin_lai_pass);
        EditText txt_sign_old = alertLayout.findViewById(R.id.txt_signin_old);
        EditText txt_sign_local = alertLayout.findViewById(R.id.txt_signin_local);
        CheckBox cbo_sign_quyen = alertLayout.findViewById(R.id.cbo_signin_quyen);

        btn_sign_xn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_sign_pass.getText().toString().equals(txt_sign_lai_pass.getText().toString())) {
                    sign_name = txt_sign_name.getText().toString().trim();
                    sign_user = txt_sign_email.getText().toString().trim();
                    sign_pass = txt_sign_pass.getText().toString().trim();
                    sign_old = txt_sign_old.getText().toString().trim();
                    sign_local = txt_sign_local.getText().toString().trim();
                    if (cbo_sign_quyen.isChecked()) {
                        sign_quyen = val.admin;
                    } else if (!cbo_sign_quyen.isChecked()) {
                        sign_quyen = val.nv;
                    }
                    sign_in();
                } else {
                    Toast.makeText(QuanLyTaiKhoan.this, "Password không trùng khớp!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_sign_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public void DialogSua(String uidEdit) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_dangky, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(QuanLyTaiKhoan.this);
        alert.setTitle("Thay đổi thông tin");
        alert.setCancelable(false);
        alert.setView(alertLayout);

        dialog = alert.create();

        Button btn_sign_huy = alertLayout.findViewById(R.id.btn_signin_huy);
        Button btn_sign_xn = alertLayout.findViewById(R.id.btn_signin_xn);
        EditText txt_sign_name = alertLayout.findViewById(R.id.txt_signin_tenngdung);
        EditText txt_sign_email = alertLayout.findViewById(R.id.txt_signin_email);
        EditText txt_sign_pass = alertLayout.findViewById(R.id.txt_signin_pass);
        EditText txt_sign_lai_pass = alertLayout.findViewById(R.id.txt_signin_lai_pass);
        EditText txt_sign_old = alertLayout.findViewById(R.id.txt_signin_old);
        EditText txt_sign_local = alertLayout.findViewById(R.id.txt_signin_local);
        CheckBox cbo_sign_quyen = alertLayout.findViewById(R.id.cbo_signin_quyen);

        myData.child(val.TT_Tai_Khoan).child(uidEdit).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    TaiKhoan tk = snapshot.getValue(TaiKhoan.class);
                   // uid = snapshot.getKey();
                    sign_user = tk.getUser();
                    sign_pass = tk.getPass();
                    sign_name = tk.getName();
                    sign_local = tk.getLocal();
                    sign_old = tk.getOld();
                    sign_quyen = tk.getQuen();

                    txt_sign_name.setText(sign_name);
                    txt_sign_old.setText(sign_old);
                    txt_sign_local.setText(sign_local);
                    txt_sign_email.setText(sign_name);
                    txt_sign_pass.setText(sign_pass);
                    txt_sign_lai_pass.setText(sign_pass);

                    if (sign_quyen.equals(val.admin)) {
                        cbo_sign_quyen.setChecked(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_sign_xn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_sign_pass.getText().toString().equals(txt_sign_lai_pass.getText().toString())) {
                    sign_name = txt_sign_name.getText().toString().trim();
                    sign_user = txt_sign_email.getText().toString().trim();
                    sign_pass = txt_sign_pass.getText().toString().trim();
                    sign_old = txt_sign_old.getText().toString().trim();
                    sign_local = txt_sign_local.getText().toString().trim();
                    if (cbo_sign_quyen.isChecked()) {
                        sign_quyen = val.admin;
                    } else if (!cbo_sign_quyen.isChecked()) {
                        sign_quyen = val.nv;
                    }
                    edit(uidEdit);
                    dialog.dismiss();
                    finish();
                } else {
                    Toast.makeText(QuanLyTaiKhoan.this, "Password không trùng khớp!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_sign_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void edit(String uid) {
        TaiKhoan taiKhoan = new TaiKhoan(sign_user, sign_pass, sign_name, sign_old, sign_local, sign_quyen, uid);
        myData.child(val.TT_Tai_Khoan).child(uid).setValue(taiKhoan, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    Toast.makeText(QuanLyTaiKhoan.this, "Cập nhật thành công!!", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            }
        });
    }

    private void sign_in() {
        //quyen user
//        myAuth.createUserWithEmailAndPassword(sign_user, sign_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    myData.child(val.TT_Tai_Khoan).child(String.valueOf(myAuth.getUid())).setValue(taiKhoan, new DatabaseReference.CompletionListener() {
//                        @Override
//                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                            if (error==null){
//                                Toast.makeText(QuanLyTaiKhoan.this, "Đăng ký thành công!!", Toast.LENGTH_SHORT).show();
//                                dialog.cancel();
//                            }
//                        }
//                    });
//                } else
//                    Toast.makeText(QuanLyTaiKhoan.this, "Đăng ký thất bại!!", Toast.LENGTH_SHORT).show();
//            }
//        });

        RandomStringExmple randomStringExmple = new RandomStringExmple();
        UUID uuid = UUID.randomUUID();
//        String r=randomStringExmple.randomAlphaNumeric(20);
        uid = uuid.toString();
        TaiKhoan taiKhoan = new TaiKhoan(sign_user, sign_pass, sign_name, sign_old, sign_local, sign_quyen,uid);
        myData.child(val.TT_Tai_Khoan).child(uid).setValue(taiKhoan, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    Toast.makeText(QuanLyTaiKhoan.this, "Đăng ký thành công!!", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            }
        });
    }

    private void anhxa() {
        btnThemTK = findViewById(R.id.btnThemTK);
        listTK = findViewById(R.id.listTK);
        arrTaikhoan = new ArrayList<>();
        btnTailai = findViewById(R.id.btnRefresh);

    }
}