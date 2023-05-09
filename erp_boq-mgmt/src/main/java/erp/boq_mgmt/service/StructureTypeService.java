package erp.boq_mgmt.service;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.dto.StructureTypeDTO;

public interface StructureTypeService {

	CustomResponse addStructureType(StructureTypeDTO typeDTO);

	CustomResponse updateStructureType(StructureTypeDTO typeDTO);

	CustomResponse getStructureTypeByIdOrName(SearchDTO search);

	CustomResponse getStructureTypes(SearchDTO search);

	CustomResponse removeStructureType(SearchDTO search);

}
