package erp.boq_mgmt.service;

import java.util.LinkedHashSet;
import java.util.List;

import erp.boq_mgmt.dto.ChainageDTO;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.Chainage;

public interface ChainageService {

	CustomResponse addChainage(ChainageDTO chainageDTO);

	CustomResponse getChainages(SearchDTO search);
	
	List<Chainage> checkSaveAndGetAllSiteChainages(LinkedHashSet<String> chainageNames, SearchDTO search);

	CustomResponse getToChainagesByFrom(SearchDTO search);

	CustomResponse fixChainageConnections(SearchDTO search);

	CustomResponse getChainageSides();

}
