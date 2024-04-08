package com.poly.heiudtph35761.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.poly.heiudtph35761.R;
import com.poly.heiudtph35761.adapter.AdapterFruit;
import com.poly.heiudtph35761.databinding.DialogEditFruitsBinding;
import com.poly.heiudtph35761.databinding.FragmentHomeBinding;
import com.poly.heiudtph35761.handle.Item_Fruits_handle;
import com.poly.heiudtph35761.model.Fruits;
import com.poly.heiudtph35761.model.Response_M;
import com.poly.heiudtph35761.service.HttpRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private View view;

    RecyclerView rcv_fruits;

    HttpRequest httpRequest;

    AdapterFruit adapterFruit;
    private Context context;

    Item_Fruits_handle handle;
    ArrayList<Fruits> list;
    private SharedPreferences sharedPreferences;
    private String token;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container, false);
        httpRequest = new HttpRequest();


        sharedPreferences = getActivity().getSharedPreferences("INFO", MODE_PRIVATE);
        // lay token tu sharePreferences
        token = sharedPreferences.getString("token", "");
        httpRequest.callAPI().getListFruits().enqueue(getListFruitsAPI);


        view = binding.getRoot();
        return view;
    }

    private void getDulieu(ArrayList<Fruits> list){
        rcv_fruits = binding.rcvDis;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        rcv_fruits.setLayoutManager(layoutManager);
        adapterFruit = new AdapterFruit(list, getContext());
        rcv_fruits.setAdapter(adapterFruit);

    }







    Callback<Response_M<ArrayList<Fruits>>> getListFruitsAPI = new Callback<Response_M<ArrayList<Fruits>>>() {
        @Override
        public void onResponse(Call<Response_M<ArrayList<Fruits>>> call, retrofit2.Response<Response_M<ArrayList<Fruits>>> response) {
            if(response.isSuccessful()){
                if(response.body().getStatus() ==200){
                    ArrayList<Fruits> list = response.body().getData();
                    getDulieu(list);
                    Toast.makeText(getContext(), "Lấy dữ liệu thành công", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response_M<ArrayList<Fruits>>> call, Throwable t) {
            Toast.makeText(getContext(), "Lỗi" + t.getMessage() , Toast.LENGTH_SHORT).show();

        }
    };
    Callback<Response_M<Fruits>> responseFruitAPI = new Callback<Response_M<Fruits>>() {
        @Override
        public void onResponse(Call<Response_M<Fruits>> call, Response<Response_M<Fruits>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    httpRequest.callAPI().getListFruits().enqueue(getListFruitsAPI);
                }
            }
        }

        @Override
        public void onFailure(Call<Response_M<Fruits>> call, Throwable t) {
            Log.d(">>>GetListFruits", "onFailure: " + t.getMessage());
        }
    };
}