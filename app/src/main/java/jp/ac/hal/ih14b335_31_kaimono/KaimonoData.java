package jp.ac.hal.ih14b335_31_kaimono;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class KaimonoData{
	public static class Item{
		public String name = ""; // 項目名
		public Item(String name){
			this.name = name;
		}
		public String toString(){
			return name;
		}
	}
	
	public String listname = ""; // リスト名
	public ArrayList<Item> items0 = new ArrayList<Item>(); // 未購入
	public ArrayList<Item> items1 = new ArrayList<Item>(); // 購入済
	public void clear(){
		listname = "";
		items0.clear();
		items1.clear();
	}
	
	// シングルトン
	private static KaimonoData _instanceData = new KaimonoData();
	public static KaimonoData instance(){
		return _instanceData;
	}
	
	// JSONArrayをKaimonoData.Item群に変換
	private static ArrayList<KaimonoData.Item> generateItems(JSONArray jarray) throws Exception{
		ArrayList<KaimonoData.Item> items = new ArrayList<KaimonoData.Item>();
		for(int i = 0; i < jarray.length(); i++){
			JSONObject item = jarray.getJSONObject(i);
			String name = item.getString("name");
			// int state = item.getInt("state");
			items.add(new Item(name));
		}
		return items;
	}
	
	// JSON文字列からKaimonoDataを構築
	public void loadJson(String jsonString) throws Exception{
		this.clear();

		// jsonをパースしてKaimonoData形式に変換
		JSONObject obj = new JSONObject(jsonString);
		this.listname = obj.getString("listname");
		this.items0 = generateItems(obj.getJSONArray("items0"));
		this.items1 = generateItems(obj.getJSONArray("items1"));
	}
}

	

