package erp.boq_mgmt.dao;

import java.util.List;
import java.util.Set;

import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.StructureDocument;
import erp.boq_mgmt.entity.StructureDocumentFile;
import erp.boq_mgmt.entity.StructureDocumentFileVersion;
import erp.boq_mgmt.entity.StructureDocumentStateTransition;
import erp.boq_mgmt.entity.StructureDocumentVersion;

public interface StructureDocumentDao {

	List<StructureDocumentFile> fetchFiles(SearchDTO search);

	List<StructureDocument> fetchDocuments(SearchDTO search);

	List<StructureDocumentStateTransition> fetchStateMappings(Long structureDocumentId);

	List<StructureDocumentVersion> fetchDocumentVersions(Long structureDocumentId);

	List<StructureDocumentFileVersion> fetchDocumentVersionFiles(SearchDTO search);

	Long saveDocument(StructureDocument doc);

	Long saveDocFile(StructureDocumentFile structureDocumentFile);

	StructureDocument fetchDocumentById(Long id);

	Boolean updateDocument(StructureDocument savedDoc);

	void deactivateDocFile(Long documentId, Long fileId, Long userId);

	Boolean deactivateDocument(Long structureDocumentId, Long userId);

	void forceUpdateDocFile(StructureDocumentFile docFile);

	Boolean forceUpdateDocument(StructureDocument doc);

	void mapDocumentStateTransition(StructureDocumentStateTransition obj);

	Long forceSaveStructureDocumentVersion(StructureDocumentVersion docVersion);

	Long forceSaveStructureDocFileVersion(StructureDocumentFileVersion docFileVersion);

	List<StructureDocumentStateTransition> fetchStateMappingsByStructureDocumentIds(Set<Long> distinctStructureDocsIds);

}
