package erp.workorder.dao;

import java.util.Date;
import java.util.List;

import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.DieselRateMapping;
import erp.workorder.entity.DieselRateTransacs;

public interface DieselRateDao {

	Long saveDieselRate(DieselRateMapping dieselRateMapping);

	DieselRateMapping fetchDieselRateByDateAndSite(Long siteId, Date date);

	List<DieselRateMapping> fetchDieselRates(SearchDTO search);

	DieselRateMapping fetchDieselRateById(Long id);

	Boolean updateDieselRateMapping(DieselRateMapping drObj);

	void saveDieselRateTransac(DieselRateTransacs dieselRateTransac);

}
