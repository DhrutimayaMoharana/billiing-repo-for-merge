package erp.workorder.service;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.UserDetail;
import erp.workorder.dto.WorkorderBoqWorkDTO;

public interface WorkorderBoqWorkService {

	CustomResponse renderWoTypeWiseBoqWork(SearchDTO search);

	CustomResponse saveWorkorderBoqWork(WorkorderBoqWorkDTO boqWork);

	CustomResponse getWorkorderBoqWorkById(SearchDTO search);

	CustomResponse amendWorkorderBoqWorkFlow(UserDetail userDetail, Long amendWorkorderId, Long workorderId);

}
