package com.yayat.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.yayat.model.Nasabah;

public class NasabahMapper implements RowMapper<Nasabah> {

	public Nasabah mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Nasabah(
				rs.getString("no"),
				rs.getString("nama"),
				rs.getString("kota"),
				rs.getString("alamat"),
				rs.getString("tlp")
				);
	}

}
