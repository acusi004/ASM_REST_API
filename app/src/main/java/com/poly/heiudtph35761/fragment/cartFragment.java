package com.poly.heiudtph35761.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poly.heiudtph35761.R;
import com.poly.heiudtph35761.databinding.FragmentCartBinding;


public class cartFragment extends Fragment {

    FragmentCartBinding binding;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        return view;
    }
}