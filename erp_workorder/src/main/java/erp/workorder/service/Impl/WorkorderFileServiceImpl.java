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

import erp.workorder.dao.DocumentTypeDao;
import erp.workorder.dao.WorkorderDao;
import erp.workorder.dao.WorkorderFileDao;
import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.dto.WorkorderFileDTO;
import erp.workorder.dto.WorkorderFilesResponse;
import erp.workorder.entity.DocumentType;
import erp.workorder.entity.S3File;
import erp.workorder.entity.Workorder;
import erp.workorder.entity.WorkorderFile;
import erp.workorder.enums.Responses;
import erp.workorder.service.WorkorderFileService;
import erp.workorder.util.CustomValidationUtil;
import erp.workorder.util.SetObject;

@Service
@Transactional
public class WorkorderFileServiceImpl implements WorkorderFileService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DocumentTypeDao fileTypeDao;

	@Autowired
	private WorkorderDao woDao;

	@Autowired
	private WorkorderFileDao woFileDao;

	@Autowired
	private CustomValidationUtil validationUtil;

	@Autowired
	private SetObject setObject;

	@Override
	public CustomResponse getWorkorderFiles(SearchDTO search) {

		try {
			CustomResponse validateRes = validationUtil.validateWorkorderFilesRequest(search);
			if (!validateRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validateRes;
			}
			Workorder workorder = woDao.fetchWorkorderById(search.getWorkorderId());
			if (workorder == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Invalid workorderId.");
			}
			List<WorkorderFile> files = woFileDao.fetchWorkorderFiles(search);
			Set<Integer> distinctFileTypes = new LinkedHashSet<>();
			if (files != null && files.size() > 0) {
				for (WorkorderFile file : files) {
					distinctFileTypes.add(file.getType().getId());
				}
			} else {
				return new CustomResponse(Responses.NOT_FOUND.getCode(), null, "No file exists.");
			}
			List<WorkorderFilesResponse> result = new ArrayList<>();
			if (distinctFileTypes.size() > 0) {
				for (Integer fileTypeId : distinctFileTypes) {
					WorkorderFilesResponse typeFiles = new WorkorderFilesResponse();
					List<WorkorderFileDTO> resultFiles = new ArrayList<>();
					for (int i = 0; i < files.size(); i++) {
						WorkorderFile file = files.get(i);
						if (file.getType().getId().equals(fileTypeId)) {
							WorkorderFileDTO obj = setObject.workorderFilesEntityToDto(file);
							if (resultFiles.size() == 0) {
								typeFiles.setFileType(obj.getType());
							}
							obj.setType(null);
							resultFiles.add(obj);
						}
					}
					typeFiles.setFiles(resultFiles);
					if (resultFiles.size() > 0)
						result.add(typeFiles);
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
	public CustomResponse addWorkorderFiles(SearchDTO search) {

		try {
			CustomResponse validateRes = validationUtil.validateAddFilesRequest(search);
			if (!validateRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validateRes;
			}
			Workorder workorder = woDao.fetchWorkorderById(search.getWorkorderId());
			if (workorder == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Invalid workorderId.");
			}
			DocumentType fileType = fileTypeDao.fetchById(search.getFileTypeId());
			if (fileType == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Invalid fileTypeId.");
			}

			for (Long fileId : search.getIdsArr()) {
				WorkorderFile file = new WorkorderFile(null, workorder.getId(), fileType, new S3File(fileId), true,
						new Date(), search.getUserId());
				woFileDao.addWorkorderFile(file);
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
	public CustomResponse deactivateWorkorderFiles(SearchDTO search) {

		try {
			CustomResponse validateRes = validationUtil.validateDeactivateFilesRequest(search);
			if (!validateRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validateRes;
			}
			boolean result = false;
			for (Long woFileId : search.getIdsArr()) {
				result = woFileDao.deactivateWorkorderFile(woFileId, search.getWorkorderId(), search.getUserId());
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
