package erp.workorder.dao;

import java.util.List;

import erp.workorder.entity.EngineState;
import erp.workorder.entity.EntityStateMap;
import erp.workorder.entity.StateTransition;
import erp.workorder.entity.UserRole;

public interface StateTransitionDao {

	StateTransition fetchStateTransition(Integer entityId, Integer siteId, Integer stateId, Integer roleId);

	List<StateTransition> fetchRoleStateTransitions(Integer entityId, Integer siteId, Integer stateId, Integer roleId);

	List<StateTransition> fetchStateTransitions(Integer entityId, Long siteId);

	EntityStateMap fetchEntityFinalState(Integer entityId);

	List<UserRole> fetchUserRoles(Integer companyId);

	List<EngineState> fetchEntityStates(Integer companyId);
}
