package erp.billing.dao;

import java.util.List;

import erp.billing.dto.SearchDTO;
import erp.billing.entity.DieselRateMapping;

public interface DieselRateDao {

	List<DieselRateMapping> fetchDieselRates(SearchDTO search);

}
