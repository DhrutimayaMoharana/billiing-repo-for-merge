package erp.boq_mgmt.dao;

import java.util.List;

import erp.boq_mgmt.entity.ProjectPlanBoq;
import erp.boq_mgmt.entity.ProjectPlanBoqDistribution;
import erp.boq_mgmt.enums.WorkType;

public interface ProjectPlanBoqDao {

	Long saveProjectPlanBoq(ProjectPlanBoq projectPlanBoq);

	ProjectPlanBoq fetchProjectPlanBoqByWorkTypeAndBoqId(WorkType workType, Long boqId, Long structureTypeId,
			Long structureId);

	List<ProjectPlanBoqDistribution> fetchProjectPlanBoqDistributionByProjectPlanBoqId(Long id);

	Boolean updateProjectPlantBoqDistribution(ProjectPlanBoqDistribution projectPlanBoqDistribution);

	Long saveProjectPlantBoqDistribution(ProjectPlanBoqDistribution projectPlanBoqDistribution);

}
