package erp.billing.dao;

import java.util.List;

import erp.billing.dto.SearchDTO;
import erp.billing.entity.DebitNoteItem;

public interface DebitNoteDao {

	List<DebitNoteItem> fetchDebitNoteItems(SearchDTO search);

}
