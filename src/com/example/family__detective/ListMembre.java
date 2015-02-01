package com.example.family__detective;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;



public class ListMembre extends Activity {
	String result;
	String obj;
	JSONObject restaurantsJsonObject;
	JSONArray data;
	ArrayList<Been> alluser = new ArrayList<Been>();
	ProgressDialog pDialog;
	ListView listt;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		listt = (ListView) findViewById(R.id.listView1);
//		View header = (View) getLayoutInflater().inflate(
//				R.layout.listview_header_resto, null);
//		list.addHeaderView(header);
		listt = (ListView) findViewById(R.id.listView1);
		new Loade().execute();
//		listt.setOnItemClickListener(new OnItemClickListener() {
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				 
//			Intent intent = new Intent(getApplicationContext(),Affichedate.class);
// 			//intent.putExtra("valeur", "bonjour"+position);
//                
//				
//				startActivity(intent);
//			}
//		});

	}

	class Loade extends AsyncTask<String, String, String> {
		String result;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ListMembre.this);
			pDialog.setMessage(Html.fromHtml("<b>Chargement....</b><br/>"));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {

			HttpClient httpClient = new DefaultHttpClient();

			final HttpParams httpParams = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 100000);
			HttpConnectionParams.setSoTimeout(httpParams, 100000);

			HttpGet httpGet = new HttpGet(
					"http://10.0.2.2:8085/projet/webresources/entity.membre/recherche");

			httpGet.setHeader("Content-Type", "application/json");
			try {
				HttpResponse response = httpClient.execute(httpGet);
				result = EntityUtils.toString(response.getEntity());

				System.out.println("test" + result);

				JSONArray a = new JSONArray(result);
				restaurantsJsonObject = new JSONObject();
				restaurantsJsonObject.put("data", a);
				data = restaurantsJsonObject.getJSONArray("data");

				for (int t = 0; t < data.length(); t++) {

					Been List = new Been();
					JSONObject JObject = data.getJSONObject(t);
					List.setNomm(JObject.getString("nom"));
					List.setImgage(JObject.getString("image"));
					List.setPrenomm(JObject.getString("prenom"));

					alluser.add(List);

				}
				System.out.println("alluer" + alluser);
			}

			catch (Exception e) {

				e.getMessage();

			}

			return result;
		}

		protected void onPostExecute(String file_url) {

			System.out.println("Result" + result);

			pDialog.cancel();

			// pDialog.dismiss();
			listt.setAdapter(new List_adapter(getApplicationContext(), alluser));

		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
//			startActivity(new Intent(getApplicationContext(),
//					MainActivityCarousel.class));

		}

		return super.onKeyDown(keyCode, event);
	}

}
