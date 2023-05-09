package erp.billing.service;

import erp.billing.dto.CustomResponse;
import erp.billing.dto.UserDetail;
import erp.billing.dto.request.ClientInvoiceAddUpdateRequest;
import erp.billing.dto.request.ClientInvoiceExportExcelRequest;
import erp.billing.dto.request.ClientInvoiceFetchRequest;
import erp.billing.dto.request.ClientInvoiceNextPossibleStatesFetchRequest;
import erp.billing.dto.request.ClientInvoicePdfRequest;
import erp.billing.dto.request.ClientIrnCancelRequest;

public interface ClientInvoiceService {

	CustomResponse addOrUpdateClientInvoice(ClientInvoiceAddUpdateRequest requestObj);

	CustomResponse getClientInvoiceList(ClientInvoiceFetchRequest requestObj);

	CustomResponse deactivateClientInvoiceById(Long clientInvoiceId, Long userId);

	CustomResponse getClientInvoiceById(Long clientInvoiceId);

	CustomResponse getNextPossibleStates(ClientInvoiceNextPossibleStatesFetchRequest requestObj);

	CustomResponse getClientInvoiceTransitionByClientInvoiceId(Long clientInvoiceId);

	CustomResponse exportClientInvoiceExcel(ClientInvoiceExportExcelRequest requestObj);

	CustomResponse generateIrn(Long clientInvoiceId, UserDetail userDetail);

	CustomResponse cancelIrn(ClientIrnCancelRequest requestDto);

	CustomResponse exportClientInvoicePdf(ClientInvoicePdfRequest requestDTO);

}
