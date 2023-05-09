package erp.workorder.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import erp.workorder.dao.SiteDao;
import erp.workorder.dao.UserDao;
import erp.workorder.dao.WorkorderBasicDetailDao;
import erp.workorder.dao.WorkorderDao;
import erp.workorder.dao.WorkorderTypeDao;
import erp.workorder.dto.CategoryItemDTO;
import erp.workorder.dto.ContractorBankDetailDTO;
import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.EntityStateMapDTO;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.StateTransitionDTO;
import erp.workorder.dto.WorkorderContractorDTO;
import erp.workorder.dto.WorkorderDTO;
import erp.workorder.entity.CategoryItem;
import erp.workorder.entity.ContractorBankDetail;
import erp.workorder.entity.ContractorBillingAddress;
import erp.workorder.entity.EngineState;
import erp.workorder.entity.FileEntity;
import erp.workorder.entity.Site;
import erp.workorder.entity.User;
import erp.workorder.entity.Workorder;
import erp.workorder.entity.WorkorderCategoryMapping;
import erp.workorder.entity.WorkorderContractor;
import erp.workorder.entity.WorkorderStateTransitionMapping;
import erp.workorder.entity.WorkorderType;
import erp.workorder.enums.EngineStates;
import erp.workorder.enums.EntitiesEnum;
import erp.workorder.enums.Responses;
import erp.workorder.enums.StateActions;
import erp.workorder.feignClient.service.WorkflowEngineService;
import erp.workorder.service.WorkorderBasicDetailService;
import erp.workorder.util.DateUtil;
import erp.workorder.util.SetObject;

@Service
@Transactional
public class WorkorderBasicDetailServiceImpl implements WorkorderBasicDetailService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;
	@Autowired
	private WorkorderBasicDetailDao woBasicDao;
	@Autowired
	private WorkorderDao workorderDao;
	@Autowired
	private WorkorderTypeDao woTypeDao;
	@Autowired
	SiteDao siteDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private WorkflowEngineService engineService;

	@Override
	public CustomResponse addBasicWorkorder(WorkorderDTO workorderDTO) {

		try {
			List<WorkorderCategoryMapping> wccms = new ArrayList<WorkorderCategoryMapping>();
			Workorder workorder = setObject.workorderDtoToEntity(workorderDTO);
			Date startDate = DateUtil.dateWithoutTime(workorderDTO.getStartDate());
			Date systemBillStartDate = DateUtil.dateWithoutTime(workorderDTO.getSystemBillStartDate());
			if (startDate == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide start dates.");
			}
			if (workorderDTO.getEndDate() != null) {
				Date endDate = DateUtil.dateWithoutTime(workorderDTO.getEndDate());
				if (endDate.before(startDate)) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Workorder end date must be greater than workorder end date.");
				}
				if (systemBillStartDate != null && endDate.before(systemBillStartDate)) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Workorder end date must be greater than system start date.");
				}
			}

			if (systemBillStartDate != null && startDate.after(systemBillStartDate)) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"Actual start date must be greater than system start date.");
			}
			if (systemBillStartDate != null && systemBillStartDate.after(startDate)) {
				if (workorder.getPreviousBillAmount() == null || workorder.getPreviousBillAmount() <= 0.0)
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Previous bills cumulated amount should be greater than zero.");
				if (workorder.getPreviousBillNo() == null || workorder.getPreviousBillNo() <= 0) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Last bill number should be greater than zero.");
				}
			} else {
				workorder.setSystemBillStartDate(workorderDTO.getStartDate());
				workorder.setPreviousBillAmount(0.0);
				workorder.setPreviousBillNo(0);
			}

			workorder.setModifiedOn(new Date());
			workorder.setCreatedOn(new Date());
			workorder.setCreatedBy(workorder.getModifiedBy());
			workorder.setVersion(0);
			workorder.setIsActive(true);
			workorder.setState(new EngineState(EngineStates.Draft.getValue()));
			if (workorder.getWoContractor() != null) {
				workorder.getWoContractor().setIsActive(true);
				workorder.getWoContractor().setModifiedBy(workorder.getModifiedBy());
				workorder.getWoContractor().setModifiedOn(new Date());
				workorder.getWoContractor().setContractorId(workorder.getContractor().getId());
			}
			String uniqueNo = generateWorkorderUniqueNoByTypeAndSiteCompany(workorderDTO.getSiteId(),
					workorderDTO.getType().getId(), workorderDTO.getCompanyId(), workorderDTO.getStartDate());
			workorder.setUniqueNo(uniqueNo);
			WorkorderContractor woContractor = workorder.getWoContractor();
			workorder.setWoContractor(null);
			Long id = woBasicDao.saveBasicWorkorder(workorder);
			WorkorderStateTransitionMapping woStateTransitionMap = new WorkorderStateTransitionMapping(null, id,
					EngineStates.Draft.getValue(), null, true, new Date(), workorderDTO.getModifiedBy());
			workorderDao.mapWorkorderStateTransition(woStateTransitionMap);
			woContractor.setWorkorderId(id);
			Long woContractorId = workorderDao.saveWorkorderContractor(woContractor);
			workorderDao.addWorkorderContractor(new WorkorderContractor(woContractorId), id);
			if (id != null && id.longValue() > 0L) {
				if (workorderDTO.getContractorCategories() != null) {
					for (Long categoryId : workorderDTO.getContractorCategories()) {
						wccms.add(new WorkorderCategoryMapping(null, id, categoryId, true, new Date(),
								workorder.getModifiedBy()));
					}
				}
				if (wccms != null) {
					for (WorkorderCategoryMapping wccm : wccms) {
						woBasicDao.saveWorkorderContractorCategoryMapping(wccm);
					}
				}
			}
			return new CustomResponse(Responses.SUCCESS.getCode(),
					((id != null && id.longValue() > 0L) ? id : "Already exists..."), Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	private String generateWorkorderUniqueNoByTypeAndSiteCompany(Long siteId, Integer typeId, Integer companyId,
			Date startDate) {

		Site site = siteDao.fetchSiteById(siteId);
		WorkorderType type = woTypeDao.fetchWorkorderTypeById(typeId);
		Workorder workorder = workorderDao.fetchLastWorkorderUniqueNoByTypeSiteAndCompany(siteId, typeId, companyId);
		Integer fiscalYear = DateUtil.getFiscalYearFromDate(startDate);
		String uniqueNo = site.getCompany().getShortName() + "/" + site.getCode().replaceAll("[^A-Za-z0-9]", "") + "/"
				+ type.getCode().replaceAll("[^A-Za-z0-9]", "") + "/" + fiscalYear + "-"
				+ (Integer.parseInt(fiscalYear.toString().substring(2)) + 1) + "/" + 1;
		if (workorder != null) {
			String lastUniqueNo = workorder.getUniqueNo();
			if (lastUniqueNo.contains("/")) {
				String[] breakLastUniqueNo = lastUniqueNo.split("/");
				String textNumber = breakLastUniqueNo[breakLastUniqueNo.length - 1];
				Integer intNumber = Integer.parseInt(textNumber);
				intNumber++;
				textNumber = intNumber.toString();
//				breakUniqueNo[breakUniqueNo.length - 1] = textNumber;
				String[] breakUniqueNo = uniqueNo.split("/");
				breakUniqueNo[breakUniqueNo.length - 1] = textNumber;
				uniqueNo = String.join("/", breakUniqueNo);
			}
		}
		return uniqueNo;
	}

	@Override
	public CustomResponse getBasicWorkorderById(SearchDTO search) {

		try {
			if (search.getWorkorderId() == null) {
				WorkorderDTO obj = new WorkorderDTO();
				Site site = siteDao.fetchSiteById(search.getSiteId());
				obj.setSite(setObject.siteEntityToDto(site));
				obj.setIsBoqWorkSaved(false);
				return new CustomResponse(Responses.SUCCESS.getCode(), obj,
						"No workorder exists with the provided id...");
			}
			Workorder workorder = workorderDao.fetchWorkorderById(search.getWorkorderId());
			WorkorderDTO obj = setObject.workorderEntityToDto(workorder);
			if (obj.getState().getId().equals(EngineStates.Draft.getValue())) {
				obj.setUniqueNo(obj.getState().getAlias() + "-" + obj.getUniqueNo());
			}
			if (obj.getWoContractor() != null) {
				if (obj.getWoContractor().getBankDetail() != null) {
					ContractorBankDetailDTO contractorBankDetail = obj.getWoContractor().getBankDetail();
					FileEntity cancelledCheque = workorder.getWoContractor().getBankDetail().getCancelChequeFile();
					String cancelChequeFullPath = null;
					if (cancelledCheque != null && cancelledCheque.getModule() != null) {
						String baseUrl = cancelledCheque.getModule().getBaseUrl();
						cancelChequeFullPath = baseUrl + contractorBankDetail.getCancelChequeFile().getPath();
					}
					contractorBankDetail.setCancelChequeFilePath(cancelChequeFullPath);
					obj.getWoContractor().setBankDetail(contractorBankDetail);
				}
			}
			Set<CategoryItemDTO> categoriesDTO = new LinkedHashSet<>();
			if (obj.getWoContractor() != null) {
				List<WorkorderCategoryMapping> wccms = woBasicDao.fetchWorkorderCategoriesByWorkorderId(obj.getId());
				List<CategoryItem> categories = woBasicDao.fetchActiveCategoryItemsByCompanyId(search.getCompanyId());
				if (categories != null) {
					for (WorkorderCategoryMapping wccm : wccms) {
						for (CategoryItem category : categories) {
							if (wccm.getCategoryId().equals(category.getId())) {
								categoriesDTO.add(setObject.categoryItemEntityToDto(category));
								break;
							}
						}
					}
				}
				obj.setCategories(categoriesDTO);
			}
			Site site = siteDao.fetchSiteById(obj.getSiteId());
			obj.setSite(setObject.siteEntityToDto(site));
			if (workorder.getBoqWork() != null) {
				obj.setIsBoqWorkSaved(true);
			} else {
				obj.setIsBoqWorkSaved(false);
			}

			// check expiry extendability
			List<StateTransitionDTO> transitions = engineService.getStateTransition(
					EntitiesEnum.WORKORDER.getEntityId(), search.getSiteId(), null, null, search.getCompanyId());
			User woUser = userDao.fetchUserById(workorder.getModifiedBy());

			List<EntityStateMapDTO> entityStateMaps = engineService
					.getEntityStatesByCompanyId(EntitiesEnum.WORKORDER.getEntityId(), search.getCompanyId());

			Integer renewStateId = null;

			for (EntityStateMapDTO esm : entityStateMaps) {

				if (esm.getEntityId().equals(EntitiesEnum.WORKORDER.getEntityId())
						&& esm.getStateActionId().equals(StateActions.Renew.getStateActionId())) {
					renewStateId = esm.getStateId();
				}

			}

			if (transitions != null && woUser != null && woUser.getRole() != null) {
				for (StateTransitionDTO st : transitions) {
					if (st.getStateId().equals(workorder.getState().getId())
							&& st.getRoleId().equals(woUser.getRole().getId())
							&& st.getNextRoleId().equals(search.getRoleId())) {
						if (renewStateId != null && st.getNextStateId().equals(renewStateId)
								&& workorder.getCloseType() == null) {
							obj.setIsExpiryExtendable(true);
							break;
						}
					}
				}
			}

			return new CustomResponse(Responses.SUCCESS.getCode(), obj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse updateBasicWorkorder(WorkorderDTO workorderDTO) {

		try {
			Workorder dbObj = woBasicDao.fetchWorkorderById(workorderDTO.getId());
			if (dbObj == null)
				return new CustomResponse(Responses.SUCCESS.getCode(), "Does not exist...",
						Responses.SUCCESS.toString());
			if (dbObj.getState().getId().equals(EngineStates.Issued.getValue()))
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Workorder is not in editable state.");
			Workorder woObj = setObject.updatedWorkorder(dbObj, setObject.workorderDtoToEntity(workorderDTO));

			Date startDate = DateUtil.dateWithoutTime(workorderDTO.getStartDate());
			Date systemBillStartDate = DateUtil.dateWithoutTime(workorderDTO.getSystemBillStartDate());
			if (startDate == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide start dates.");
			}
			if (workorderDTO.getEndDate() != null) {
				Date endDate = DateUtil.dateWithoutTime(workorderDTO.getEndDate());
				if (endDate.before(startDate)) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Workorder end date must be greater than workorder end date.");
				}
				if (systemBillStartDate != null && endDate.before(systemBillStartDate)) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Workorder end date must be greater than system start date.");
				}
			}
			if (systemBillStartDate != null && startDate.after(systemBillStartDate)) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
						"Actual start date must be greater than system start date.");
			}
			if (systemBillStartDate != null && systemBillStartDate.after(startDate)) {
				if (woObj.getPreviousBillAmount() == null || woObj.getPreviousBillAmount() <= 0.0)
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Previous bills cumulated amount should be greater than zero.");
				if (woObj.getPreviousBillNo() == null || woObj.getPreviousBillNo() <= 0) {
					return new CustomResponse(Responses.BAD_REQUEST.getCode(), null,
							"Last bill number should be greater than zero.");
				}
			} else {
				woObj.setSystemBillStartDate(workorderDTO.getStartDate());
				woObj.setPreviousBillAmount(0.0);
				woObj.setPreviousBillNo(0);
			}

			WorkorderContractor woContractor = dbObj.getWoContractor();
			WorkorderContractorDTO woContractorDTO = workorderDTO.getWoContractor();
			if (woContractor.getContractorId().equals(workorderDTO.getContractor().getId())) {
				woContractor.setBankDetail(new ContractorBankDetail(woContractorDTO.getBankDetail().getId()));
				woContractor
						.setBillingAddress(new ContractorBillingAddress(woContractorDTO.getBillingAddress().getId()));
			} else {
				return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), false, Responses.SUCCESS.toString());
			}
			Boolean isSaved = woBasicDao.updateBasicWorkorder(woObj);
			workorderDao.updateWorkorderContractor(woContractor);
			if (!isSaved) {
				return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), false, Responses.SUCCESS.toString());
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), isSaved, Responses.SUCCESS.toString());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
