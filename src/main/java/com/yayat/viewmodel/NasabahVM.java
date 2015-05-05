package com.yayat.viewmodel;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Popup;

import com.yayat.dao.NasabahDao;
import com.yayat.model.Nasabah;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class NasabahVM {

	@WireVariable
	NasabahDao nasabahDaoImpl;

	private Nasabah newNasabah= new Nasabah();
	private List<Nasabah> listOfNasabah;

	@Init
	public void init() {
		listOfNasabah = nasabahDaoImpl.findAll();
	}

	@Command
	@NotifyChange("listOfNasabah")
	public void delete(@BindingParam("entity") Nasabah nasabah) {
		try {
			listOfNasabah.remove(nasabah);
		} catch (Exception e) {
			//error info here
		}
	}

	

	@Command
	@NotifyChange({"newNasabah","listOfNasabah"})
	public void confirmAdd(@BindingParam("data")Popup popup) {
		try {
			listOfNasabah.add(newNasabah);
			newNasabah= new Nasabah();
			popup.close();
		} catch (Exception e) {
			//error info here
		}
	
	}

	@Command
	@NotifyChange("listOfNasabah")
	public void submit() {
		try {
			//apply batch later
			for(Nasabah nasabah:listOfNasabah){
				nasabahDaoImpl.saveOrUpdate(nasabah);
			}
		} catch (Exception e) {
			//error info here
		}
	}

	public void setNewNasabah(Nasabah newNasabah) {
		this.newNasabah = newNasabah;
	}
	public Nasabah getNewNasabah() {
		return newNasabah;
	}

	public List<Nasabah> getListOfNasabah() {
		return listOfNasabah;
	}

	public void setListOfNasabah(List<Nasabah> listOfNasabah) {
		this.listOfNasabah = listOfNasabah;
	}

}
