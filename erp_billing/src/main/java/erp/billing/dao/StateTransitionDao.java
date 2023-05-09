package erp.billing.dao;

import erp.billing.entity.EntityStateMap;

public interface StateTransitionDao {

	EntityStateMap fetchEntityFinalState(Integer entityId);
}
