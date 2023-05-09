package erp.boq_mgmt.dao;

import java.util.List;

import erp.boq_mgmt.entity.StructureGroup;

public interface StructureGroupDao {

	Integer saveStructureGroup(StructureGroup group);

	StructureGroup fetchGroupById(Integer id);

	Boolean updateStructureGroup(StructureGroup group);

	List<StructureGroup> fetchGroups(Integer companyId, Long structureTypeId);

}
