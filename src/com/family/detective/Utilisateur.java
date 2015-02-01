package com.family.detective;

import com.example.family__detective.ListMembre;
import com.example.family__detective.R;
import com.family.detective.maps.MainActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Utilisateur extends Activity {
	ImageView parametre,securite,suivant,tracage,plus;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ImageView id_parametre, id_securite, id_suivant, id_tracage;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.utiisateur);
		 parametre = (ImageView) findViewById(R.id.id_parametre);
		 securite = (ImageView) findViewById(R.id.id_securite);
		 suivant = (ImageView) findViewById(R.id.id_suivant);
		 tracage = (ImageView) findViewById(R.id.id_tracage);
		 plus = (ImageView) findViewById(R.id.id_plus);
		plus.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
			Intent intent= new Intent(getApplicationContext(),InscripMembre.class);
			startActivity(intent);
			}
		
			

		});
		tracage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(intent);
			}

	});
		parametre.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						ListMembre.class);
				startActivity(intent);
			}

	});
		
	
	}
	
	

}
