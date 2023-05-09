package erp.boq_mgmt.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import erp.boq_mgmt.dto.request.RfiMainByStateActionFetchRequest;
import erp.boq_mgmt.dto.request.RfiMainFetchRequest;
import erp.boq_mgmt.entity.RfiMain;
import erp.boq_mgmt.entity.RfiMainComments;
import erp.boq_mgmt.entity.RfiMainStateTransition;

public interface RfiMainDao {

	RfiMain fetchRfiById(Long id);

	void forceUpdateRfi(RfiMain rfi);

	List<RfiMain> fetchAllRfiMain(RfiMainFetchRequest requestDTO);

	Long saveRfiMain(RfiMain rfi, Integer fromChainageNumericValue, Integer toChainageNumericValue);

	Boolean updateRfiMain(RfiMain rfi, Integer fromChainageNumericValue, Integer toChainageNumericValue);

	List<RfiMain> fetchByBoqStructureAndSite(Long structureId, Long boqId, Integer workLayerId, Integer siteId);

	List<RfiMain> fetchByBoqChainageRangeAndSite(Long boqId, Integer workLayerId, Integer fromChainageNameNumericValue,
			Integer toChainageNameNumericValue, Integer siteId);

	List<RfiMain> fetchByBoqHighwayAndSite(Long boqId, Integer workLayerId, Integer siteId);

	List<RfiMainStateTransition> fetchRfiMainStateTransitionList(RfiMainFetchRequest requestDTO,
			Map<Integer, Set<Integer>> roleStateMap);

	Integer fetchRfiMainStateTransitionListCount(RfiMainFetchRequest requestDTO,
			Map<Integer, Set<Integer>> roleStateMap);

	Long saveRfiMainStateTransitionMapping(RfiMainStateTransition stateTransition);

	List<RfiMain> fetchRfiMainByStateIds(Set<Integer> stateIds, RfiMainByStateActionFetchRequest requestDTO);

	Long fetchRfiMainByStateIdsCount(Set<Integer> stateIds, RfiMainByStateActionFetchRequest requestDTO);

	List<RfiMainComments> getRfiMainCommentsByRfiId(Long id);

	Long saveRfiMainComment(RfiMainComments rfiMainComment);

	Boolean updateRfiMainComment(RfiMainComments rfiMainComment);

	List<RfiMainComments> fetchRfiMainCommentByTypeAndRfiIds(Integer type, Set<Long> distinctRfiIds);

	List<RfiMainStateTransition> fetchRfiMainStateTransitionByRfiMainId(Long rfiMainId);

}
