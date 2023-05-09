package erp.workorder.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import erp.workorder.dao.WorkorderDao;
import erp.workorder.dao.WorkorderMaterialConfigDao;
import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.MaterialGroupDTO;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.WorkorderMaterialConfigDTO;
import erp.workorder.entity.MaterialGroup;
import erp.workorder.entity.Workorder;
import erp.workorder.entity.WorkorderMaterialConfig;
import erp.workorder.enums.EngineStates;
import erp.workorder.enums.Responses;
import erp.workorder.service.WorkorderMaterialConfigService;
import erp.workorder.util.SetObject;

@Service
@Transactional
public class WorkorderMaterialConfigServiceImpl implements WorkorderMaterialConfigService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WorkorderMaterialConfigDao woMaterialConfigDao;

	@Autowired
	private SetObject setObject;

	@Autowired
	private WorkorderDao workorderDao;

	@Override
	public CustomResponse getMaterialGroups(SearchDTO search) {

		try {
			List<MaterialGroupDTO> resultMaterials = new ArrayList<>();
			List<MaterialGroup> materialGroups = woMaterialConfigDao.fetchMaterialGroups(search);
			if (materialGroups != null) {
				for (MaterialGroup materialGroup : materialGroups) {
					MaterialGroupDTO materialGroupDTO = setObject.materialGroupEntityToDto(materialGroup);
					resultMaterials.add(materialGroupDTO);
				}
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), resultMaterials, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse issueMaterial(WorkorderMaterialConfigDTO woMaterialIssueDTO) {

		try {
			WorkorderMaterialConfig woMaterialIssue = setObject.workorderMaterialConfigDtoToEntity(woMaterialIssueDTO);
			Workorder workorder = workorderDao.fetchDetachedWorkorderById(woMaterialIssue.getWorkorderId());
			if (!workorder.getState().getId().equals(EngineStates.Draft.getValue()))
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Workorder is not in draft state.");
			woMaterialIssue.setModifiedOn(new Date());
			woMaterialIssue.setIsActive(true);
			Long id = woMaterialConfigDao.issueMaterial(woMaterialIssue);
			return new CustomResponse(Responses.SUCCESS.getCode(), id, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse updateIssuedMaterial(WorkorderMaterialConfigDTO materialConfig) {

		try {
			WorkorderMaterialConfig issuedMaterial = setObject.workorderMaterialConfigDtoToEntity(materialConfig);
			Workorder workorder = workorderDao.fetchDetachedWorkorderById(issuedMaterial.getWorkorderId());
			if (!workorder.getState().getId().equals(EngineStates.Draft.getValue()))
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Workorder is not in draft state.");
			issuedMaterial.setModifiedOn(new Date());
			Boolean result = woMaterialConfigDao.updateMaterialConfig(issuedMaterial);
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse deactivateIssuedMaterial(SearchDTO search) {

		try {
			WorkorderMaterialConfig issuedMaterial = woMaterialConfigDao.fetchMaterialConfigById(search.getId());
			Workorder workorder = workorderDao.fetchDetachedWorkorderById(issuedMaterial.getWorkorderId());
			if (!workorder.getState().getId().equals(EngineStates.Draft.getValue()))
				return new CustomResponse(Responses.FORBIDDEN.getCode(), null, "Workorder is not in draft state.");
			issuedMaterial.setModifiedBy(search.getUserId());
			issuedMaterial.setModifiedOn(new Date());
			issuedMaterial.setIsActive(false);
			Boolean result = woMaterialConfigDao.updateMaterialConfig(issuedMaterial);
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
