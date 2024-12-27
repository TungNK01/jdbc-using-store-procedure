package com.codegym.shoppingcartservlet.service;

import com.codegym.shoppingcartservlet.model.Product;

import java.sql.SQLException;

public interface IProductDAO {
    public int insertProduct(Product product) throws SQLException;
}
