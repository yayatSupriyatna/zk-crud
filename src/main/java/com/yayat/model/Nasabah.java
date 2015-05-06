package com.yayat.model;

import java.io.Serializable;

public class Nasabah implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String no;
	private String nama;
	private String kota;
	private String alamat;
	private String tlp;
	
	
	
	public Nasabah(String no, String nama, String kota, String alamat,
			String tlp) {
		super();
		this.no = no;
		this.nama = nama;
		this.kota = kota;
		this.alamat = alamat;
		this.tlp = tlp;
	}
	public Nasabah() {
		
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public String getKota() {
		return kota;
	}
	public void setKota(String kota) {
		this.kota = kota;
	}
	public String getAlamat() {
		return alamat;
	}
	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}
	public String getTlp() {
		return tlp;
	}
	public void setTlp(String tlp) {
		this.tlp = tlp;
	}
	
	

}
