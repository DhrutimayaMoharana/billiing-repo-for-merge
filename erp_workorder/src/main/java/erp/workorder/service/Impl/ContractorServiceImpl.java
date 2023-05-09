package erp.workorder.service.Impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import erp.workorder.dao.ContractorDao;
import erp.workorder.dto.ContractorBankDetailDTO;
import erp.workorder.dto.ContractorBillingAddressDTO;
import erp.workorder.dto.ContractorDTO;
import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.CategoryContractorMapping;
import erp.workorder.entity.Contractor;
import erp.workorder.entity.ContractorBankDetail;
import erp.workorder.entity.ContractorBillingAddress;
import erp.workorder.enums.Responses;
import erp.workorder.service.ContractorService;
import erp.workorder.util.SetObject;

@Transactional
@Service
public class ContractorServiceImpl implements ContractorService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;
	@Autowired
	private ContractorDao contractorDao;

	@Override
	public CustomResponse getContractors(SearchDTO search) {

		try {

			List<Contractor> contractors = contractorDao.fetchContractors(search);
			List<ContractorDTO> obj = new ArrayList<>();
			if (contractors != null)
				contractors.forEach(contractor -> obj.add(setObject.contractorEntityToDto(contractor)));

			return new CustomResponse(Responses.SUCCESS.getCode(), obj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getContractorById(SearchDTO search) {

		try {
			Contractor contractor = contractorDao.fetchContractorById(search.getContractorId());
			return new CustomResponse(Responses.SUCCESS.getCode(), setObject.contractorEntityToDto(contractor),
					Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getBankDetailsByContractorId(SearchDTO search) {

		try {
			List<ContractorBankDetail> list = contractorDao.fetchBankDetailsByContractorId(search.getContractorId());
			List<ContractorBankDetailDTO> obj = new ArrayList<>();
			if (list != null) {
				for (ContractorBankDetail cbd : list) {
					String cancelChequeFullPath = null;
					ContractorBankDetailDTO objToAdd = setObject.contractorBankDetailEntityToDto(cbd);
					if (cbd.getCancelChequeFile() != null && cbd.getCancelChequeFile().getModule() != null) {
						String baseUrl = cbd.getCancelChequeFile().getModule().getBaseUrl();
						cancelChequeFullPath = baseUrl + cbd.getCancelChequeFile().getPath();
					}
					objToAdd.setCancelChequeFilePath(cancelChequeFullPath);
					obj.add(objToAdd);
				}
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), obj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getBillingAddressesByContractorId(SearchDTO search) {

		try {
			List<ContractorBillingAddress> list = contractorDao
					.fetchBillingAddressesByContractorId(search.getContractorId());
			List<ContractorBillingAddressDTO> obj = new ArrayList<>();
			if (list != null)
				list.forEach(item -> obj.add(setObject.contractorBillingAddressEntityToDto(item)));
			return new CustomResponse(Responses.SUCCESS.getCode(), obj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getContractorsByCategory(SearchDTO search) {

		try {
			if (search.getIdsArrSet() == null || search.getIdsArrSet().size() < 1) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide category Ids.");
			}
			List<CategoryContractorMapping> categoryContractors = contractorDao
					.fetchContractorsByCategoryIdsArr(search.getIdsArrSet());
			Set<ContractorDTO> result = new LinkedHashSet<>();
			if (categoryContractors != null) {
				Set<Long> contractorIds = new LinkedHashSet<>();
				List<Contractor> contractors = contractorDao.fetchContractors(search);
				for (CategoryContractorMapping contactorCategory : categoryContractors) {
					contractorIds.add(contactorCategory.getContractorId());
				}
				Set<Long> filteredContractorIds = new LinkedHashSet<>();
				for (Long contractorId : contractorIds) {
					Set<Long> contractorCategoryIds = new LinkedHashSet<Long>();
					for (CategoryContractorMapping contactorCategory : categoryContractors) {
						if (contactorCategory.getContractorId().equals(contractorId)) {
							contractorCategoryIds.add(contactorCategory.getCategoryId());
						}
					}
					if (contractorCategoryIds.size() == search.getIdsArrSet().size()) {
						filteredContractorIds.add(contractorId);
					}
				}
				for (Long contractorId : filteredContractorIds) {
					for (Contractor vendor : contractors) {
						if (vendor.getId().equals(contractorId)) {
							result.add(setObject.contractorEntityToDto(vendor));
							break;
						}
					}
				}
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
