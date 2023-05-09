package erp.billing.dao;

import java.util.List;
import java.util.Set;

import erp.billing.entity.ClientInvoiceIrnInfo;

public interface ClientInvoiceIrnInfoDao {

	public Long saveClientInvoiceIrnInfo(ClientInvoiceIrnInfo ClientInvoiceIrnInfoObj);

	public ClientInvoiceIrnInfo fetchclientInvoiceIrnInfoByClientId(Long clientInvoiceId);

	public Boolean updateClientInvoiceIrnInfo(ClientInvoiceIrnInfo ClientInvoiceIrnInfoObj);

	public List<ClientInvoiceIrnInfo> fetchAllclientInvoiceIrnInfoByClientInvoiceId(Set<Long> distinctIds);

}
