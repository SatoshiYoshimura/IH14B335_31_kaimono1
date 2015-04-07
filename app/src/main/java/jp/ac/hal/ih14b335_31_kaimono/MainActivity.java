package jp.ac.hal.ih14b335_31_kaimono;

import jp.ac.hal.ih14b335_31_kaimono.KaimonoData.Item;


import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.preference.PreferenceManager;

import java.util.Properties;

public class MainActivity extends Activity {
	//public void button1(View v){
	//	  KaimonoData data = KaimonoData.instance();
	//	  data.listname = "test";
	//	  data.items0.add(new KaimonoData.Item("test"));
	//	  MyHttpSaveTask task = new MyHttpSaveTask();
	//	  task.execute(KaimonoData.instance());  
	//	 }
		 
	//	 public void button2(View v){
		//  MyHttpLoadTask myLoad = new MyHttpLoadTask();
		//  myLoad.execute();
		  
	//	 }
	
	Fragment fragment_kounyu;
	Fragment fragment_mikou;
	private String listna;
	ListView listview0;
	ListView listview1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		fragment_kounyu = new fragment_kounyu();
		fragment_mikou = new fragment_mikou();
		FragmentTransaction f = getFragmentManager().beginTransaction();
		f.add(R.id.container,fragment_kounyu);
		f.add(R.id.container,fragment_mikou);
		f.hide(fragment_kounyu);
		f.hide(fragment_mikou);
		f.commit();
		
		
		
		//Tabモード
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		Tab taba = getActionBar().newTab().setText("未購入一覧").setTabListener(new MyTabListener(fragment_mikou));
		Tab tabb = getActionBar().newTab().setText("購入済み").setTabListener(new MyTabListener(fragment_kounyu));
		getActionBar().addTab(taba);
		getActionBar().addTab(tabb);

    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, 0, Menu.NONE, "設定");
		menu.add(Menu.NONE, 1, Menu.NONE, "ロード");
		menu.add(Menu.NONE, 2, Menu.NONE, "セーブ");
		menu.add(Menu.NONE, 3, Menu.NONE, "項目の追加");
		return true;
	}
	
	
	//リスト設定に取得
	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		listna = "aaa";
	//listna = pref.getString("listname", "");
	
	if (listna.length() == 0) {
		// Toast表示
		Toast.makeText(this, "リスト設定してください", Toast.LENGTH_LONG).show();

		// SettingActivity起動
		Intent intent = new Intent(this,SettingActivity.class);
		startActivity(intent);
		return;
	}

	KaimonoData kaimono = KaimonoData.instance();
	kaimono.listname = listna;
	MyHttpLoadTask task = new MyHttpLoadTask(getApplicationContext());
	task.execute();
		
	}
	

	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		final String listnana = pref.getString("listname", "test");
		
		
		
		
		switch (item.getItemId()) {
		//設定画面
		case 0:
			Intent inte = new Intent(this,SettingActivity.class);
			startActivity(inte);
			return true;
		//ロード
		case 1:
			//Intent inter = new Intent(this,MainActivity.class);
			//startActivity(inter);
			//Toast t = Toast.makeText(this, "ロード中",Toast.LENGTH_SHORT);
			// t.show();
			final KaimonoData kau = KaimonoData.instance();
			listview0 = (ListView) findViewById(R.id.listview0);
			listview1 = (ListView) findViewById(R.id.listview1);
			ArrayAdapter<KaimonoData.Item> adapter0 = new ArrayAdapter<KaimonoData.Item>
			(this, android.R.layout.simple_expandable_list_item_1,kau.items0);
			setListAdapter(adapter0);
			listview0.setAdapter(adapter0);
			
			ArrayAdapter<KaimonoData.Item> adapter1 = new ArrayAdapter<KaimonoData.Item>
			(this, android.R.layout.simple_expandable_list_item_1,kau.items1);
			setListAdapter(adapter1);
			listview1.setAdapter(adapter1);
			
			// リスト項目がクリックされた時の処理
			listview0.setOnItemClickListener(new OnItemClickListener() {
				// 項目クリック時
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						final int position, long id) {
					ListView listView0 = (ListView) parent;
					final String item = listview0.getItemAtPosition(position)
							.toString();
					// 変更ダイアログを表示
					AlertDialog.Builder builder = new AlertDialog.Builder(
							MainActivity.this);
					builder.setTitle("買い物情報の変更");
					builder.setSingleChoiceItems(new String[] { "未購入", "購入済",
							"削除" }, 0, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (which == 0) {
								// 未購入
							} else if (which == 1) {
								// 購入済
								kau.items0.remove(position);
								kau.items1.add(new KaimonoData.Item(item));
							} else if (which == 2) {
								// 削除
								kau.items0.remove(position);
							}
							KaimonoData.instance().listname = listnana;
							MyHttpSaveTask task2 = new MyHttpSaveTask();
							task2.execute();

							dialog.dismiss();
						}
					});
					builder.show();
				}
			});

			// リスト項目がクリックされた時の処理
			listview1.setOnItemClickListener(new OnItemClickListener() {
				// 項目クリック時
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						final int position, long id) {
					ListView listView1 = (ListView) parent;
					final String item = listview1.getItemAtPosition(position)
							.toString();
					// 変更ダイアログを表示
					AlertDialog.Builder builder = new AlertDialog.Builder(
							MainActivity.this);
					builder.setTitle("買い物情報の変更");
					builder.setSingleChoiceItems(new String[] { "未購入", "購入済",
							"削除" }, 0, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (which == 0) {
								// 未購入
								kau.items1.remove(position);
								kau.items0.add(new KaimonoData.Item(item));
							} else if (which == 1) {
								// 購入済
							} else if (which == 2) {
								// 削除
								kau.items1.remove(position);
							}
							KaimonoData.instance().listname = listnana;
							MyHttpSaveTask task2 = new MyHttpSaveTask();
							task2.execute();

                            dialog.dismiss();
						}
					});
					builder.show();
				}
			});			
			
			return true;
		//セーブ
		case 2:
		//	Toast te = Toast.makeText(this, "セーブ中",Toast.LENGTH_SHORT);
		//	te.show();
			
			KaimonoData.instance().listname = listnana;
			MyHttpSaveTask save = new MyHttpSaveTask();
			save.execute();

			return true;
		//追加
		case 3:
			Toast tea = Toast.makeText(this, "追加中",Toast.LENGTH_SHORT);
			tea.show();

			// 項目名入力ダイアログ：決定が押されたら入力値を用いて項目を追加する
			final EditText editText = new EditText(this);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("項目名を入力");
			builder.setView(editText);
			builder.setPositiveButton("決定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							String name = editText.getText().toString();
							if (name.length() == 0) {
							} else {
								// ～～～
								KaimonoData kaimono = KaimonoData.instance();
								/*
								 * kaimono.listname = listname; MyHttpLoadTask
								 * task = new
								 * MyHttpLoadTask(getApplicationContext());
								 * task.execute();
								 */
								kaimono.items0.add(new KaimonoData.Item(name));
								MyHttpSaveTask task2 = new MyHttpSaveTask();
								task2.execute();
							}
							dialog.dismiss();
						}
					});
			builder.setNegativeButton("キャンセル",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
				}
			});
			builder.setCancelable(true);
			AlertDialog dlg = builder.create();
			dlg.show();
			
			
			
			
			return true;
		default:
			break;
		}
		return false;
		
		
	}

	private void setListAdapter(ArrayAdapter<Item> adapter0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	// タブ切替
		public class MyTabListener implements ActionBar.TabListener {
			private Fragment mFragment;

			public MyTabListener(Fragment fragment) {
				mFragment = fragment;
			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				// ft.add(R.id.container, mFragment);
				// ft.attach(mFragment);
				ft.show(mFragment);
				if (tab.getText().equals("未購入一覧")) {
				} else if (tab.getText().equals("購入一覧")) {
				}
			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// ft.remove(mFragment);
				// ft.detach(mFragment);
				ft.hide(mFragment);
			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
			}
		}

        //ローディン
        public void loadButtonMethod(View v){
            ProgressBar p = (ProgressBar)findViewById(R.id.progressBar);
            p.setVisibility(View.VISIBLE);
        }
        //ローディン
        public void loadButtonMethod2(View v){
            ProgressBar p = (ProgressBar)findViewById(R.id.progressBar);
            p.setVisibility(View.INVISIBLE);
        }
}