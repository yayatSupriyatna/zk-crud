package com.yayat.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.yayat.dao.NasabahDao;
import com.yayat.dao.NasabahMapper;
import com.yayat.model.Nasabah;

@Repository
@Transactional
public class NasabahDaoImpl implements NasabahDao {
	@Autowired
	DriverManagerDataSource dataSource;
	@Autowired
	JdbcTemplate jdbcTemplate;

	public void saveOrUpdate(Nasabah nasabah) {
		checkValidData(nasabah.getNo());
		SqlParameterSource params= new BeanPropertySqlParameterSource(nasabah);
		if (isNoNasabahExist(nasabah.getNo())) {
			jdbcTemplate.update("update nasabah set nama=?,kota=?alamat=?,tlp,where no=?",params);
		}else{
			SimpleJdbcInsert jdbcInsert= new SimpleJdbcInsert(dataSource);
			jdbcInsert.withTableName("nasabah").execute(params);
		}
	}

	public void delete(String noNasabah) {
		jdbcTemplate.update("delete from nasabah where no=?",noNasabah);
	}

	public List<Nasabah> findAll() {
		return jdbcTemplate.query(
				"select no,nama,kota,alamat,tlp from nasabah",
				new NasabahMapper()
				);
	}

	void checkValidData(String noNasabah) {
		if (noNasabah == null || noNasabah.isEmpty()) {
			throw new IllegalArgumentException("Invalid no");
		}
	}

	boolean isNoNasabahExist(String noNasabah) {
		return jdbcTemplate.query(
				"select no from nasabah where no=?",
				new String[] { noNasabah }, new ResultSetExtractor<String>() {
					public String extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						return rs.next() ? rs.getString("no") : null;
					}
				}) != null;
	}

}
