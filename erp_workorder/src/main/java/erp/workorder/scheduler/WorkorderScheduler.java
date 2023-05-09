package erp.workorder.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import erp.workorder.service.WorkorderService;

@Component
@EnableScheduling
public class WorkorderScheduler {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WorkorderService workorderService;

	@Scheduled(cron = "0 15 0 * * ?")
	public void autoEmploymentTypeTransition() {
		workorderService.triggerWorkorderBeforeExpiryMail();
		LOGGER.info("Event for Workorder Before Expiry Mail");
	}

}
