package com.poly.heiudtph35761;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationBarView;
import com.poly.heiudtph35761.databinding.ActivityMainBinding;
import com.poly.heiudtph35761.fragment.HomeFragment;
import com.poly.heiudtph35761.fragment.cartFragment;
import com.poly.heiudtph35761.fragment.profileFragment;
import com.poly.heiudtph35761.fragment.searchFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(menuItem -> {

            int item = menuItem.getItemId();

            if (item == R.id.home){
                replaceFragment(new HomeFragment());
            }else if(item == R.id.cart){
                replaceFragment(new cartFragment());
            }else if(item == R.id.profile){
                replaceFragment(new profileFragment());
            } else if (item ==R.id.search) {
                replaceFragment(new searchFragment());
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();

    }
}