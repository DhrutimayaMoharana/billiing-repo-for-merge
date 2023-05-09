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
import org.springframework.web.multipart.MultipartFile;

import erp.boq_mgmt.dao.FileUtilDao;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.FileDTO;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.FileEntity;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.service.FileUtilService;
import erp.boq_mgmt.util.FileUploadUtil;
import erp.boq_mgmt.util.SetObject;

@Transactional
@Service
public class FileUtilServiceImpl implements FileUtilService {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Autowired private FileUtilDao fileUtilDao;
	
	@Autowired private SetObject setObject;

	@Override
	public CustomResponse addFiles(SearchDTO search) {
		
		try {
			MultipartFile[] files = search.getFiles();
			String fileSavingPath = SetObject.fileSavingPath;
			String folderName = SetObject.folderName;
			List<FileDTO> resultFiles = new ArrayList<>();
			if(files != null && files.length > 0) {
				for(int i=0 ; i<files.length ; i++) {
					MultipartFile[] files11 = new MultipartFile[1];
					files11[0] = files[i];
					if (files11.length != 0){
						String url=FileUploadUtil.uploadFile(fileSavingPath, folderName, files11);
						FileEntity uploadFile = new FileEntity(null, files[i].getOriginalFilename(),
								url, new Date(), search.getUserId(), SetObject.billingModuleId);
						Long fileId = fileUtilDao.saveFile(uploadFile);
						if(fileId != null && fileId.longValue() > 0L) {
							FileDTO addedFile = setObject.fileEntityToDto(uploadFile);
							addedFile.setId(fileId);
							resultFiles.add(addedFile);
						}
					}	
				}
			}
			if(resultFiles != null && resultFiles.size() > 0) {
				return new CustomResponse(Responses.SUCCESS.getCode(),
						resultFiles, Responses.SUCCESS.toString());
			}
			return new CustomResponse(Responses.SUCCESS.getCode(),
					"No files added...", Responses.SUCCESS.toString());
		}
		catch(Exception e){
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null, Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public FileEntity addFile(SearchDTO search) {
		
		MultipartFile[] files = search.getFiles();
		String fileSavingPath = SetObject.fileSavingPath;
		if(search.getRootpath() == null) {
			search.setRootpath(fileSavingPath);
		}
		if(files == null || files.length == 0)
			return null;
		MultipartFile file = files[0];
		String folderName = SetObject.folderName;
		if(file != null) {
			MultipartFile[] files11 = new MultipartFile[1];
			files11[0] = file;
			if (files11.length != 0){
				String url=FileUploadUtil.uploadFile(search.getRootpath(), folderName, files11);
				FileEntity uploadFile = new FileEntity(null, file.getOriginalFilename(),
						url, new Date(), search.getUserId(), SetObject.billingModuleId);
				Long fileId = fileUtilDao.saveFile(uploadFile);
				if(fileId != null && fileId.longValue() > 0L) {
					uploadFile.setId(fileId);
					return uploadFile;
				}
			}
		}
		return null;
	}

}
