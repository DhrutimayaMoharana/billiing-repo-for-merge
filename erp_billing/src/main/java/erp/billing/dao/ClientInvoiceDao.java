package erp.billing.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import erp.billing.dto.request.ClientInvoiceFetchRequest;
import erp.billing.entity.ClientInvoice;
import erp.billing.entity.ClientInvoiceProduct;
import erp.billing.entity.ClientInvoiceStateTransitionMapping;
import erp.billing.entity.ClientInvoiceStateTransitionMappingV2;

public interface ClientInvoiceDao {

	Long saveClientInvoice(ClientInvoice clientInvoice);

	Long saveClientInvoiceProduct(ClientInvoiceProduct productObj);

	List<ClientInvoice> fetchClientInvoiceList(ClientInvoiceFetchRequest requestObj);

	List<ClientInvoiceProduct> fetchClientInvoiceProductsByInvoiceIds(Set<Long> distinctClientInvoiceIds);

	ClientInvoice fetchClientInvoiceById(Long clientInvoiceId);

	Boolean deactivateClientInvoice(ClientInvoice clientInvoice);

	void deactivateClientInvoiceProduct(ClientInvoiceProduct ciProduct);

	ClientInvoice mergeClientInvoice(ClientInvoice clientInvoice);

	ClientInvoiceProduct mergeClientInvoiceProduct(ClientInvoiceProduct productObj);

	List<ClientInvoiceStateTransitionMapping> fetchClientInvoiceStateTransitionByClientInvoiceIds(
			Set<Long> distinctIds);

	void saveClientInvoiceStateTransitionMapping(ClientInvoiceStateTransitionMapping clientInvoiceStateTransitionMap);

	List<ClientInvoiceStateTransitionMappingV2> fetchClientInvoiceStateTransitionListByClientInvoiceId(
			Long clientInvoiceId);

	List<ClientInvoiceStateTransitionMappingV2> fetchClientInvoiceStateTransitionList(
			ClientInvoiceFetchRequest requestObj, Map<Integer, Set<Integer>> roleStateMap, Integer draftStateId,
			Set<Integer> stateVisibilityIds);

	Integer fetchClientInvoiceStateTransitionCount(ClientInvoiceFetchRequest requestObj,
			Map<Integer, Set<Integer>> roleStateMap, Integer draftStateId, Set<Integer> stateVisibilityIds);

}
