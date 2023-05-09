package erp.boq_mgmt.dao;

import erp.boq_mgmt.entity.EntityStateMap;

public interface StateTransitionDao {

	EntityStateMap fetchEntityFinalState(Integer entityId);
}
