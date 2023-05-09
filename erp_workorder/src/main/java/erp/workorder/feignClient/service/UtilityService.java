package erp.workorder.feignClient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.enums.Responses;
import erp.workorder.feignClient.UtilityClient;

@Component
public class UtilityService {

	@Autowired
	private UtilityClient utilityClient;

	public String getFileS3EncodedBase64(Long fileId) {
		try {
			SearchDTO search = new SearchDTO();
			search.setId(fileId);
			CustomResponse response = utilityClient.getFileS3(search);
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			if (response != null && response.getStatus().equals(Responses.SUCCESS.getCode())
					&& response.getData() != null) {
				String encodedString = mapper.convertValue(response.getData(), String.class);
				if (encodedString.trim().isEmpty()) {
					return null;
				}
				return encodedString;
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
