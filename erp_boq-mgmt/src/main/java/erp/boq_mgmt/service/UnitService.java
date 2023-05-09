package erp.boq_mgmt.service;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.dto.UnitDTO;

public interface UnitService {

	CustomResponse addUnit(UnitDTO unitDTO);

	CustomResponse getUnits(SearchDTO search);

	CustomResponse getUnitTypes(SearchDTO search);

}
