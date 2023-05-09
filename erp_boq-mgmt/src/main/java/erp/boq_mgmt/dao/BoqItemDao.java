package erp.boq_mgmt.dao;

import java.util.List;

import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.BoqItem;

public interface BoqItemDao {

	List<BoqItem> fetchBoqItems(SearchDTO search);

	Integer fetchCount(SearchDTO search);

	Long saveBoqItem(BoqItem boq);

	BoqItem fetchBoqItemById(Long id);

	Boolean updateBoqItem(BoqItem boq);

	void forceUpdateBoqItem(BoqItem boq);
	
	Long getIdByCodeOrSave(SearchDTO search);
	
	String fetchCodeByStandardBookCode(String standardBookCode, Integer companyId);
	
	Long getIdByCode(SearchDTO search);
	
	Long getIdByCodeUpdateOrSave(SearchDTO search);
	
	Long getIdByCodeUpdate(SearchDTO search);

	BoqItem forceSaveBoqItem(BoqItem item);

}
