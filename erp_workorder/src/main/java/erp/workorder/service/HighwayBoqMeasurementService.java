package erp.workorder.service;

import org.springframework.web.multipart.MultipartFile;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;

public interface HighwayBoqMeasurementService {

	CustomResponse importHighwayBoqMeasurementExcel(MultipartFile excelFile, SearchDTO search);

}
