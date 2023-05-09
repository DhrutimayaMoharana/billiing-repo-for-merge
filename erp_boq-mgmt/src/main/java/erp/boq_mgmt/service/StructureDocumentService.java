package erp.boq_mgmt.service;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.dto.request.StructureDocumentDeactivateRequest;
import erp.boq_mgmt.dto.request.StructureDocumentRequest;

public interface StructureDocumentService {

	CustomResponse getStructureDocuments(SearchDTO search);

	CustomResponse getStructureDocumentRevisions(SearchDTO search);

	CustomResponse getStructureDocumentRevision(SearchDTO search);

	CustomResponse addUpdateStructureDocument(StructureDocumentRequest docRequest);

	CustomResponse deactivateStructureDocumentFiles(StructureDocumentDeactivateRequest remRequest);

	CustomResponse updateDocState(SearchDTO search);

	CustomResponse getNextPossibleStates(SearchDTO search);

	CustomResponse addStructureDocumentFiles(SearchDTO search);

}
