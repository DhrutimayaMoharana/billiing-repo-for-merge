package erp.workorder.service;

import org.springframework.web.multipart.MultipartFile;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.DieselRateMappingDTO;
import erp.workorder.dto.SearchDTO;

public interface DieselRateService {

	CustomResponse saveDieselRate(DieselRateMappingDTO dieselRateMapping);

	CustomResponse getDieselRates(SearchDTO search);

	CustomResponse getDieselRateById(SearchDTO search);

	CustomResponse updateDieselRate(DieselRateMappingDTO dieselRateDTO);

	CustomResponse deactivateDieselRate(SearchDTO search);

	CustomResponse importDieselRateMapping(MultipartFile excelFile, SearchDTO search);

}
