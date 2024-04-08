package com.poly.heiudtph35761.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.poly.heiudtph35761.R;
import com.poly.heiudtph35761.databinding.DialogConfirmCartBinding;
import com.poly.heiudtph35761.databinding.DialogEditFruitsBinding;
import com.poly.heiudtph35761.databinding.ItemFruitBinding;
import com.poly.heiudtph35761.handle.Item_Fruits_handle;
import com.poly.heiudtph35761.model.Fruits;
import com.poly.heiudtph35761.model.Response_M;
import com.poly.heiudtph35761.service.HttpRequest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterFruit extends RecyclerView.Adapter<AdapterFruit.ViewHolderFruits> {

    ArrayList<Fruits> list;
    Context context;

    HttpRequest httpRequest;
    Item_Fruits_handle handle;

    AdapterFruit adapterFruit;

    private SharedPreferences sharedPreferences;
    private String token;
    public AdapterFruit(ArrayList<Fruits> list, Context context ) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public AdapterFruit.ViewHolderFruits onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFruitBinding binding = ItemFruitBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        adapterFruit = new AdapterFruit(list, context);
        return new ViewHolderFruits(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFruit.ViewHolderFruits holder, int position) {
        Fruits fruits = list.get(position);
        httpRequest = new HttpRequest();

        sharedPreferences = context.getSharedPreferences("INFO", Context.MODE_PRIVATE);
        // lay token tu sharePreferences
        token = sharedPreferences.getString("token", "");

        String img = list.get(position).getImage();
        Picasso.get().load(img).into(holder.binding.spImage);
        holder.binding.spName.setText(fruits.getName());
        holder.binding.spPrice.setText(String.valueOf(fruits.getPrice()+ " VND" ));
        holder.binding.spType.setText(fruits.getType());


        // edit
        holder.binding.itemFruit.setOnClickListener(v -> {
            DialogEditFruitsBinding binding;
            binding = DialogEditFruitsBinding.inflate(LayoutInflater.from(v.getContext()));
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setView(binding.getRoot());
            Dialog dialog = builder.create();
            dialog.show();

            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


            binding.edtEditName.setText(fruits.getName());
            binding.edtEditSize.setText(fruits.getSize());
            binding.edtEditQuantity.setText(String.valueOf(fruits.getQuantity()));
            binding.edtEditOrigin.setText(fruits.getOrigin());
            binding.edtEditType.setText(fruits.getType());
            binding.edtEditPrice.setText(String.valueOf(fruits.getPrice()));
            binding.edtEditStatus.setText(fruits.getStatus());
            binding.edtEditImage.setText(fruits.getImage());

            binding.btnEditConfirm.setOnClickListener(v13 -> {
                String _name = binding.edtEditName.getText().toString().trim();
                int _quantity = Integer.parseInt(binding.edtEditQuantity.getText().toString().trim());
                String _type = binding.edtEditType.getText().toString().trim();
                int _price = Integer.parseInt(binding.edtEditPrice.getText().toString().trim());
                String _size = binding.edtEditSize.getText().toString().trim();
                String _origin = binding.edtEditOrigin.getText().toString().trim();
                String _status = binding.edtEditStatus.getText().toString().trim();
                String _image = binding.edtEditImage.getText().toString().trim();
                if(_name.isEmpty()|| _type.isEmpty()||_size.isEmpty()||_origin.isEmpty()||_status.isEmpty()|| _image.isEmpty()){
                    Toast.makeText(context, "Vui lòng nhập dữ liệu", Toast.LENGTH_SHORT).show();
                }else{
                    Fruits fruits1 = new Fruits();
                    fruits1.setName(_name);
                    fruits1.setQuantity(_quantity);
                    fruits1.setType(_type);
                    fruits1.setPrice(_price);
                    fruits1.setSize(_size);
                    fruits1.setOrigin(_origin);
                    fruits1.setStatus(_status);
                    fruits1.setImage(_image);
                    httpRequest.callAPI().updateFruitsById(fruits.get_id(), fruits1).enqueue(responseFruitAPI);
                    dialog.dismiss();
                    Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                }
            });
        });

        // delete
        holder.binding.itemFruit.setOnLongClickListener(v -> {
            Dialog dialog = new Dialog(v.getContext());
            dialog.setContentView(R.layout.dialog_confirm_cart);
            dialog.show();

            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            Button btn_co;
            TextView btn_khong;
            btn_co =dialog.findViewById(R.id.btn_co);
            btn_khong =dialog.findViewById(R.id.btn_khong);

            btn_co.setOnClickListener(v1 -> {
                httpRequest.callAPI().deleteFruitsById(fruits.get_id()).enqueue(responseFruitAPI);
                adapterFruit.notifyDataSetChanged();
                dialog.dismiss();

                Toast.makeText(context, "xoa thanh cong", Toast.LENGTH_SHORT).show();
            });
            btn_khong.setOnClickListener(v12 -> {
                Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });

            return true;
        });

    }



    Callback<Response_M<ArrayList<Fruits>>> getListFruitsAPI = new Callback<Response_M<ArrayList<Fruits>>>() {
        @Override
        public void onResponse(Call<Response_M<ArrayList<Fruits>>> call, retrofit2.Response<Response_M<ArrayList<Fruits>>> response) {
            if(response.isSuccessful()){
                if(response.body().getStatus() ==200){
                    ArrayList<Fruits> list = response.body().getData();

                    Toast.makeText(context, "Lấy dữ liệu thành công", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response_M<ArrayList<Fruits>>> call, Throwable t) {
            Toast.makeText(context, "Lỗi" + t.getMessage() , Toast.LENGTH_SHORT).show();

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

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderFruits extends RecyclerView.ViewHolder {
        ItemFruitBinding binding;
        public ViewHolderFruits(@NonNull ItemFruitBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
