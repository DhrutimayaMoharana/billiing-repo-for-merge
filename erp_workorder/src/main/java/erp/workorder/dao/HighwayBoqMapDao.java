package erp.workorder.dao;

import java.util.List;

import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.HighwayBoqMapping;

public interface HighwayBoqMapDao {

	List<HighwayBoqMapping> fetchHbqsByCategories(SearchDTO search);

}
