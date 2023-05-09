package erp.workorder.service;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.WorkorderMaterialConfigDTO;

public interface WorkorderMaterialConfigService {

	CustomResponse getMaterialGroups(SearchDTO search);

	CustomResponse issueMaterial(WorkorderMaterialConfigDTO woMaterialIssue);

	CustomResponse updateIssuedMaterial(WorkorderMaterialConfigDTO materialConfig);

	CustomResponse deactivateIssuedMaterial(SearchDTO search);

}
