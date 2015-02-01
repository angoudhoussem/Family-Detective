package com.example.family__detective;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class List_adapter extends BaseAdapter {

	private ArrayList<Been> listData;

	private LayoutInflater layoutInflater;

	public List_adapter(Context context, ArrayList<Been> listData) {
		this.listData = listData;
		layoutInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return listData.size();
	}

	public Object getItem(int position) {
		return listData.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {

			convertView = layoutInflater.inflate(R.layout.listitem, null); //afficher les données de la base vers listeitem 

			holder = new ViewHolder();

			holder.nom = (TextView) convertView.findViewById(R.id.textView1);
			holder.prenom = (TextView) convertView.findViewById(R.id.textView2);
			holder.image = (ImageView) convertView.findViewById(R.id.imageView1);
			

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();// si vide , il l'affiche qd mm
		}

		Been newsItem = (Been) listData.get(position);
		holder.nom.setText(newsItem.getNomm());
		holder.prenom.setText(newsItem.getPrenomm());
		holder.image.setImageBitmap(ConvertImageFromStringToBitmap.convert(newsItem.getImgage()));
		
		
		return convertView;
	}

	static class ViewHolder {
		TextView nom;
		TextView prenom;
		ImageView image;
		
	}
}

