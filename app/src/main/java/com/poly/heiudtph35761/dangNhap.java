package com.poly.heiudtph35761;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.poly.heiudtph35761.databinding.ActivityDangNhapBinding;
import com.poly.heiudtph35761.model.Response_M;
import com.poly.heiudtph35761.model.Users;
import com.poly.heiudtph35761.service.HttpRequest;

import retrofit2.Call;
import retrofit2.Callback;

public class dangNhap extends AppCompatActivity {
    ActivityDangNhapBinding binding;

    HttpRequest httpRequest;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDangNhapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        httpRequest = new HttpRequest();

        setPassUser();

        binding.btnDangNhap.setOnClickListener(v -> {
            Users users = new Users();
            String _username = binding.edtLgUsername.getText().toString();
            String _password = binding.edtLgPassword.getText().toString();
            if (_username.isEmpty() || _password.isEmpty()) {
                Toast.makeText(this, "Cần nhập dữ liệu", Toast.LENGTH_SHORT).show();
            } else {
                if(binding.chkRememberPass.isChecked()){
                    sharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user", _username);
                    editor.putString("pass", _password);
                    editor.commit();
                }else{
                    // is check == false
                    sharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user", "");
                    editor.putString("pass", "");
                    editor.commit();
                }
                httpRequest.callAPI().login(users).enqueue(responeLogin);
                finish();
            }
        });
        binding.tvLgDangKyTaiKhoan.setOnClickListener(v -> {
            startActivity(new Intent(dangNhap.this, dangKy.class));
            finish();

        });

    }

    private void setPassUser(){
        sharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE);
        String user = sharedPreferences.getString("user", "");
        String pass = sharedPreferences.getString("pass", "");
        binding.edtLgUsername.setText(user);
        binding.edtLgPassword.setText(pass);
    }
    Callback<Response_M<Users>> responeLogin = new Callback<Response_M<Users>>() {
        @Override
        public void onResponse(Call<Response_M<Users>> call, retrofit2.Response<Response_M<Users>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    SharedPreferences sharedPreferences = getSharedPreferences("INFO", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", response.body().getToken());
                    editor.putString("refreshToken", response.body().getRefreshToken());
                    editor.putString("id", response.body().getData().get_id());
                    editor.apply();
                    Toast.makeText(dangNhap.this, "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(dangNhap.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(dangNhap.this, "Đăng Nhập Thành Bại", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response_M<Users>> call, Throwable throwable) {
            System.out.println(throwable.getMessage());
        }
    };
}