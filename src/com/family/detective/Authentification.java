package com.family.detective;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.example.family__detective.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Authentification extends Activity {
	EditText login, password;
	Button connexion, inc;
	ProgressDialog pDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.authentification);

		login = (EditText) findViewById(R.id.email);
		password = (EditText) findViewById(R.id.password);
		connexion = (Button) findViewById(R.id.connexion);
		inc = (Button) findViewById(R.id.inscription);
		inc.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					final Intent intent = new Intent(getApplicationContext(), Inscription.class);

					startActivity(intent);
				}
			});
		connexion.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				final Intent intent = new Intent(getApplicationContext(), Membre.class);
//
//				startActivity(intent);
				new Loade().execute();
			}
		});
		
	}
	class Loade extends AsyncTask<String, String, String> { 
		String result;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Authentification.this);
			pDialog.setMessage(Html.fromHtml("<b>Chargement....</b><br/>")); //avant le service
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {

			HttpClient httpClient = new DefaultHttpClient(); //acceder entre webservice et Serveur par http qui contient get et post
			final HttpParams httpParams = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 100000);
			HttpConnectionParams.setSoTimeout(httpParams, 100000);

			HttpGet httpGet = new HttpGet(
					"http://10.0.2.2:8085/projet/webresources/entity.profil/authen"
							+ "/" + login.getText().toString() + "/" + password.getText().toString()); // prend les valeurs du edit text
			httpGet.setHeader("Content-Type", "text/plain"); 
			try {
				HttpResponse response = httpClient.execute(httpGet); 
				result = EntityUtils.toString(response.getEntity()); // convertir la reponse "yes/no"  tostring
				

			} catch (Exception e) {

				System.out.println("erreur" + e.getMessage()); // exception

			}
			if (result.equals("yes")) { 

				pDialog.cancel();

			}
			return result;
		}

		protected void onPostExecute(String file_url) {

			if (result.equals("yes")) {

				Intent i = new Intent(getApplicationContext(),
						Membre.class);
				startActivity(i);
				pDialog.cancel();

				Toast.makeText(getApplicationContext(), "yes", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(getApplicationContext(), "erreur", Toast.LENGTH_SHORT)
						.show();
				pDialog.cancel();
			}

		}
	}
	

}
