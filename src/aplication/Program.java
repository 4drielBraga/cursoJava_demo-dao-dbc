package aplication;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;


public class Program {

	public static void main(String[] args) {
		

		SellerDao sellerDao = DaoFactory.createSellerDao();
		Scanner read = new Scanner(System.in);
		
		System.out.println("=== TEST 1: seller findById =====");
		
		Seller seller = sellerDao.findById(3);
		
		System.out.println(seller);
		
		System.out.println("\n=== TEST 2: seller findByDepartment =====");
		
		Department department = new Department(2,null);
		List<Seller> list = sellerDao.findByDepartment(department);
		
		for(Seller objt : list) {
			System.out.println(objt);
		}
		
		System.out.println("\n=== TEST 3: seller findAll =====");
		list = sellerDao.findAll();
		
		for(Seller objt : list) {
			System.out.println(objt);
		}

		
		System.out.println("\n=== TEST 4: seller seller insert =====");
		Seller objSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(),4000.00,department);
		sellerDao.insert(objSeller);
		System.out.println("Inserted! New id = " + objSeller.getId());
		
		System.out.println("\n=== TEST 5: seller update  =====");
		seller = sellerDao.findById(1);
		seller.setName("Marth Waine");
		sellerDao.update(seller);
		System.out.println("Update complete");
		
		System.out.println("\n=== TEST 6: seller delete  =====");
		System.out.print("Enter ID for delete test: ");
		int id = read.nextInt();
		sellerDao.deleteById(id);
		System.out.println("Delete completed");
		
		
		
		read.close();
	}

}
