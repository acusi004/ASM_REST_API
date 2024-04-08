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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.poly.heiudtph35761.R;
import com.poly.heiudtph35761.adapter.AdapterFruit;
import com.poly.heiudtph35761.databinding.DialogEditFruitsBinding;
import com.poly.heiudtph35761.databinding.FragmentSearchBinding;
import com.poly.heiudtph35761.handle.Item_Fruits_handle;
import com.poly.heiudtph35761.model.Fruits;
import com.poly.heiudtph35761.model.Response_M;
import com.poly.heiudtph35761.service.HttpRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class searchFragment extends Fragment{

    Context context;
    FragmentSearchBinding binding;
    View view;
    HttpRequest httpRequest;
    AdapterFruit adapterFruit;
    RecyclerView rcv_fruits;
    Item_Fruits_handle handle;
    private SharedPreferences sharedPreferences;
    private String token;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        httpRequest = new HttpRequest();

        sharedPreferences = getActivity().getSharedPreferences("INFO", MODE_PRIVATE);
        // lay token tu sharePreferences
        token = sharedPreferences.getString("token", "");
        httpRequest.callAPI().getListFruits().enqueue(getListFruitsAPI);

        binding.btnAdd.setOnClickListener(v -> {
            OpenDialog();
        });

        binding.edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    String key = binding.edtSearch.getText().toString();

                    httpRequest.callAPI().searchFruits(key)
                            .enqueue(getListFruitsAPI);

                    return true;
                }

                return false;
            }
        });
        view = binding.getRoot();
        return view;
    }

    private void OpenDialog(){
        DialogEditFruitsBinding binding1;
        binding1 = DialogEditFruitsBinding.inflate(getLayoutInflater());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(binding1.getRoot());
        Dialog dialog = builder.create();
        dialog.show();

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        binding1.btnEditConfirm.setOnClickListener(v -> {
            String name = binding1.edtEditName.getText().toString();
            String quantity = binding1.edtEditQuantity.getText().toString();
            String type = binding1.edtEditType.getText().toString();
            String price = binding1.edtEditPrice.getText().toString();
            String size = binding1.edtEditSize.getText().toString();
            String origin = binding1.edtEditOrigin.getText().toString();
            String status = binding1.edtEditStatus.getText().toString();
            String image = binding1.edtEditImage.getText().toString();

            Fruits fruits = new Fruits();

            if(name.isEmpty()|| quantity.isEmpty() || type.isEmpty()|| price.isEmpty()||size.isEmpty()||origin.isEmpty()||status.isEmpty()|| image.isEmpty()){
                Toast.makeText(getContext(), "Vui lòng nhập dữ liệu", Toast.LENGTH_SHORT).show();
            }else{
                fruits.setName(name);
                fruits.setQuantity(Integer.parseInt(quantity));
                fruits.setType(type);
                fruits.setPrice(Integer.parseInt(price));
                fruits.setSize(size);
                fruits.setOrigin(origin);
                fruits.setStatus(status);
                fruits.setImage(image);
                httpRequest.callAPI().addFruits(fruits).enqueue(responseFruitAPI);
                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }




        });
    }
    private void getDulieu(ArrayList<Fruits> list){
        rcv_fruits = binding.rcvFruits;

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