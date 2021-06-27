package com.geko.test.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.geko.test.R;

public class LoginFragment extends Fragment {

  private static final String TAG = "Recents";

  public LoginFragment() {
    // Required empty public constructor
  }

  /**
   * @return A new instance of fragment RecentsFragment.
   */
  public static LoginFragment newInstance() {
    return new LoginFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View root = inflater.inflate(R.layout.fragment_login, container, false);

    return root;
  }
}
