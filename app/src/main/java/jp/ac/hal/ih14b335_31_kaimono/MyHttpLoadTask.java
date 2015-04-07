package jp.ac.hal.ih14b335_31_kaimono;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


// 設定を元に、サーバからデータを読み取り
class MyHttpLoadTask extends AsyncTask<String, Integer, String>{
	private Context context;
	private ProgressDialog dialog;
	
	public MyHttpLoadTask(Context con){
		this.context = con;
	}
	
	@Override
	protected String doInBackground(String... params) {
		try{
			Thread.sleep(500);
			// 通信を行う
			DefaultHttpClient client = new DefaultHttpClient();
			// リクエストボディ
			JSONObject obj = new JSONObject();
			obj.put("action", "getlist");
			obj.put("listname", KaimonoData.instance().listname);
			String strJson = obj.toString(); 
			StringEntity body = new StringEntity(strJson, "UTF-8");

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
		Log.i("test", "---- load response ----");
		Log.i("test", result);
		
		KaimonoData.instance().clear();
		
		try{
			// 応答内容をKaimonoDataに変換する
			KaimonoData.instance().loadJson(result);
			
			// 確認
			KaimonoData data = KaimonoData.instance();
			Log.i("test", "size:" + data.items0.size());
		}
		catch(Exception ex){
		}
	}
};