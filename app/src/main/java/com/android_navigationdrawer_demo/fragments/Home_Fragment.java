package com.android_navigationdrawer_demo.fragments;

import com.android_navigationdrawer_demo.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Home_Fragment extends Fragment{

@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view  =inflater.inflate(R.layout.home_fragment, container,false);
		return view;
	}
}
