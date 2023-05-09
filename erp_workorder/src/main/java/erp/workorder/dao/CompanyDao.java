package erp.workorder.dao;

import java.util.List;

import erp.workorder.entity.Company;

public interface CompanyDao {

	List<Company> fetchCompanyList();

}
