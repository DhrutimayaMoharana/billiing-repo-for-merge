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

import erp.boq_mgmt.dao.WorkLayerBoqDao;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.request.WorkLayerBoqsAddUpdateRequest;
import erp.boq_mgmt.dto.request.WorkLayerDeactivateRequest;
import erp.boq_mgmt.entity.WorkLayerBoqs;
import erp.boq_mgmt.service.WorkLayerBoqService;

@Service
@Transactional
public class WorkLayerBoqServiceImpl implements WorkLayerBoqService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WorkLayerBoqDao workLayerBoqDao;

	@Override
	public CustomResponse addUpdateWorkLayerBoq(WorkLayerBoqsAddUpdateRequest requestDTO) {
		try {

			Set<Integer> checklistItemIds = new HashSet<Integer>(Arrays.asList(requestDTO.getId()));
			List<WorkLayerBoqs> dbList = workLayerBoqDao.fetchWorkLayerBoqsByWorkLayerIds(checklistItemIds);

			// list to save
			List<WorkLayerBoqs> listToSave = new ArrayList<WorkLayerBoqs>();
			for (Long boqItemId : requestDTO.getBoqItemIds()) {
				Boolean hasFound = false;
				for (WorkLayerBoqs wlb : dbList) {
					if (wlb.getBoqItemId().equals(boqItemId)) {
						hasFound = true;
					}
				}
				if (!hasFound) {

					WorkLayerBoqs wlbObj = new WorkLayerBoqs(null, requestDTO.getId(), boqItemId, true, new Date(),
							requestDTO.getUserDetail().getId().intValue());
					listToSave.add(wlbObj);
				}
			}

			// list to deactivate
			List<WorkLayerBoqs> listToDeactivate = new ArrayList<WorkLayerBoqs>();
			for (WorkLayerBoqs wlb : dbList) {

				if (!requestDTO.getBoqItemIds().contains(wlb.getBoqItemId())) {
					WorkLayerBoqs wlbObj = new WorkLayerBoqs(wlb.getId(), wlb.getWorkLayerId(), wlb.getBoqItemId(),
							false, new Date(), requestDTO.getUserDetail().getId().intValue());
					listToDeactivate.add(wlbObj);
				}

			}

			if (listToSave != null && !listToSave.isEmpty()) {
				for (WorkLayerBoqs wlbObj : listToSave) {
					workLayerBoqDao.saveWorkLayerBoq(wlbObj);
				}
			}

			if (listToDeactivate != null && !listToDeactivate.isEmpty()) {
				for (WorkLayerBoqs wlbObj : listToDeactivate) {
					workLayerBoqDao.deactivateWorkLayerBoq(wlbObj);
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
	public CustomResponse deactivateWorkLayerBoqsByWorkLayerId(WorkLayerDeactivateRequest requestDTO) {
		try {

			Set<Integer> checklistItemIds = new HashSet<Integer>(Arrays.asList(requestDTO.getId()));

			List<WorkLayerBoqs> dbList = workLayerBoqDao.fetchWorkLayerBoqsByWorkLayerIds(checklistItemIds);

			if (dbList != null && !dbList.isEmpty()) {

				for (WorkLayerBoqs wlb : dbList) {
					wlb.setIsActive(false);
					wlb.setUpdatedOn(new Date());
					wlb.setUpdatedBy(requestDTO.getUserDetail().getId().intValue());

					workLayerBoqDao.deactivateWorkLayerBoq(wlb);
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
