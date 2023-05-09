package erp.workorder.service;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;

public interface SiteService {

	CustomResponse getSiteById(SearchDTO search);

}
