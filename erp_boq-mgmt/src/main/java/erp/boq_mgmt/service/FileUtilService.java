package erp.boq_mgmt.service;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.FileEntity;

public interface FileUtilService {

	CustomResponse addFiles(SearchDTO search);
	
	FileEntity addFile(SearchDTO search);

}
