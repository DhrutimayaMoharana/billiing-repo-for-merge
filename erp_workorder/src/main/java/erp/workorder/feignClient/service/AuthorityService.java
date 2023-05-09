package erp.workorder.feignClient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import erp.workorder.dto.AuthorityDTO;
import erp.workorder.dto.CustomResponse;
import erp.workorder.dto.SearchDTO;
import erp.workorder.enums.Responses;
import erp.workorder.feignClient.AuthorityClient;

@Component
public class AuthorityService {

	@Autowired
	private AuthorityClient authorityClient;

	public CustomResponse checkViewAuthority(SearchDTO search) {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		CustomResponse authResponse = authorityClient.getRoleEntityAuthorities(search);
		if (authResponse != null && authResponse.getStatus().equals(Responses.SUCCESS.getCode())) {
			AuthorityDTO permissions = mapper.convertValue(authResponse.getData(), AuthorityDTO.class);
			if (!(permissions.getView() != null && permissions.getView().equals(true)))
				return new CustomResponse(Responses.PERMISSION_DENIED.getCode(), false,
						Responses.PERMISSION_DENIED.toString());
		} else
			return new CustomResponse(Responses.PERMISSION_DENIED.getCode(), false,
					Responses.PERMISSION_DENIED.toString());
		return new CustomResponse(Responses.SUCCESS.getCode(), true, Responses.SUCCESS.toString());

	};

	public CustomResponse checkAddAuthority(SearchDTO search) {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		CustomResponse authResponse = authorityClient.getRoleEntityAuthorities(search);
		if (authResponse != null && authResponse.getStatus().equals(Responses.SUCCESS.getCode())) {
			AuthorityDTO permissions = mapper.convertValue(authResponse.getData(), AuthorityDTO.class);
			if (!(permissions.getAdd() != null && permissions.getAdd().equals(true)))
				return new CustomResponse(Responses.PERMISSION_DENIED.getCode(), false,
						Responses.PERMISSION_DENIED.toString());
		} else
			return new CustomResponse(Responses.PERMISSION_DENIED.getCode(), false,
					Responses.PERMISSION_DENIED.toString());
		return new CustomResponse(Responses.SUCCESS.getCode(), true, Responses.SUCCESS.toString());

	};

	public CustomResponse checkUpdateAuthority(SearchDTO search) {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		CustomResponse authResponse = authorityClient.getRoleEntityAuthorities(search);
		if (authResponse != null && authResponse.getStatus().equals(Responses.SUCCESS.getCode())) {
			AuthorityDTO permissions = mapper.convertValue(authResponse.getData(), AuthorityDTO.class);
			if (!(permissions.getUpdate() != null && permissions.getUpdate().equals(true)))
				return new CustomResponse(Responses.PERMISSION_DENIED.getCode(), false,
						Responses.PERMISSION_DENIED.toString());
		} else
			return new CustomResponse(Responses.PERMISSION_DENIED.getCode(), false,
					Responses.PERMISSION_DENIED.toString());
		return new CustomResponse(Responses.SUCCESS.getCode(), true, Responses.SUCCESS.toString());

	};

	public CustomResponse checkRemoveAuthority(SearchDTO search) {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		CustomResponse authResponse = authorityClient.getRoleEntityAuthorities(search);
		if (authResponse != null && authResponse.getStatus().equals(Responses.SUCCESS.getCode())) {
			AuthorityDTO permissions = mapper.convertValue(authResponse.getData(), AuthorityDTO.class);
			if (!(permissions.getRemove() != null && permissions.getRemove().equals(true)))
				return new CustomResponse(Responses.PERMISSION_DENIED.getCode(), false,
						Responses.PERMISSION_DENIED.toString());
		} else
			return new CustomResponse(Responses.PERMISSION_DENIED.getCode(), false,
					Responses.PERMISSION_DENIED.toString());
		return new CustomResponse(Responses.SUCCESS.getCode(), true, Responses.SUCCESS.toString());

	};

}
