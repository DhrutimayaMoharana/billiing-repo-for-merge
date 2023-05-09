package erp.boq_mgmt.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.service.FileUtilService;
import erp.boq_mgmt.util.SetObject;

@RequestMapping(value = "/util/file")
@RestController
@CrossOrigin
public class FileUtilController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Autowired private FileUtilService fileUtilService;
	
	@RequestMapping(value = "/file", headers = "content-type=multipart/*", method = RequestMethod.POST)
	public ResponseEntity<?> addFile(@RequestParam(name = "file") MultipartFile file, @ModelAttribute SearchDTO search,
			HttpServletRequest request) throws Exception {

		LOGGER.info("Executing addFile...");
		
		search.setRootpath(SetObject.fileSavingPath);
		MultipartFile[] files = {file};
		search.setFiles(files);
		CustomResponse response = fileUtilService.addFiles(search);
		
		LOGGER.info("Executed addFile...");
		return ResponseEntity.ok(response);
		
	}
	
	@RequestMapping(value = "/files", headers = "content-type=multipart/*", method = RequestMethod.POST)
	public ResponseEntity<?> addFiles(@RequestParam(name = "files") MultipartFile[] files, @ModelAttribute SearchDTO search,
			HttpServletRequest request) throws Exception {

		LOGGER.info("Executing addFiles...");
		
		search.setRootpath(SetObject.fileSavingPath);
		search.setFiles(files);
		CustomResponse response = fileUtilService.addFiles(search);
		
		LOGGER.info("Executed addFiles...");
		return ResponseEntity.ok(response);
		
	}

}
