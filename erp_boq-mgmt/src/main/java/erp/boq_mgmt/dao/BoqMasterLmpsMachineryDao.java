package erp.boq_mgmt.dao;

import java.util.List;

import erp.boq_mgmt.entity.BoqMasterLmpsMachinery;

public interface BoqMasterLmpsMachineryDao {

	Long saveBoqMasterLmpsMachinery(BoqMasterLmpsMachinery boqMasterLmpsMachinery);

	List<BoqMasterLmpsMachinery> fetchBoqMasterLmpsMachineryByBoqMasterLmpsId(Long boqMasterLmpsId);

	BoqMasterLmpsMachinery fetchBoqMasterLmpsMachineryById(Long id);

	Boolean deactivateBoqMasterLmpsMachinery(BoqMasterLmpsMachinery dbObj);

	Boolean updateBoqMasterLmpsMachinery(BoqMasterLmpsMachinery boqMasterLmpsMachinery);

}
