package com.family.detective;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.family__detective.CurlActivity;
import com.example.family__detective.R;
import com.family.detective.message.MainActivity;
import com.family.detective.message.SmsStatus;

public class Membre extends Activity {
	ImageView msg, conx, soos, help, sos, suivant;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.membre);
		msg = (ImageView) findViewById(R.id.id_msg);
		conx = (ImageView) findViewById(R.id.id_sesignaler);
		soos = (ImageView) findViewById(R.id.id_soos);
		help = (ImageView) findViewById(R.id.id_help);
		sos = (ImageView) findViewById(R.id.id_sos);
		suivant = (ImageView) findViewById(R.id.id_suivant);
		suivant.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
			Intent intent= new Intent(getApplicationContext(),
					Utilisateur.class);
			startActivity(intent);
			}
			 
		});
		msg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
			Intent intent= new Intent(getApplicationContext(),
					MainActivity.class);
			startActivity(intent);
			}
			 
		});
		help.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
			Intent intent= new Intent(getApplicationContext(),
					CurlActivity.class);
			startActivity(intent);
			}
			 
		});
	}
}
