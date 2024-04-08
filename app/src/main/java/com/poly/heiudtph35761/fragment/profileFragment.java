package com.poly.heiudtph35761.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poly.heiudtph35761.R;
import com.poly.heiudtph35761.dangNhap;
import com.poly.heiudtph35761.databinding.DialogConfirmLogoutBinding;
import com.poly.heiudtph35761.databinding.DialogEditFruitsBinding;
import com.poly.heiudtph35761.databinding.FragmentProfileBinding;


public class profileFragment extends Fragment {


    FragmentProfileBinding binding;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
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
}