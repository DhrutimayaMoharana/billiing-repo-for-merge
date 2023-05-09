package erp.billing.feignClient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import erp.billing.dto.CustomResponse;
import erp.billing.dto.SearchDTO;
import erp.billing.dto.response.CancelIrnResponse;
import erp.billing.dto.response.ClientIrnResponse;
import erp.billing.enums.Responses;
import erp.billing.feignClient.UtilityClient;
import erp.billing.feignClient.dto.IrnCancelRequest;
import erp.billing.feignClient.dto.IrnGenerateRequest;

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

	public ClientIrnResponse generateIrn(IrnGenerateRequest requestDTO) {
		try {
			CustomResponse response = utilityClient.generateIrn(requestDTO);
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			if (response != null && response.getStatus().equals(Responses.SUCCESS.getCode())
					&& response.getData() != null) {
				return (ClientIrnResponse) mapper.convertValue(response.getData(),
						new TypeReference<ClientIrnResponse>() {
						});

			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public CancelIrnResponse cancelIrn(IrnCancelRequest requestDTO) {
		try {
			CustomResponse response = utilityClient.cancelIrn(requestDTO);
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			if (response != null && response.getStatus().equals(Responses.SUCCESS.getCode())
					&& response.getData() != null) {
				return (CancelIrnResponse) mapper.convertValue(response.getData(),
						new TypeReference<CancelIrnResponse>() {
						});

			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public CustomResponse generateIrnByGstHero(IrnGenerateRequest requestDTO) {
		try {
			CustomResponse response = utilityClient.generateIrnByGstHero(requestDTO);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public CustomResponse cancelIrnByGstHero(IrnCancelRequest requestDTO) {
		try {
			CustomResponse response = utilityClient.cancelIrnByGstHero(requestDTO);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
