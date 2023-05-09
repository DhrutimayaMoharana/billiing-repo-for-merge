package erp.boq_mgmt.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import erp.boq_mgmt.dao.ChainageDao;
import erp.boq_mgmt.dao.SiteDao;
import erp.boq_mgmt.dto.ChainageDTO;
import erp.boq_mgmt.dto.CustomResponse;
import erp.boq_mgmt.dto.PaginationDTO;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.dto.response.ChainageResponseDTO;
import erp.boq_mgmt.dto.response.IdNameDTO;
import erp.boq_mgmt.entity.Chainage;
import erp.boq_mgmt.entity.ChainageTraverse;
import erp.boq_mgmt.entity.Site;
import erp.boq_mgmt.enums.ChainageSide;
import erp.boq_mgmt.enums.Responses;
import erp.boq_mgmt.service.ChainageService;
import erp.boq_mgmt.util.SetObject;

@Transactional
@Service
public class ChainageServiceImpl implements ChainageService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;
	@Autowired
	private ChainageDao chainageDao;
	@Autowired
	private SiteDao siteDao;

	public Integer getCount(SearchDTO search) {
		return chainageDao.fetchCount(search);
	}

	@Override
	public CustomResponse addChainage(ChainageDTO chainageDTO) {

		try {
			Chainage chainage = setObject.chainageDtoToEntity(chainageDTO);
			chainage.setModifiedOn(new Date());
			chainage.setModifiedBy(chainageDTO.getModifiedBy());
			Long id = chainageDao.saveChainage(chainage);
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
	public CustomResponse getChainages(SearchDTO search) {

		try {
			List<Chainage> chainages = chainageDao.fetchChainages(search);
			List<ChainageResponseDTO> obj = new ArrayList<>();
			if (chainages != null)
				chainages.forEach(chainage -> obj.add(setObject.chainageEntityToResponseDto(chainage)));
			PaginationDTO resultObj = new PaginationDTO(obj.size(), obj);
			return new CustomResponse(Responses.SUCCESS.getCode(), resultObj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public List<Chainage> checkSaveAndGetAllSiteChainages(LinkedHashSet<String> chainageNames, SearchDTO search) {

		if (search.getSiteId() == null || chainageNames == null || chainageNames.size() == 0)
			return null;
		List<Chainage> chainagesFromNames = new ArrayList<Chainage>();
		int newChainageSaveCount = 0;
		List<Chainage> chainages = chainageDao.fetchChainagesBySite(search.getSiteId());
		for (String chainageName : chainageNames) {
			boolean isPresent = false;
			for (Chainage chainage : chainages) {
				if (chainage.getName().equalsIgnoreCase(chainageName)) {
					chainagesFromNames.add(chainage);
					isPresent = true;
					break;
				}
			}
			if (!isPresent) {
				Chainage chainageToSave = new Chainage(null, chainageName,
						Integer.parseInt(chainageName.replaceAll("[^0-9]", "")), true, null, null, search.getSiteId(),
						search.getCompanyId(), new Date(), search.getUserId());
				Long id = chainageDao.forceSaveChainage(chainageToSave);
				if (id != null) {
					chainageToSave.setId(id);
					newChainageSaveCount++;
				}
				chainagesFromNames.add(chainageToSave);
			}
		}
		List<Chainage> updatedChainages = chainageDao.fetchChainages(search);
		if (newChainageSaveCount > 0) {
			for (int i = 0; i < updatedChainages.size(); i++) {
				Chainage chainage = updatedChainages.get(i);
				if (i == 0) {
					chainage.setPrevious(null);
					chainage.setNameNumericValue(Integer.parseInt(chainage.getName().replaceAll("[^0-9]", "")));
					chainage.setNext(new ChainageTraverse(updatedChainages.get(i + 1).getId()));
				} else if (i == updatedChainages.size() - 1) {
					chainage.setPrevious(new ChainageTraverse(updatedChainages.get(i - 1).getId()));
					chainage.setNameNumericValue(Integer.parseInt(chainage.getName().replaceAll("[^0-9]", "")));
					chainage.setNext(null);
				} else {
					chainage.setPrevious(new ChainageTraverse(updatedChainages.get(i - 1).getId()));
					chainage.setNameNumericValue(Integer.parseInt(chainage.getName().replaceAll("[^0-9]", "")));
					chainage.setNext(new ChainageTraverse(updatedChainages.get(i + 1).getId()));
				}
			}
			for (Chainage cc : updatedChainages) {
				chainageDao.updateChainage(cc);
			}
		}
		return updatedChainages;
	}

	@Override
	public CustomResponse getToChainagesByFrom(SearchDTO search) {

		try {
			List<Chainage> chainages = chainageDao.fetchChainages(search);
			List<ChainageResponseDTO> obj = new ArrayList<>();
			if (chainages != null) {
				boolean hasFoundFrom = false;
				for (Chainage chainage : chainages) {
					if (hasFoundFrom) {
						obj.add(setObject.chainageEntityToResponseDto(chainage));
					}
					if (chainage.getId().equals(search.getFromChainageId())) {
						hasFoundFrom = true;
					}
				}
			}
			PaginationDTO resultObj = new PaginationDTO(obj.size(), obj);
			return new CustomResponse(Responses.SUCCESS.getCode(), resultObj, Responses.SUCCESS.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

	@Override
	public CustomResponse fixChainageConnections(SearchDTO search) {

		if (search.getSiteId() == null) {
			return new CustomResponse(Responses.BAD_REQUEST.getCode(), null, "Provide Site.");
		}
		Site site = siteDao.fetchById(search.getSiteId());
		List<Chainage> chainages = chainageDao.fetchChainages(search);
		if (chainages != null && chainages.size() > 0) {
			for (int i = 0; i < chainages.size(); i++) {
				Chainage chainage = chainages.get(i);
				if (i == 0 && i != chainages.size() - 1) {
					chainage.setPrevious(null);
					chainage.setNameNumericValue(Integer.parseInt(chainage.getName().replaceAll("[^0-9]", "")));
					chainage.setNext(new ChainageTraverse(chainages.get(i + 1).getId()));
				} else if (i == (chainages.size() - 1) && i > 0) {
					chainage.setPrevious(new ChainageTraverse(chainages.get(i - 1).getId()));
					chainage.setNameNumericValue(Integer.parseInt(chainage.getName().replaceAll("[^0-9]", "")));
					chainage.setNext(null);
				} else {
					chainage.setPrevious(new ChainageTraverse(chainages.get(i - 1).getId()));
					chainage.setNameNumericValue(Integer.parseInt(chainage.getName().replaceAll("[^0-9]", "")));
					chainage.setNext(new ChainageTraverse(chainages.get(i + 1).getId()));
				}
			}
			for (Chainage cc : chainages) {
				chainageDao.updateChainage(cc);
			}
			if (chainages != null && chainages.size() > 0) {
				site.setStartChainageId(chainages.get(0).getId());
				site.setEndChainageId(chainages.get(chainages.size() - 1).getId());
			}
			siteDao.forceUpdateSite(site);
		}
		return new CustomResponse(Responses.SUCCESS.getCode(), true, Responses.SUCCESS.toString());
	}

	@Override
	public CustomResponse getChainageSides() {

		try {
			List<IdNameDTO> result = new ArrayList<>();
			for (ChainageSide enumObj : ChainageSide.values()) {
				result.add(new IdNameDTO(Long.valueOf(enumObj.ordinal()), enumObj.name()));
			}
			return new CustomResponse(Responses.SUCCESS.getCode(), result, Responses.SUCCESS.toString());
		} catch (Exception e) {
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
