package com.yayat.dao;


import java.util.List;

import com.yayat.model.Nasabah;

public  interface NasabahDao {
	void saveOrUpdate(Nasabah nasabah);
	void delete(String noNasabah);
	List<Nasabah> findAll();
}
