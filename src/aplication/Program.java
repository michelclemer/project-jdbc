package aplication;

import java.sql.Connection;
import java.util.Date;

import db.DB;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {
	public static void main(String[] args) {

		SellerDao sellerdao = DaoFactory.createSellerDao();
		System.out.println("==== Teste 1 : Seeler findById ====");
		Seller seller = sellerdao.findById(3);
		System.out.println(seller);
	}
}
