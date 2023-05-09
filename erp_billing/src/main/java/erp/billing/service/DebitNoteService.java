package erp.billing.service;

import erp.billing.dto.CustomResponse;
import erp.billing.dto.SearchDTO;

public interface DebitNoteService {

	CustomResponse getBillDebitNoteItems(SearchDTO search);

	CustomResponse getWorkorderDebitNoteItems(SearchDTO search);

}
