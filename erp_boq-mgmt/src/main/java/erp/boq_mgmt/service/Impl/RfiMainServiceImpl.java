package erp.boq_mgmt.service.Impl;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import erp.boq_mgmt.dao.ChainageBoqQuantityDao;
import erp.boq_mgmt.dao.ChainageDao;
import erp.boq_mgmt.dao.HighwayBoqMapDao;
import erp.boq_mgmt.dao.RfiMainChecklistItemDao;
import erp.boq_mgmt.dao.RfiMainDao;
import erp.boq_mgmt.dao.RfiMainFileDao;
import erp.boq_mgmt.dao.RfiMainTransacDao;
import erp.boq_mgmt.dao.SiteDao;
import erp.boq_mgmt.dao.StructureBoqQuantityDao;
import erp.boq_mgmt.dao.UserDao;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.EntityStateMapDTO;
import erp.boq_mgmt.dto.NextStateTransitDTO;
import erp.boq_mgmt.dto.PaginationDTO;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.dto.StateTransitionDTO;
import erp.boq_mgmt.dto.request.RfiBoqGetExecutableQuantityRequest;
import erp.boq_mgmt.dto.request.RfiMainAddUpdateRequest;
import erp.boq_mgmt.dto.request.RfiMainByStateActionFetchRequest;
import erp.boq_mgmt.dto.request.RfiMainCommentAddUpdateRequest;
import erp.boq_mgmt.dto.request.RfiMainCommentTypeResponse;
import erp.boq_mgmt.dto.request.RfiMainDeactivateRequest;
import erp.boq_mgmt.dto.request.RfiMainExportSummaryRequest;
import erp.boq_mgmt.dto.request.RfiMainFetchRequest;
import erp.boq_mgmt.dto.request.RfiMainNextPossibleStatesFetchRequest;
import erp.boq_mgmt.dto.response.IdNameDTO;
import erp.boq_mgmt.dto.response.PrintRfiMainResponse;
import erp.boq_mgmt.dto.response.RfiMainCommentResponse;
import erp.boq_mgmt.dto.response.RfiMainResponse;
import erp.boq_mgmt.dto.response.RfiMainStateTransitionFetchResponse;
import erp.boq_mgmt.entity.Chainage;
import erp.boq_mgmt.entity.ChainageBoqQuantityMapping;
import erp.boq_mgmt.entity.ChainageTraverse;
import erp.boq_mgmt.entity.HighwayBoqMapping;
import erp.boq_mgmt.entity.RfiMain;
import erp.boq_mgmt.entity.RfiMainChecklist;
import erp.boq_mgmt.entity.RfiMainComments;
import erp.boq_mgmt.entity.RfiMainExecutedQuantityTransaction;
import erp.boq_mgmt.entity.RfiMainFile;
import erp.boq_mgmt.entity.RfiMainStateTransition;
import erp.boq_mgmt.entity.Site;
import erp.boq_mgmt.entity.SitesV2;
import erp.boq_mgmt.entity.StructureBoqQuantityMapping;
import erp.boq_mgmt.entity.User;
import erp.boq_mgmt.enums.EntitiesEnum;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.enums.RfiMainCommentType;
import erp.boq_mgmt.enums.RfiMode;
import erp.boq_mgmt.enums.RfiWorkType;
import erp.boq_mgmt.enums.StateAction;
import erp.boq_mgmt.enums.UnitTypes;
import erp.boq_mgmt.feignClient.service.WorkflowEngineService;
import erp.boq_mgmt.service.RfiMainService;
import erp.boq_mgmt.util.CustomValidationUtil;
import erp.boq_mgmt.util.SetObject;

@Service
@Transactional
public class RfiMainServiceImpl implements RfiMainService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RfiMainDao rfiMainDao;

	@Autowired
	private SetObject setObject;

	@Autowired
	private HighwayBoqMapDao hbmDao;

	@Autowired
	private StructureBoqQuantityDao sbqDao;

	@Autowired
	private ChainageBoqQuantityDao cbqDao;

	@Autowired
	private ChainageDao chainageDao;

	@Autowired
	private RfiMainFileDao rfiMainFileDao;

	@Autowired
	private RfiMainChecklistItemDao rfiMainChecklistDao;

	@Autowired
	private SiteDao siteDao;

	@Autowired
	private RfiMainTransacDao rfiMainTransacDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private CustomValidationUtil validate;

	@Autowired
	private WorkflowEngineService engineService;

	@Override
	public CustomResponse addRfiMain(RfiMainAddUpdateRequest requestDTO) {
		try {
			Site site = siteDao.fetchById(requestDTO.getSiteId().longValue());
			RfiMain rfi = setObject.rfiMainAddUpdateRequestDtoToEntity(requestDTO);
			if (rfi.getBoqId() != null) {

				RfiBoqGetExecutableQuantityRequest rfiExecutableQuantityRequest = new RfiBoqGetExecutableQuantityRequest(
						rfi.getMode(), rfi.getType(), rfi.getParentId(), rfi.getBoqId(), requestDTO.getWorkLayerId(),
						rfi.getFromChainageId(), rfi.getToChainageId(), rfi.getStructureId(),
						rfi.getSiteId().longValue(), requestDTO.getUserDetail());
				CustomResponse executableQuantityResponse = getWorkItemExecutableQuantity(rfiExecutableQuantityRequest);
				if (executableQuantityResponse.getStatus().equals(Responses.SUCCESS.getCode())) {
					Double remainingExecutableQuantity = 0.0;
					if (executableQuantityResponse.getData() != null) {
						remainingExecutableQuantity = (Double) executableQuantityResponse.getData();
						if (remainingExecutableQuantity < rfi.getExecutableWorkQuantity()) {
							return new CustomResponse(HttpStatus.NOT_ACCEPTABLE.value(), null,
									"Available executable work quantity left : " + remainingExecutableQuantity);
						}
					}
				}
			}
			Integer fromChainageNumericValue = null;
			Integer toChainageNumericValue = null;
			if (rfi.getFromChainageId() != null && rfi.getToChainageId() != null) {

				ChainageTraverse fromChainage = chainageDao.fetchChainageTraverseById(rfi.getFromChainageId());
				ChainageTraverse toChainage = chainageDao.fetchChainageTraverseById(rfi.getToChainageId());
				if (fromChainage != null)
					fromChainageNumericValue = fromChainage.getNameNumericValue();
				if (toChainage != null)
					toChainageNumericValue = toChainage.getNameNumericValue();

				if (fromChainageNumericValue != null && toChainageNumericValue != null
						&& (rfi.getLength() == null || rfi.getLength() == 0)) {
					Integer deltaLength = toChainageNumericValue - fromChainageNumericValue;
					rfi.setLength(deltaLength.doubleValue());
				}

			}

			if (rfi.getMode().equals(RfiMode.Reinspection)) {
				RfiMain referenceRfi = rfiMainDao.fetchRfiById(rfi.getParentId());
				if (referenceRfi.getMode().equals(RfiMode.New)) {
					rfi.setUniqueCode(referenceRfi.getUniqueCode() + "-A");
				} else {
					String referenceRfiCode = referenceRfi.getUniqueCode();
					String baseUniqueCode = referenceRfiCode.substring(0, referenceRfiCode.lastIndexOf("-"));
					String incrementalCode = referenceRfiCode.substring(referenceRfiCode.lastIndexOf("-") + 1);
					if (incrementalCode.length() > 0) {
						char newCodePostfix = (char) (incrementalCode.charAt(0) + 1);
						rfi.setUniqueCode(baseUniqueCode + newCodePostfix);
					}
				}
			}
			DecimalFormat df = new DecimalFormat("#.000");
			rfi.setActualExecutedQuantity(Double.parseDouble(df.format(rfi.getActualExecutedQuantity())));

			Long rfiId = rfiMainDao.saveRfiMain(rfi, fromChainageNumericValue, toChainageNumericValue);

			if (rfiId != null && rfiId > 0) {

				requestDTO.setId(rfiId);

				if (requestDTO.getS3FileIds() != null && requestDTO.getS3FileIds().size() > 0) {
					for (Long fileId : requestDTO.getS3FileIds()) {
						rfiMainFileDao.saveRfiMainFile(new RfiMainFile(null, rfiId, fileId, true, new Date(),
								requestDTO.getUserDetail().getId().intValue()));
					}
				}
				if (requestDTO.getChecklistItemIds() != null && requestDTO.getChecklistItemIds().size() > 0) {
					for (Integer checkListItemId : requestDTO.getChecklistItemIds()) {
						rfiMainChecklistDao.saveRfiMainChecklistItem(new RfiMainChecklist(null, rfiId, checkListItemId,
								true, new Date(), requestDTO.getUserDetail().getId().intValue()));
					}
				}

				if (rfi.getMode().equals(RfiMode.New)) {

					StringBuilder uniqueCodePattern = new StringBuilder();
//					append company code
					uniqueCodePattern.append(
							site.getCompany().getShortName() != null && !site.getCompany().getShortName().isBlank()
									? site.getCompany().getShortName().trim() + "/"
									: "");
//					append site code
					uniqueCodePattern.append(
							site.getName() != null && !site.getName().isBlank() ? site.getName().trim() + "/" : "");
//					append work type code
					uniqueCodePattern.append(rfi.getType().getCode() + "/");
//					append ID
					uniqueCodePattern.append(rfiId);
					rfi.setUniqueCode(uniqueCodePattern.toString());

					rfiMainDao.forceUpdateRfi(rfi);

				}

				// save state transition
				RfiMainStateTransition stateTransition = new RfiMainStateTransition(null, rfiId,
						requestDTO.getStateId(), requestDTO.getStateRemark(), true, new Date(),
						requestDTO.getUserDetail().getId().intValue());
				rfiMainDao.saveRfiMainStateTransitionMapping(stateTransition);

				// save Rfi Main Executed Quantity Transaction
				RfiMainExecutedQuantityTransaction transacObj = new RfiMainExecutedQuantityTransaction(null, rfiId,
						rfi.getClientExecutedQuantity(), rfi.getActualExecutedQuantity(), true, new Date(),
						rfi.getUpdatedBy());

				rfiMainTransacDao.saveRfiMainExecutedQuantityTransaction(transacObj);

			}

			return new CustomResponse(
					((rfiId != null && rfiId > 0) ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value()), rfiId,
					((rfiId != null && rfiId > 0) ? HttpStatus.OK.name() : "Already exists."));

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse updateRfiMain(RfiMainAddUpdateRequest requestDTO) {

		try {

			RfiMain dbRfi = rfiMainDao.fetchRfiById(requestDTO.getId());

			if (dbRfi == null)
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());

			// get entity state data
			List<EntityStateMapDTO> entityStateMaps = engineService.getEntityStatesByCompanyId(
					EntitiesEnum.RFI_MAIN.getEntityId(), requestDTO.getUserDetail().getCompanyId());

			Set<Integer> finalSuccessStateIds = new HashSet<Integer>();

			for (EntityStateMapDTO esm : entityStateMaps) {

				if (esm.getEntityId().equals(EntitiesEnum.RFI_MAIN.getEntityId())
						&& esm.getStateActionId().equals(StateAction.Final_Success.getValue())) {
					finalSuccessStateIds.add(esm.getStateId());
				}

			}
//			if (!finalSuccessStateIds.isEmpty() && finalSuccessStateIds.contains(requestDTO.getStateId())) {
//				if (!requestDTO.getClientExecutedQuantity().equals(requestDTO.getExecutableWorkQuantity())) {
//					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
//							"Client executed quantity must be equal to executable work quantity.");
//
//				}
//				dbRfi.setSubmissionDate(dbRfi.getUpdatedOn());
//
//			}

			Double savedClientExecutedQuantity = dbRfi.getClientExecutedQuantity();
			Double savedActualExecutedQuantity = dbRfi.getActualExecutedQuantity();

			dbRfi = setObject.mergeRfiEntityWithDto(dbRfi, requestDTO);

			Integer fromChainageNumericValue = null;
			Integer toChainageNumericValue = null;
			if (dbRfi.getFromChainageId() != null && dbRfi.getToChainageId() != null) {

				ChainageTraverse fromChainage = chainageDao.fetchChainageTraverseById(dbRfi.getFromChainageId());
				ChainageTraverse toChainage = chainageDao.fetchChainageTraverseById(dbRfi.getToChainageId());
				if (fromChainage != null)
					fromChainageNumericValue = fromChainage.getNameNumericValue();
				if (toChainage != null)
					toChainageNumericValue = toChainage.getNameNumericValue();

				if (fromChainageNumericValue != null && toChainageNumericValue != null
						&& (dbRfi.getLength() == null || dbRfi.getLength() == 0)) {
					Integer deltaLength = toChainageNumericValue - fromChainageNumericValue;
					dbRfi.setLength(deltaLength.doubleValue());
				}

			}

			Boolean isUpdated = rfiMainDao.updateRfiMain(dbRfi, fromChainageNumericValue, toChainageNumericValue);

			if (isUpdated) {

				// save Rfi Main Executed Quantity Transaction
				if (dbRfi.getClientExecutedQuantity() != savedClientExecutedQuantity
						|| dbRfi.getActualExecutedQuantity() != savedActualExecutedQuantity) {

					RfiMainExecutedQuantityTransaction transacObj = new RfiMainExecutedQuantityTransaction(null,
							dbRfi.getId(), dbRfi.getClientExecutedQuantity(), dbRfi.getActualExecutedQuantity(), true,
							new Date(), dbRfi.getUpdatedBy());

					rfiMainTransacDao.saveRfiMainExecutedQuantityTransaction(transacObj);

					// save or update RFI Main files
					List<RfiMainFile> rfiFiles = rfiMainFileDao.fetchRfiMainFiles(dbRfi.getId());

					Set<Long> dbFileIds = new HashSet<Long>();
					if (rfiFiles != null && !rfiFiles.isEmpty()) {
						rfiFiles.forEach(obj -> dbFileIds.add(obj.getFileId()));
					}

					List<RfiMainFile> fileListToSave = new ArrayList<>();

					if (requestDTO.getS3FileIds() != null)
						for (Long fileId : requestDTO.getS3FileIds()) {
							if (!dbFileIds.contains(fileId)) {
								fileListToSave.add(new RfiMainFile(null, dbRfi.getId(), fileId, true, new Date(),
										requestDTO.getUserDetail().getId().intValue()));
							}
						}

					List<RfiMainFile> fileListToUpdate = new ArrayList<>();

					if (rfiFiles != null)
						for (RfiMainFile dbFile : rfiFiles) {
							if (!requestDTO.getS3FileIds().contains(dbFile.getFileId())) {
								fileListToUpdate.add(new RfiMainFile(dbFile.getId(), dbRfi.getId(), dbFile.getFileId(),
										false, new Date(), requestDTO.getUserDetail().getId().intValue()));
							}

						}

					if (fileListToSave != null && !fileListToSave.isEmpty()) {
						for (RfiMainFile rfiMainFile : fileListToSave) {
							rfiMainFileDao.saveRfiMainFile(rfiMainFile);
						}
					}
					if (fileListToUpdate != null && !fileListToUpdate.isEmpty()) {
						for (RfiMainFile rfiMainFile : fileListToUpdate) {
							rfiMainFileDao.updateRfiMainFile(rfiMainFile);
						}
					}

					// save or update RFI Main files
					List<RfiMainChecklist> rfiChecklistDbObj = rfiMainChecklistDao
							.fetchRfiMainChecklistItems(dbRfi.getId());

					Set<Integer> dbChecklistIds = new HashSet<Integer>();
					if (rfiChecklistDbObj != null && !rfiChecklistDbObj.isEmpty()) {
						rfiChecklistDbObj.forEach(obj -> dbChecklistIds.add(obj.getChecklistItemId()));
					}

					List<RfiMainChecklist> checklistListToSave = new ArrayList<>();

					if (requestDTO.getChecklistItemIds() != null)
						for (Integer checklistId : requestDTO.getChecklistItemIds()) {
							if (!dbChecklistIds.contains(checklistId)) {
								checklistListToSave.add(new RfiMainChecklist(null, dbRfi.getId(), checklistId, true,
										new Date(), requestDTO.getUserDetail().getId().intValue()));
							}
						}

					List<RfiMainChecklist> checklistListToUpdate = new ArrayList<>();

					if (rfiChecklistDbObj != null)
						for (RfiMainChecklist dbChecklist : rfiChecklistDbObj) {
							if (!requestDTO.getChecklistItemIds().contains(dbChecklist.getChecklistItemId())) {
								checklistListToUpdate.add(new RfiMainChecklist(dbChecklist.getId(), dbRfi.getId(),
										dbChecklist.getChecklistItemId(), false, new Date(),
										requestDTO.getUserDetail().getId().intValue()));
							}

						}

					if (checklistListToSave != null && !checklistListToSave.isEmpty()) {
						for (RfiMainChecklist rfiMainChecklist : checklistListToSave) {
							rfiMainChecklistDao.saveRfiMainChecklistItem(rfiMainChecklist);
						}
					}
					if (checklistListToUpdate != null && !checklistListToUpdate.isEmpty()) {
						for (RfiMainChecklist rfiMainChecklist : checklistListToUpdate) {
							rfiMainChecklistDao.updateRfiMainChecklistItem(rfiMainChecklist);
						}
					}

				}

				// save state transition
				RfiMainStateTransition stateTransition = new RfiMainStateTransition(null, dbRfi.getId(),
						requestDTO.getStateId(), requestDTO.getStateRemark(), true, new Date(),
						requestDTO.getUserDetail().getId().intValue());
				rfiMainDao.saveRfiMainStateTransitionMapping(stateTransition);

				// save or update rfi main comment
				if (requestDTO.getRfiMainComments() != null && !requestDTO.getRfiMainComments().isEmpty()) {

					List<RfiMainComments> rfiMainCommentsList = rfiMainDao.getRfiMainCommentsByRfiId(dbRfi.getId());

					for (RfiMainCommentAddUpdateRequest rfiMainCommentRequestDTO : requestDTO.getRfiMainComments()) {

						if (rfiMainCommentRequestDTO.getId() == null) {
							RfiMainComments rfiMainComment = setObject
									.rfiMainCommentAddUpdateRequestDtoToEntity(rfiMainCommentRequestDTO);
							rfiMainComment.setRfiMainId(dbRfi.getId());
							rfiMainComment.setUpdatedBy(requestDTO.getUserDetail().getId().intValue());
							rfiMainDao.saveRfiMainComment(rfiMainComment);
						} else {
							Boolean hasCommentFound = false;
							for (RfiMainComments rfiMainCommentDbObj : rfiMainCommentsList) {
								if (rfiMainCommentRequestDTO.getId().equals(rfiMainCommentDbObj.getId())) {
									hasCommentFound = true;
								}

							}
							if (!hasCommentFound) {
								TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
								return new CustomResponse(Responses.BAD_REQUEST.getCode(),
										rfiMainCommentRequestDTO.getId(), "Invalid comment.");
							} else {
								RfiMainComments rfiMainComment = setObject
										.rfiMainCommentAddUpdateRequestDtoToEntity(rfiMainCommentRequestDTO);
								rfiMainComment.setRfiMainId(dbRfi.getId());
								rfiMainComment.setUpdatedBy(requestDTO.getUserDetail().getId().intValue());
								rfiMainDao.updateRfiMainComment(rfiMainComment);
							}

						}

					}

				}
			}

			return new CustomResponse(HttpStatus.OK.value(), isUpdated, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}

	}

	@Override
	public CustomResponse getAllRfiMain(RfiMainFetchRequest requestDTO) {
		try {

			List<StateTransitionDTO> transitions = engineService.getStateTransition(EntitiesEnum.RFI_MAIN.getEntityId(),
					requestDTO.getSiteId().longValue(), null, null, requestDTO.getUserDetail().getCompanyId());

			Map<Integer, Set<Integer>> roleStateMap = new HashMap<Integer, Set<Integer>>();

//			set next states
			if (transitions != null)
				for (StateTransitionDTO st : transitions) {
					if (st.getNextRoleId().equals(requestDTO.getUserDetail().getRoleId())) {

						if (roleStateMap.containsKey(st.getRoleId())) {
							Set<Integer> stateIds = roleStateMap.get(st.getRoleId());
							stateIds.add(st.getStateId());

							roleStateMap.replace(st.getRoleId(), roleStateMap.get(st.getRoleId()), stateIds);

						} else {
							Set<Integer> stateIds = new HashSet<Integer>();
							stateIds.add(st.getStateId());
							roleStateMap.put(st.getRoleId(), stateIds);
						}

					}
				}

			List<RfiMainStateTransition> fetchRfiMainStateTransitionList = rfiMainDao
					.fetchRfiMainStateTransitionList(requestDTO, roleStateMap);
			Integer fetchRfiMainStateTransitionListCount = rfiMainDao.fetchRfiMainStateTransitionListCount(requestDTO,
					roleStateMap);

			List<RfiMain> rfiList = new ArrayList<>();
			Set<Long> distinctRfiIds = new HashSet<Long>();

			if (fetchRfiMainStateTransitionList != null && !fetchRfiMainStateTransitionList.isEmpty()) {
				fetchRfiMainStateTransitionList.forEach(obj -> {
					rfiList.add(obj.getRfiMain());
					distinctRfiIds.add(obj.getRfiMainId());
				});
			}

			List<RfiMainComments> rfiCommentList = rfiMainDao.fetchRfiMainCommentByTypeAndRfiIds(
					RfiMainCommentType.INDEPENDENT_ENGINEER.ordinal(), distinctRfiIds);

			List<RfiMainResponse> resultList = new ArrayList<>();
			if (rfiList != null) {

				// get entity state data
				List<EntityStateMapDTO> entityStateMaps = engineService.getEntityStatesByCompanyId(
						EntitiesEnum.RFI_MAIN.getEntityId(), requestDTO.getUserDetail().getCompanyId());

				Set<Integer> finalSuccessStateIds = new HashSet<Integer>();
				if (entityStateMaps != null && !entityStateMaps.isEmpty())
					for (EntityStateMapDTO esm : entityStateMaps) {

						if (esm.getEntityId().equals(EntitiesEnum.RFI_MAIN.getEntityId())
								&& esm.getStateActionId().equals(StateAction.Final_Success.getValue())) {
							finalSuccessStateIds.add(esm.getStateId());
						}

					}
				for (RfiMain rfiMain : rfiList) {
					RfiMainResponse rfiMainResponse = setObject.rfiMainEntityToResponseDto(rfiMain);
					if (rfiCommentList != null && !rfiCommentList.isEmpty()) {
						for (RfiMainComments comment : rfiCommentList) {
							if (rfiMain.getId().equals(comment.getRfiMainId())) {
								rfiMainResponse.setInspectionDate(comment.getCommentTimestamp());
							}

						}
					}
					if (rfiMainResponse.getInspectionDate() == null
							&& finalSuccessStateIds.contains(rfiMain.getStateId())) {
						rfiMainResponse.setInspectionDate(rfiMain.getUpdatedOn());
					}

					resultList.add(rfiMainResponse);
				}
			}

			PaginationDTO responseObj = new PaginationDTO(fetchRfiMainStateTransitionListCount, resultList);

			return new CustomResponse(HttpStatus.OK.value(), responseObj, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse getRfiMainById(Long rfiId, Integer companyId) {
		try {

			RfiMain rfi = rfiMainDao.fetchRfiById(rfiId);
			RfiMainResponse resultObj = setObject.rfiMainEntityToResponseDto(rfi);
			List<RfiMainFile> rfiFiles = rfiMainFileDao.fetchRfiMainFiles(rfiId);
			List<RfiMainChecklist> rfiChecklistItems = rfiMainChecklistDao.fetchRfiMainChecklistItems(rfiId);

			if (rfiFiles != null) {
				for (RfiMainFile rfiFile : rfiFiles) {
					resultObj.getFiles().add(setObject.s3FileEntityToResponseDto(rfiFile.getFile()));
				}
			}
			if (rfiChecklistItems != null) {
				for (RfiMainChecklist rfiChecklist : rfiChecklistItems) {
					resultObj.getChecklistItems().add(
							setObject.rfiChecklistItemEntityToFinalSuccessResponse(rfiChecklist.getChecklistItem()));
				}
			}

			List<RfiMainComments> commentsList = rfiMainDao.getRfiMainCommentsByRfiId(rfiId);

			if (commentsList != null && !commentsList.isEmpty()) {

				List<RfiMainCommentResponse> commentsResponseObj = new ArrayList<RfiMainCommentResponse>();

				commentsList.forEach(obj -> commentsResponseObj.add(setObject.rfiMainCommentEntityToDto(obj)));
				resultObj.setComments(commentsResponseObj);

			}

			// set entity state data
			List<EntityStateMapDTO> entityStateMaps = engineService
					.getEntityStatesByCompanyId(EntitiesEnum.RFI_MAIN.getEntityId(), companyId);

			Boolean isInitial = false;
			Boolean isEditable = false;
			Boolean isFinal = false;
			Boolean isDeletable = false;

			for (EntityStateMapDTO esm : entityStateMaps) {

				if (esm.getEntityId().equals(EntitiesEnum.RFI_MAIN.getEntityId())
						&& esm.getStateId().equals(rfi.getState().getId())) {
					if (!isInitial && esm.getIsInitial() != null) {
						isInitial = esm.getIsInitial();
					}
					if (!isEditable && esm.getIsEditable() != null) {
						isEditable = esm.getIsEditable();
					}
					if (!isDeletable && esm.getIsDeletable() != null) {
						isDeletable = esm.getIsDeletable();
					}
					if (!isFinal && esm.getIsFinal() != null) {
						isFinal = esm.getIsFinal();
					}

				}

			}

			resultObj.setIsEditable(isEditable);
			resultObj.setIsDeletable(isDeletable);
			resultObj.setIsFinalState(isFinal);

			return new CustomResponse(HttpStatus.OK.value(), resultObj, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse deactivateRfiMain(RfiMainDeactivateRequest requestDTO) {
		try {

			RfiMain rfi = rfiMainDao.fetchRfiById(requestDTO.getId());
			rfi.setIsActive(false);
			rfi.setUpdatedOn(new Date());
			rfi.setUpdatedBy(requestDTO.getUserDetail().getId().intValue());
			rfiMainDao.forceUpdateRfi(rfi);

			return new CustomResponse(HttpStatus.OK.value(), true, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse getWorkItemExecutableQuantity(RfiBoqGetExecutableQuantityRequest requestDTO) {

		try {
			Double remainingWorkExecutableQuantity = 0.0;

			if (requestDTO.getRfiMode().equals(RfiMode.New)) {

				if (requestDTO.getRfiWorkType().equals(RfiWorkType.Structure)) {

					SearchDTO searchObj = new SearchDTO();
					searchObj.setBoqId(requestDTO.getBoqId());
					searchObj.setSiteId(requestDTO.getSiteId());
					searchObj.setStructureId(requestDTO.getStructureId());
					List<StructureBoqQuantityMapping> sbqs = sbqDao.fetchStructureBoqQuantityMappingBySearch(searchObj);

					Double totalWorkExecutableQuantity = 0.0;
					if (sbqs != null) {
						for (StructureBoqQuantityMapping sbq : sbqs) {
							totalWorkExecutableQuantity += sbq.getQuantity();
						}
					}

					Double totalWorkExecutedQuantity = 0.0;
					List<RfiMain> rfiMains = rfiMainDao.fetchByBoqStructureAndSite(requestDTO.getStructureId(),
							requestDTO.getBoqId(), requestDTO.getWorkLayerId(), requestDTO.getSiteId().intValue());
					if (rfiMains != null) {
						for (RfiMain rfiMain : rfiMains) {
							totalWorkExecutedQuantity += rfiMain.getExecutableWorkQuantity();
						}
					}

					remainingWorkExecutableQuantity = totalWorkExecutableQuantity - totalWorkExecutedQuantity;

				} else if (requestDTO.getRfiWorkType().equals(RfiWorkType.Highway)) {

					Double totalWorkExecutableQuantity = 0.0;

					if (requestDTO.getFromChainageId() != null) {

						Chainage fromChainage = chainageDao.fetchById(requestDTO.getFromChainageId());
						Chainage toChainage = chainageDao.fetchById(requestDTO.getToChainageId());
						List<ChainageBoqQuantityMapping> cbqs = cbqDao.fetchByRangeBoqAndSite(requestDTO.getBoqId(),
								fromChainage.getNameNumericValue(), toChainage.getNameNumericValue(),
								requestDTO.getSiteId());
						if (cbqs != null && cbqs.size() > 0) {
							ChainageBoqQuantityMapping cbq = cbqs.get(0);
							Double totalArea = 0.0;
							Double totalVolume = 0.0;
							for (int i = 0; i < cbqs.size(); i++) {
								ChainageBoqQuantityMapping toSaveObj = cbqs.get(i);
								totalArea += (toSaveObj.getLhsQuantity() + toSaveObj.getRhsQuantity());
								if (i == 0) {
									continue;
								}
								ChainageBoqQuantityMapping previousToSaveObj = cbqs.get(i - 1);
								Integer numericChainage = toSaveObj.getChainage().getNameNumericValue() != null
										? toSaveObj.getChainage().getNameNumericValue()
										: 0;
								Integer previousChainage = previousToSaveObj.getChainage().getNameNumericValue() != null
										? previousToSaveObj.getChainage().getNameNumericValue()
										: 0;
								Double length = Double.valueOf(numericChainage) - Double.valueOf(previousChainage);
								Double previousArea = previousToSaveObj.getLhsQuantity()
										+ previousToSaveObj.getRhsQuantity();
								Double currentArea = toSaveObj.getLhsQuantity() + toSaveObj.getRhsQuantity();
								if (!(toSaveObj.getStructureRemark() == null
										|| toSaveObj.getStructureRemark().isBlank())) {
									length = 0.0;
								}
								totalVolume += ((currentArea + previousArea) / 2) * length;
							}
							Double totalBoqQuantity = totalArea;
							if (cbq.getBoq().getUnit().getType().getId().equals(UnitTypes.VOLUME.getTypeId())) {
								totalBoqQuantity = totalVolume;
							}

							totalWorkExecutableQuantity = totalBoqQuantity;

							List<RfiMain> rfiMains = rfiMainDao.fetchByBoqChainageRangeAndSite(requestDTO.getBoqId(),
									requestDTO.getWorkLayerId(), fromChainage.getNameNumericValue(),
									toChainage.getNameNumericValue(), requestDTO.getSiteId().intValue());
							if (rfiMains != null && rfiMains.size() > 0) {
								totalWorkExecutableQuantity = 0.0;
							}
						}
						remainingWorkExecutableQuantity = totalWorkExecutableQuantity;
					} else {

						List<HighwayBoqMapping> hbqs = hbmDao.fetchByBoqAndSite(requestDTO.getBoqId(),
								requestDTO.getSiteId());

						if (hbqs != null) {
							for (HighwayBoqMapping hbq : hbqs) {
								totalWorkExecutableQuantity += hbq.getQuantity();
							}
						}

						Double totalWorkExecutedQuantity = 0.0;
						List<RfiMain> rfiMains = rfiMainDao.fetchByBoqHighwayAndSite(requestDTO.getBoqId(),
								requestDTO.getWorkLayerId(), requestDTO.getSiteId().intValue());
						if (rfiMains != null) {
							for (RfiMain rfiMain : rfiMains) {
								totalWorkExecutedQuantity += rfiMain.getExecutableWorkQuantity();
							}
						}

						remainingWorkExecutableQuantity = totalWorkExecutableQuantity - totalWorkExecutedQuantity;

					}
				}

			} else if (requestDTO.getRfiMode().equals(RfiMode.Reinspection)) {

				RfiMain rfi = rfiMainDao.fetchRfiById(requestDTO.getRfiMainId());
				remainingWorkExecutableQuantity = rfi.getExecutableWorkQuantity();
			}
			if (remainingWorkExecutableQuantity <= 0.0) {
				return new CustomResponse(Responses.Not_Found.getCode(), remainingWorkExecutableQuantity,
						"No executable quantity available in this BOQ.");

			} else {
				return new CustomResponse(Responses.SUCCESS.getCode(), remainingWorkExecutableQuantity,
						Responses.SUCCESS.toString());
			}
		} catch (Exception e) {
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getRfiWorkTypes() {

		try {
			List<IdNameDTO> result = new ArrayList<>();
			for (RfiWorkType rfiType : RfiWorkType.values()) {
				result.add(new IdNameDTO(Long.valueOf(rfiType.ordinal()), rfiType.name()));
			}

			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getRfiModes() {

		try {
			List<IdNameDTO> result = new ArrayList<>();
			for (RfiMode rfiMode : RfiMode.values()) {
				result.add(new IdNameDTO(Long.valueOf(rfiMode.ordinal()), rfiMode.name()));
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getNextPossibleStates(RfiMainNextPossibleStatesFetchRequest requestObj) {

		try {
			if (requestObj.getRfiMainId() == null) {
				return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, "Provide rfiMainId.");
			}
			RfiMain rfiMain = rfiMainDao.fetchRfiById(requestObj.getRfiMainId());
			List<StateTransitionDTO> transitions = engineService.getStateTransition(EntitiesEnum.RFI_MAIN.getEntityId(),
					requestObj.getSiteId().longValue(), null, null, requestObj.getUser().getCompanyId());
			List<NextStateTransitDTO> nextStates = new ArrayList<>();
			User rfiUser = userDao.fetchUserById(rfiMain.getUpdatedBy().longValue());

//			set next states
			if (transitions != null && rfiUser != null && rfiUser.getRole() != null) {
				for (StateTransitionDTO st : transitions) {
					if (st.getStateId().equals(rfiMain.getState().getId())
							&& st.getRoleId().equals(rfiUser.getRole().getId())
							&& st.getNextRoleId().equals(requestObj.getUser().getRoleId())) {
						NextStateTransitDTO nst = new NextStateTransitDTO();
						nst.setNextStateId(st.getNextStateId());
						nst.setNextRoleId(st.getNextRoleId());
						nst.setIsButtonRequired(true);
						nst.setNextRoleName(st.getNextRole().getName());
						nst.setNextStateName(st.getNextState().getName());
						nst.setButtonText(st.getNextState().getButtonText());
						nst.setIsButtonRequired(st.getNextState().getButtonText() != null ? true : false);
						nst.setNextStateAlias(st.getNextState().getAlias());
						nst.setIsFinalState(st.getIsNextStateFinal());
						nst.setRgbColorCode(st.getNextState().getRgbColorCode());
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
	public CustomResponse getPrintRfiMainById(Long rfiId, Integer companyId) {
		try {

			RfiMain rfi = rfiMainDao.fetchRfiById(rfiId);
			if (rfi == null) {
				return new CustomResponse(HttpStatus.NOT_FOUND.value(), null, HttpStatus.NOT_FOUND.name());
			}
			PrintRfiMainResponse resultObj = setObject.rfiMainEntityToPrintResponseDto(rfi);

			CustomResponse response = validate.validatePrintRfiMainResponse(resultObj);
			if (!response.getStatus().equals(Responses.SUCCESS.getCode())) {
				return response;
			}
			List<RfiMainFile> rfiFiles = rfiMainFileDao.fetchRfiMainFiles(rfiId);
			List<RfiMainChecklist> rfiChecklistItems = rfiMainChecklistDao.fetchRfiMainChecklistItems(rfiId);

			if (rfiFiles != null) {
				for (RfiMainFile rfiFile : rfiFiles) {
					resultObj.getFiles().add(setObject.s3FileEntityToResponseDto(rfiFile.getFile()));
				}
			}
			if (rfiChecklistItems != null) {
				for (RfiMainChecklist rfiChecklist : rfiChecklistItems) {
					resultObj.getChecklistItems().add(
							setObject.rfiChecklistItemEntityToFinalSuccessResponse(rfiChecklist.getChecklistItem()));
				}
			}

			List<RfiMainComments> commentsList = rfiMainDao.getRfiMainCommentsByRfiId(rfiId);

			if (commentsList != null && !commentsList.isEmpty()) {

				List<RfiMainCommentResponse> commentsResponseObj = new ArrayList<RfiMainCommentResponse>();
				for (RfiMainComments rmc : commentsList) {
					commentsResponseObj.add(setObject.rfiMainCommentEntityToDto(rmc));
				}

				resultObj.setComments(commentsResponseObj);

			}

			return new CustomResponse(HttpStatus.OK.value(), resultObj, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse getRfiMainListByStateAction(RfiMainByStateActionFetchRequest requestDTO) {
		try {

			// get entity state data
			List<EntityStateMapDTO> entityStateMaps = engineService.getEntityStatesByCompanyId(
					EntitiesEnum.RFI_MAIN.getEntityId(), requestDTO.getUserDetail().getCompanyId());

			Set<Integer> stateIds = new HashSet<Integer>();

			for (EntityStateMapDTO esm : entityStateMaps) {

				if (esm.getEntityId().equals(EntitiesEnum.RFI_MAIN.getEntityId())
						&& requestDTO.getStateActionIds().contains(esm.getStateActionId())) {
					stateIds.add(esm.getStateId());
				}

			}

			List<RfiMain> rfiMainList = rfiMainDao.fetchRfiMainByStateIds(stateIds, requestDTO);

			List<RfiMainResponse> resultList = new ArrayList<>();
			if (rfiMainList != null) {
				Set<Long> distinctParentIds = new HashSet<>();
				if (!(requestDTO.getIncludeParents() != null && requestDTO.getIncludeParents())) {
					for (RfiMain rfiMain : rfiMainList) {

						if (rfiMain.getParentId() != null)
							distinctParentIds.add(rfiMain.getParentId());

					}
				}

				for (RfiMain rfiMain : rfiMainList) {

					if (!distinctParentIds.contains(rfiMain.getId()))
						resultList.add(setObject.rfiMainEntityToResponseDto(rfiMain));

				}
			}

			PaginationDTO responseObj = new PaginationDTO(resultList.size(), resultList);

			return new CustomResponse(HttpStatus.OK.value(), responseObj, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}

	}

	@Override
	public CustomResponse getRfiCommentTypes() {

		try {
			List<RfiMainCommentTypeResponse> result = new ArrayList<>();
			for (RfiMainCommentType type : RfiMainCommentType.values()) {
				result.add(new RfiMainCommentTypeResponse(type.ordinal(), type.name(), type.getDescription()));
			}

			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse exportRfiMainSummary(RfiMainExportSummaryRequest requestDTO) {
		try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream bos = new ByteArrayOutputStream();) {

			SitesV2 site = siteDao.fetchSiteV2ById(requestDTO.getSiteId().longValue());

			if (site == null) {
				return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, "Site not found.");
			}

			XSSFSheet sheet = workbook.createSheet("RFI Summary");

			XSSFCellStyle headerStyle = workbook.createCellStyle();
			headerStyle.setAlignment(HorizontalAlignment.LEFT);
			headerStyle.setWrapText(true);
			headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

			XSSFFont headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontName("Black1");
			headerFont.setItalic(true);
			headerFont.setFontHeightInPoints((short) 12);
			headerStyle.setFont(headerFont);

			XSSFCellStyle header2Style = workbook.createCellStyle();
			header2Style.setAlignment(HorizontalAlignment.CENTER);
			header2Style.setVerticalAlignment(VerticalAlignment.CENTER);

			XSSFFont header2Font = workbook.createFont();
			header2Font.setBold(true);
			header2Font.setFontName("DejaVu Serif Condensed");
			header2Font.setItalic(true);
			header2Font.setFontHeightInPoints((short) 10);
			header2Style.setFont(header2Font);

			XSSFColor customYellowColor = new XSSFColor(new java.awt.Color(227, 145, 39));
			XSSFCellStyle coloredHeaderStyle = workbook.createCellStyle();
			coloredHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
			coloredHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			coloredHeaderStyle.setFillForegroundColor(customYellowColor);
			coloredHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			XSSFFont colorFont = workbook.createFont();
			colorFont.setBold(true);
			colorFont.setFontName("DejaVu Serif Condensed");
			colorFont.setItalic(true);
			colorFont.setFontHeightInPoints((short) 12);
			coloredHeaderStyle.setFont(colorFont);

			XSSFCellStyle valueCellStyle = workbook.createCellStyle();
			valueCellStyle.setWrapText(true);
			valueCellStyle.setAlignment(HorizontalAlignment.CENTER);

			int rowNum = 0;

			// first row
			XSSFRow row0 = sheet.createRow(rowNum++);
			row0.setHeight((short) 800);
			XSSFCell c04 = row0.createCell(0);
			c04.setCellValue("Name of the Project");
			c04.setCellStyle(headerStyle);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
			XSSFCell c05 = row0.createCell(5);
			c05.setCellValue(site.getDescription() != null ? site.getDescription() : "-");
			c05.setCellStyle(headerStyle);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 5, 24));

			// Second row

			XSSFRow row1 = sheet.createRow(rowNum++);
			XSSFCell c14 = row1.createCell(0);
			c14.setCellValue("Authority");
			c14.setCellStyle(headerStyle);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 4));
			XSSFCell c15 = row1.createCell(5);
			c15.setCellValue(site.getClient() != null ? site.getClient().getName() : "-");
			c15.setCellStyle(headerStyle);

			sheet.addMergedRegion(new CellRangeAddress(1, 1, 5, 24));

			// third row
			XSSFRow row2 = sheet.createRow(rowNum++);
			XSSFCell c24 = row2.createCell(0);
			c24.setCellValue("Authority Engineer");
			c24.setCellStyle(headerStyle);
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 4));
			XSSFCell c25 = row2.createCell(5);
			c25.setCellValue(site.getConsultant() != null ? site.getConsultant().getName() : "-");
			c25.setCellStyle(headerStyle);

			sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 24));

			// fourth row
			XSSFRow row3 = sheet.createRow(rowNum++);
			XSSFCell c34 = row3.createCell(0);
			c34.setCellValue("EPC Contractor");
			c34.setCellStyle(headerStyle);
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 4));
			XSSFCell c35 = row3.createCell(5);
			c35.setCellValue(site.getCompany() != null ? site.getCompany().getName() : "-");
			c35.setCellStyle(headerStyle);

			sheet.addMergedRegion(new CellRangeAddress(3, 3, 5, 24));

			// header row
			XSSFRow headerRow1 = sheet.createRow(rowNum++);
			XSSFCell h1 = headerRow1.createCell(0);
			h1.setCellValue("Sr.No");
			h1.setCellStyle(header2Style);
			sheet.addMergedRegion(new CellRangeAddress(4, 6, 0, 0));

			XSSFCell h2 = headerRow1.createCell(1);
			h2.setCellValue("RFI No.");
			h2.setCellStyle(header2Style);
			sheet.addMergedRegion(new CellRangeAddress(4, 6, 1, 4));

			XSSFCell h3 = headerRow1.createCell(5);
			h3.setCellValue("Chainage");
			h3.setCellStyle(header2Style);
			sheet.addMergedRegion(new CellRangeAddress(4, 4, 5, 6));

			XSSFCell h4 = headerRow1.createCell(7);
			h4.setCellValue("Side");
			h4.setCellStyle(header2Style);
			sheet.addMergedRegion(new CellRangeAddress(4, 6, 7, 7));

			XSSFCell h5 = headerRow1.createCell(8);
			h5.setCellValue("Length(m)");
			h5.setCellStyle(header2Style);
			sheet.addMergedRegion(new CellRangeAddress(4, 6, 8, 8));

			XSSFCell h6 = headerRow1.createCell(9);
			h6.setCellValue("Type Of Work");
			h6.setCellStyle(header2Style);
			sheet.addMergedRegion(new CellRangeAddress(4, 6, 9, 10));

			XSSFCell h7 = headerRow1.createCell(11);
			h7.setCellValue("Description");
			h7.setCellStyle(header2Style);
			sheet.addMergedRegion(new CellRangeAddress(4, 6, 11, 14));

			XSSFCell h8 = headerRow1.createCell(15);
			h8.setCellValue("Type of Structure");
			h8.setCellStyle(header2Style);
			sheet.addMergedRegion(new CellRangeAddress(4, 6, 15, 16));

			XSSFCell h9 = headerRow1.createCell(17);
			h9.setCellValue("Date of Submission");
			h9.setCellStyle(header2Style);
			sheet.addMergedRegion(new CellRangeAddress(4, 6, 17, 18));

			XSSFCell h10 = headerRow1.createCell(19);
			h10.setCellValue("Date of Inspection");
			h10.setCellStyle(header2Style);
			sheet.addMergedRegion(new CellRangeAddress(4, 6, 19, 20));

			XSSFCell h11 = headerRow1.createCell(21);
			h11.setCellValue("Time");
			h11.setCellStyle(header2Style);
			sheet.addMergedRegion(new CellRangeAddress(4, 6, 21, 21));

			XSSFCell h12 = headerRow1.createCell(22);
			h12.setCellValue("Engineer / Incharge");
			h12.setCellStyle(header2Style);
			sheet.addMergedRegion(new CellRangeAddress(4, 6, 22, 24));

			XSSFRow subH1 = sheet.createRow(5);
			XSSFCell subCell = subH1.createCell(5);
			subCell.setCellValue("From");
			sheet.addMergedRegion(new CellRangeAddress(5, 6, 5, 5));

			XSSFCell subCell2 = subH1.createCell(6);
			subCell2.setCellValue("To");
			sheet.addMergedRegion(new CellRangeAddress(5, 6, 6, 6));

			int row07 = 7;

			// for colorful row
			XSSFRow colorRow = sheet.createRow(row07++);
			XSSFCell colorCell = colorRow.createCell(0);
			colorCell.setCellValue(
					requestDTO.getWorkType() == null ? "Structure/Highway" : requestDTO.getWorkType().name());
			colorCell.setCellStyle(coloredHeaderStyle);
			sheet.addMergedRegion(new CellRangeAddress(7, 8, 0, 24));

			// get entity state data
			List<EntityStateMapDTO> entityStateMaps = engineService.getEntityStatesByCompanyId(
					EntitiesEnum.RFI_MAIN.getEntityId(), requestDTO.getUserDetail().getCompanyId());

			Set<Integer> stateIds = new HashSet<Integer>();

			if (entityStateMaps != null && !entityStateMaps.isEmpty())
				for (EntityStateMapDTO esm : entityStateMaps) {

					if (esm.getEntityId().equals(EntitiesEnum.RFI_MAIN.getEntityId())
							&& esm.getStateActionId().equals(StateAction.Final_Success.getValue())) {
						stateIds.add(esm.getStateId());
					}

				}

			RfiMainByStateActionFetchRequest rfiMainFetchRequest = new RfiMainByStateActionFetchRequest();
			rfiMainFetchRequest.setUserDetail(requestDTO.getUserDetail());
			rfiMainFetchRequest.setSiteId(requestDTO.getSiteId());
			rfiMainFetchRequest.setSearchField(requestDTO.getSearchField());
			rfiMainFetchRequest.setWorkType(requestDTO.getWorkType());
			List<RfiMain> rfiMainList = rfiMainDao.fetchRfiMainByStateIds(stateIds, rfiMainFetchRequest);

			if (rfiMainList != null && !rfiMainList.isEmpty()) {
				Set<Long> distinctRfiIds = new HashSet<Long>();
				rfiMainList.forEach(obj -> distinctRfiIds.add(obj.getId()));

				List<RfiMainComments> rfiCommentList = rfiMainDao.fetchRfiMainCommentByTypeAndRfiIds(null,
						distinctRfiIds);

				DateFormat date = new SimpleDateFormat("dd/MMM/yyyy");
				DateFormat time = new SimpleDateFormat("hh:mm:ss a");
				int count = 9;
				int serialNo = 1;
				for (RfiMain rfi : rfiMainList) {

					RfiMainComments comment = null;
					String siteEngineerName = null;
					for (RfiMainComments rfiComment : rfiCommentList) {
						if (rfi.getId().equals(rfiComment.getRfiMainId())
								&& rfiComment.getCommentType().equals(RfiMainCommentType.INDEPENDENT_ENGINEER)) {
							comment = rfiComment;
						}
						if (rfi.getId().equals(rfiComment.getRfiMainId())
								&& rfiComment.getCommentType().equals(RfiMainCommentType.SITE_ENGINEER)) {
							siteEngineerName = rfiComment.getCommentatorName();
						}

					}

					XSSFRow row = sheet.createRow(count);

					XSSFCell cellSrNoValue = row.createCell(0);
					cellSrNoValue.setCellValue(serialNo);
					cellSrNoValue.setCellStyle(valueCellStyle);
//					sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 0));

					XSSFCell cellRfiNoValue = row.createCell(1);
					cellRfiNoValue.setCellValue(rfi.getUniqueCode());
					cellRfiNoValue.setCellStyle(valueCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(count, count, 1, 4));

					XSSFCell cellFromChainageValue = row.createCell(5);
					cellFromChainageValue
							.setCellValue(rfi.getFromChainage() != null ? rfi.getFromChainage().getName() : "-");
					cellFromChainageValue.setCellStyle(valueCellStyle);
//					sheet.addMergedRegion(new CellRangeAddress(i, i, 5, 5));

					XSSFCell cellToChainageValue = row.createCell(6);
					cellToChainageValue.setCellValue(rfi.getToChainage() != null ? rfi.getToChainage().getName() : "-");
					cellToChainageValue.setCellStyle(valueCellStyle);
//					sheet.addMergedRegion(new CellRangeAddress(i, i, 6, 6));

					XSSFCell cellSideValue = row.createCell(7);
					cellSideValue.setCellValue(rfi.getSide() != null ? rfi.getSide().name() : "-");
					cellSideValue.setCellStyle(valueCellStyle);
//					sheet.addMergedRegion(new CellRangeAddress(i, i, 7, 7));

					XSSFCell cellLengthValue = row.createCell(8);
					cellLengthValue.setCellValue(rfi.getLength() != null ? rfi.getLength().toString() : "-");
					cellLengthValue.setCellStyle(valueCellStyle);
//					sheet.addMergedRegion(new CellRangeAddress(i, i, 8, 8));

					XSSFCell cellTypeOfWorkValue = row.createCell(9);
					cellTypeOfWorkValue.setCellValue(rfi.getBoq() != null && rfi.getBoq().getCategory() != null
							? rfi.getBoq().getCategory().getName()
							: "-");
					cellTypeOfWorkValue.setCellStyle(valueCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(count, count, 9, 10));

					XSSFCell cellDescriptionValue = row.createCell(11);
					cellDescriptionValue
							.setCellValue(rfi.getWorkDescription() != null ? rfi.getWorkDescription() : "-");
					cellDescriptionValue.setCellStyle(valueCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(count, count, 11, 14));

					XSSFCell cellTypeOfStructureValue = row.createCell(15);
					cellTypeOfStructureValue
							.setCellValue(rfi.getType().equals(RfiWorkType.Structure) && rfi.getStructure() != null
									&& rfi.getStructure().getType() != null ? rfi.getStructure().getType().getName()
											: "-");
					cellTypeOfStructureValue.setCellStyle(valueCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(count, count, 15, 16));

					XSSFCell cellDateOfSubmissionValue = row.createCell(17);
					// format submission date
					Date submissionDate = rfi.getSubmissionDate() != null ? rfi.getSubmissionDate() : null;
					if (submissionDate != null && site.getTimezoneMinutes() != null) {
						Long submissionDateInMs = submissionDate.getTime();
						submissionDateInMs = submissionDateInMs + (site.getTimezoneMinutes() * 60 * 1000);
						rfi.setSubmissionDate(new Date(submissionDateInMs));
					}
					cellDateOfSubmissionValue
							.setCellValue(rfi.getSubmissionDate() != null ? date.format(rfi.getSubmissionDate()) : "-");
					cellDateOfSubmissionValue.setCellStyle(valueCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(count, count, 17, 18));

					XSSFCell cellDateOfInspectionValue = row.createCell(19);
					// format comment timestamp
					Date commentTimestamp = comment != null && comment.getCommentTimestamp() != null
							? comment.getCommentTimestamp()
							: null;
					if (commentTimestamp != null && site.getTimezoneMinutes() != null) {
						Long commentTimestampInMs = commentTimestamp.getTime();
						commentTimestampInMs = commentTimestampInMs + (site.getTimezoneMinutes() * 60 * 1000);
						comment.setCommentTimestamp(new Date(commentTimestampInMs));
					}

					cellDateOfInspectionValue.setCellValue(comment != null && comment.getCommentTimestamp() != null
							? date.format(comment.getCommentTimestamp())
							: date.format(rfi.getUpdatedOn()));
					cellDateOfInspectionValue.setCellStyle(valueCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(count, count, 19, 20));

					XSSFCell cellTimeValue = row.createCell(21);
					cellTimeValue.setCellValue(comment != null && comment.getCommentTimestamp() != null
							? time.format(comment.getCommentTimestamp())
							: time.format(rfi.getUpdatedOn()));
					cellTimeValue.setCellStyle(valueCellStyle);
//					sheet.addMergedRegion(new CellRangeAddress(i, i, 21, 21));

					XSSFCell cellEngineerOrInchargeValue = row.createCell(22);
					cellEngineerOrInchargeValue.setCellValue(siteEngineerName != null ? siteEngineerName : "-");
					cellEngineerOrInchargeValue.setCellStyle(valueCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(count, count, 22, 24));

					count++;
					serialNo++;

				}

			}

			workbook.write(bos);
			byte[] bytes = bos.toByteArray();
			return new CustomResponse(Responses.SUCCESS.getCode(), Base64.getEncoder().encodeToString(bytes),
					Responses.SUCCESS.name());

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public CustomResponse getRfiMainStateTransitionByRfiMainId(Long rfiMainId) {

		try {
			List<RfiMainStateTransition> rfiMainStateTransitionList = rfiMainDao
					.fetchRfiMainStateTransitionByRfiMainId(rfiMainId);
			List<RfiMainStateTransitionFetchResponse> results = new ArrayList<>();
			if (rfiMainStateTransitionList != null) {
				rfiMainStateTransitionList
						.forEach(obj -> results.add(setObject.rfiMainStateTransitionEntityToDto(obj)));
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), results, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}
}
