package org.openmrs.module.patientmatching.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.patientmatching.DecisionTreeXML;
import org.openmrs.module.patientmatching.db.DecisionTreeXMLMetadataDao;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
public class HibernateDecisionTreeXMLMetadataDAO implements DecisionTreeXMLMetadataDao {

	private SessionFactory sessionFactory;
	protected final Log log = LogFactory.getLog(this.getClass());

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public HibernateDecisionTreeXMLMetadataDAO() {
		super();
	}

	public void saveDecisionTreeXML(DecisionTreeXML decisionTreeXML) {
		sessionFactory.getCurrentSession().saveOrUpdate(decisionTreeXML);
	}

	public DecisionTreeXML findDecisionTreeXMLById(int decisionTreeXMLId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DecisionTreeXML.class);
		criteria.add(Restrictions.eq("decisionTreeXMLId", decisionTreeXMLId));
		return (DecisionTreeXML) criteria.uniqueResult();
	}

	public List<DecisionTreeXML> getAllDecisionTreeXML() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DecisionTreeXML.class);
		return criteria.list();
	}
   	
	public void deleteDecisionTreeXMLById(int decisionTreeXMLId) {
		DecisionTreeXML decisionTreeXML = findDecisionTreeXMLById(decisionTreeXMLId);
		sessionFactory.getCurrentSession().delete(decisionTreeXML);
	}
}
