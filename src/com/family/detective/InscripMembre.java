package com.family.detective;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.family__detective.R;

public class InscripMembre extends Activity {
	EditText nomm, prenomm;
	String Nom, Prenom;
	ImageView img;
	Button invitation;
	String result = null;
	InputStream is;
	public static String ACTUAL_PASSWORD = null;
	public static Uri ACTUAL_IMAGE_PATH = null;
	private static int RESULT_LOAD_IMAGE = 1;
	String picturePath;
	Uri selectedImage = null;
	ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inviter_membre);
		nomm = (EditText) findViewById(R.id.id_nomm);
		prenomm = (EditText) findViewById(R.id.id_prenomm);
		img = (ImageView) findViewById(R.id.id_imageView1);
		invitation = (Button) findViewById(R.id.id_invitation);
		

		invitation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Nom = nomm.getText().toString();
				Prenom = prenomm.getText().toString();

				if (Nom.length() != 0 && Prenom.length() != 0) {
					new Loade().execute();
					Context context = getApplicationContext();
					CharSequence text = "vrai";
					int duration = Toast.LENGTH_SHORT;

					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
					
				}

				else

				{

					Context context = getApplicationContext();
					CharSequence text = " vide";
					int duration = Toast.LENGTH_SHORT;

					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}

			}
		});

		img.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// String msg;
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			selectedImage = data.getData();

			ACTUAL_IMAGE_PATH = selectedImage;

			try {

				img.setImageBitmap(scaleImage(this, selectedImage));

			} catch (Exception e) {

			}

			System.out.println("hello" + selectedImage);
		}

	}

	public static Bitmap scaleImage(Context context, Uri photoUri)
			throws IOException {
		InputStream is = context.getContentResolver().openInputStream(photoUri);
		BitmapFactory.Options dbo = new BitmapFactory.Options();
		dbo.inJustDecodeBounds = true;
		int MAX_IMAGE_DIMENSION = 140;
		BitmapFactory.decodeStream(is, null, dbo);
		is.close();

		int rotatedWidth, rotatedHeight;
		int orientation = getOrientation(context, photoUri);

		if (orientation == 90 || orientation == 270) {
			rotatedWidth = dbo.outHeight;
			rotatedHeight = dbo.outWidth;
		} else {
			rotatedWidth = dbo.outWidth;
			rotatedHeight = dbo.outHeight;
		}

		Bitmap srcBitmap;
		is = context.getContentResolver().openInputStream(photoUri);
		if (rotatedWidth > MAX_IMAGE_DIMENSION
				|| rotatedHeight > MAX_IMAGE_DIMENSION) {
			float widthRatio = ((float) rotatedWidth)
					/ ((float) MAX_IMAGE_DIMENSION);
			float heightRatio = ((float) rotatedHeight)
					/ ((float) MAX_IMAGE_DIMENSION);
			float maxRatio = Math.max(widthRatio, heightRatio);

			// Create the bitmap from file
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = (int) maxRatio;
			srcBitmap = BitmapFactory.decodeStream(is, null, options);
		} else {
			srcBitmap = BitmapFactory.decodeStream(is);
		}
		is.close();

		if (orientation > 0) {
			Matrix matrix = new Matrix();
			matrix.postRotate(orientation);

			srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0,
					srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, true);
		}

		String type = context.getContentResolver().getType(photoUri);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (type.equals("image/png")) {
			srcBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		} else if (type.equals("image/jpg") || type.equals("image/jpeg")) {
			srcBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		}
		byte[] bMapArray = baos.toByteArray();
		baos.close();
		return BitmapFactory.decodeByteArray(bMapArray, 0, bMapArray.length);
	}

	private String getEncodeData(Bitmap img) {
		String encodedimage1 = null;
		if (img != null) {
			try {

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				img.compress(Bitmap.CompressFormat.JPEG, 10, baos); // bm is the
																	// bitmap
																	// object
				byte[] b = baos.toByteArray();
				encodedimage1 = Base64.encodeToString(b, Base64.DEFAULT);
			} catch (Exception e) {
				System.out
						.println("Exception: In getEncodeData" + e.toString());
			}
		}
		return encodedimage1;
	}

	public static int getOrientation(Context context, Uri photoUri) {

		Cursor cursor = context.getContentResolver().query(photoUri,
				new String[] { MediaStore.Images.ImageColumns.ORIENTATION },
				null, null, null);

		if (cursor.getCount() != 1) {
			return -1;
		}

		cursor.moveToFirst();
		return cursor.getInt(0);

	}

	class Loade extends AsyncTask<String, String, String> {
		String result;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(InscripMembre.this);
			pDialog.setMessage(Html.fromHtml("<b>Chargement....</b><br/>"));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			String codeImage = null;
			try {
				codeImage = getEncodeData(scaleImage(getApplicationContext(),
						selectedImage));
				codeImage = URLEncoder.encode(codeImage, "UTF-8");
				System.out.println("img" + codeImage);
			} catch (Exception e) {

			}

			if (codeImage == null) {
				codeImage = "noImage";
			}
			HttpClient httpClient = new DefaultHttpClient();

			final HttpParams httpParams = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 100000);
			HttpConnectionParams.setSoTimeout(httpParams, 100000);

			String req = Nom.replaceAll("\\s+", "") + "/"
					+ Prenom.replaceAll("\\s+", "") + "/"

					+ codeImage.toString();
			System.out.println("res" + req);

			HttpGet httpGet = new HttpGet(
					"http://10.0.2.2:8085/projet/webresources/entity.membre/ajout/"
							+ Nom.replaceAll("\\s+", "") + "/"
							+ Prenom.replaceAll("\\s+", "") + "/"
							+ codeImage.toString());

			httpGet.setHeader("Content-Type", "text/plain");
			try {
				HttpResponse response = httpClient.execute(httpGet);
				HttpEntity entity = response.getEntity(); // reponse dans une
															// entit�
				is = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8")); // lire les
																// caracteres
																// spetiaux

				StringBuilder sb = new StringBuilder();

				String line = null;

				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				result = sb.toString();

				System.out.println("test" + result);
			}

			catch (Exception e) {

				System.out.println("erreur" + e.getMessage());

			}
			if (result.equals("yes")) {

				pDialog.cancel();

			}

			return result;
		}

		protected void onPostExecute(String file_url) {

			System.out.println("Resulta" + result);

			Toast.makeText(getApplicationContext(),
					"inscription r�ussi " + result, Toast.LENGTH_LONG).show();

			pDialog.cancel();
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			startActivity(new Intent(getApplicationContext(),
					Authentification.class));

		}

		return super.onKeyDown(keyCode, event);
	}

}
