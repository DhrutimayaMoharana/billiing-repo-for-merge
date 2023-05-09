package erp.boq_mgmt.dao;

import java.util.List;

import erp.boq_mgmt.entity.BoqMasterLmpsLabour;

public interface BoqMasterLmpsLabourDao {

	Long saveBoqMasterLmpsLabour(BoqMasterLmpsLabour boqMasterLmpsLabour);

	List<BoqMasterLmpsLabour> fetchBoqMasterLmpsLabourByBoqMasterLmpsId(Long boqMasterLmpsId);

	BoqMasterLmpsLabour fetchBoqMasterLmpsLabourById(Long id);

	Boolean deactivateBoqMasterLmpsLabour(BoqMasterLmpsLabour dbObj);

	Boolean updateBoqMasterLmpsLabour(BoqMasterLmpsLabour boqMasterLmpsLabour);

}
