package erp.boq_mgmt.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import erp.boq_mgmt.dao.DocumentSubTypeDao;
import erp.boq_mgmt.dao.StructureDocStatusDao;
import erp.boq_mgmt.dao.StructureDocumentDao;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.EngineStateDTO;
import erp.boq_mgmt.dto.EntityStateMapDTO;
import erp.boq_mgmt.dto.NextStateTransitDTO;
import erp.boq_mgmt.dto.S3FileDTO;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.dto.StateTransitionDTO;
import erp.boq_mgmt.dto.request.StructureDocumentDeactivateRequest;
import erp.boq_mgmt.dto.request.StructureDocumentRequest;
import erp.boq_mgmt.dto.response.IdNameList;
import erp.boq_mgmt.dto.response.StructureDocumentResponse;
import erp.boq_mgmt.entity.DocumentSubType;
import erp.boq_mgmt.entity.DocumentType;
import erp.boq_mgmt.entity.EngineState;
import erp.boq_mgmt.entity.S3File;
import erp.boq_mgmt.entity.StructureDocument;
import erp.boq_mgmt.entity.StructureDocumentFile;
import erp.boq_mgmt.entity.StructureDocumentFileVersion;
import erp.boq_mgmt.entity.StructureDocumentStateTransition;
import erp.boq_mgmt.entity.StructureDocumentStatus;
import erp.boq_mgmt.entity.StructureDocumentVersion;
import erp.boq_mgmt.entity.User;
import erp.boq_mgmt.enums.EntitiesEnum;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.feignClient.service.WorkflowEngineService;
import erp.boq_mgmt.service.StructureDocumentService;
import erp.boq_mgmt.util.CustomValidationUtil;
import erp.boq_mgmt.util.SetObject;

@Service
@Transactional
public class StructureDocumentServiceImpl implements StructureDocumentService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StructureDocumentDao structureDocDao;

	@Autowired
	private StructureDocStatusDao structureDocStatusDao;

	@Autowired
	private DocumentSubTypeDao documentSubtypeDao;

	@Autowired
	private CustomValidationUtil validationService;

	@Autowired
	private WorkflowEngineService engineService;

	@Autowired
	private SetObject setObject;

	@Override
	public CustomResponse getStructureDocuments(SearchDTO search) {

		try {
			CustomResponse validationResponse = validationService.validateGetStructureDocuments(search);
			if (!validationResponse.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationResponse;
			}
			List<StateTransitionDTO> transitions = engineService.getStateTransition(
					EntitiesEnum.STRUCTURE_DOCUMENT.getEntityId(), search.getSiteId(), null, null,
					search.getCompanyId());
			List<StructureDocument> documents = structureDocDao.fetchDocuments(search);
			Set<Long> distinctStructureDocsIds = new HashSet<>();
			if (documents != null)
				documents.forEach(doc -> distinctStructureDocsIds.add(doc.getId()));
			List<StructureDocumentFile> docFiles = structureDocDao.fetchFiles(search);

			List<StructureDocumentStateTransition> docsStateMaps = structureDocDao
					.fetchStateMappingsByStructureDocumentIds(distinctStructureDocsIds);

			List<EntityStateMapDTO> entityStateMaps = engineService
					.getEntityStatesByCompanyId(EntitiesEnum.STRUCTURE_DOCUMENT.getEntityId(), search.getCompanyId());
			Set<Integer> entityFinalStateIds = new HashSet<>();
			Set<Integer> entityEditableStateIds = new HashSet<>();
			if (entityStateMaps != null) {
				for (EntityStateMapDTO esMapItr : entityStateMaps) {
					if (esMapItr.getIsEditable() != null && esMapItr.getIsEditable()) {
						entityEditableStateIds.add(esMapItr.getStateId());
					}
					if (esMapItr.getIsFinal() != null && esMapItr.getIsFinal()) {
						entityFinalStateIds.add(esMapItr.getStateId());
					}
				}
			}

			List<StructureDocumentResponse> allDocs = new ArrayList<>();
			if (documents != null) {
				for (StructureDocument document : documents) {
					User docUser = document.getModifiedByUser();

//					set edit mode
					Boolean isDocEditable = false;
					if (entityEditableStateIds.contains(document.getState().getId()))
						isDocEditable = true;

//					set next states
					List<NextStateTransitDTO> docNextStates = new ArrayList<>();
					if (transitions != null && docUser != null && docUser.getRole() != null) {
						List<NextStateTransitDTO> nextStates = new ArrayList<>();
						for (StateTransitionDTO st : transitions) {
							if (st.getStateId().equals(document.getState().getId())
									&& st.getRoleId().equals(docUser.getRole().getId())
									&& st.getNextRoleId().equals(search.getRoleId())) {
								NextStateTransitDTO nst = new NextStateTransitDTO();
								nst.setNextStateId(st.getNextStateId());
								nst.setNextRoleId(st.getNextRoleId());
								nst.setIsButtonRequired(true);
								nst.setNextRoleName(st.getNextRole().getName());
								nst.setNextStateName(st.getNextState().getName());
								nst.setButtonText(st.getNextState().getButtonText());
								nst.setIsButtonRequired(st.getNextState().getButtonText() != null ? true : false);
								nst.setNextStateAlias(st.getNextState().getAlias());
								nextStates.add(nst);
							}
						}
						docNextStates.addAll(nextStates);
					}

//					set final state mode
					Boolean docInFinalState = false;
					if (entityFinalStateIds.contains(document.getState().getId()))
						docInFinalState = true;

//					set to response obj
					StructureDocumentResponse docRes = new StructureDocumentResponse(document.getId(),
							document.getType() != null ? document.getType().getId() : null,
							document.getType() != null ? document.getType().getName() : null,
							document.getSubtype() != null ? document.getSubtype().getId() : null,
							document.getSubtype() != null ? document.getSubtype().getName() : null,
							document.getReference(), document.getStatus() != null ? document.getStatus().getId() : null,
							document.getStatus() != null ? document.getStatus().getName() : null, document.getDate(),
							document.getRemark(), document.getState() != null ? document.getState().getId() : null,
							document.getState() != null ? document.getState().getName() : null, null, isDocEditable,
							docNextStates, docInFinalState, document.getVersion());

//					add doc in result list
					boolean hasAdded = false;

					if (isDocEditable && document.getCreatedBy().equals(search.getUserId())) {
						allDocs.add(docRes);
						hasAdded = true;
					}

					if (!hasAdded && docNextStates.size() > 0) {
						allDocs.add(docRes);
						hasAdded = true;
					}
					if (!hasAdded && !isDocEditable) {
						List<StructureDocumentStateTransition> docStateMaps = new ArrayList<>();
						if (docsStateMaps != null) {
							for (StructureDocumentStateTransition sdstItr : docsStateMaps) {
								if (sdstItr.getStructureDocumentId().equals(document.getId())) {
									docStateMaps.add(sdstItr);
								}
							}
						}
						if (docStateMaps != null) {
							for (StructureDocumentStateTransition stm : docStateMaps) {
								if (stm.getCreatedBy().equals(search.getUserId())) {
									allDocs.add(docRes);
									hasAdded = true;
									break;
								}
							}
						}
					}
					if (!hasAdded && docInFinalState) {
						allDocs.add(docRes);
						hasAdded = true;
					}

				}
			}
			List<IdNameList> result = new ArrayList<>();

//			group docs type-wise and set files
			Set<Integer> uniqueDocTypeIds = new LinkedHashSet<>();
			for (StructureDocumentResponse docRes : allDocs) {
				if (docRes.getTypeId() != null)
					uniqueDocTypeIds.add(docRes.getTypeId());
			}
			for (Integer typeId : uniqueDocTypeIds) {
				String typeName = null;
				List<StructureDocumentResponse> typeDocs = new ArrayList<>();
				for (StructureDocumentResponse docRes : allDocs) {
					if (docRes.getTypeId().equals(typeId)) {
						if (typeName == null)
							typeName = docRes.getTypeName();
						List<S3FileDTO> docWiseFiles = new ArrayList<>();
						for (StructureDocumentFile file : docFiles) {
							if (file.getDocument().getId().equals(docRes.getId())) {
								docWiseFiles.add(setObject.s3FileEntityToDto(file.getFile()));
							}
						}
						docRes.setFiles(docWiseFiles);
						typeDocs.add(docRes);
					}
				}
				if (typeDocs.size() > 0) {
					result.add(new IdNameList(typeId.longValue(), typeName, typeDocs));
				}
			}

// 			return result

			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getStructureDocumentRevisions(SearchDTO search) {

		try {
			CustomResponse validationResponse = validationService.validateGetStructureDocumentRevisions(search);
			if (!validationResponse.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationResponse;
			}
			List<StructureDocumentVersion> docVersions = structureDocDao
					.fetchDocumentVersions(search.getStructureDocumentId());
			Set<Integer> versions = new HashSet<>();
			if (docVersions != null) {
				docVersions.forEach(docVersion -> versions.add(docVersion.getVersion()));
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), docVersions, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getStructureDocumentRevision(SearchDTO search) {

		try {
			CustomResponse validationResponse = validationService.validateGetStructureDocumentByRevision(search);
			if (!validationResponse.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationResponse;
			}
			List<StructureDocumentFileVersion> docFiles = structureDocDao.fetchDocumentVersionFiles(search);
			StructureDocumentResponse docRes = null;
			if (docFiles != null && docFiles.size() > 0) {
				StructureDocumentVersion document = docFiles.get(0).getDocumentVersion();
				docRes = new StructureDocumentResponse(document.getId(),
						document.getType() != null ? document.getType().getId() : null,
						document.getType() != null ? document.getType().getName() : null,
						document.getSubtype() != null ? document.getSubtype().getId() : null,
						document.getSubtype() != null ? document.getSubtype().getName() : null, document.getReference(),
						document.getStatus() != null ? document.getStatus().getId() : null,
						document.getStatus() != null ? document.getStatus().getName() : null, document.getDate(),
						document.getRemark(), document.getState() != null ? document.getState().getId() : null,
						document.getState() != null ? document.getState().getName() : null, null, null, null, null,
						document.getVersion());
				List<S3FileDTO> files = new ArrayList<>();
				for (StructureDocumentFileVersion file : docFiles) {
					files.add(setObject.s3FileEntityToDto(file.getFile()));
				}
				docRes.setFiles(files);
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), docRes, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse addUpdateStructureDocument(StructureDocumentRequest docRequest) {

		try {
			CustomResponse validationResponse = validationService.validateAddUpdateStructureDocument(docRequest);
			if (!validationResponse.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationResponse;
			}
//			add new doc
			if (docRequest.getId() == null) {
				int version = 0;
				// TODO Engine state initial work
				EngineStateDTO entityInitialState = engineService.getEntityInitialState(
						EntitiesEnum.STRUCTURE_DOCUMENT.getEntityId(), docRequest.getUser().getCompanyId());
				if (entityInitialState == null) {
					return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Define entity's initial state.");
				}
				StructureDocument doc = new StructureDocument(null, docRequest.getStructureId(),
						new DocumentType(docRequest.getTypeId()), null, docRequest.getReference(), null,
						docRequest.getDate(), docRequest.getRemark(), new EngineState(entityInitialState.getId()),
						version, true, new Date(), docRequest.getUser().getId(), new Date(),
						docRequest.getUser().getId(), docRequest.getSiteId());

				if (docRequest.getDocStatusName() != null && !docRequest.getDocStatusName().trim().isEmpty()) {
					Integer statusId = structureDocStatusDao.fetchIdByNameOrSave(docRequest.getDocStatusName().trim(),
							docRequest.getUser().getCompanyId(), docRequest.getUser().getId());
					if (statusId != null && statusId > 0) {
						doc.setStatus(new StructureDocumentStatus(statusId));
					}
				}

				if (docRequest.getSubtypeName() != null && !docRequest.getSubtypeName().trim().isEmpty()) {
					Integer subtypeId = documentSubtypeDao.fetchIdByNameOrSave(docRequest.getSubtypeName().trim(),
							docRequest.getUser().getCompanyId(), docRequest.getUser().getId());
					if (subtypeId != null && subtypeId > 0) {
						doc.setSubtype(new DocumentSubType(subtypeId));
					}
				}

				Long docId = structureDocDao.saveDocument(doc);
				doc.setId(docId);
//				save files
				if (docId != null && docId > 0L) {
					for (Long fileId : docRequest.getFileIds()) {
						structureDocDao.saveDocFile(new StructureDocumentFile(null, doc, new S3File(fileId), true,
								new Date(), docRequest.getUser().getId(), new Date(), docRequest.getUser().getId()));
					}
				}
				return new CustomResponse(Responses.SUCCESS.getCode(), docId,
						docId != null ? "Added." : "Already exists.");
			}
//			update existing doc
			else {
				StructureDocument savedDoc = structureDocDao.fetchDocumentById(docRequest.getId());
				Boolean isEntityEditable = engineService.findIfEntityInEditableState(
						EntitiesEnum.STRUCTURE_DOCUMENT.getEntityId(), docRequest.getUser().getCompanyId(),
						savedDoc.getState().getId());
				if (!isEntityEditable)
					return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Not in editable state.");
				savedDoc.setReference(docRequest.getReference());
				savedDoc.setDate(docRequest.getDate());
				savedDoc.setRemark(docRequest.getRemark());
				savedDoc.setModifiedOn(new Date());
				savedDoc.setModifiedBy(docRequest.getUser().getId());
				if (docRequest.getDocStatusName() != null && !docRequest.getDocStatusName().trim().isEmpty()) {
					Integer statusId = structureDocStatusDao.fetchIdByNameOrSave(docRequest.getDocStatusName().trim(),
							docRequest.getUser().getCompanyId(), docRequest.getUser().getId());
					if (statusId != null && statusId > 0) {
						if (savedDoc.getStatus() == null || !savedDoc.getStatus().getId().equals(statusId)) {
							savedDoc.setStatus(new StructureDocumentStatus(statusId));
						}
					} else {
						savedDoc.setStatus(null);
					}
				} else {
					savedDoc.setStatus(null);
				}

				if (docRequest.getSubtypeName() != null && !docRequest.getSubtypeName().trim().isEmpty()) {
					Integer subtypeId = documentSubtypeDao.fetchIdByNameOrSave(docRequest.getSubtypeName().trim(),
							docRequest.getUser().getCompanyId(), docRequest.getUser().getId());
					if (subtypeId != null && subtypeId > 0) {
						if (savedDoc.getSubtype() == null || !savedDoc.getSubtype().getId().equals(subtypeId)) {
							savedDoc.setSubtype(new DocumentSubType(subtypeId));
						}
					} else {
						savedDoc.setSubtype(null);
					}
				} else {
					savedDoc.setSubtype(null);
				}
				Boolean isUpdated = structureDocDao.updateDocument(savedDoc);

//				update files
				if (isUpdated) {
					List<Long> fileIdsRequest = docRequest.getFileIds();
					SearchDTO search = new SearchDTO();
					search.setStructureDocumentId(savedDoc.getId());
					List<StructureDocumentFile> savedFiles = structureDocDao.fetchFiles(search);
					Set<Long> savedFileIds = new HashSet<Long>();
					if (savedFiles != null) {
						savedFiles.forEach(item -> savedFileIds.add(item.getFile().getId()));
					}

					Set<Long> filesToAdd = new LinkedHashSet<Long>();
					for (Long fileIdReq : fileIdsRequest) {
						if (!savedFileIds.contains(fileIdReq))
							filesToAdd.add(fileIdReq);
					}
					for (Long fileId : filesToAdd) {
						structureDocDao.saveDocFile(new StructureDocumentFile(null, savedDoc, new S3File(fileId), true,
								new Date(), docRequest.getUser().getId(), new Date(), docRequest.getUser().getId()));
					}
					Set<Long> filesToDeactivate = new LinkedHashSet<Long>();
					for (Long savedFileId : savedFileIds) {
						if (!fileIdsRequest.contains(savedFileId))
							filesToDeactivate.add(savedFileId);
					}
					for (Long fileId : filesToDeactivate) {
						structureDocDao.deactivateDocFile(docRequest.getId(), fileId, docRequest.getUser().getId());
					}
				}
				return new CustomResponse(Responses.SUCCESS.getCode(), savedDoc.getId(), "Updated.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse deactivateStructureDocumentFiles(StructureDocumentDeactivateRequest remRequest) {

		try {
			CustomResponse validationResponse = validationService.validateDeactivateStructureDocumentFiles(remRequest);
			if (!validationResponse.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationResponse;
			}
			StructureDocument doc = structureDocDao.fetchDocumentById(remRequest.getStructureDocumentId());
			EntityStateMapDTO entityStateMap = engineService.getEntityStateMap(
					EntitiesEnum.STRUCTURE_DOCUMENT.getEntityId(), doc.getState().getId(),
					remRequest.getUser().getCompanyId());
			if (entityStateMap == null)
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
						"Relation to this state is not defined yet.");

//			remove files only
			if (remRequest.getRemoveFilesOnly()) {
				if (!entityStateMap.getIsEditable()) {
					return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Not in editable state mode.");
				}
				SearchDTO search = new SearchDTO();
				search.setStructureDocumentId(remRequest.getStructureDocumentId());
				List<StructureDocumentFile> docFiles = structureDocDao.fetchFiles(search);
				if (docFiles != null && docFiles.size() <= remRequest.getFileIds().size()) {
					return new CustomResponse(Responses.FORBIDDEN.getCode(), null,
							"This Document needs atleast one file, remove document instead.");
				}
				for (Long fileId : remRequest.getFileIds()) {
					structureDocDao.deactivateDocFile(remRequest.getStructureDocumentId(), fileId,
							remRequest.getUser().getId());
				}
			}
//			remove document and files
			else {
				if (!entityStateMap.getIsDeletable()) {
					return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Not in removable state mode.");
				}
				Boolean isRemoved = structureDocDao.deactivateDocument(remRequest.getStructureDocumentId(),
						remRequest.getUser().getId());
				if (isRemoved) {
					SearchDTO search = new SearchDTO();
					search.setStructureDocumentId(remRequest.getStructureDocumentId());
					List<StructureDocumentFile> docFiles = structureDocDao.fetchFiles(search);
					if (docFiles != null)
						for (StructureDocumentFile docFile : docFiles) {
							docFile.setModifiedOn(new Date());
							docFile.setModifiedBy(remRequest.getUser().getId());
							docFile.setIsActive(false);
							structureDocDao.forceUpdateDocFile(docFile);
						}
				}
			}

			return new CustomResponse(Responses.SUCCESS.getCode(), true, "Removed.");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse updateDocState(SearchDTO search) {

		try {
			if (search.getStructureDocumentId() == null || search.getStateId() == null || search.getUserId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
			}
			StructureDocument doc = structureDocDao.fetchDocumentById(search.getStructureDocumentId());
			if (doc.getState().getId().equals(search.getStateId())) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Document is already in same state.");
			}
			Integer stateId = doc.getState().getId();
			User lastModifiedBy = doc.getModifiedByUser();

			List<StateTransitionDTO> stateTransits = engineService.getStateTransition(
					EntitiesEnum.STRUCTURE_DOCUMENT.getEntityId(), search.getSiteId(), stateId,
					lastModifiedBy.getRole().getId(), search.getCompanyId());
			StateTransitionDTO stateTransit = null;
			if (stateTransits != null) {
				for (StateTransitionDTO st : stateTransits) {
					if (st.getNextRoleId().equals(search.getRoleId())
							&& st.getNextStateId().equals(search.getStateId())) {
						stateTransit = st;
						break;
					}
				}
			}
			if (stateTransit == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "No transition available to perform.");
			}
			EngineState newDocState = new EngineState(search.getStateId());
//			maintain versions
			Integer currentDocVersion = doc.getVersion();
			EntityStateMapDTO entityStateMap = engineService.getEntityStateMap(
					EntitiesEnum.STRUCTURE_DOCUMENT.getEntityId(), search.getStateId(), search.getCompanyId());

			Boolean isVersionableState = false;
			if (entityStateMap != null && entityStateMap.getMaintainVersion()) {
				isVersionableState = true;
			}
			if (isVersionableState) {
				StructureDocumentVersion docVersion = new StructureDocumentVersion(null, doc.getId(),
						doc.getStructureId(), doc.getType(), doc.getSubtype(), doc.getReference(), doc.getStatus(),
						doc.getDate(), doc.getRemark(), newDocState, currentDocVersion + 1, true, new Date(),
						search.getUserId(), doc.getSiteId());
				search.setStructureId(null);
				Long docVersionId = structureDocDao.forceSaveStructureDocumentVersion(docVersion);
				if (docVersionId != null && docVersionId > 0L) {
					docVersion.setId(docVersionId);
					List<StructureDocumentFile> docFiles = structureDocDao.fetchFiles(search);
					if (docFiles != null) {
						for (StructureDocumentFile docFile : docFiles) {
							StructureDocumentFileVersion docFileVersion = new StructureDocumentFileVersion(null,
									docVersion, docFile.getFile(), true, new Date(), search.getUserId());
							structureDocDao.forceSaveStructureDocFileVersion(docFileVersion);
						}
					}
				}
				doc.setVersion(currentDocVersion + 1);
			}
			doc.setState(newDocState);
			doc.setModifiedOn(new Date());
			doc.setModifiedBy(search.getUserId());
			Boolean result = structureDocDao.forceUpdateDocument(doc);
			if (result != null && result) {
				StructureDocumentStateTransition sdst = new StructureDocumentStateTransition(null, doc.getId(),
						search.getStateId(), true, new Date(), search.getUserId());
				structureDocDao.mapDocumentStateTransition(sdst);
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getNextPossibleStates(SearchDTO search) {

		try {
			if (search.getStructureDocumentId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide structureDocumentId.");
			}
			StructureDocument doc = structureDocDao.fetchDocumentById(search.getStructureDocumentId());
			List<StateTransitionDTO> transitions = engineService.getStateTransition(
					EntitiesEnum.STRUCTURE_DOCUMENT.getEntityId(), search.getSiteId(), null, null,
					search.getCompanyId());
			List<NextStateTransitDTO> nextStates = new ArrayList<>();
			User docUser = doc.getModifiedByUser();

//			set next states
			if (transitions != null && docUser != null && docUser.getRole() != null) {
				for (StateTransitionDTO st : transitions) {
					if (st.getStateId().equals(doc.getState().getId())
							&& st.getRoleId().equals(docUser.getRole().getId())
							&& st.getNextRoleId().equals(search.getRoleId())) {
						NextStateTransitDTO nst = new NextStateTransitDTO();
						nst.setNextStateId(st.getNextStateId());
						nst.setNextRoleId(st.getNextRoleId());
						nst.setIsButtonRequired(true);
						nst.setNextRoleName(st.getNextRole().getName());
						nst.setNextStateName(st.getNextState().getName());
						nst.setButtonText(st.getNextState().getButtonText());
						nst.setIsButtonRequired(st.getNextState().getButtonText() != null ? true : false);
						nst.setNextStateAlias(st.getNextState().getAlias());
						nextStates.add(nst);
					}
				}
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), nextStates, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse addStructureDocumentFiles(SearchDTO search) {

		try {
			CustomResponse validationResponse = validationService.validateAddStructureDocumentFiles(search);
			if (!validationResponse.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validationResponse;
			}
			StructureDocument doc = structureDocDao.fetchDocumentById(search.getStructureDocumentId());
			if (doc == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide valid documentId.");
			}
			Boolean isEntityEditable = engineService.findIfEntityInEditableState(
					EntitiesEnum.STRUCTURE_DOCUMENT.getEntityId(), search.getCompanyId(), doc.getState().getId());
			if (!isEntityEditable)
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Not in editable state.");
			for (Long fileId : search.getIdsArr()) {
				structureDocDao.saveDocFile(new StructureDocumentFile(null, doc, new S3File(fileId), true, new Date(),
						search.getUserId(), new Date(), search.getUserId()));
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), null, "Added.");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
