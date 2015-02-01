package com.example.family__detective;

import com.google.android.gms.maps.model.LatLng;

public class Been {
	private static LatLng nom , prenom,img;
	String nomm;
	String prenomm;
	String imgage;
	public Been(String nomm, String prenomm, String imgage) {
		super();
		this.nomm = nomm;
		this.prenomm = prenomm;
		this.imgage = imgage;
	}
	public Been() {
		super();
	}
	public static LatLng getNom() {
		return nom;
	}
	public static void setNom(LatLng nom) {
		Been.nom = nom;
	}
	public static LatLng getPrenom() {
		return prenom;
	}
	public static void setPrenom(LatLng prenom) {
		Been.prenom = prenom;
	}
	public static LatLng getImg() {
		return img;
	}
	public static void setImg(LatLng img) {
		Been.img = img;
	}
	public String getNomm() {
		return nomm;
	}
	public void setNomm(String nomm) {
		this.nomm = nomm;
	}
	public String getPrenomm() {
		return prenomm;
	}
	public void setPrenomm(String prenomm) {
		this.prenomm = prenomm;
	}
	public String getImgage() {
		return imgage;
	}
	public void setImgage(String imgage) {
		this.imgage = imgage;
	}
	

}
