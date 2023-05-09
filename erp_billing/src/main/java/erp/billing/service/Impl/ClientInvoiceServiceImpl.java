package erp.billing.service.Impl;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import erp.billing.constant.IrnConstant;
import erp.billing.dao.ClientInvoiceDao;
import erp.billing.dao.ClientInvoiceIrnInfoDao;
import erp.billing.dao.ClientIrnCredentialDao;
import erp.billing.dao.SiteDao;
import erp.billing.dao.UserDao;
import erp.billing.dto.CustomResponse;
import erp.billing.dto.EntityStateMapDTO;
import erp.billing.dto.NextStateTransitDTO;
import erp.billing.dto.StateTransitionDTO;
import erp.billing.dto.UserDetail;
import erp.billing.dto.request.ClientInvoiceAddUpdateRequest;
import erp.billing.dto.request.ClientInvoiceExportExcelRequest;
import erp.billing.dto.request.ClientInvoiceFetchRequest;
import erp.billing.dto.request.ClientInvoiceNextPossibleStatesFetchRequest;
import erp.billing.dto.request.ClientInvoicePdfRequest;
import erp.billing.dto.request.ClientInvoiceProductAddRequest;
import erp.billing.dto.request.ClientIrnCancelRequest;
import erp.billing.dto.response.CancelIrnResponse;
import erp.billing.dto.response.ClientInvoiceFetchResponse;
import erp.billing.dto.response.ClientInvoiceIrnInfoResponse;
import erp.billing.dto.response.ClientInvoiceProductFetchResponse;
import erp.billing.dto.response.ClientInvoiceStateTransitionFetchResponse;
import erp.billing.dto.response.ClientIrnResponse;
import erp.billing.dto.response.PaginationResultObject;
import erp.billing.entity.ClientInvoice;
import erp.billing.entity.ClientInvoiceIrnInfo;
import erp.billing.entity.ClientInvoiceProduct;
import erp.billing.entity.ClientInvoiceStateTransitionMapping;
import erp.billing.entity.ClientInvoiceStateTransitionMappingV2;
import erp.billing.entity.ClientIrnCredential;
import erp.billing.entity.Site;
import erp.billing.entity.User;
import erp.billing.enums.EngineStateActions;
import erp.billing.enums.EntitiesEnum;
import erp.billing.enums.GstTypeEnum;
import erp.billing.enums.Responses;
import erp.billing.enums.StateActions;
import erp.billing.feignClient.dto.IrnBuyerDetailRequest;
import erp.billing.feignClient.dto.IrnCancelRequest;
import erp.billing.feignClient.dto.IrnDispatchDetailRequest;
import erp.billing.feignClient.dto.IrnDocumentDetailRequest;
import erp.billing.feignClient.dto.IrnGenerateRequest;
import erp.billing.feignClient.dto.IrnItemRequest;
import erp.billing.feignClient.dto.IrnSellerDetailRequest;
import erp.billing.feignClient.dto.IrnTransactionDetailRequest;
import erp.billing.feignClient.dto.IrnValueDetailRequest;
import erp.billing.feignClient.service.UtilityService;
import erp.billing.feignClient.service.WorkflowEngineService;
import erp.billing.service.ClientInvoiceService;
import erp.billing.util.ClientInvoicePdfUtil;
import erp.billing.util.CustomValidationUtil;
import erp.billing.util.DateUtil;
import erp.billing.util.SetObject;

@Service
@Transactional
@PropertySource("classpath:application.properties")
public class ClientInvoiceServiceImpl implements ClientInvoiceService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;

	@Autowired
	private ClientInvoiceDao clientInvoiceDao;

	@Autowired
	private CustomValidationUtil validate;

	@Autowired
	private UserDao userDao;

	@Autowired
	private WorkflowEngineService engineService;

	@Autowired
	private UtilityService utilityService;

	@Autowired
	private ClientInvoiceIrnInfoDao clientInvoiceIrnInfoDao;

	@Autowired
	private ClientIrnCredentialDao clientIrnCredentialDao;

	@Autowired
	private SiteDao siteDao;

	static String[] staticColumnsOfExportClientInvoiceDetails = { "S No.", "Invoice Type", "Supply Type",
			"Reverse Charge", "Transport Mode", "Vehicle No.", "Invoice No.", "Invoice Date", "Date of Supply",
			"Supply State", "GSTIN", "Billing Name", "Billing Address", "Shipping Name", "Shipping Address",
			"Product Name", "Product Remarks", "HSN/SAC Code", "Ouantity", "Unit", "GST", "Amount", "Amount After Tax",
			"Status", "Acknowledgement Date", "Acknowledgement Number", "IRN Number", "Cancel Date", "Cancel Reason",
			"Cancel Remark" };

	@Value("${upi.url.variable.amount.name}")
	private String amountVariableName;
	@Value("${upi.url.variable.amount.pattern}")
	private String amountVariablePattern;

	@Value("${upi.url.variable.invoiceNo.name}")
	private String invoiceNoVariableName;
	@Value("${upi.url.variable.invoiceNo.pattern}")
	private String invoiceNoVariablePattern;

	@Value("${upi.url.variable.invoiceDate.name}")
	private String invoiceDateVariableName;
	@Value("${upi.url.variable.invoiceDate.pattern}")
	private String invoiceDateVariablePattern;

	@Override
	public CustomResponse addOrUpdateClientInvoice(ClientInvoiceAddUpdateRequest requestObj) {
		try {

			CustomResponse validateAddRequest = validate.addOrUpdateClientInvoice(requestObj);
			if (validateAddRequest.getStatus().equals(Responses.BAD_REQUEST.getCode())) {
				return validateAddRequest;
			}

			if (requestObj.getId() == null) {

				ClientInvoice clientInvoice = setObject.clientInvoiceAddOrUpdateRequestDtoToEntity(requestObj);

				Long id = clientInvoiceDao.saveClientInvoice(clientInvoice);

				if (id != null) {
					for (ClientInvoiceProductAddRequest productReq : requestObj.getProducts()) {
						productReq.setClientInvoiceId(id);
						productReq.setUpdatedBy(requestObj.getUpdatedBy());
						ClientInvoiceProduct productObj = setObject
								.clientInvoiceProductAddOrUpdateRequestDtoToEntity(productReq);
						if (productReq.getIsIgst()) {
							productObj.setApplicableCgstPercentage(productReq.getApplicableIgstPercentage());
							productObj.setApplicableCgstAmount(productReq.getApplicableIgstAmount());
						}
						clientInvoiceDao.saveClientInvoiceProduct(productObj);
					}

					// save state transition
					ClientInvoiceStateTransitionMapping clientInvoiceStateTransitionMap = new ClientInvoiceStateTransitionMapping(
							null, id, clientInvoice.getState().getId(), true, new Date(), clientInvoice.getUpdatedBy());
					clientInvoiceDao.saveClientInvoiceStateTransitionMapping(clientInvoiceStateTransitionMap);

				}

				return new CustomResponse(
						((id != null && id.longValue() > 0) ? Responses.SUCCESS.getCode()
								: Responses.FORBIDDEN.getCode()),
						id, ((id != null && id.longValue() > 0) ? Responses.SUCCESS.toString()
								: "Invoice No. already exists."));

			} else {

				ClientInvoice clientInvoice = setObject.clientInvoiceAddOrUpdateRequestDtoToEntity(requestObj);

				ClientInvoice dbObj = clientInvoiceDao.fetchClientInvoiceById(clientInvoice.getId());

				if (dbObj == null) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Client Invoice not found.");
				}

				clientInvoice.setInvoiceNo(dbObj.getInvoiceNo());
				ClientInvoice mergedClientInvoice = clientInvoiceDao.mergeClientInvoice(clientInvoice);

				// save state transition
				if (mergedClientInvoice != null && mergedClientInvoice.getId() != null
						&& !clientInvoice.getState().getId().equals(dbObj.getState().getId())) {

					ClientInvoiceStateTransitionMapping clientInvoiceStateTransitionMap = new ClientInvoiceStateTransitionMapping(
							null, clientInvoice.getId(), clientInvoice.getState().getId(), true, new Date(),
							clientInvoice.getUpdatedBy());
					clientInvoiceDao.saveClientInvoiceStateTransitionMapping(clientInvoiceStateTransitionMap);
				}

				Set<Long> invoiceProductIdsNew = new HashSet<>();
				if (mergedClientInvoice != null && mergedClientInvoice.getId() != null) {
					for (ClientInvoiceProductAddRequest productReq : requestObj.getProducts()) {
						productReq.setClientInvoiceId(mergedClientInvoice.getId());
						productReq.setUpdatedBy(requestObj.getUpdatedBy());
						ClientInvoiceProduct productObj = setObject
								.clientInvoiceProductAddOrUpdateRequestDtoToEntity(productReq);
						if (productReq.getIsIgst()) {
							productObj.setApplicableCgstPercentage(productReq.getApplicableIgstPercentage());
							productObj.setApplicableCgstAmount(productReq.getApplicableIgstAmount());
						}
						ClientInvoiceProduct mergedClientInvoiceProduct = clientInvoiceDao
								.mergeClientInvoiceProduct(productObj);
						if (mergedClientInvoiceProduct != null && mergedClientInvoiceProduct.getId() != null) {
							invoiceProductIdsNew.add(mergedClientInvoiceProduct.getId());
						} else {
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
									"Invalid product details.");
						}

					}
				}

				if (mergedClientInvoice != null && mergedClientInvoice.getId() != null
						&& invoiceProductIdsNew != null) {

					Set<Long> distinctClientInvoiceIds = new HashSet<>();
					distinctClientInvoiceIds.add(clientInvoice.getId());
					List<ClientInvoiceProduct> products = clientInvoiceDao
							.fetchClientInvoiceProductsByInvoiceIds(distinctClientInvoiceIds);
					Set<Long> distinctClientInvoiceProductIdOldAndNew = new HashSet<>();
					Set<Long> distinctClientInvoiceProductRemoveOldIds = new HashSet<>();
					if (products != null) {
						products.forEach(p -> distinctClientInvoiceProductIdOldAndNew.add(p.getId()));
						for (Long oldNewId : distinctClientInvoiceProductIdOldAndNew) {
							if (!invoiceProductIdsNew.contains(oldNewId)) {
								distinctClientInvoiceProductRemoveOldIds.add(oldNewId);
							}
						}
						for (ClientInvoiceProduct ciProduct : products) {
							if (!distinctClientInvoiceProductRemoveOldIds.contains(ciProduct.getId())) {
								continue;
							}
							ciProduct.setIsActive(false);
							ciProduct.setUpdatedOn(new Date());
							ciProduct.setUpdatedBy(requestObj.getUpdatedBy());
							clientInvoiceDao.deactivateClientInvoiceProduct(ciProduct);
						}
					}
				}

				return new CustomResponse(
						((mergedClientInvoice != null && mergedClientInvoice.getId() != null
								&& mergedClientInvoice.getId().longValue() > 0) ? Responses.SUCCESS.getCode()
										: Responses.FORBIDDEN.getCode()),
						mergedClientInvoice != null ? mergedClientInvoice.getId() : null,
						((mergedClientInvoice != null && mergedClientInvoice.getId() != null
								&& mergedClientInvoice.getId().longValue() > 0) ? Responses.SUCCESS.toString()
										: "Invoice No. already exists."));
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		}
	}

	@Override
	public CustomResponse getClientInvoiceList(ClientInvoiceFetchRequest requestObj) {
		try {

//			Get client invoices using search

			// set entity state data
			List<EntityStateMapDTO> entityStateMaps = engineService.getEntityStatesByCompanyId(
					EntitiesEnum.Client_Invoice.getEntityId(), requestObj.getUser().getCompanyId());

			if (entityStateMaps == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Entity state mapping not found.");
			}

			List<StateTransitionDTO> transitions = engineService.getStateTransition(
					EntitiesEnum.Client_Invoice.getEntityId(), requestObj.getSiteId().longValue(), null, null,
					requestObj.getUser().getCompanyId());

			if (transitions == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "State transition not found.");
			}

			Map<Integer, Set<Integer>> roleStateMap = new HashMap<Integer, Set<Integer>>();
			Integer draftStateId = null;
			Set<Integer> stateVisibilityIds = new HashSet<>();

			for (EntityStateMapDTO esm : entityStateMaps) {
				if (esm.getStateActionId().equals(StateActions.FinalSuccess.getStateActionId())) {
					stateVisibilityIds.add(esm.getStateId());
				}
				if (esm.getStateActionId().equals(StateActions.Draft.getStateActionId())) {
					draftStateId = esm.getStateId();
				}

			}

//			set next states
			for (StateTransitionDTO st : transitions) {
				if (st.getNextRoleId().equals(requestObj.getUser().getRoleId())) {

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

			List<ClientInvoiceStateTransitionMappingV2> clientInvoiceStateTransitionList = clientInvoiceDao
					.fetchClientInvoiceStateTransitionList(requestObj, roleStateMap, draftStateId, stateVisibilityIds);
			Integer clientInvoiceCount = clientInvoiceDao.fetchClientInvoiceStateTransitionCount(requestObj,
					roleStateMap, draftStateId, stateVisibilityIds);

			List<ClientInvoice> clientInvoicesUpdatedList = new ArrayList<>();

			if (clientInvoiceStateTransitionList != null) {
				clientInvoiceStateTransitionList.forEach(obj -> clientInvoicesUpdatedList.add(obj.getClientInvoice()));
			}

			Set<Long> distinctClientInvoiceIds = new HashSet<>();
			if (clientInvoiceStateTransitionList != null) {
				for (ClientInvoice clientInvoice : clientInvoicesUpdatedList) {
					distinctClientInvoiceIds.add(clientInvoice.getId());
				}
			}

//			Get all products

			List<ClientInvoiceProduct> products = clientInvoiceDao
					.fetchClientInvoiceProductsByInvoiceIds(distinctClientInvoiceIds);

//			Get All IRN Info
			List<ClientInvoiceIrnInfo> irnDetails = clientInvoiceIrnInfoDao
					.fetchAllclientInvoiceIrnInfoByClientInvoiceId(distinctClientInvoiceIds);

			Set<Integer> entityFinalStateIds = new HashSet<>();
			Set<Integer> entityEditableStateIds = new HashSet<>();
			Set<Integer> entityDeletableStateIds = new HashSet<>();
			Integer entityInitialStateId = null;
			if (entityStateMaps != null) {
				for (EntityStateMapDTO esMapItr : entityStateMaps) {
					if (entityInitialStateId == null && esMapItr.getIsInitial()) {
						entityInitialStateId = esMapItr.getStateId();
					}
					if (esMapItr.getIsEditable() != null && esMapItr.getIsEditable()) {
						entityEditableStateIds.add(esMapItr.getStateId());
					}
					if (esMapItr.getIsDeletable() != null && esMapItr.getIsDeletable()) {
						entityDeletableStateIds.add(esMapItr.getStateId());
					}
					if (esMapItr.getIsFinal() != null && esMapItr.getIsFinal()) {
						entityFinalStateIds.add(esMapItr.getStateId());
					}
				}
			}

			List<ClientInvoiceFetchResponse> clientInvoiceResObj = new ArrayList<>();
			List<ClientInvoiceProductFetchResponse> ciProducts;
			if (clientInvoicesUpdatedList != null) {
				for (ClientInvoice ci : clientInvoicesUpdatedList) {

//					Set client invoice response object
					ClientInvoiceFetchResponse ciResponseObj = setObject.clientInvoiceEntityToFetchResponseDto(ci);

//									set edit mode
					ciResponseObj.setIsEditable(false);
					if (entityEditableStateIds.contains(ciResponseObj.getStateId())) {
						ciResponseObj.setIsEditable(true);
					}

//									set delete mode
					ciResponseObj.setIsDeletable(false);
					if (entityDeletableStateIds.contains(ciResponseObj.getStateId())) {
						ciResponseObj.setIsDeletable(true);
					}
//								set final state mode
					ciResponseObj.setInFinalState(false);
					if (entityFinalStateIds.contains(ciResponseObj.getStateId())) {
						ciResponseObj.setInFinalState(true);
					}

					ciProducts = new ArrayList<>();
					Boolean firstTraversalPointer = true;
					Boolean isIgst = true;
					Double applicableIgstPercentage = 0.0;
					Double applicableSgstPercentage = 0.0;
					Double applicableCgstPercentage = 0.0;
					Double amount = 0.0;
					Double taxableAmount = 0.0;

//					Traverse `irnInfo` Object to bind it to corresponding invoice
					if (irnDetails != null && !irnDetails.isEmpty())
						for (ClientInvoiceIrnInfo irnInfo : irnDetails) {

							if (ciResponseObj.getClientInvoiceId().equals(irnInfo.getClientInvoiceId())) {

								if (irnInfo.getCancelDate() != null) {
									ciResponseObj.setIsIrnCancelled(true);
								} else {
									ciResponseObj.setIsIrnCancelled(false);
								}
								ciResponseObj.setClientInvoiceIrnInfo(
										setObject.clientInvoiceIrnInfoEntityToFetchResponseDto(irnInfo));
							}

						}

//					Traverse `products` Object to bind it to corresponding invoice
					if (products != null) {
						for (ClientInvoiceProduct ciProduct : products) {
							if (ciProduct.getClientInvoiceId().equals(ciResponseObj.getClientInvoiceId())) {

								if (firstTraversalPointer) {
									isIgst = ciProduct.getIsIgst();
									Double currentIgstPercentage = ciProduct.getApplicableCgstPercentage()
											+ ciProduct.getApplicableSgstPercentage();
									applicableIgstPercentage = currentIgstPercentage;
									applicableCgstPercentage = ciProduct.getApplicableCgstPercentage();
									applicableSgstPercentage = ciProduct.getApplicableSgstPercentage();
									amount = ciProduct.getAmount();
								} else {
									if (!ciProduct.getIsIgst()) {
										isIgst = false;
									}
//								Average out calculative variables for current invoice
									Double currentIgstPercentage = ciProduct.getApplicableCgstPercentage()
											+ ciProduct.getApplicableSgstPercentage();
									applicableIgstPercentage = (applicableIgstPercentage + currentIgstPercentage) / 2;
									applicableCgstPercentage = (applicableCgstPercentage
											+ ciProduct.getApplicableCgstPercentage()) / 2;
									applicableSgstPercentage = (applicableSgstPercentage
											+ ciProduct.getApplicableSgstPercentage()) / 2;
									amount = (amount + ciProduct.getAmount()) / 2;
								}
								ciProducts.add(new ClientInvoiceProductFetchResponse(ciProduct.getId(),
										ciProduct.getGstManagement() != null ? ciProduct.getGstManagement().getId()
												: null,
										ciProduct.getGstManagement() != null ? ciProduct.getGstManagement().getName()
												: null,
										ciProduct.getGstManagement() != null ? ciProduct.getGstManagement().getCode()
												: null,
										ciProduct.getAmount(),
										ciProduct.getGstManagement() != null ? ciProduct.getGstManagement().getType()
												: null,
										ciProduct.getIsIgst(),
										ciProduct.getIsIgst()
												? ciProduct.getApplicableCgstPercentage()
														+ ciProduct.getApplicableSgstPercentage()
												: 0.0,
										ciProduct.getApplicableCgstAmount() + ciProduct.getApplicableSgstAmount(),
										!ciProduct.getIsIgst() ? ciProduct.getApplicableCgstPercentage() : 0.0,
										!ciProduct.getIsIgst() ? ciProduct.getApplicableCgstAmount() : 0.0,
										!ciProduct.getIsIgst() ? ciProduct.getApplicableSgstPercentage() : 0.0,
										!ciProduct.getIsIgst() ? ciProduct.getApplicableSgstAmount() : 0.0, ci.getId(),
										ciProduct.getRemark(),
										ciProduct.getUnit() != null && ciProduct.getUnit().getGovtUnit() != null
												? ciProduct.getUnit().getGovtUnit().getAlias()
												: null,
										ciProduct.getUnit() != null ? ciProduct.getUnit().getId().intValue() : null,
										ciProduct.getUnit() != null ? ciProduct.getUnit().getName() : null,
										ciProduct.getQuantity()));
								taxableAmount = ciProduct.getApplicableCgstAmount()
										+ ciProduct.getApplicableSgstAmount();
							}
						}
					}
					ciResponseObj.setIsIgst(isIgst);
					ciResponseObj.setApplicableCgst(applicableCgstPercentage);
					ciResponseObj.setApplicableIgst(applicableIgstPercentage);
					ciResponseObj.setApplicableSgst(applicableSgstPercentage);
					ciResponseObj.setTotalAmountBeforeGst(amount);
					ciResponseObj.setProducts(ciProducts);
					ciResponseObj.setBillingAddress(
							ci.getSite() != null && ci.getSite().getClientImplementationUnit() != null
									&& ci.getSite().getClientImplementationUnit().getBillingAddress() != null
											? ci.getSite().getClientImplementationUnit().getBillingAddress()
											: null);
//					Apply GST
					Double amountPostApplyGst = amount + taxableAmount;
					ciResponseObj.setTotalAmountAfterGst(amountPostApplyGst);

					// update upi url
					String upiUrl = ci != null && ci.getSite() != null && ci.getSite().getCompany() != null
							? ci.getSite().getCompany().getUpiUrl()
							: null;
					if (upiUrl != null && !upiUrl.isEmpty()) {

						Boolean isValidUpiUrl = true;

						if (isValidUpiUrl)
							isValidUpiUrl = upiUrl.contains(amountVariablePattern);
						if (isValidUpiUrl)
							isValidUpiUrl = upiUrl.contains(invoiceNoVariablePattern);
						if (isValidUpiUrl)
							isValidUpiUrl = upiUrl.contains(invoiceDateVariablePattern);
						else
							isValidUpiUrl = false;

						if (isValidUpiUrl) {

							upiUrl = upiUrl.replace(amountVariablePattern,
									ciResponseObj.getTotalAmountAfterGst().toString());
							upiUrl = upiUrl.replace(invoiceNoVariablePattern, ciResponseObj.getInvoiceNo());
							upiUrl = upiUrl.replace(invoiceDateVariablePattern,
									ciResponseObj.getInvoiceDate().toString());

						}

					}
					ciResponseObj.setUpiUrl(upiUrl);

					clientInvoiceResObj.add(ciResponseObj);
				}
			}

			PaginationResultObject resultObj = new PaginationResultObject(clientInvoiceCount, clientInvoiceResObj);

			return new CustomResponse(Responses.SUCCESS.getCode(), resultObj, Responses.SUCCESS.toString());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		}
	}

	@Override
	public CustomResponse deactivateClientInvoiceById(Long clientInvoiceId, Long userId) {

		try {
			ClientInvoice clientInvoice = clientInvoiceDao.fetchClientInvoiceById(clientInvoiceId);
			if (clientInvoice == null)
				return new CustomResponse(Responses.NOT_FOUND.getCode(), null, "Does not exist.");

			clientInvoice.setIsActive(false);
			clientInvoice.setUpdatedOn(new Date());
			clientInvoice.setUpdatedBy(userId);
			Boolean isDeactivated = clientInvoiceDao.deactivateClientInvoice(clientInvoice);

			Set<Long> distinctClientInvoiceIds = new HashSet<>();
			distinctClientInvoiceIds.add(clientInvoice.getId());
//			Get all products
			List<ClientInvoiceProduct> products = clientInvoiceDao
					.fetchClientInvoiceProductsByInvoiceIds(distinctClientInvoiceIds);
			if (products != null) {
				for (ClientInvoiceProduct ciProduct : products) {
					ciProduct.setIsActive(false);
					ciProduct.setUpdatedOn(new Date());
					ciProduct.setUpdatedBy(userId);
					clientInvoiceDao.deactivateClientInvoiceProduct(ciProduct);
				}
			}
			return new CustomResponse(isDeactivated ? Responses.SUCCESS.getCode() : Responses.FORBIDDEN.getCode(),
					isDeactivated, isDeactivated ? Responses.SUCCESS.toString() : Responses.FORBIDDEN.toString());

		} catch (

		Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		}
	}

	@Override
	public CustomResponse getClientInvoiceById(Long clientInvoiceId) {
		try {

			ClientInvoiceFetchResponse resultObj = null;
			ClientInvoice clientInvoice = clientInvoiceDao.fetchClientInvoiceById(clientInvoiceId);

			if (clientInvoice != null) {
				ClientInvoiceIrnInfo clientInvoiceIrnInfo = clientInvoiceIrnInfoDao
						.fetchclientInvoiceIrnInfoByClientId(clientInvoiceId);

				ClientInvoiceIrnInfoResponse clientInvoiceIrnInfoResponse = setObject
						.clientInvoiceIrnInfoEntityToFetchResponseDto(clientInvoiceIrnInfo);
				resultObj = setObject.clientInvoiceEntityToFetchResponseDto(clientInvoice);
				resultObj.setClientInvoiceIrnInfoResponse(clientInvoiceIrnInfoResponse);

				Set<Long> distinctClientInvoiceIds = new HashSet<>();
				distinctClientInvoiceIds.add(clientInvoice.getId());
//			Get all products

				List<ClientInvoiceProduct> products = clientInvoiceDao
						.fetchClientInvoiceProductsByInvoiceIds(distinctClientInvoiceIds);
				Boolean firstTraversalPointer = true;
				Boolean isIgst = true;
				Double applicableIgstPercentage = 0.0;
				Double applicableIgstAmount = 0.0;
				Double applicableSgstPercentage = 0.0;
				Double applicableSgstAmount = 0.0;
				Double applicableCgstPercentage = 0.0;
				Double applicableCgstAmount = 0.0;
				Double amount = 0.0;
				Double taxableAmount = 0.0;

				List<ClientInvoiceProductFetchResponse> ciProducts = new ArrayList<>();
				if (products != null) {
					for (ClientInvoiceProduct ciProduct : products) {
						if (firstTraversalPointer) {
							isIgst = ciProduct.getIsIgst();
							Double currentIgstPercentage = ciProduct.getApplicableCgstPercentage()
									+ ciProduct.getApplicableSgstPercentage();
							applicableIgstPercentage = currentIgstPercentage;
							applicableCgstPercentage = ciProduct.getApplicableCgstPercentage();
							applicableSgstPercentage = ciProduct.getApplicableSgstPercentage();
							applicableIgstAmount = applicableIgstAmount + ciProduct.getApplicableCgstAmount()
									+ ciProduct.getApplicableSgstAmount();

							applicableCgstAmount = ciProduct.getApplicableCgstAmount();
							applicableSgstAmount = ciProduct.getApplicableSgstAmount();
							amount = ciProduct.getAmount();
						} else {
							if (!ciProduct.getIsIgst()) {
								isIgst = false;
							}
//						Average out calculative variables for current invoice
							Double currentIgstPercentage = ciProduct.getApplicableCgstPercentage()
									+ ciProduct.getApplicableSgstPercentage();
							applicableIgstPercentage = (applicableIgstPercentage + currentIgstPercentage) / 2;
							applicableCgstPercentage = (applicableCgstPercentage
									+ ciProduct.getApplicableCgstPercentage()) / 2;
							applicableSgstPercentage = (applicableSgstPercentage
									+ ciProduct.getApplicableSgstPercentage()) / 2;
							amount = (amount + ciProduct.getAmount()) / 2;
							applicableSgstAmount = applicableSgstAmount + ciProduct.getApplicableSgstAmount();
							applicableCgstAmount = applicableCgstAmount + ciProduct.getApplicableCgstAmount();
						}
						ciProducts.add(new ClientInvoiceProductFetchResponse(ciProduct.getId(),
								ciProduct.getGstManagement() != null ? ciProduct.getGstManagement().getId() : null,
								ciProduct.getGstManagement() != null ? ciProduct.getGstManagement().getName() : null,
								ciProduct.getGstManagement() != null ? ciProduct.getGstManagement().getCode() : null,
								ciProduct.getAmount(),
								ciProduct.getGstManagement() != null ? ciProduct.getGstManagement().getType() : null,
								ciProduct.getIsIgst(),
								ciProduct.getIsIgst()
										? ciProduct.getApplicableCgstPercentage()
												+ ciProduct.getApplicableSgstPercentage()
										: 0.0,
								ciProduct.getApplicableCgstAmount() + ciProduct.getApplicableSgstAmount(),
								!ciProduct.getIsIgst() ? ciProduct.getApplicableCgstPercentage() : 0.0,
								!ciProduct.getIsIgst() ? ciProduct.getApplicableCgstAmount() : 0.0,
								!ciProduct.getIsIgst() ? ciProduct.getApplicableSgstPercentage() : 0.0,
								!ciProduct.getIsIgst() ? ciProduct.getApplicableSgstAmount() : 0.0,
								clientInvoice.getId(), ciProduct.getRemark(),
								ciProduct.getUnit() != null && ciProduct.getUnit().getGovtUnit() != null
										? ciProduct.getUnit().getGovtUnit().getAlias()
										: null,
								ciProduct.getUnit() != null ? ciProduct.getUnit().getId().intValue() : null,
								ciProduct.getUnit() != null ? ciProduct.getUnit().getName() : null,
								ciProduct.getQuantity()));
						taxableAmount = ciProduct.getApplicableCgstAmount() + ciProduct.getApplicableSgstAmount();
					}
				}

				resultObj.setApplicableCgstAmount(applicableCgstAmount);
				resultObj.setApplicableIgstAmount(applicableIgstAmount);
				resultObj.setApplicableSgstAmount(applicableSgstAmount);
				resultObj.setIsIgst(isIgst);
				resultObj.setApplicableCgst(applicableCgstPercentage);
				resultObj.setApplicableIgst(applicableIgstPercentage);
				resultObj.setApplicableSgst(applicableSgstPercentage);
				resultObj.setTotalAmountBeforeGst(amount);
				resultObj.setProducts(ciProducts);
//			Apply GST
				Double amountPostApplyGst = amount + taxableAmount;
				resultObj.setTotalAmountAfterGst(amountPostApplyGst);

				// update upi url
				String upiUrl = clientInvoice != null && clientInvoice.getSite() != null
						&& clientInvoice.getSite().getCompany() != null
								? clientInvoice.getSite().getCompany().getUpiUrl()
								: null;
				if (upiUrl != null && !upiUrl.isEmpty()) {

					Boolean isValidUpiUrl = true;

					if (isValidUpiUrl)
						isValidUpiUrl = upiUrl.contains(amountVariablePattern);
					if (isValidUpiUrl)
						isValidUpiUrl = upiUrl.contains(invoiceNoVariablePattern);
					if (isValidUpiUrl)
						isValidUpiUrl = upiUrl.contains(invoiceDateVariablePattern);
					else
						isValidUpiUrl = false;

					if (isValidUpiUrl) {

						upiUrl = upiUrl.replace(amountVariablePattern, resultObj.getTotalAmountAfterGst().toString());
						upiUrl = upiUrl.replace(invoiceNoVariablePattern, resultObj.getInvoiceNo());
						upiUrl = upiUrl.replace(invoiceDateVariablePattern, resultObj.getInvoiceDate().toString());

					}

				}
				resultObj.setUpiUrl(upiUrl);

				List<EntityStateMapDTO> entityStateMaps = engineService.getEntityStatesByCompanyId(
						EntitiesEnum.Client_Invoice.getEntityId(), clientInvoice.getCompanyId());

				Boolean isInitial = false;
				Boolean isEditable = false;
				Boolean isFinal = false;
				Boolean isFinalSuccess = false;
				Boolean isDeletable = false;

				for (EntityStateMapDTO esm : entityStateMaps) {

					if (esm.getEntityId().equals(EntitiesEnum.Client_Invoice.getEntityId())
							&& esm.getStateId().equals(clientInvoice.getState().getId())) {
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

						if (!isFinalSuccess && esm.getIsFinal() != null
								&& esm.getStateActionId().equals(EngineStateActions.Final_Success.getStateActionId())) {
							isFinalSuccess = esm.getIsFinal();
						}

					}

				}

				resultObj.setIsEditable(isEditable);
				resultObj.setIsDeletable(isDeletable);
				resultObj.setInFinalState(isFinal);
				resultObj.setIsFinalSuccessState(isFinalSuccess);
			}

			return new CustomResponse(resultObj != null ? Responses.SUCCESS.getCode() : Responses.NOT_FOUND.getCode(),
					resultObj, resultObj != null ? Responses.SUCCESS.toString() : "Invalid id.");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		}
	}

	@Override
	public CustomResponse getNextPossibleStates(ClientInvoiceNextPossibleStatesFetchRequest requestObj) {

		try {
			if (requestObj.getClientInvoiceId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide clientInvoiceId.");
			}
			ClientInvoice clientInvoice = clientInvoiceDao.fetchClientInvoiceById(requestObj.getClientInvoiceId());
			List<StateTransitionDTO> transitions = engineService.getStateTransition(
					EntitiesEnum.Client_Invoice.getEntityId(), requestObj.getSiteId(), null, null,
					requestObj.getUser().getCompanyId());
			List<NextStateTransitDTO> nextStates = new ArrayList<>();
			User clientInvoiceUser = userDao.fetchUserById(clientInvoice.getUpdatedBy());

//			set next states
			if (transitions != null && clientInvoiceUser != null && clientInvoiceUser.getRole() != null) {
				for (StateTransitionDTO st : transitions) {
					if (st.getStateId().equals(clientInvoice.getState().getId())
							&& st.getRoleId().equals(clientInvoiceUser.getRole().getId())
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
	public CustomResponse getClientInvoiceTransitionByClientInvoiceId(Long clientInvoiceId) {

		try {
			List<ClientInvoiceStateTransitionMappingV2> clientInvoiceTransitionList = clientInvoiceDao
					.fetchClientInvoiceStateTransitionListByClientInvoiceId(clientInvoiceId);
			List<ClientInvoiceStateTransitionFetchResponse> results = new ArrayList<>();
			if (clientInvoiceTransitionList != null) {
				clientInvoiceTransitionList
						.forEach(obj -> results.add(setObject.clientInvoiceStateTransitionEntityToDto(obj)));
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), results, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse exportClientInvoiceExcel(ClientInvoiceExportExcelRequest requestObj) {

		try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream bos = new ByteArrayOutputStream();) {

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			DateFormat dateWithTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

//			Get client invoices using search

			ClientInvoiceFetchRequest dbRequestObj = new ClientInvoiceFetchRequest(requestObj.getSearch(),
					requestObj.getSiteId(), requestObj.getStateId(), null, null, requestObj.getSortByInvoiceDateInAsc(),
					requestObj.getUser());
			List<ClientInvoice> clientInvoices = clientInvoiceDao.fetchClientInvoiceList(dbRequestObj);

			Set<Long> distinctIds = new HashSet<>();

			if (clientInvoices != null) {
				clientInvoices.forEach(obj -> distinctIds.add(obj.getId()));
			}
			List<StateTransitionDTO> transitions = engineService.getStateTransition(
					EntitiesEnum.Client_Invoice.getEntityId(), requestObj.getSiteId().longValue(), null, null,
					requestObj.getUser().getCompanyId());

			List<ClientInvoiceStateTransitionMapping> clientInvoiceStateMaps = clientInvoiceDao
					.fetchClientInvoiceStateTransitionByClientInvoiceIds(distinctIds);

			List<ClientInvoiceIrnInfo> clientInvoiceIrnInfo = clientInvoiceIrnInfoDao
					.fetchAllclientInvoiceIrnInfoByClientInvoiceId(distinctIds);

			List<EntityStateMapDTO> entityStateMaps = engineService.getEntityStatesByCompanyId(
					EntitiesEnum.Client_Invoice.getEntityId(), requestObj.getUser().getCompanyId());

			Set<Integer> entityFinalStateIds = new HashSet<>();
			Set<Integer> entityEditableStateIds = new HashSet<>();
			Set<Integer> entityDeletableStateIds = new HashSet<>();
			Integer entityInitialStateId = null;
			if (entityStateMaps != null) {
				for (EntityStateMapDTO esMapItr : entityStateMaps) {
					if (entityInitialStateId == null && esMapItr.getIsInitial()) {
						entityInitialStateId = esMapItr.getStateId();
					}
					if (esMapItr.getIsEditable() != null && esMapItr.getIsEditable()) {
						entityEditableStateIds.add(esMapItr.getStateId());
					}
					if (esMapItr.getIsDeletable() != null && esMapItr.getIsDeletable()) {
						entityDeletableStateIds.add(esMapItr.getStateId());
					}
					if (esMapItr.getIsFinal() != null && esMapItr.getIsFinal()) {
						entityFinalStateIds.add(esMapItr.getStateId());
					}
				}
			}

			List<ClientInvoice> clientInvoicesUpdatedList = new ArrayList<>();

			for (ClientInvoice ci : clientInvoices) {

//				set next states
				List<NextStateTransitDTO> nextStates = new ArrayList<>();
				if (transitions != null && ci.getUpdatedByDetail() != null
						&& ci.getUpdatedByDetail().getRole() != null) {
					for (StateTransitionDTO st : transitions) {
						if (st.getStateId().equals(ci.getState().getId())
								&& st.getRoleId().equals(ci.getUpdatedByDetail().getRole().getId())
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
							nextStates.add(nst);
						}
					}
				}

//				add client invoice in result list
				boolean hasAdded = false;

				if (ci.getUpdatedByDetail().getId().equals(requestObj.getUser().getId())) {
					clientInvoicesUpdatedList.add(ci);
					hasAdded = true;
				}

				if (!hasAdded && nextStates != null && nextStates.size() > 0) {
					clientInvoicesUpdatedList.add(ci);
					hasAdded = true;
				}
				if (!hasAdded) {
					List<ClientInvoiceStateTransitionMapping> ciStateMapList = new ArrayList<>();
					if (clientInvoiceStateMaps != null) {
						for (ClientInvoiceStateTransitionMapping cistObj : clientInvoiceStateMaps) {
							if (cistObj.getClientInvoiceId().equals(ci.getId())) {
								ciStateMapList.add(cistObj);
							}
						}
					}
					if (!hasAdded && ciStateMapList != null) {
						for (ClientInvoiceStateTransitionMapping stm : ciStateMapList) {
							if (stm.getCreatedBy().equals(requestObj.getUser().getId())) {
								clientInvoicesUpdatedList.add(ci);
								hasAdded = true;
								break;
							}
						}
					}
				}
				if (!hasAdded && entityFinalStateIds.contains(ci.getState().getId())) {
					clientInvoicesUpdatedList.add(ci);
					hasAdded = true;
				}

			}

			Set<Long> distinctClientInvoiceIds = new HashSet<>();
			if (clientInvoices != null) {
				for (ClientInvoice clientInvoice : clientInvoicesUpdatedList) {
					distinctClientInvoiceIds.add(clientInvoice.getId());
				}
			}

//			Get all products

			List<ClientInvoiceProduct> products = clientInvoiceDao
					.fetchClientInvoiceProductsByInvoiceIds(distinctClientInvoiceIds);
			List<ClientInvoiceFetchResponse> clientInvoiceResObj = new ArrayList<>();
			List<ClientInvoiceProductFetchResponse> ciProducts;
			if (clientInvoices != null) {
				for (ClientInvoice ci : clientInvoicesUpdatedList) {

//					Set client invoice response object
					ClientInvoiceFetchResponse ciResponseObj = setObject.clientInvoiceEntityToFetchResponseDto(ci);

//									set edit mode
					ciResponseObj.setIsEditable(false);
					if (entityEditableStateIds.contains(ciResponseObj.getStateId())) {
						ciResponseObj.setIsEditable(true);
					}

//									set delete mode
					ciResponseObj.setIsDeletable(false);
					if (entityDeletableStateIds.contains(ciResponseObj.getStateId())) {
						ciResponseObj.setIsDeletable(true);
					}
//								set final state mode
					ciResponseObj.setInFinalState(false);
					if (entityFinalStateIds.contains(ciResponseObj.getStateId())) {
						ciResponseObj.setInFinalState(true);
					}

					ciProducts = new ArrayList<>();
					Boolean firstTraversalPointer = true;
					Boolean isIgst = true;
					Double applicableIgstPercentage = 0.0;
					Double applicableSgstPercentage = 0.0;
					Double applicableCgstPercentage = 0.0;
					Double amount = 0.0;
					Double taxableAmount = 0.0;

//					Traverse `products` Object to bind it to corresponding invoice
					if (products != null) {
						for (ClientInvoiceProduct ciProduct : products) {
							if (ciProduct.getClientInvoiceId().equals(ciResponseObj.getClientInvoiceId())) {

								if (firstTraversalPointer) {
									isIgst = ciProduct.getIsIgst();
									Double currentIgstPercentage = ciProduct.getApplicableCgstPercentage()
											+ ciProduct.getApplicableSgstPercentage();
									applicableIgstPercentage = currentIgstPercentage;
									applicableCgstPercentage = ciProduct.getApplicableCgstPercentage();
									applicableSgstPercentage = ciProduct.getApplicableSgstPercentage();
									amount = ciProduct.getAmount();
								} else {
									if (!ciProduct.getIsIgst()) {
										isIgst = false;
									}
//								Average out calculative variables for current invoice
									Double currentIgstPercentage = ciProduct.getApplicableCgstPercentage()
											+ ciProduct.getApplicableSgstPercentage();
									applicableIgstPercentage = (applicableIgstPercentage + currentIgstPercentage) / 2;
									applicableCgstPercentage = (applicableCgstPercentage
											+ ciProduct.getApplicableCgstPercentage()) / 2;
									applicableSgstPercentage = (applicableSgstPercentage
											+ ciProduct.getApplicableSgstPercentage()) / 2;
									amount = (amount + ciProduct.getAmount()) / 2;
								}
								ciProducts.add(new ClientInvoiceProductFetchResponse(ciProduct.getId(),
										ciProduct.getGstManagement() != null ? ciProduct.getGstManagement().getId()
												: null,
										ciProduct.getGstManagement() != null ? ciProduct.getGstManagement().getName()
												: null,
										ciProduct.getGstManagement() != null ? ciProduct.getGstManagement().getCode()
												: null,
										ciProduct.getAmount(),
										ciProduct.getGstManagement() != null ? ciProduct.getGstManagement().getType()
												: null,
										ciProduct.getIsIgst(),
										ciProduct.getIsIgst()
												? ciProduct.getApplicableCgstPercentage()
														+ ciProduct.getApplicableSgstPercentage()
												: 0.0,
										ciProduct.getApplicableCgstAmount() + ciProduct.getApplicableSgstAmount(),
										!ciProduct.getIsIgst() ? ciProduct.getApplicableCgstPercentage() : 0.0,
										!ciProduct.getIsIgst() ? ciProduct.getApplicableCgstAmount() : 0.0,
										!ciProduct.getIsIgst() ? ciProduct.getApplicableSgstPercentage() : 0.0,
										!ciProduct.getIsIgst() ? ciProduct.getApplicableSgstAmount() : 0.0, ci.getId(),
										ciProduct.getRemark(),
										ciProduct.getUnit() != null && ciProduct.getUnit().getGovtUnit() != null
												? ciProduct.getUnit().getGovtUnit().getAlias()
												: null,
										ciProduct.getUnit() != null ? ciProduct.getUnit().getId().intValue() : null,
										ciProduct.getUnit() != null ? ciProduct.getUnit().getName() : null,
										ciProduct.getQuantity()));
								taxableAmount = ciProduct.getApplicableCgstAmount()
										+ ciProduct.getApplicableSgstAmount();
							}
						}
					}
					ciResponseObj.setIsIgst(isIgst);
					ciResponseObj.setApplicableCgst(applicableCgstPercentage);
					ciResponseObj.setApplicableIgst(applicableIgstPercentage);
					ciResponseObj.setApplicableSgst(applicableSgstPercentage);
					ciResponseObj.setTotalAmountBeforeGst(amount);
					ciResponseObj.setProducts(ciProducts);
					ciResponseObj.setBillingAddress(
							ci.getSite() != null && ci.getSite().getClientImplementationUnit() != null
									&& ci.getSite().getClientImplementationUnit().getBillingAddress() != null
											? ci.getSite().getClientImplementationUnit().getBillingAddress()
											: null);
//					Apply GST
					Double amountPostApplyGst = amount + taxableAmount;
					ciResponseObj.setTotalAmountAfterGst(amountPostApplyGst);

//					Client Irn Info
					ClientInvoiceIrnInfo clientIrnInfoObj = clientInvoiceIrnInfo.stream()
							.filter(obj -> obj.getClientInvoiceId().equals(ci.getId())).findFirst().orElse(null);
					ClientInvoiceIrnInfoResponse clientInvoiceIrnInfoResponse = setObject
							.clientInvoiceIrnInfoEntityToFetchResponseDto(clientIrnInfoObj);
					ciResponseObj.setClientInvoiceIrnInfo(clientInvoiceIrnInfoResponse);

					clientInvoiceResObj.add(ciResponseObj);

				}
			}

			if (clientInvoiceResObj.isEmpty())
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "No client invoice found.");

			List<String> columnsList = new ArrayList<String>(Arrays.asList(staticColumnsOfExportClientInvoiceDetails));

			XSSFSheet sheet = workbook.createSheet("Client Invoice");
			int row = 0;
			Row headerRow = sheet.createRow(row++);

			CellStyle boldCellStyle = workbook.createCellStyle();
			Font boldFont = workbook.createFont();
			boldFont.setBold(true);
			boldFont.setFontHeightInPoints((short) 12);
			boldCellStyle.setFont(boldFont);
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.WHITE.getIndex());
			headerFont.setFontHeightInPoints((short) 14);
			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFillForegroundColor(IndexedColors.DARK_TEAL.getIndex());
			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerCellStyle.setFont(headerFont);
			headerCellStyle.setWrapText(true);
			headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
			for (int i = 0; i < columnsList.size(); i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(columnsList.get(i));
				cell.setCellStyle(headerCellStyle);
			}
			Integer serialNoCount = 0;

			// if (outdoorDutyDetailsList != null && outdoorDutyDetailsList.size() > 0) {
			for (ClientInvoiceFetchResponse ci : clientInvoiceResObj) {
				ClientInvoiceProductFetchResponse ciProduct = ci.getProducts() != null && !ci.getProducts().isEmpty()
						? ci.getProducts().get(0)
						: null;
				serialNoCount++;
				Row clientInvoiceRow = sheet.createRow(row++);
				for (int i = 0; i < columnsList.size(); i++) {
					Cell cell = clientInvoiceRow.createCell(i);
					if (i == 0) {
						cell.setCellValue(serialNoCount);
						cell.setCellStyle(boldCellStyle);
					} else if (i == 1) {
						cell.setCellValue(ci.getInvoiceType() != null ? ci.getInvoiceType().getName() : "-");
					} else if (i == 2) {
						cell.setCellValue(ci.getSupplyType() != null ? ci.getSupplyType().getName() : "-");
					} else if (i == 3) {
						cell.setCellValue(!ci.getReverseCharge() ? "No" : "Yes");
					} else if (i == 4) {
						cell.setCellValue(ci.getTransportModeName() != null ? ci.getTransportModeName() : "None");
					} else if (i == 5) {
						cell.setCellValue(ci.getVehicleNo() != null ? ci.getVehicleNo() : "-");
					} else if (i == 6) {
						cell.setCellValue(ci.getInvoiceNo());
					} else if (i == 7) {
						cell.setCellValue(dateFormat.format(ci.getInvoiceDate()));
					} else if (i == 8) {
						cell.setCellValue(dateFormat.format(ci.getSupplyDate()));
					} else if (i == 9) {
						cell.setCellValue(ci.getSupplyStateName() != null ? ci.getSupplyStateName() : "-");
					} else if (i == 10) {
						cell.setCellValue(ci.getGstNo() != null ? ci.getGstNo() : "-");
					} else if (i == 11) {
						cell.setCellValue(ci.getCiuOfficeName() != null ? ci.getCiuOfficeName() : "");
					} else if (i == 12) {
						cell.setCellValue(ci.getBillingAddress() != null ? ci.getBillingAddress() : "");
					} else if (i == 13) {
						cell.setCellValue(ci.getCiuOfficeName() != null ? ci.getCiuOfficeName() : "");

					} else if (i == 14) {
						cell.setCellValue(ci.getShippingAddress() != null ? ci.getShippingAddress() : "");
					} else if (i == 15) {
						cell.setCellValue(ciProduct != null && ciProduct.getName() != null ? ciProduct.getName() : "");
					} else if (i == 16) {
						cell.setCellValue(
								ciProduct != null && ciProduct.getRemark() != null ? ciProduct.getRemark() : "");
					} else if (i == 17) {
						cell.setCellValue(
								ciProduct != null && ciProduct.getHsnCode() != null ? ciProduct.getHsnCode() : "");
					} else if (i == 18) {
						cell.setCellValue(
								ciProduct != null && ciProduct.getQuantity() != null ? ciProduct.getQuantity() : 0.0);
					} else if (i == 19) {
						cell.setCellValue(
								ciProduct != null && ciProduct.getUnitName() != null ? ciProduct.getUnitName() : "-");
					} else if (i == 20) {
						cell.setCellValue(ci.getApplicableIgst() != null ? String.format("%.2f", ci.getApplicableIgst())
								: "0.00");
					} else if (i == 21) {
						cell.setCellValue(ci.getTotalAmountBeforeGst() != null
								? String.format("%.2f", ci.getTotalAmountBeforeGst())
								: "0.00");
					} else if (i == 22) {
						cell.setCellValue(
								ci.getTotalAmountAfterGst() != null ? String.format("%.2f", ci.getTotalAmountAfterGst())
										: "0.00");
					} else if (i == 23) {
						cell.setCellValue(ci.getStateName() != null ? ci.getStateName() : "-");
					} else if (i == 24) {
						cell.setCellValue(ci.getClientInvoiceIrnInfo() != null
								? dateWithTimeFormat.format(ci.getClientInvoiceIrnInfo().getAckDt())
								: "-");
					} else if (i == 25) {
						cell.setCellValue(
								ci.getClientInvoiceIrnInfo() != null ? ci.getClientInvoiceIrnInfo().getAckNo() : "-");
					} else if (i == 26) {
						cell.setCellValue(
								ci.getClientInvoiceIrnInfo() != null ? ci.getClientInvoiceIrnInfo().getIrn() : "-");
					} else if (i == 27) {
						cell.setCellValue(ci.getClientInvoiceIrnInfo() != null
								&& ci.getClientInvoiceIrnInfo().getCancelDate() != null
										? dateWithTimeFormat.format(ci.getClientInvoiceIrnInfo().getCancelDate())
										: "-");
					} else if (i == 28) {
						cell.setCellValue(ci.getClientInvoiceIrnInfo() != null
								&& ci.getClientInvoiceIrnInfo().getCancelReason() != null
										? ci.getClientInvoiceIrnInfo().getCancelReason()
										: "-");
					} else if (i == 29) {
						cell.setCellValue(ci.getClientInvoiceIrnInfo() != null
								&& ci.getClientInvoiceIrnInfo().getCancelRemark() != null
										? ci.getClientInvoiceIrnInfo().getCancelRemark()
										: "-");
					}
				}
			}
			for (int i = 0; i < columnsList.size(); i++) {
				sheet.autoSizeColumn(i);
			}
			workbook.write(bos);
			byte[] bytes = bos.toByteArray();
			return new CustomResponse(200, Base64.getEncoder().encodeToString(bytes), "SUCCESS");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(400, false, "Bad Request");
		}
	}

	@Override
	public CustomResponse generateIrn(Long clientInvoiceId, UserDetail userDetail) {
		try {

			CustomResponse clientInvoiceResponseObj = getClientInvoiceById(clientInvoiceId);

			ClientInvoiceFetchResponse clientInvoice = null;

			/*
			 * converting clientInvoiceResponseObj(CustomResponse Object) into
			 * ClientInvoiceFetchResponse
			 */
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			if (clientInvoiceResponseObj != null
					&& clientInvoiceResponseObj.getStatus().equals(Responses.SUCCESS.getCode())
					&& clientInvoiceResponseObj.getData() != null) {
				clientInvoice = (ClientInvoiceFetchResponse) mapper.convertValue(clientInvoiceResponseObj.getData(),
						new TypeReference<ClientInvoiceFetchResponse>() {
						});
			} else
				return clientInvoiceResponseObj;

			if (clientInvoice == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Invoice not exists.");
			}

			// check client invoice whether in final success state or not
			if (clientInvoice != null && !clientInvoice.getIsFinalSuccessState()) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"Final success state required to generate IRN.");
			}

			if (clientInvoice.getClientInvoiceIrnInfo() != null
					&& clientInvoice.getClientInvoiceIrnInfo().getIrn() != null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Duplicate IRN");
			}

			Site site = siteDao.fetchSiteById(clientInvoice.getSiteId());

			IrnGenerateRequest irnGenerateObj = new IrnGenerateRequest();

			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

			irnGenerateObj.setVersion(IrnConstant.version);
			irnGenerateObj.getSiteId();

			// set tranDetails
			IrnTransactionDetailRequest transactionDetails = new IrnTransactionDetailRequest(IrnConstant.taxScheme,
					IrnConstant.supplyType, clientInvoice.getReverseCharge() ? "Y" : "N", null, null);
			irnGenerateObj.setTranDtls(transactionDetails);

			// set docDetails
			IrnDocumentDetailRequest documentDetails = new IrnDocumentDetailRequest(IrnConstant.docType,
					clientInvoice.getInvoiceNo(), formatter.format(clientInvoice.getInvoiceDate()));
			irnGenerateObj.setDocDtls(documentDetails);

//			// set sellerDetails
			IrnSellerDetailRequest sellerDetails = new IrnSellerDetailRequest(site.getGstNo(),
					site.getCompany() != null ? site.getCompany().getName() : null, null, site.getAddress(), null,
					site.getCity() != null ? site.getCity().getName() : null,
					site.getZipCode() != null ? site.getZipCode().getCode() : null,
					site.getState() != null ? site.getState().getStateCode() : null, null, null);
			irnGenerateObj.setSellerDtls(sellerDetails);

//			// set buyerDetails
			IrnBuyerDetailRequest buyerDetails = new IrnBuyerDetailRequest(clientInvoice.getGstNo(),
					clientInvoice.getCiuOfficeName(), null, clientInvoice.getSupplyStateCode(),
					clientInvoice.getBillingAddress(), null, clientInvoice.getBillingCityName(),
					clientInvoice.getBillingZipCode(), clientInvoice.getBillingStateCode(), null, null);
			irnGenerateObj.setBuyerDtls(buyerDetails);

//			// set dispatchDetails
			IrnDispatchDetailRequest dispatchDetails = new IrnDispatchDetailRequest(site.getName(),
					clientInvoice.getDispatchAddress(), null, clientInvoice.getDispatchCityName(),
					clientInvoice.getDispatchZipCode(), clientInvoice.getDispatchStateCode());

			if (clientInvoice.getDispatchAddress() != null) {
				irnGenerateObj.setDispDtls(dispatchDetails);
			}

//			// set itemList
			List<IrnItemRequest> itemList = new ArrayList<IrnItemRequest>();

			Integer itemSerialNoCount = 0;
			for (ClientInvoiceProductFetchResponse cip : clientInvoice.getProducts()) {
				itemSerialNoCount++;
				IrnItemRequest itemObj = new IrnItemRequest(itemSerialNoCount.toString(), cip.getName(),
						cip.getType().equals(GstTypeEnum.GOODS) ? "N" : "Y", cip.getHsnCode(), null, cip.getQuantity(),
						null, cip.getGovtUnitAlias(), cip.getAmount(), cip.getAmount(), 0.0, 1.0, cip.getAmount(),
						cip.getIsIgst() ? cip.getApplicableIgstPercentage()
								: cip.getApplicableCgstPercentage() + cip.getApplicableSgstPercentage(),
						cip.getIsIgst() ? Double.parseDouble(String.format("%.2f", cip.getApplicableIgstAmount()))
								: 0.0,
						!cip.getIsIgst() ? Double.parseDouble(String.format("%.2f", cip.getApplicableCgstAmount()))
								: 0.0,
						!cip.getIsIgst() ? Double.parseDouble(String.format("%.2f", cip.getApplicableSgstAmount()))
								: 0.0,
						0.0, null, 0.0, null, 0.0, null, null,
						cip.getIsIgst()
								? Double.parseDouble(
										String.format("%.2f", cip.getAmount() + cip.getApplicableIgstAmount()))
								: Double.parseDouble(
										String.format("%.2f",
												cip.getAmount() + cip.getApplicableSgstAmount()
														+ cip.getApplicableCgstAmount())),
						null, null, null, null, null);
				itemList.add(itemObj);

			}

//			// set Item value for government
//			Integer itemSerialNoCount = 0;
//			for (ClientInvoiceProductFetchResponse cip : clientInvoice.getProducts()) {
//				itemSerialNoCount++;
//				IrnItemRequest itemObj = new IrnItemRequest(itemSerialNoCount.toString(), null,
//						cip.getType().equals(GstTypeEnum.GOODS) ? "N" : "Y", cip.getHsnCode(), null, cip.getQuantity(),
//						null, cip.getGovtUnitAlias(), cip.getAmount(), cip.getAmount(), 0.0, 1.0, cip.getAmount(),
//						cip.getIsIgst() ? cip.getApplicableIgstPercentage()
//								: cip.getApplicableCgstPercentage() + cip.getApplicableSgstPercentage(),
//						cip.getIsIgst() ? Double.parseDouble(String.format("%.2f", cip.getApplicableIgstAmount()))
//								: null,
//						!cip.getIsIgst() ? Double.parseDouble(String.format("%.2f", cip.getApplicableCgstAmount()))
//								: null,
//						!cip.getIsIgst() ? Double.parseDouble(String.format("%.2f", cip.getApplicableSgstAmount()))
//								: null,
//						null, null, null, null, null, null, null,
//						cip.getIsIgst()
//								? Double.parseDouble(
//										String.format("%.2f", cip.getAmount() + cip.getApplicableIgstAmount()))
//								: Double.parseDouble(
//										String.format("%.2f",
//												cip.getAmount() + cip.getApplicableSgstAmount()
//														+ cip.getApplicableCgstAmount())),
//						null, null, null, null, null);
//				itemList.add(itemObj);
//
//			}

//			// set valueDetails for government
//			IrnValueDetailRequest valueDetails = new IrnValueDetailRequest(clientInvoice.getTotalAmountBeforeGst(),
//					!clientInvoice.getIsIgst() ? clientInvoice.getApplicableCgstAmount() : null,
//					!clientInvoice.getIsIgst() ? clientInvoice.getApplicableSgstAmount() : null,
//					clientInvoice.getIsIgst() ? clientInvoice.getApplicableIgstAmount() : null, null, null, 0.0, null,
//					null, clientInvoice.getTotalAmountAfterGst(), null);

			irnGenerateObj.setItemList(itemList);

			// set valueDetails for GstHero
			IrnValueDetailRequest valueDetails = new IrnValueDetailRequest(clientInvoice.getTotalAmountBeforeGst(),
					!clientInvoice.getIsIgst() ? clientInvoice.getApplicableCgstAmount() : 0,
					!clientInvoice.getIsIgst() ? clientInvoice.getApplicableSgstAmount() : 0,
					clientInvoice.getIsIgst() ? clientInvoice.getApplicableIgstAmount() : 0, 0.0, 0.0, 0.0, null, null,
					clientInvoice.getTotalAmountAfterGst(), 0.0);

			irnGenerateObj.setValDtls(valueDetails);

			irnGenerateObj.setUserDetail(userDetail);

//			ClientIrnResponse obj = utilityService.generateIrn(irnGenerateObj);

			CustomResponse response = utilityService.generateIrnByGstHero(irnGenerateObj);

			if (response.getData() == null)
				return response;

			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			ClientIrnResponse obj = (ClientIrnResponse) mapper.convertValue(response.getData(),
					new TypeReference<ClientIrnResponse>() {
					});

			if (obj != null && obj.getErrorDetails() != null && !obj.getErrorDetails().isEmpty()) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString(),
						obj.getErrorDetails());
			}
			Long clientInvoiceIrnInfoId = null;
			if (obj != null && obj.getIrnResponse() != null && obj.getIrnResponse().getIrn() != null) {
				Date ackdt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(obj.getIrnResponse().getAckDt());
				ClientInvoiceIrnInfo clientInvoiceIrnInfo = new ClientInvoiceIrnInfo(null,
						clientInvoice.getClientInvoiceId(), obj.getIrnResponse().getAckNo().toString(), ackdt,
						obj.getIrnResponse().getIrn(), obj.getIrnResponse().getSignedInvoice(),
						obj.getIrnResponse().getSignedQRCode(), null, null, null, true, new Date(),
						userDetail.getId().intValue(), new Date(), userDetail.getId().intValue());

				clientInvoiceIrnInfoId = clientInvoiceIrnInfoDao.saveClientInvoiceIrnInfo(clientInvoiceIrnInfo);
			}

			return new CustomResponse(Responses.SUCCESS.getCode(), clientInvoiceIrnInfoId,
					Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		}
	}

	@Override
	public CustomResponse cancelIrn(ClientIrnCancelRequest requestDTO) {
		try {

			CustomResponse validateRequest = validate.clientIrnCancel(requestDTO);
			if (validateRequest.getStatus().equals(Responses.BAD_REQUEST.getCode())) {
				return validateRequest;
			}

			ClientIrnCredential ClientIrnCredentialDbObj = clientIrnCredentialDao
					.fetchClientByCompanyIdAndSiteId(requestDTO.getUserDetail().getCompanyId(), requestDTO.getSiteId());

			if (ClientIrnCredentialDbObj == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString(),
						List.of("CLient Einvoice Credentials not exist."));

			ClientInvoiceIrnInfo clientInvoiceIrnInfo = clientInvoiceIrnInfoDao
					.fetchclientInvoiceIrnInfoByClientId(requestDTO.getClientInvoiceId());

			if (clientInvoiceIrnInfo == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString(),
						List.of("IRN not generated."));

			if (clientInvoiceIrnInfo.getCancelDate() != null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString(),
						List.of("Invoice already cancelled."));

			Site site = siteDao.fetchSiteById(requestDTO.getSiteId().longValue());
			if (ClientIrnCredentialDbObj.getIrnCancelTimeInMinutes() != null
					&& DateUtil.dateAfterAddingMinutes(new Date(), site.getTimezoneMinutes())
							.after(DateUtil.dateAfterAddingMinutes(clientInvoiceIrnInfo.getAckDate(),
									ClientIrnCredentialDbObj.getIrnCancelTimeInMinutes())))
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString(),
						List.of("Invoice cancellation time expired."));

			IrnCancelRequest cancelFeignReq = new IrnCancelRequest(clientInvoiceIrnInfo.getIrn(),
					requestDTO.getCancelReason().getId().toString(), requestDTO.getCancelRemark(),
					requestDTO.getUserDetail());

			CustomResponse response = utilityService.cancelIrnByGstHero(cancelFeignReq);
			if (response.getData() == null)
				return response;

			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			CancelIrnResponse obj = (CancelIrnResponse) mapper.convertValue(response.getData(),
					new TypeReference<CancelIrnResponse>() {
					});

			if (obj == null || (obj.getErrorDetails() != null && !obj.getErrorDetails().isEmpty())) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString(),
						obj != null ? obj.getErrorDetails() : null);
			}
			Boolean hasUpdated = false;
			if (obj.getCancelIrnResponse() != null && obj.getCancelIrnResponse().getCancelDate() != null) {
				Date cancelDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(obj.getCancelIrnResponse().getCancelDate());
				clientInvoiceIrnInfo.setCancelDate(cancelDate);
				clientInvoiceIrnInfo.setCancelReason(requestDTO.getCancelReason());
				clientInvoiceIrnInfo.setCancelRemark(requestDTO.getCancelRemark());
				clientInvoiceIrnInfo.setUpdatedOn(new Date());
				hasUpdated = clientInvoiceIrnInfoDao.updateClientInvoiceIrnInfo(clientInvoiceIrnInfo);
			}

			return new CustomResponse(Responses.SUCCESS.getCode(), hasUpdated, Responses.SUCCESS.toString(),
					obj.getErrorDetails());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
		}

	}

	@Override
	public CustomResponse exportClientInvoicePdf(ClientInvoicePdfRequest requestDTO) {
		byte[] pdfBytes = null;
		try {
			Site site = siteDao.fetchSiteById(requestDTO.getSiteId());
			CustomResponse clientInvoiceDbObj = getClientInvoiceById(requestDTO.getClientInvoiceId().get(0));

			ClientInvoiceFetchResponse clientInvoice = null;

			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			if (clientInvoiceDbObj != null && clientInvoiceDbObj.getStatus().equals(Responses.SUCCESS.getCode())
					&& clientInvoiceDbObj.getData() != null) {
				clientInvoice = (ClientInvoiceFetchResponse) mapper.convertValue(clientInvoiceDbObj.getData(),
						new TypeReference<ClientInvoiceFetchResponse>() {
						});
			} else
				return clientInvoiceDbObj;

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			Document document = new Document(PageSize.A4, 0, 0, 10, 10);
			PdfWriter.getInstance(document, bos);
			document.open();
			ClientInvoicePdfUtil.addContent(document, clientInvoice, site);
			document.close();
			pdfBytes = bos.toByteArray();
			return new CustomResponse(200, Base64.getEncoder().encodeToString(pdfBytes), "SUCCESS");
		} catch (Exception e) {
			e.printStackTrace();
			return new CustomResponse(500, null, "Something went wrong!");

		}
	}
}
