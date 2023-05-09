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

import erp.workorder.dao.WorkorderTypeDao;
import erp.workorder.dao.WorkorderTypeTncMapDao;
import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.MinimalObjectDTO;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.WoTncDTO;
import erp.workorder.dto.WoTypeTncMapRequestDTO;
import erp.workorder.dto.WoTypeTncMappingDTO;
import erp.workorder.dto.WorkorderTypesBoqsTncsResponseDTO;
import erp.workorder.entity.WoTypeTncMapping;
import erp.workorder.entity.WorkorderType;
import erp.workorder.enums.Responses;
import erp.workorder.service.WorkorderTypeTncMapService;
import erp.workorder.util.SetObject;

@Transactional
@Service
public class WorkorderTypeTncMapServiceImpl implements WorkorderTypeTncMapService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;
	@Autowired
	private WorkorderTypeTncMapDao woTypeTncDao;
	@Autowired
	private WorkorderTypeDao woTypeDao;

	@Override
	public CustomResponse getWorkorderTypesTncsCount(SearchDTO search) {

		try {
			List<WoTypeTncMapping> typeTncs = woTypeTncDao.fetchWoTypesTncs(search.getCompanyId());
			List<WorkorderType> woTypes = woTypeDao.fetchWorkorderTypes(search);
			List<MinimalObjectDTO> result = new ArrayList<MinimalObjectDTO>();
			if (woTypes != null && typeTncs != null) {
				for (WorkorderType type : woTypes) {
					MinimalObjectDTO resultObj = new MinimalObjectDTO(type.getId().longValue(), type.getName());
					int tncsCount = 0;
					for (WoTypeTncMapping obj : typeTncs) {
						if (obj.getIsActive() && obj.getWoTypeId() != null
								&& obj.getWoTypeId().equals(type.getId().longValue())) {
							tncsCount++;
						}
					}
					if (tncsCount > 0) {
						resultObj.setCount(tncsCount);
					}
					result.add(resultObj);
				}
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
	public CustomResponse getWorkorderTypeTncs(SearchDTO search) {

		try {
			List<WoTypeTncMapping> typeTncs = woTypeTncDao.fetchWoTypeTncs(search);
			WorkorderType type = woTypeDao.fetchWorkorderTypeById(search.getWoTypeId());
			List<WoTncDTO> tncs = new ArrayList<>();
			if (typeTncs != null) {
				for (WoTypeTncMapping typeTnc : typeTncs) {
					if (!typeTnc.getIsActive()) {
						continue;
					}
					WoTncDTO tnc = setObject.woTncEntityToDto(typeTnc.getWoTnc());
					tnc.setMappingId(typeTnc.getId());
					tncs.add(tnc);
				}
			}
			WorkorderTypesBoqsTncsResponseDTO result = new WorkorderTypesBoqsTncsResponseDTO(type.getId().longValue(),
					type.getName(), tncs, tncs.size());
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse mapWorkorderTypeTnc(WoTypeTncMapRequestDTO typeTncMapRequestDTO) {

		try {
			if (typeTncMapRequestDTO.getWoTypeId() == null || typeTncMapRequestDTO.getWoTncIds() == null
					|| typeTncMapRequestDTO.getWoTncIds().size() < 1 || typeTncMapRequestDTO.getCreatedBy() == null
					|| typeTncMapRequestDTO.getCompanyId() == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, Responses.BAD_REQUEST.toString());
			}
			List<WoTypeTncMappingDTO> typeTncsMapDTO = new ArrayList<>();
			for (Long woTncId : typeTncMapRequestDTO.getWoTncIds()) {
				if (woTncId != null && !woTncId.equals(0L)) {
					WoTypeTncMappingDTO obj = new WoTypeTncMappingDTO(null, typeTncMapRequestDTO.getWoTypeId(),
							new WoTncDTO(woTncId), true, new Date(), typeTncMapRequestDTO.getCreatedBy(),
							typeTncMapRequestDTO.getCompanyId());
					typeTncsMapDTO.add(obj);
				}
			}
			int nullCount = 0;
			for (WoTypeTncMappingDTO typeTncMapDTO : typeTncsMapDTO) {
				WoTypeTncMapping typeTncMap = setObject.woTypeTncMappingDtoToEntity(typeTncMapDTO);
				typeTncMap.setCreatedOn(new Date());
				typeTncMap.setIsActive(true);
				Long mapId = woTypeTncDao.mapWorkorderTypeTnc(typeTncMap);
				if (mapId == null)
					++nullCount;
			}
			if (nullCount > 0 && nullCount == typeTncsMapDTO.size()) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), true, "Already exists.");
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), true, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse removeWorkorderTypeTnc(SearchDTO search) {

		try {
			WoTypeTncMapping typeTnc = woTypeTncDao.fetchWoTypeTncById(search.getId());
			typeTnc.setIsActive(false);
			typeTnc.setCreatedBy(search.getUserId());
			woTypeTncDao.forceUpdateWoTypeTncMapping(typeTnc);
			return new CustomResponse(Responses.SUCCESS.getCode(), true, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
