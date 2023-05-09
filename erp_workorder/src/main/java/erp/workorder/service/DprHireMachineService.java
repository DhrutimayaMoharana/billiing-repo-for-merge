package erp.workorder.service;

import org.springframework.web.multipart.MultipartFile;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.request.MachineDPRImportExcelRequest;
import erp.workorder.dto.request.MachineDprAddUpdateRequest;
import erp.workorder.dto.request.MachineDprDeactivateRequest;
import erp.workorder.dto.request.MachineDprGetRequest;

public interface DprHireMachineService {

	CustomResponse addOrUpdateMachineDPR(MachineDprAddUpdateRequest requestDTO);

	CustomResponse deactivateMachineDPR(MachineDprDeactivateRequest requestDTO);

	CustomResponse getMachineDPR(MachineDprGetRequest requestDTO);

	CustomResponse importMachineDPRExcel(MultipartFile excelFile, MachineDPRImportExcelRequest importExcelRequest);

	CustomResponse getMachineryAttendnaceStatus();

	CustomResponse getMachineryRunningMode();

	CustomResponse getMachineryShifts();

}
