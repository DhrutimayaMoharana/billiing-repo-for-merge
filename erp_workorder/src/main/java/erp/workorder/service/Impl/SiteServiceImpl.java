package erp.workorder.service.Impl;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import erp.workorder.dao.SiteDao;
import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.Site;
import erp.workorder.enums.Responses;
import erp.workorder.service.SiteService;
import erp.workorder.util.SetObject;

@Transactional
@Service
public class SiteServiceImpl implements SiteService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;
	@Autowired
	SiteDao siteDao;

	@Override
	public CustomResponse getSiteById(SearchDTO search) {

		try {
			Site site = siteDao.fetchSiteById(search.getSiteId());
			return new CustomResponse(Responses.SUCCESS.getCode(), setObject.siteEntityToDto(site),
					Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
