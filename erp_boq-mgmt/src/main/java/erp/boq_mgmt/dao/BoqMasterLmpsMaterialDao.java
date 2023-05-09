package erp.boq_mgmt.dao;

import java.util.List;

import erp.boq_mgmt.dto.request.MaterialFetchRequest;
import erp.boq_mgmt.entity.BoqMasterLmpsMaterial;
import erp.boq_mgmt.entity.Material;

public interface BoqMasterLmpsMaterialDao {

	Long saveBoqMasterLmpsMaterial(BoqMasterLmpsMaterial boqMasterLmpsMaterial);

	List<BoqMasterLmpsMaterial> fetchBoqMasterLmpsMaterialByBoqMasterLmpsId(Long boqMasterLmpsId);

	BoqMasterLmpsMaterial fetchBoqMasterLmpsMaterialById(Long id);

	Boolean deactivateBoqMasterLmpsMaterial(BoqMasterLmpsMaterial dbObj);

	Boolean updateBoqMasterLmpsMaterial(BoqMasterLmpsMaterial boqMasterLmpsMaterial);

	List<Material> fetchMaterialList(MaterialFetchRequest requestDTO);

}
