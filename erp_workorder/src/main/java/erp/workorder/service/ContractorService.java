package erp.workorder.service;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;

public interface ContractorService {

	CustomResponse getContractors(SearchDTO search);

	CustomResponse getContractorById(SearchDTO search);

	CustomResponse getBankDetailsByContractorId(SearchDTO search);

	CustomResponse getBillingAddressesByContractorId(SearchDTO search);

	CustomResponse getContractorsByCategory(SearchDTO search);

}
