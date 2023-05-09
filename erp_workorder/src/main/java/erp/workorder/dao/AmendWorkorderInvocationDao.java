package erp.workorder.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import erp.workorder.dto.request.AmendWorkorderInvocationGetRequest;
import erp.workorder.entity.AmendWorkorderInvocation;
import erp.workorder.entity.AmendWorkorderInvocationTransitionMapping;

public interface AmendWorkorderInvocationDao {

	List<AmendWorkorderInvocation> fetchWorkorderAmendmentInvocations(
			AmendWorkorderInvocationGetRequest clientRequestDTO);

	List<AmendWorkorderInvocationTransitionMapping> fetchAmendWorkorderInvocationStateMappings(
			Long amendWorkorderInvocationId);

	Long saveAmendWorkorderInvocation(AmendWorkorderInvocation invokeAmendment);

	void mapAmendWorkorderInvocationTransition(AmendWorkorderInvocationTransitionMapping trasition);

	AmendWorkorderInvocation fetchAmendWorkorderInvocationById(Long id);

	Boolean updateAmendWorkorderInvocation(AmendWorkorderInvocation savedInvocation);

	void forceUpdateAmendWorkorderInvocation(AmendWorkorderInvocation savedInvocation);

	List<AmendWorkorderInvocationTransitionMapping> fetchAmendWorkorderInvocationStateMappingsByAmendWorkorderInvocationIds(
			Set<Long> distinctInvocationIds);

	List<AmendWorkorderInvocationTransitionMapping> fetchWorkorderAmendmentInvocationStateTransitionList(
			AmendWorkorderInvocationGetRequest clientRequestDTO, Map<Integer, Set<Integer>> roleStateMap,
			Integer draftStateId, Set<Integer> stateVisibilityIds);

	Integer fetchWorkorderAmendmentInvocationStateTransitionListCount(
			AmendWorkorderInvocationGetRequest clientRequestDTO, Map<Integer, Set<Integer>> roleStateMap,
			Integer draftStateId, Set<Integer> stateVisibilityIds);

}
