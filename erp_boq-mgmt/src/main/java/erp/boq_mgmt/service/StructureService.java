package erp.boq_mgmt.service;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.dto.StructureDTO;

public interface StructureService {

	CustomResponse getStructures(SearchDTO search);

	CustomResponse addStructure(StructureDTO structureDTO);

	CustomResponse getStructureById(SearchDTO search);

	CustomResponse updateStructure(StructureDTO structureDTO);

	CustomResponse getTypeWiseStructures(SearchDTO search);
	
	CustomResponse removeStructure(SearchDTO search);

	CustomResponse getTypeStructures(SearchDTO search);

	CustomResponse getGroupStructures(Integer groupId);

}
