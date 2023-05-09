package erp.workorder.dao;

import java.util.List;

import erp.workorder.dto.SearchDTO;
import erp.workorder.entity.WoTnc;
import erp.workorder.entity.WoTncFormulaVariable;
import erp.workorder.entity.WoTypeTncMapping;
import erp.workorder.entity.WorkorderMasterVariable;

public interface WorkorderTncDao {

	List<WoTypeTncMapping> fetchWoTypeTermsConditions(Long typeId);

	List<WoTnc> fetchActiveTermsConditionsBySearch(SearchDTO search);

	List<WoTnc> fetchWoTncsByTncTypeId(Long id);

	Long saveTermCondition(WoTnc tnc);

	WoTnc fetchWoTncById(Long id);

	void forceUpdateWoTnc(WoTnc woTnc);

	Boolean updateTermCondition(WoTnc savedTnc);

	WoTncFormulaVariable fetchWoTncFormulaVariableByTncAndVar(Long woTncId, Long variableId);

	Long saveOrUpdateWoTncFormulaVariable(WoTncFormulaVariable fv);

	void forceDeactivateWoTncFormulaVariable(WoTncFormulaVariable savedWoTncFv);

	List<WorkorderMasterVariable> fetchWorkorderMasterVariables();

}
