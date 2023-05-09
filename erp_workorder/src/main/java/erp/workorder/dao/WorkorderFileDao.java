package erp.workorder.dao;

import java.util.List;

import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.WorkorderFile;

public interface WorkorderFileDao {

	List<WorkorderFile> fetchWorkorderFiles(SearchDTO search);

	Long addWorkorderFile(WorkorderFile file);

	boolean deactivateWorkorderFile(Long woFileId, Long workorderId, Long userId);

}
