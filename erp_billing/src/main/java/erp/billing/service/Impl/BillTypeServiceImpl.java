package erp.billing.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import erp.billing.dao.BillTypeDao;
import erp.billing.dto.BillTypeDTO;
import erp.billing.dto.CustomResponse;
import erp.billing.dto.SearchDTO;
import erp.billing.entity.BillType;
import erp.billing.enums.Responses;
import erp.billing.service.BillTypeService;
import erp.billing.util.SetObject;

@Service
@Transactional
public class BillTypeServiceImpl implements BillTypeService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SetObject setObject;

	@Autowired
	private BillTypeDao billTypeDao;

	@Override
	public CustomResponse getBillTypes(SearchDTO search) {

		try {
			List<BillType> types = billTypeDao.fetchBillTypes(search);
			List<BillTypeDTO> results = new ArrayList<>();
			if (types != null) {
				for (BillType type : types) {
					BillTypeDTO obj = setObject.billTypeEntityToDto(type);
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
