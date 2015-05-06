package com.yayat.viewmodel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Window;

import com.yayat.dao.NasabahDao;
import com.yayat.model.Nasabah;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class NasabahVM {

	@WireVariable
	NasabahDao nasabahDaoImpl;

	private Nasabah newNasabah = new Nasabah();
	private List<Nasabah> listOfNasabah;
	private List<String> listOfRemovedNo = new ArrayList<String>();

	@Init
	public void init() {
		listOfNasabah = nasabahDaoImpl.findAll();
	}

	@Command
	public void onShowReport() {
		System.out.println(Executions.getCurrent().getDesktop().getWebApp()
				.getResourcePaths("/")
				+ " path jasper");
		JRDataSource ds = new JRBeanCollectionDataSource(listOfNasabah);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(Executions
					.getCurrent().getDesktop().getWebApp().getRealPath("/")
					+ "/nasabah.jasper", new HashMap(), ds);
			JasperExportManager.exportReportToPdfStream(jasperPrint, output);
			// JasperExportManager.exportReportToXmlStream(jasperPrint, output);
			AMedia amedia = new AMedia("report", "pdf", "application/pdf",
					output.toByteArray());
			// Filedownload.save(amedia); //untuk download ke browser
			createIFrame(amedia);
		} catch (Exception e) {
			notifyError("Gagal generate report", e);
		}
	}

	public void createIFrame(AMedia amedia) {
		Window win = (Window) Executions.createComponents("/pages/report.zul",
				null, null);
		Iframe frame = (Iframe) win.getFellow("reportframe");
		frame.setContent(amedia);
	}

	@Command
	@NotifyChange("listOfNasabah")
	public void delete(@BindingParam("entity") Nasabah nasabah) {
		try {
			listOfRemovedNo.add(nasabah.getNo());
			listOfNasabah.remove(nasabah);
		} catch (Exception e) {
			notifyError("Gagal menghapus data", e);

		}
	}

	@Command
	@NotifyChange({ "newNasabah", "listOfNasabah" })
	public void confirmAdd(@BindingParam("data") Popup popup) {
		try {
			listOfNasabah.add(newNasabah);
			newNasabah = new Nasabah();
			popup.close();
		} catch (Exception e) {
			notifyError("Gagal membuat data nasabah", e);
		}

	}

	@Command
	@NotifyChange("listOfNasabah")
	public void submit() {
		try {
			// apply batch later
			for (String noBeDeleted : listOfRemovedNo) {
				nasabahDaoImpl.delete(noBeDeleted);
			}
			for (Nasabah nasabah : listOfNasabah) {
				nasabahDaoImpl.saveOrUpdate(nasabah);
			}
			notifySucces("Update data", "Update data nasabah berhasil");
		} catch (Exception e) {
			notifyError(e.getMessage(), e);
		}
	}

	private void notifyError(String msg, Exception e) {
		Messagebox.show(msg, "Error", Messagebox.OK, Messagebox.ERROR);
		Logger.getLogger(NasabahVM.class.getName()).log(Level.SEVERE, msg, e);
	}

	private void notifySucces(String title, String msg) {
		Messagebox.show(msg, title, Messagebox.OK, Messagebox.INFORMATION);
		Logger.getLogger(NasabahVM.class.getName()).log(Level.INFO, msg);
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
