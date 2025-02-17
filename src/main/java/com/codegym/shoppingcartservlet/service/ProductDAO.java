package com.codegym.shoppingcartservlet.service;

import com.codegym.shoppingcartservlet.model.Product;

import java.sql.*;

public class ProductDAO implements IProductDAO{
    private String jdbcURL = "jdbc:mysql://localhost:3306/shopping_cart_servlet?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "12345678";

    public ProductDAO(){}
    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

     @Override
    public int insertProduct(Product product) throws SQLException {
        try (Connection connection = getConnection()) {
            CallableStatement cs=connection.prepareCall("{call insert_product(?,?,?,?)}");
            cs.setString(1, product.getName());
            cs.setDouble(2, product.getPrice());
            cs.setDouble(3, product.getQuantity());
            cs.registerOutParameter(4, Types.INTEGER);
            cs.executeUpdate();
            return cs.getInt(4);
        } catch (SQLException e) {
            printSQLException(e);
        }
        return 0;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
