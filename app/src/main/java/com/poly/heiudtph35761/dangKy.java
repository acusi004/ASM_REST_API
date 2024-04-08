package com.poly.heiudtph35761;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.poly.heiudtph35761.databinding.ActivityDangKyBinding;
import com.poly.heiudtph35761.model.Response_M;
import com.poly.heiudtph35761.model.Users;
import com.poly.heiudtph35761.service.HttpRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class dangKy extends AppCompatActivity {

    HttpRequest httpRequest;
    ActivityDangKyBinding binding;
    File file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDangKyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        httpRequest = new HttpRequest();

        binding.btnDkDangNhap.setOnClickListener(v -> {
            RequestBody _username = RequestBody.create(MediaType.parse("multipart/form-data"), binding.edtName.getText().toString());//RequestBody từ dữ liệu nhập từ trường txtuserdk và sử dụng loại dữ liệu multipart/form-data
            RequestBody _password = RequestBody.create(MediaType.parse("multipart/form-data"), binding.edtPassword.getText().toString());
            RequestBody _email = RequestBody.create(MediaType.parse("multipart/form-data"), binding.edtEmail.getText().toString());
            RequestBody _phone = RequestBody.create(MediaType.parse("multipart/form-data"), binding.edtPhone.getText().toString());
            MultipartBody.Part multipartbody;
            if (file != null){
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                multipartbody = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
            }else{
                multipartbody = null;
            }
                httpRequest.callAPI().register(_username, _password, _email, _phone,multipartbody).enqueue(responseUser);
        });

        binding.btnImage.setOnClickListener(v -> {
                chooseImage();
        });




    }

    Callback<Response_M<Users>> responseUser = new Callback<Response_M<Users>>() {
        //Phương thức này được gọi khi request đăng ký người dùng được gửi thành công
        @Override
        public void onResponse(Call<Response_M<Users>> call, retrofit2.Response<Response_M<Users>> response) {
            if(response.isSuccessful()){
                if (response.body().getStatus() == 200) {
                    Toast.makeText(dangKy.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(dangKy.this, dangNhap.class));
                    finish();
                } else {
                    Toast.makeText(dangKy.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        }
        @Override
        public void onFailure(Call<Response_M<Users>> call, Throwable t) {
            Log.e("Lỗi", t.getMessage());
        }
    };



    private void chooseImage(){
        if (checkSelfPermission(android.Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            getImage.launch(intent);
        }else{
            requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 1);
        }
    }
    private File createFileFromUri(Uri path, String name){
        File _file = new File(dangKy.this.getCacheDir() , name+".png");
        try {
            InputStream in = dangKy.this.getContentResolver().openInputStream(path);
            OutputStream out = new FileOutputStream(_file);
            byte[] buff = new byte[1024];
            int len;
            while ((len = in.read(buff))>0){
                out.write(buff, 0, len);
            }
            in.close();
            out.close();
            return _file;
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }
    ActivityResultLauncher<Intent> getImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o.getResultCode() == Activity.RESULT_OK){
                Intent data = o.getData();
                Uri imagePath = data.getData();
                file = createFileFromUri(imagePath, "avatar");
                Glide.with(dangKy.this)
                        .load(file)
                        .thumbnail(Glide.with(dangKy.this).load(R.mipmap.ic_launcher_round))
                        .centerCrop()
                        .circleCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(binding.shapeableImageView);
            }
        }
    });
}