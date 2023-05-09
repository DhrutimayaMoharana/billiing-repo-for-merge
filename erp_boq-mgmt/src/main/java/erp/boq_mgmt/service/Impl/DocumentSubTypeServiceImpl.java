package erp.boq_mgmt.service.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import erp.boq_mgmt.dao.DocumentSubTypeDao;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.dto.response.IdNameDTO;
import erp.boq_mgmt.entity.DocumentSubType;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.service.DocumentSubTypeService;

@Transactional
@Service
public class DocumentSubTypeServiceImpl implements DocumentSubTypeService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DocumentSubTypeDao docSubtypeDao;

	@Override
	public CustomResponse getDocSubTypes(SearchDTO search) {

		try {
			if (search.getCompanyId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide companyId.");
			}
			List<DocumentSubType> subtypes = docSubtypeDao.fetchDocSubTypes(search);
			List<IdNameDTO> obj = new ArrayList<>();
			if (subtypes != null)
				subtypes.forEach(item -> obj.add(new IdNameDTO(item.getId().longValue(), item.getName())));
			return new CustomResponse(Responses.SUCCESS.getCode(), obj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
