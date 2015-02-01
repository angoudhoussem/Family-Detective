package com.family.detective.maps;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;

import com.example.family__detective.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity {
	private static LatLng position , ISITCom,stade;
	String result;
	String obj;
	JSONObject usersJsonObject;
	JSONArray data;
//	ArrayList<Vehicule> alluser = new ArrayList<Vehicule>();
	ProgressDialog pDialog;
	ArrayList<Marker> markerList;
	ArrayList<LatLng> markerPoints;
	Marker marker, myplace;
	MarkerOptions marker1;
	GoogleMap map;
	InputStream is = null;
	int n;
	LatLng tab[];


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maps);

		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		map.setTrafficEnabled(true);

		//markerList = new ArrayList<Marker>();

	//	new Loade().execute();
		
		//ISITCom = new LatLng(35.850212, 10.597382);
	//	myplace = map
				//.addMarker(new MarkerOptions()
						//.position(ISITCom)
						//.title("ISITcom")
						//.snippet(
						//		"Institut supérieur des sciences et technologie de communication")
						//.icon(BitmapDescriptorFactory
							//	.fromResource(R.drawable.icone)));
		//myplace.showInfoWindow();
		
		stade = new LatLng(35.825795, 10.609615);
		myplace = map
				.addMarker(new MarkerOptions()
						.position(stade)
						.title("stade")
						.snippet(
								"stade olympique")
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.icone)));
		myplace.showInfoWindow();

	}

	class Loade extends AsyncTask<String, String, String> {
		String result;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
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
					"http://10.0.2.2:8080/PFE/webresources/entity.vehicule/mapVehicule"
							);
			httpGet.setHeader("Content-Type", "application/json");
			try {
				HttpResponse response = httpClient.execute(httpGet);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"));

				StringBuilder sb = new StringBuilder();

				String line = null;

				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				result = sb.toString();
				JSONArray a = new JSONArray(result);
				usersJsonObject = new JSONObject();
				usersJsonObject.put("data", a);
				data = usersJsonObject.getJSONArray("data");

				
			//	System.out.println("alluer" + alluser);
			}
			catch (Exception e) {

				e.getMessage();

			}
			if (result.equals("yes")) {

				pDialog.cancel();

			}

			return result;
		}

		protected void onPostExecute(String file_url) {

//			for (int i = 0; i < alluser.size(); i++) {
//				// String snppt = null;
//				// String adress = restaurant.getAdresse();
//				// String info = restaurant.getInfo().toString();
//				// snppt = adress + " \n " + info;
//
//				// System.out.println("snppt" + snppt);
//
//				position = new LatLng(Double.parseDouble(alluser.get(i)
//						.getLatitude()), Double.parseDouble(alluser.get(i)
//						.getLongitude()));
//				marker = map.addMarker(new MarkerOptions().position(position)
//						// .title(alluser.get(i).getAdresse())
//						.title(vehicule.getMatricule())
//						.snippet(vehicule.getPrix())
//						.icon(BitmapDescriptorFactory
//								.fromResource(R.drawable.map)));
//
//				map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 35));
//				map.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
//				markerList.add(marker);
//
//			}
			// // latitude and longitude
			// double latitude = 35.850212;
			// double longitude = 10.597382;

		

			// map.moveCamera(CameraUpdateFactory.newLatLngZoom(ESSTHS, 35));
			// map.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);

			System.out.println("Result" + result);

			pDialog.cancel();
		}
	}

}
