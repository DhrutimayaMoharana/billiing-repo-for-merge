package erp.boq_mgmt.service;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.request.ProjectPlanBoqAddUpdateRequest;
import erp.boq_mgmt.dto.request.ProjectPlanBoqFetchRequest;

public interface ProjectPlanBoqService {

	CustomResponse addUpdateProjectPlanBoq(ProjectPlanBoqAddUpdateRequest requestDTO);

	CustomResponse getProjectPlanBoq(ProjectPlanBoqFetchRequest requestDTO);

	CustomResponse getStructureTypeAndStructureByBoqId(Long boqId, Integer siteId);

}
