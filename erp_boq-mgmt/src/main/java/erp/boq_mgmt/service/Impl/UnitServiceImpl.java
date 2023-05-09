package erp.boq_mgmt.service.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import erp.boq_mgmt.dao.UnitDao;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.PaginationDTO;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.dto.UnitDTO;
import erp.boq_mgmt.dto.UnitTypeDTO;
import erp.boq_mgmt.dto.response.UnitResponseDTO;
import erp.boq_mgmt.entity.Unit;
import erp.boq_mgmt.entity.UnitType;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.service.UnitService;
import erp.boq_mgmt.util.SetObject;

@Transactional
@Service
public class UnitServiceImpl implements UnitService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;

	@Autowired
	private UnitDao unitDao;

	@Override
	public CustomResponse addUnit(UnitDTO unitDTO) {

		try {
			Unit unit = setObject.unitDtoToEntity(unitDTO);
			Long id = unitDao.saveUnit(unit);
			return new CustomResponse(Responses.SUCCESS.getCode(),
					((id != null && id.longValue() > 0L) ? id : "Already exists..."), Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse getUnits(SearchDTO search) {

		try {
			List<Unit> units = unitDao.fetchUnits(search);
			List<UnitResponseDTO> obj = new ArrayList<>();
			if (units != null)
				units.forEach(unit -> obj.add(new UnitResponseDTO(unit.getId().intValue(), unit.getName(),
						unit.getType() != null ? unit.getType().getName() : null,
						unit.getGovtUnit() != null ? unit.getGovtUnit().getId() : null,
						unit.getGovtUnit() != null ? unit.getGovtUnit().getName() : null)));
			return new CustomResponse(Responses.SUCCESS.getCode(), obj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	private Integer getCount(SearchDTO search) {
		return unitDao.fetchCount(search);
	}

	@Override
	public CustomResponse getUnitTypes(SearchDTO search) {

		try {
			List<UnitType> unitTypes = unitDao.fetchUnitTypes(search);
			List<UnitTypeDTO> obj = new ArrayList<>();
			if (unitTypes != null)
				unitTypes.forEach(type -> obj.add(setObject.unitTypeEntityToDto(type)));
			Integer count = getCount(search);
			PaginationDTO resultObj = new PaginationDTO(count, obj);
			return new CustomResponse(Responses.SUCCESS.getCode(), resultObj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
