package erp.boq_mgmt.dao;

import java.util.List;

import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.Unit;
import erp.boq_mgmt.entity.UnitType;

public interface UnitDao {

	Long saveUnit(Unit unit);

	List<Unit> fetchUnits(SearchDTO search);

	Integer fetchCount(SearchDTO search);

	List<UnitType> fetchUnitTypes(SearchDTO search);
	
	Long getIdByNameOrSave(SearchDTO search);

}
