package jp.ac.hal.ih14b335_31_kaimono;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class fragment_mikou extends Fragment {

	public View fragment_mikouv;
	public  fragment_mikou() {}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
		View fragment_mikouv = inflater.inflate(R.layout.fragment_mikou,container,false);
		return fragment_mikouv;
		
	}
}
