package erp.workorder.dao;

import java.util.List;

import erp.workorder.entity.WoTncMapping;
import erp.workorder.entity.WoTncMappingVersions;

public interface WorkorderTncMapDao {

	Boolean addWorkorderTncs(WoTncMapping woTnc);

	Boolean saveUpdateWorkorderTncs(WoTncMapping woTnc);

	WoTncMapping fetchWorkorderTncMapById(Long woTncId);
	
	void forceUpdateWorkorderTncMap(WoTncMapping woTnc);

	Object executeSQLQuery(String referenceSql);

	List<WoTncMapping> fetchWorkorderTncMapByWorkorderId(Long workorderId);

	Long saveWorkorderTermsAndConditionsVersion(WoTncMappingVersions woTncVersion);

}
