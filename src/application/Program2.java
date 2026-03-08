package application;

import java.util.ArrayList;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		Department dep = new Department(null, "Food");
		departmentDao.insert(dep);
		System.out.println("Teste: department insert:");
		System.out.println("Department criado. ID = " + dep.getId());
		
		System.out.println("=============================");
		System.out.println("Teste: department update: ");
		dep.setName("Testando");
		departmentDao.update(dep);
		
		System.out.println("=============================");
		System.out.println("Teste: department deleteById: ");
		departmentDao.deleteById(8);
		
		System.out.println("=============================");
		System.out.println("Teste: department findById: ");
		Department findById = departmentDao.findById(1);
		System.out.println(findById);
		
		System.out.println("=============================");
		System.out.println("Teste: department findAll: ");
		List<Department> departmentList = new ArrayList<>();
		departmentList = departmentDao.findAll();
		for(Department x : departmentList) {
			System.out.println(x);
		}
	}

}
