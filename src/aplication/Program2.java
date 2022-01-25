package aplication;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;
import model.entities.Department;


public class Program2 {

	public static void main(String[] args) {
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		Scanner read = new Scanner(System.in);
		
		System.out.println("=== TEST 1: department findById =====");
		
		Department department = departmentDao.findById(3);
		
		System.out.println(department);
		
		
		System.out.println("\n=== TEST 3: department findAll =====");
		
		List<Department> listDepartment = departmentDao.findAll();
		listDepartment = departmentDao.findAll();
		
		for(Department objt : listDepartment) {
			System.out.println(objt);
		}

		
		System.out.println("\n=== TEST 4:  department insert =====");
		Department objDepartment = new Department(null, "Music");
		departmentDao.insert(objDepartment);
		System.out.println("Inserted! New id = " + objDepartment.getId());
		
		System.out.println("\n=== TEST 5: department update  =====");
		department = departmentDao.findById(1);
		department.setName("Marth Waine");
		departmentDao.update(department);
		System.out.println("Update complete");
		
		System.out.println("\n=== TEST 6: department delete  =====");
		System.out.print("Enter ID for delete test: ");
		int id = read.nextInt();
		departmentDao.deleteById(id);
		System.out.println("Delete completed");
		
		
		
		read.close();
	}

}
