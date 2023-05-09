package erp.workorder.dao;

import java.util.List;

import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.BorewellBoqMapping;
import erp.workorder.entity.GenericBoqMappingHighway;
import erp.workorder.entity.HighwayBoqMapping;

public interface HighwayBoqMapDao {

	List<HighwayBoqMapping> fetchHbqsByCategories(SearchDTO search);

	List<BorewellBoqMapping> fetchBbqsByCategories(SearchDTO search);

	List<GenericBoqMappingHighway> fetchGenericBbqsByCategories(SearchDTO search);

}
