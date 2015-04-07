package jp.ac.hal.ih14b335_31_kaimono;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;


// サーバからデータを読み取り
class MyHttpSaveTask extends AsyncTask<KaimonoData, Integer, String>{
	public MyHttpSaveTask(){
	}
	
	private JSONArray generateJsonArray(ArrayList<KaimonoData.Item> items) throws Exception{
		JSONArray jarray = new JSONArray();
		for(KaimonoData.Item item : items){
			JSONObject obj = new JSONObject();
			obj.put("name", item.name);
			jarray.put(obj);
		}
		return jarray;
	}
	
	@Override
	protected String doInBackground(KaimonoData... params) {
		try{
			KaimonoData data = KaimonoData.instance();
			
			// 通信を行う
			DefaultHttpClient client = new DefaultHttpClient();
			
			// リクエストボディ
			JSONObject obj = new JSONObject();
			obj.put("action", "putlist");
			obj.put("listname", KaimonoData.instance().listname);
			obj.put("items0", generateJsonArray(data.items0));
			obj.put("items1", generateJsonArray(data.items1));
			String jsonString = obj.toString();
			StringEntity body = new StringEntity(jsonString, "UTF-8");
			// リクエスト
			HttpPost request = new HttpPost("http://apibox.sakura.ne.jp/st41/kaimono/api.php");
			request.addHeader("Content-type", "application/json");
			request.setEntity(body);
			// 送信
			HttpResponse response = client.execute(request);
			
			// 結果
			String result = EntityUtils.toString(response.getEntity(), "UTF-8");
			return result;
		}
		catch(Exception ex){
			Log.e("test", ex.toString());
		}
		return "";
	}
	@Override
	protected void onPostExecute(String result) {
		Log.i("test", "---- save response ----");
		Log.i("test", result);
	}
};

