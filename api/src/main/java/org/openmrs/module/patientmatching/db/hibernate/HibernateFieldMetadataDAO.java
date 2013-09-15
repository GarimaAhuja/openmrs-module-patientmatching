package org.openmrs.module.patientmatching.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.cfg.Configuration;

import java.util.List;
import org.openmrs.module.patientmatching.HibernateConnection;

@Transactional
public class HibernateFieldMetadataDAO{

	private SessionFactory sessionFactory;
	protected final Log log = LogFactory.getLog(this.getClass());

	public HibernateFieldMetadataDAO() {
	}

	public java.util.List getField(String tableName, String columnName)
	{
		HibernateConnection hc = new HibernateConnection();
		sessionFactory = hc.getSessionFactory();
		Session session = sessionFactory.openSession();
		Query query = session.createSQLQuery("select "+columnName+" from "+tableName);
		List result = query.list();
		session.close();
		return result;
	}
	public java.util.List getFieldWhere(String tableName, String columnName, String whereColumn, String whereValue)
	{
		HibernateConnection hc = new HibernateConnection();
		sessionFactory = hc.getSessionFactory();
		Session session = sessionFactory.openSession();
		Query query = session.createSQLQuery("select "+columnName+" from "+tableName+" where "+whereColumn+"="+whereValue);
		List result = query.list();
		session.close();
		return result;
	}

}
