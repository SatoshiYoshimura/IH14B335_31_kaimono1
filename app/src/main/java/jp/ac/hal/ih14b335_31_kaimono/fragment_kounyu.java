package jp.ac.hal.ih14b335_31_kaimono;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class fragment_kounyu extends Fragment {
	
	public View fragment_kounyuv;
	public  fragment_kounyu() {}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
		View fragment_kounyuv = inflater.inflate(R.layout.fragment_kounyu,container,false);
		return fragment_kounyuv;
		
	}

}
