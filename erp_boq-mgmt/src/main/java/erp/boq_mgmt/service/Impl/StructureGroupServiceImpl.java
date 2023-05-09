package erp.boq_mgmt.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import erp.boq_mgmt.dao.StructureGroupDao;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.request.StructureGroupAddUpdateRequest;
import erp.boq_mgmt.dto.response.StructureGroupGetResponse;
import erp.boq_mgmt.entity.StructureGroup;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.service.StructureGroupService;
import erp.boq_mgmt.util.CustomValidationUtil;

@Service
@Transactional
public class StructureGroupServiceImpl implements StructureGroupService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CustomValidationUtil validationUtil;

	@Autowired
	private StructureGroupDao structureGroupDao;

	@Override
	public CustomResponse addUpdateStructureGroup(StructureGroupAddUpdateRequest groupRequest) {

		try {
			CustomResponse response = null;
			if (groupRequest.getId() == null)
				response = validationUtil.validateStructureGroupAddRequest(groupRequest);
			else
				response = validationUtil.validateStructureGroupUpdateRequest(groupRequest);
			if (!response.getStatus().equals(Responses.SUCCESS.getCode()))
				return response;

			if (groupRequest.getId() == null) {
				StructureGroup group = new StructureGroup(null, groupRequest.getName().trim(),
						groupRequest.getDescription(), groupRequest.getStructureTypeId(), true, new Date(),
						groupRequest.getUserDetail().getId(), groupRequest.getUserDetail().getCompanyId());

				Integer id = structureGroupDao.saveStructureGroup(group);
				return new CustomResponse(Responses.SUCCESS.getCode(),
						((id != null && id > 0) ? "Added." : "Already exists."), Responses.SUCCESS.toString());
			} else {
				StructureGroup group = structureGroupDao.fetchGroupById(groupRequest.getId());
				if (group == null)
					return new CustomResponse(Responses.Not_Found.getCode(), null, "Group not found.");
				group.setName(groupRequest.getName().trim());
				group.setDescription(groupRequest.getDescription());
				Boolean isUpdated = structureGroupDao.updateStructureGroup(group);
				return new CustomResponse(Responses.SUCCESS.getCode(),
						isUpdated ? "Updated." : "Another group exists with same name.", Responses.SUCCESS.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getStructureGroupById(Integer groupId) {

		try {
			StructureGroup group = structureGroupDao.fetchGroupById(groupId);
			if (group == null)
				return new CustomResponse(Responses.Not_Found.getCode(), null, "Group not found.");
			StructureGroupGetResponse resultObj = new StructureGroupGetResponse(group.getId(), group.getName(),
					group.getDescription(), group.getStructureType().getId(), group.getStructureType().getName());
			return new CustomResponse(Responses.SUCCESS.getCode(), resultObj, Responses.SUCCESS.toString());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getStructureGroups(Integer companyId, Long structureTypeId) {

		try {
			if (companyId == null)
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide user company.");

			List<StructureGroup> groups = structureGroupDao.fetchGroups(companyId, structureTypeId);

			List<StructureGroupGetResponse> resultObj = new ArrayList<>();
			if (groups != null) {
				for (StructureGroup group : groups) {
					resultObj.add(new StructureGroupGetResponse(group.getId(), group.getName(), group.getDescription(),
							group.getStructureTypeId(), group.getStructureType().getName()));
				}
			}

			return new CustomResponse(Responses.SUCCESS.getCode(), resultObj, Responses.SUCCESS.toString());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse deactivateStructureGroup(Integer groupId) {

		try {
			StructureGroup group = structureGroupDao.fetchGroupById(groupId);
			if (group == null)
				return new CustomResponse(Responses.Not_Found.getCode(), null, "Group not found.");
			group.setIsActive(false);
			structureGroupDao.updateStructureGroup(group);
			return new CustomResponse(Responses.SUCCESS.getCode(), "Removed.", Responses.SUCCESS.toString());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
