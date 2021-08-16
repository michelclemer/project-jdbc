package aplication;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

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
		System.out.println("==== Teste 2 : Seeler findByIdDepartment ====");
		Department department = new Department(2, null);
		List<Seller> list = sellerdao.findByDepartment(department);
		for(Seller obj: list) {
			System.out.println(obj);
		}
		System.out.println("==== Teste 3 : Seller findAll ====");
		
		list = sellerdao.findAll();
		for(Seller obj: list) {
			System.out.println(obj);
		}
		System.out.println("==== Teste 5 : Seller Insert ====");
		
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000, department);
		sellerdao.insert(newSeller);
		System.out.println("Inserted: "+newSeller.getId());
	}
}
