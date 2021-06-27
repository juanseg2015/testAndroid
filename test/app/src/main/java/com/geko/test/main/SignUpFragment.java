package com.geko.test.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.geko.test.R;

public class SignUpFragment extends Fragment {

  public SignUpFragment() {
    // Required empty public constructor
  }

  public static SignUpFragment newInstance() {
    return new SignUpFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View root = inflater.inflate(R.layout.fragment_signup, container, false);
    return root;
  }
}
