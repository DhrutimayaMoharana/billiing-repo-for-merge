package erp.boq_mgmt.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import erp.boq_mgmt.dto.request.BoqMasterLmpsFetchRequest;
import erp.boq_mgmt.dto.request.BoqMasterLmpsFinalSuccessFetchRequest;
import erp.boq_mgmt.dto.request.UndefinedMasterLmpsBoqsFetchRequest;
import erp.boq_mgmt.entity.BoqMasterLmps;
import erp.boq_mgmt.entity.BoqMasterLmpsStateTransition;
import erp.boq_mgmt.entity.BoqItem;

public interface BoqMasterLmpsDao {

	Long saveBoqMasterLmps(BoqMasterLmps boqMasterLmps);

	BoqMasterLmps fetchBoqMasterLmpsById(Long id);

	Boolean deactivateBoqMasterLmps(BoqMasterLmps dbObj);

	Boolean updateBoqMasterLmps(BoqMasterLmps boqMasterLmps);

	Long saveBoqMasterLmpsStateTransitionMapping(BoqMasterLmpsStateTransition stateTransition);

	List<BoqMasterLmpsStateTransition> fetchBoqMasterLmpsStateTransitionByBoqMasterLmpsId(
			Long boqMasterLmpsId);

	List<BoqMasterLmpsStateTransition> fetchBoqMasterLmpsStateTransitionList(
			BoqMasterLmpsFetchRequest requestDTO, Map<Integer, Set<Integer>> roleStateMap);

	Integer fetchBoqMasterLmpsStateTransitionListCount(BoqMasterLmpsFetchRequest requestDTO,
			Map<Integer, Set<Integer>> roleStateMap);

	List<BoqMasterLmps> fetchBoqMasterLmpsByStateIds(Set<Integer> finalSuccessStateIds,
			BoqMasterLmpsFinalSuccessFetchRequest requestDTO);

	Long fetchBoqMasterLmpsByStateIdsCount(Set<Integer> finalSuccessStateIds,
			BoqMasterLmpsFinalSuccessFetchRequest requestDTO);

	List<BoqMasterLmps> fetchBoqMasterLmpsList(UndefinedMasterLmpsBoqsFetchRequest requestDTO);

	List<BoqItem> fetchUndefinedMasterLmpsBoqs(UndefinedMasterLmpsBoqsFetchRequest requestDTO,
			Set<Long> distinctDefinedMasterLmpsBoqIds);

	BoqMasterLmps fetchBoqMasterLmpsByBoqId(Long boqId);

}
