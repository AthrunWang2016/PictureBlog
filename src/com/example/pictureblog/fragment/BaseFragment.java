package com.example.pictureblog.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

public abstract class BaseFragment extends Fragment {

	View view;
	
	abstract void setViews();
	abstract void setLiseners();
	
}
