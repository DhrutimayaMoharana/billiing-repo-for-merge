package erp.boq_mgmt.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import erp.boq_mgmt.dao.StructureDocumentDao;
import erp.boq_mgmt.dto.SearchDTO;
import erp.boq_mgmt.entity.StructureDocument;
import erp.boq_mgmt.entity.StructureDocumentFile;
import erp.boq_mgmt.entity.StructureDocumentFileVersion;
import erp.boq_mgmt.entity.StructureDocumentStateTransition;
import erp.boq_mgmt.entity.StructureDocumentVersion;

@Repository
public class StructureDocumentDaoImpl implements StructureDocumentDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<StructureDocumentFile> fetchFiles(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureDocumentFile> cr = cb.createQuery(StructureDocumentFile.class);
		Root<StructureDocumentFile> root = cr.from(StructureDocumentFile.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (search.getStructureId() != null) {
			andPredicates.add(cb.equal(root.get("document").get("structureId"), search.getStructureId()));
			andPredicates.add(cb.equal(root.get("document").get("isActive"), true));
		}
		if (search.getStructureDocumentId() != null)
			andPredicates.add(cb.equal(root.get("document").get("id"), search.getStructureDocumentId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<StructureDocumentFile> query = session.createQuery(cr);
		List<StructureDocumentFile> results = query.getResultList();
		return results;
	}

	@Override
	public List<StructureDocument> fetchDocuments(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureDocument> cr = cb.createQuery(StructureDocument.class);
		Root<StructureDocument> root = cr.from(StructureDocument.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (search.getStructureId() != null) {
			andPredicates.add(cb.equal(root.get("structureId"), search.getStructureId()));
		}
		if (search.getStructureDocumentId() != null)
			andPredicates.add(cb.equal(root.get("id"), search.getStructureDocumentId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<StructureDocument> query = session.createQuery(cr);
		List<StructureDocument> results = query.getResultList();
		return results;
	}

	@Override
	public List<StructureDocumentStateTransition> fetchStateMappings(Long structureDocumentId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureDocumentStateTransition> cr = cb.createQuery(StructureDocumentStateTransition.class);
		Root<StructureDocumentStateTransition> root = cr.from(StructureDocumentStateTransition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("structureDocumentId"), structureDocumentId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<StructureDocumentStateTransition> query = session.createQuery(cr);
		List<StructureDocumentStateTransition> results = query.getResultList();
		return results;
	}

	@Override
	public List<StructureDocumentVersion> fetchDocumentVersions(Long structureDocumentId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureDocumentVersion> cr = cb.createQuery(StructureDocumentVersion.class);
		Root<StructureDocumentVersion> root = cr.from(StructureDocumentVersion.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("structureDocumentId"), structureDocumentId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.orderBy(cb.desc(root.get("version")));
		cr.distinct(true).select(root).where(andPredicate);
		Query<StructureDocumentVersion> query = session.createQuery(cr);
		List<StructureDocumentVersion> results = query.getResultList();
		return results;
	}

	@Override
	public List<StructureDocumentFileVersion> fetchDocumentVersionFiles(SearchDTO search) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureDocumentFileVersion> cr = cb.createQuery(StructureDocumentFileVersion.class);
		Root<StructureDocumentFileVersion> root = cr.from(StructureDocumentFileVersion.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates
				.add(cb.equal(root.get("documentVersion").get("structureDocumentId"), search.getStructureDocumentId()));
		andPredicates.add(cb.equal(root.get("documentVersion").get("version"), search.getVersion()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<StructureDocumentFileVersion> query = session.createQuery(cr);
		List<StructureDocumentFileVersion> results = query.getResultList();
		return results;
	}

	@Override
	public Long saveDocument(StructureDocument doc) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureDocument> cr = cb.createQuery(StructureDocument.class);
		Root<StructureDocument> root = cr.from(StructureDocument.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("siteId"), doc.getSiteId()));
		andPredicates.add(cb.equal(root.get("structureId"), doc.getStructureId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("type").get("id"), doc.getType().getId()));
		if (doc.getSubtype() != null)
			andPredicates.add(cb.equal(root.get("subtype").get("id"), doc.getSubtype().getId()));
		else
			andPredicates.add(cb.isNull(root.get("subtype")));

		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<StructureDocument> query = session.createQuery(cr);
		List<StructureDocument> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return null;
		}
		Long id = (Long) session.save(doc);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public Long saveDocFile(StructureDocumentFile docFile) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureDocumentFile> cr = cb.createQuery(StructureDocumentFile.class);
		Root<StructureDocumentFile> root = cr.from(StructureDocumentFile.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("document").get("id"), docFile.getDocument().getId()));
		andPredicates.add(cb.equal(root.get("file").get("id"), docFile.getFile().getId()));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<StructureDocumentFile> query = session.createQuery(cr);
		List<StructureDocumentFile> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return null;
		}
		Long id = (Long) session.save(docFile);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public StructureDocument fetchDocumentById(Long id) {

		Session session = entityManager.unwrap(Session.class);
		StructureDocument doc = session.get(StructureDocument.class, id);
		return doc;
	}

	@Override
	public Boolean updateDocument(StructureDocument doc) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureDocument> cr = cb.createQuery(StructureDocument.class);
		Root<StructureDocument> root = cr.from(StructureDocument.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.notEqual(root.get("id"), doc.getId()));
		andPredicates.add(cb.equal(root.get("siteId"), doc.getSiteId()));
		andPredicates.add(cb.equal(root.get("structureId"), doc.getStructureId()));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		andPredicates.add(cb.equal(root.get("type").get("id"), doc.getType().getId()));
		if (doc.getSubtype() != null)
			andPredicates.add(cb.equal(root.get("subtype").get("id"), doc.getSubtype().getId()));
		else
			andPredicates.add(cb.isNull(root.get("subtype")));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<StructureDocument> query = session.createQuery(cr);
		List<StructureDocument> results = query.getResultList();
		if (results != null && results.size() > 0) {
			return false;
		}
		session.update(doc);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public void deactivateDocFile(Long documentId, Long fileId, Long userId) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureDocumentFile> cr = cb.createQuery(StructureDocumentFile.class);
		Root<StructureDocumentFile> root = cr.from(StructureDocumentFile.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		andPredicates.add(cb.equal(root.get("file").get("id"), fileId));
		andPredicates.add(cb.equal(root.get("document").get("id"), documentId));
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<StructureDocumentFile> query = session.createQuery(cr);
		List<StructureDocumentFile> results = query.getResultList();
		if (results != null && results.size() > 0) {
			StructureDocumentFile file = results.get(0);
			file.setModifiedOn(new Date());
			file.setModifiedBy(userId);
			file.setIsActive(false);
			session.update(file);
			session.flush();
			session.clear();
		}
		session.clear();
	}

	@Override
	public Boolean deactivateDocument(Long structureDocumentId, Long userId) {

		Session session = entityManager.unwrap(Session.class);
		StructureDocument doc = session.get(StructureDocument.class, structureDocumentId);
		doc.setIsActive(false);
		doc.setModifiedBy(userId);
		doc.setModifiedOn(new Date());
		session.update(doc);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public void forceUpdateDocFile(StructureDocumentFile docFile) {

		Session session = entityManager.unwrap(Session.class);
		session.update(docFile);
		session.flush();
		session.clear();

	}

	@Override
	public Boolean forceUpdateDocument(StructureDocument doc) {

		Session session = entityManager.unwrap(Session.class);
		session.update(doc);
		session.flush();
		session.clear();
		return true;
	}

	@Override
	public void mapDocumentStateTransition(StructureDocumentStateTransition obj) {

		Session session = entityManager.unwrap(Session.class);
		session.save(obj);
		session.flush();
		session.clear();
	}

	@Override
	public Long forceSaveStructureDocumentVersion(StructureDocumentVersion docVersion) {

		Session session = entityManager.unwrap(Session.class);
		Long id = (Long) session.save(docVersion);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public Long forceSaveStructureDocFileVersion(StructureDocumentFileVersion docFileVersion) {

		Session session = entityManager.unwrap(Session.class);
		Long id = (Long) session.save(docFileVersion);
		session.flush();
		session.clear();
		return id;
	}

	@Override
	public List<StructureDocumentStateTransition> fetchStateMappingsByStructureDocumentIds(
			Set<Long> distinctStructureDocsIds) {

		Session session = entityManager.unwrap(Session.class);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<StructureDocumentStateTransition> cr = cb.createQuery(StructureDocumentStateTransition.class);
		Root<StructureDocumentStateTransition> root = cr.from(StructureDocumentStateTransition.class);
		List<Predicate> andPredicates = new ArrayList<Predicate>();
		if (distinctStructureDocsIds != null) {
			In<Long> inClause = cb.in(root.get("structureDocumentId"));
			for (Long id : distinctStructureDocsIds) {
				inClause.value(id);
			}
			andPredicates.add(inClause);
		}
		andPredicates.add(cb.equal(root.get("isActive"), true));
		Predicate andPredicate = cb.and(andPredicates.toArray(new Predicate[] {}));
		cr.distinct(true).select(root).where(andPredicate);
		Query<StructureDocumentStateTransition> query = session.createQuery(cr);
		List<StructureDocumentStateTransition> results = query.getResultList();
		return results;
	}

}
