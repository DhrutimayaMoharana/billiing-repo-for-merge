package erp.workorder.dao;

import java.util.List;

import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.MaterialGroup;
import erp.workorder.entity.WorkorderMaterialConfig;

public interface WorkorderMaterialConfigDao {

	List<MaterialGroup> fetchMaterialGroups(SearchDTO search);

	Long issueMaterial(WorkorderMaterialConfig woMaterialIssue);

	List<WorkorderMaterialConfig> fetchWorkorderMaterialConfig(SearchDTO search);

	Boolean updateMaterialConfig(WorkorderMaterialConfig issuedMaterial);

	WorkorderMaterialConfig fetchMaterialConfigById(Long id);

}
