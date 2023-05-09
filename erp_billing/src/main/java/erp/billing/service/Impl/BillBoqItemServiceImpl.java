package erp.billing.service.Impl;

import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import erp.billing.dao.BillBoqItemDao;
import erp.billing.dto.CustomResponse;
import erp.billing.dto.SearchDTO;
import erp.billing.entity.BillBoqQuantityItem;
import erp.billing.entity.BillBoqQuantityItemTransac;
import erp.billing.enums.Responses;
import erp.billing.service.BillBoqItemService;
import erp.billing.util.SetObject;

@Service
@Transactional
public class BillBoqItemServiceImpl implements BillBoqItemService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SetObject setObject;

	@Autowired
	private BillBoqItemDao billItemDao;

	@Override
	public CustomResponse removeBillQuantityItem(SearchDTO search) {

		try {
			BillBoqQuantityItem billQtyItem = billItemDao.fetchBillBoqQuantityItemById(search.getBoqQtyItemId());
			BillBoqQuantityItemTransac qtyItemTransac = setObject.billBoqQuantityItemEntityToTransac(billQtyItem);
			billQtyItem.setIsActive(false);
			billQtyItem.setModifiedOn(new Date());
			billQtyItem.setModifiedBy(search.getUserId());
			billItemDao.forceUpdateBillBoqQuantityItem(billQtyItem);
			billItemDao.saveBillBoqQuantityItemTransac(qtyItemTransac);
			return new CustomResponse(Responses.SUCCESS.getCode(), null, "Removed!");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
			return new CustomResponse(Responses.PROBLEM_OCCURRED.getCode(), null,
					Responses.PROBLEM_OCCURRED.toString());
		}
	}

}
