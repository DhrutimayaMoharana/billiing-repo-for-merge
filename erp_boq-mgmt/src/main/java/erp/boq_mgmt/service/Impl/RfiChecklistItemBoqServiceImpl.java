package erp.boq_mgmt.service.Impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import erp.boq_mgmt.dao.RfiChecklistItemBoqDao;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.request.RfiChecklistItemBoqsAddUpdateRequest;
import erp.boq_mgmt.dto.request.RfiChecklistItemDeactivateRequest;
import erp.boq_mgmt.entity.RfiChecklistItemBoqs;
import erp.boq_mgmt.service.RfiChecklistItemBoqService;

@Service
@Transactional
public class RfiChecklistItemBoqServiceImpl implements RfiChecklistItemBoqService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RfiChecklistItemBoqDao rfiChecklistItemBoqDao;

	@Override
	public CustomResponse addUpdateRfiChecklistItemBoq(RfiChecklistItemBoqsAddUpdateRequest requestDTO) {
		try {

			Set<Integer> checklistItemIds = new HashSet<Integer>(Arrays.asList(requestDTO.getId()));
			List<RfiChecklistItemBoqs> dbList = rfiChecklistItemBoqDao
					.fetchRfiChecklistItemBoqsByCheckListItemIds(checklistItemIds);

			// list to save
			List<RfiChecklistItemBoqs> listToSave = new ArrayList<RfiChecklistItemBoqs>();
			for (Long boqItemId : requestDTO.getBoqItemIds()) {
				Boolean hasFound = false;
				for (RfiChecklistItemBoqs clib : dbList) {
					if (clib.getBoqItemId().equals(boqItemId)) {
						hasFound = true;
					}
				}
				if (!hasFound) {

					RfiChecklistItemBoqs clibObj = new RfiChecklistItemBoqs(null, requestDTO.getId(), boqItemId, true,
							new Date(), requestDTO.getUserDetail().getId().intValue());
					listToSave.add(clibObj);
				}
			}

			// list to deactivate
			List<RfiChecklistItemBoqs> listToDeactivate = new ArrayList<RfiChecklistItemBoqs>();
			for (RfiChecklistItemBoqs clib : dbList) {

				if (!requestDTO.getBoqItemIds().contains(clib.getBoqItemId())) {
					RfiChecklistItemBoqs clibObj = new RfiChecklistItemBoqs(clib.getId(), clib.getChecklistItemId(),
							clib.getBoqItemId(), false, new Date(), requestDTO.getUserDetail().getId().intValue());
					listToDeactivate.add(clibObj);
				}

			}

			if (listToSave != null && !listToSave.isEmpty()) {
				for (RfiChecklistItemBoqs clibObj : listToSave) {
					rfiChecklistItemBoqDao.saveRfiChecklistItemBoq(clibObj);
				}
			}

			if (listToDeactivate != null && !listToDeactivate.isEmpty()) {
				for (RfiChecklistItemBoqs clibObj : listToDeactivate) {
					rfiChecklistItemBoqDao.deactivateRfiChecklistItemBoq(clibObj);
				}

			}

			return new CustomResponse(HttpStatus.OK.value(), true, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

	@Override
	public CustomResponse deactivateRfiChecklistItemBoqsByCheckListItemId(
			RfiChecklistItemDeactivateRequest requestDTO) {
		try {

			Set<Integer> checklistItemIds = new HashSet<Integer>(Arrays.asList(requestDTO.getId()));

			List<RfiChecklistItemBoqs> dbList = rfiChecklistItemBoqDao
					.fetchRfiChecklistItemBoqsByCheckListItemIds(checklistItemIds);

			if (dbList != null && !dbList.isEmpty()) {

				for (RfiChecklistItemBoqs clib : dbList) {
					clib.setIsActive(false);
					clib.setUpdatedOn(new Date());
					clib.setUpdatedBy(requestDTO.getUserDetail().getId().intValue());

					rfiChecklistItemBoqDao.deactivateRfiChecklistItemBoq(clib);
				}

			}

			return new CustomResponse(HttpStatus.OK.value(), true, HttpStatus.OK.name());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.name());
		}
	}

}
