package com.poly.heiudtph35761.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.poly.heiudtph35761.R;
import com.poly.heiudtph35761.dangNhap;
import com.poly.heiudtph35761.databinding.DialogConfirmLogoutBinding;
import com.poly.heiudtph35761.databinding.DialogEditFruitsBinding;
import com.poly.heiudtph35761.databinding.FragmentProfileBinding;
import com.poly.heiudtph35761.model.Fruits;
import com.poly.heiudtph35761.model.Response_M;
import com.poly.heiudtph35761.model.Users;
import com.poly.heiudtph35761.service.HttpRequest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


public class profileFragment extends Fragment {

    HttpRequest httpRequest;

    ArrayList<Users> list;
    FragmentProfileBinding binding;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        httpRequest = new HttpRequest();
        httpRequest.callAPI().getListUser().enqueue(getListUsers);

        binding.tvProLogout.setOnClickListener(v -> {
            DialogConfirmLogoutBinding binding1;
            binding1 = DialogConfirmLogoutBinding.inflate(inflater, container, false);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(binding1.getRoot());
            Dialog dialog = builder.create();
            dialog.show();

            binding1.btnCo.setOnClickListener(v1 -> {
                Intent i = new Intent(getContext(), dangNhap.class);
                startActivity(i);
                getActivity().finish();
            });
            binding1.btnKhong.setOnClickListener(v1 -> {
                dialog.dismiss();
            });

        });

        view = binding.getRoot();
        return view;
    }

    private void getData(ArrayList<Users> list){
        for(Users users:list){
            String userName = users.getUsername();
            String email = users.getEmail();
            Toast.makeText(getContext(), "name: "+ userName+ "mail: "+ email, Toast.LENGTH_SHORT).show();
            binding.tvProName.setText(userName);
            binding.tvProEmail.setText(email);

        }
    }
    Callback<Response_M<ArrayList<Users>>> getListUsers = new Callback<Response_M<ArrayList<Users>>>() {
        @Override
        public void onResponse(Call<Response_M<ArrayList<Users>>> call, retrofit2.Response<Response_M<ArrayList<Users>>> response) {
            if(response.isSuccessful()){
                if(response.body().getStatus() ==200){
                    ArrayList<Users> list = response.body().getData();
                    getData(list);
                    Toast.makeText(getContext(), "Lấy dữ liệu thành công", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response_M<ArrayList<Users>>> call, Throwable t) {
            Log.d(">>>GetListUsers", "onFailure: " + t.getMessage());
        }

    };
}