package erp.boq_mgmt.service;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.request.StructureGroupAddUpdateRequest;

public interface StructureGroupService {

	CustomResponse addUpdateStructureGroup(StructureGroupAddUpdateRequest groupRequest);

	CustomResponse getStructureGroupById(Integer groupId);

	CustomResponse getStructureGroups(Integer companyId, Long structureTypeId);

	CustomResponse deactivateStructureGroup(Integer groupId);

}
