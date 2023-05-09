package erp.billing.service.Impl;

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

import erp.billing.dao.BillDao;
import erp.billing.dao.BillFileDao;
import erp.billing.dao.DocumentTypeDao;
import erp.billing.dto.BillFileDTO;
import erp.billing.dto.CustomResponse;
import erp.billing.dto.SearchDTO;
import erp.billing.dto.response.BillFilesResponse;
import erp.billing.entity.Bill;
import erp.billing.entity.BillFile;
import erp.billing.entity.DocumentType;
import erp.billing.entity.S3File;
import erp.billing.enums.Responses;
import erp.billing.service.BillFileService;
import erp.billing.util.CustomValidationUtil;
import erp.billing.util.SetObject;

@Service
@Transactional
public class BillFileServiceImpl implements BillFileService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DocumentTypeDao fileTypeDao;

	@Autowired
	private BillDao billDao;

	@Autowired
	private BillFileDao billFileDao;

	@Autowired
	private CustomValidationUtil validationUtil;

	@Autowired
	private SetObject setObject;

	@Override
	public CustomResponse getBillFiles(SearchDTO search) {

		try {
			CustomResponse validateRes = validationUtil.validateGetFilesRequest(search);
			if (!validateRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validateRes;
			}
			Bill bill = billDao.fetchBillById(search.getBillId());
			if (bill == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Invalid billId.");
			}
			List<BillFile> files = billFileDao.fetchBillFiles(search);
			Set<Integer> distinctFileTypes = new LinkedHashSet<>();
			if (files != null && files.size() > 0) {
				for (BillFile file : files) {
					distinctFileTypes.add(file.getType().getId());
				}
			} else {
				return new CustomResponse(Responses.NOT_FOUND.getCode(), null, "No file exists.");
			}
			List<BillFilesResponse> result = new ArrayList<>();
			if (distinctFileTypes.size() > 0) {
				for (Integer fileTypeId : distinctFileTypes) {
					BillFilesResponse typeFiles = new BillFilesResponse();
					List<BillFileDTO> resultFiles = new ArrayList<>();
					for (int i = 0; i < files.size(); i++) {
						BillFile file = files.get(i);
						if (file.getType().getId().equals(fileTypeId)) {
							BillFileDTO obj = setObject.billFilesEntityToDto(file);
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
	public CustomResponse deactivateBillFiles(SearchDTO search) {

		try {
			CustomResponse validateRes = validationUtil.validateDeactivateFilesRequest(search);
			if (!validateRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validateRes;
			}
			boolean result = false;
			for (Long billFileId : search.getIdsArr()) {
				result = billFileDao.deactivateBillFile(billFileId, search.getBillId(), search.getUserId());
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
	public CustomResponse addBillFiles(SearchDTO search) {

		try {
			CustomResponse validateRes = validationUtil.validateAddFilesRequest(search);
			if (!validateRes.getStatus().equals(Responses.SUCCESS.getCode())) {
				return validateRes;
			}
			Bill bill = billDao.fetchBillById(search.getBillId());
			if (bill == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Invalid billId.");
			}
			DocumentType fileType = fileTypeDao.fetchById(search.getFileTypeId());
			if (fileType == null) {
				return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Invalid fileTypeId.");
			}

			for (Long fileId : search.getIdsArr()) {
				BillFile file = new BillFile(null, bill.getId(), fileType, new S3File(fileId), true, new Date(),
						search.getUserId());
				billFileDao.addBillFile(file);
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), true, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
