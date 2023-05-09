package erp.boq_mgmt.service;

import erp.boq_mgmt.dto.BoqItemDTO;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.SearchDTO;

public interface BoqItemService {

	CustomResponse getBoqItems(SearchDTO search);

	CustomResponse addBoqItem(BoqItemDTO boq);

	CustomResponse updateBoqItem(BoqItemDTO boqDTO);

	CustomResponse getBoqItemById(SearchDTO search);

	CustomResponse getCategoryBoqs(SearchDTO search);

	CustomResponse getAllByWorkType(Long siteId, Integer workType);

}
