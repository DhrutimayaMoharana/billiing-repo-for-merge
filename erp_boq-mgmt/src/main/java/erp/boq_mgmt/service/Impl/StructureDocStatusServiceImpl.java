package erp.boq_mgmt.service.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import erp.boq_mgmt.dao.StructureDocStatusDao;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.dto.response.IdNameDTO;
import erp.boq_mgmt.entity.StructureDocumentStatus;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.service.StructureDocStatusService;

@Transactional
@Service
public class StructureDocStatusServiceImpl implements StructureDocStatusService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private StructureDocStatusDao docStatusDao;

	@Override
	public CustomResponse getStructureDocStatus(SearchDTO search) {

		try {
			if (search.getCompanyId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide companyId.");
			}
			List<StructureDocumentStatus> docStatuses = docStatusDao.fetchStructureDocStatus(search);
			List<IdNameDTO> obj = new ArrayList<>();
			if (docStatuses != null)
				docStatuses.forEach(item -> obj.add(new IdNameDTO(item.getId().longValue(), item.getName())));
			return new CustomResponse(Responses.SUCCESS.getCode(), obj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
