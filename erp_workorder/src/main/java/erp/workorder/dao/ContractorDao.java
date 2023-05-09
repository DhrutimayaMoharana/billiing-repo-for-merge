package erp.workorder.dao;

import java.util.List;
import java.util.Set;

import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.CategoryContractorMapping;
import erp.workorder.entity.Contractor;
import erp.workorder.entity.ContractorBankDetail;
import erp.workorder.entity.ContractorBillingAddress;

public interface ContractorDao {

	List<Contractor> fetchContractors(SearchDTO search);

	Contractor fetchContractorById(Long contractorId);

	List<ContractorBankDetail> fetchBankDetailsByContractorId(Long contractorId);

	List<ContractorBillingAddress> fetchBillingAddressesByContractorId(Long contractorId);

	List<CategoryContractorMapping> fetchContractorsByCategoryId(Long categoryId);

	List<CategoryContractorMapping> fetchContractorsByCategoryIdsArr(Set<Long> categoryIds);

}
