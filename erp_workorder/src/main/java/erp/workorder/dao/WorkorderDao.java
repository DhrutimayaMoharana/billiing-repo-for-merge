package erp.workorder.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.WoTncMapping;
import erp.workorder.entity.Workorder;
import erp.workorder.entity.WorkorderBoqWork;
import erp.workorder.entity.WorkorderContractor;
import erp.workorder.entity.WorkorderStateTransitionMapping;
import erp.workorder.entity.WorkorderV2;
import erp.workorder.entity.WorkorderV3;
import erp.workorder.entity.WorkorderV4;
import erp.workorder.entity.WorkorderVersion;

public interface WorkorderDao {

	List<Workorder> fetchWorkorders(SearchDTO search);

	Integer fetchCountBySearch(SearchDTO search);

	Workorder fetchWorkorderById(Long workorderId);

	Workorder fetchLastWorkorderUniqueNoByTypeSiteAndCompany(Long siteId, Integer typeId, Integer companyId);

	Boolean forceUpdateWorkorder(Workorder workorder);

	Long saveWorkorderContractor(WorkorderContractor woContractor);

	Boolean addWorkorderBoqWork(WorkorderBoqWork workorderBoqWork, Long workorderId);

	Boolean addWorkorderContractor(WorkorderContractor workorderContractor, Long workorderId);

	Boolean finishDraft(Long workorderId);

	Boolean updateWorkorderContractor(WorkorderContractor woContractor);

	Workorder fetchDetachedWorkorderById(Long workorderId);

	List<Workorder> fetchWorkordersByTypeId(Long id);

	void mapWorkorderStateTransition(WorkorderStateTransitionMapping woStateTransitionMap);

	List<WorkorderStateTransitionMapping> fetchWorkorderStateMappings(Long workorderId);

	List<WorkorderV2> fetchWorkordersByStateId(SearchDTO search);

	List<WorkorderStateTransitionMapping> fetchWorkorderStateMappingsByWorkorderIds(Set<Long> distinctWorkorderIds);

	List<WorkorderV2> fetchWorkordersBySiteIds(Set<Long> distinctSiteIds);

	WorkorderV3 fetchWorkorderV3ByWorkorderId(Long workorderId);

	Boolean forceUpdateWorkorderBillInfo(WorkorderV3 workorder);

	Long saveWorkorderVersion(WorkorderVersion workorderVersion);

	Boolean forceUpdateWorkorderV3(WorkorderV3 workorder);

	List<Workorder> fetchWorkordersByEndDatesAndStateIds(Set<Integer> finalSuccessStateIds, Set<Date> distinctDateList,
			Integer id);

	WorkorderV4 fetchWorkorderV4ByWorkorderId(Long workorderId);

	Boolean forceUpdateWorkorderV4(WorkorderV4 workorder);

	List<WoTncMapping> fetchWorkorderTnCByWorkorderId(Long id);

}
