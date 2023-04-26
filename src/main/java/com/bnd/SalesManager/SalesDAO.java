package com.bnd.SalesManager;


import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class SalesDAO {


    private final JdbcTemplate jdbcTemplate;

    public SalesDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Sale> list() {
        String sql = "SELECT * FROM SALES";
        List<Sale> listSale = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Sale.class));
        return listSale;
    }

    public void save(Sale sale) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withTableName("sales").usingColumns("ITEM","QUANTITY","AMOUNT");
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(sale);
        insertActor.execute(parameterSource);

    }

    public Sale get(int id) {
        String sql = "SELECT * FROM SALES WHERE id = ?";
        Object[] args = {id};
        Sale sale = jdbcTemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(Sale.class));
        return sale;
    }

    public void update(Sale sale) {
        String sql = "UPDATE SALES SET item=:item, quantity=:quantity, amount=:amount WHERE id=:id";
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(sale);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
        template.update(sql,param);
    }

    public void delete(int id) {
    String sql = "DELETE FROM SALES WHERE id = ?";
    jdbcTemplate.update(sql,id);
    }
}
