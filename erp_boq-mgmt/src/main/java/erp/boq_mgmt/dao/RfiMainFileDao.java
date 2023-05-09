package erp.boq_mgmt.dao;

import java.util.List;

import erp.boq_mgmt.entity.RfiMainFile;

public interface RfiMainFileDao {

	List<RfiMainFile> fetchRfiMainFiles(Long rfiId);

	Long saveRfiMainFile(RfiMainFile rfiMainFile);

	Boolean updateRfiMainFile(RfiMainFile rfiMainFile);

}
