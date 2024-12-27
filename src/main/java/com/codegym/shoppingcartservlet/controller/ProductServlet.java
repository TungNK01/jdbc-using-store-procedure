package com.codegym.shoppingcartservlet.controller;

import com.codegym.shoppingcartservlet.model.Product;
import com.codegym.shoppingcartservlet.service.ProductDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ProductServlet", urlPatterns = "/products")
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO;

    public void init() {
        productDAO = new ProductDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    showNewForm(request, response);
                    break;
                default:
                    break;
            }
        } catch (ServletException ex) {
            throw new ServletException(ex);
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    insertProduct(request, response);
                    break;
            }
        } catch (ServletException ex) {
            throw new ServletException(ex);
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/create.jsp");
        dispatcher.forward(request, response);
    }

    private void insertProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        Double price = Double.parseDouble(request.getParameter("price"));
        Double quantity = Double.parseDouble(request.getParameter("quantity"));
        Product newProduct = new Product(name, price, quantity);
        int result = 0;
        try {
            result = productDAO.insertProduct(newProduct);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/create.jsp");
        dispatcher.forward(request, response);
    }
}
