package erp.billing.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import erp.billing.dao.BillDeductionDao;
import erp.billing.dto.BillDeductionDTO;
import erp.billing.dto.CustomResponse;
import erp.billing.dto.SearchDTO;
import erp.billing.entity.BillDeduction;
import erp.billing.enums.Responses;
import erp.billing.service.BillDeductionService;
import erp.billing.util.SetObject;

@Service
@Transactional
public class BillDeductionServiceImpl implements BillDeductionService {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SetObject setObject;

	@Autowired
	private BillDeductionDao deductionDao;

	@Override
	public CustomResponse getBillDeductions(SearchDTO search) {

		try {
			List<BillDeduction> types = deductionDao.fetchBillDeductions(search);
			List<BillDeductionDTO> results = new ArrayList<>();
			if (types != null) {
				for (BillDeduction type : types) {
					BillDeductionDTO obj = setObject.billDeductionEntityToDto(type);
					results.add(obj);
				}
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), results, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
