package com.catalanrenegado.tinkdatabase.database;

import java.io.Serializable;
import java.util.*;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.internal.util.config.ConfigurationException;
import org.hibernate.tool.schema.spi.CommandAcceptanceException;

public class DatabaseConnection {

	private StandardServiceRegistry ssr;
	// or
	private Configuration configObject;
	private String configurationXML;

	private SessionFactory sessionFactory;
	private Session session;

	/**
	 * 
	 * @throws ConfigurationException
	 * @throws CommandAcceptanceException if a entity has reserved keywords as
	 *                                    attributes
	 */
	public DatabaseConnection() throws ConfigurationException, CommandAcceptanceException {
		this.configurationXML = "hibernate.cfg.xml";
		this.ssr = new StandardServiceRegistryBuilder().configure().build();
	}

	public DatabaseConnection(Configuration configuration) {
		this.configObject = configuration;
		;
	}
	public DatabaseConnection(String configuration) throws ConfigurationException, CommandAcceptanceException { 
		this.configurationXML = configuration;
		this.ssr = new StandardServiceRegistryBuilder().configure(configuration).build();
	}
	public DatabaseConnection beginTransaction() {
		if (!transactionIsActive()) {
			getSession().getTransaction().begin();
		}
		return this;
	}

	public void close() {
		closeSession();
		closeSessionFactory();
	}

	public DatabaseConnection closeSession() { // NO_UCD (use default)
		session.close();
		return this;
	}

	public void closeSessionFactory() { // NO_UCD (use private)
		sessionFactory.close();
	}

	public <T> T find(Class<T> klazz, Serializable id) {
		return getSession().find(klazz,id);
	}

	/**
	 * 
	 * @param configuration
	 * @throws ConfigurationException
	 * @throws CommandAcceptanceException if a entity has reserved keywords as
	 *                                    attributes
	 */
	public <T> T get(Class<T> klazz, Serializable id) {
		return getSession().get(klazz,id);
	}

	public String getConfiguration() {
		return configurationXML;
	}

	public CriteriaBuilder getCriteriaBuilder() {
		return getSession().getCriteriaBuilder();
	}

	public Transaction getTransaction() {
		return getSession().getTransaction();
	}

	public Session getSession() {
		if (session == null) {
			this.openSession();
		}
		return session;
	}

	public synchronized SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			this.openFactory();
		}
		return sessionFactory;
	}

	public StandardServiceRegistry getStandardServiceRegistry() {
		return ssr;
	}

	public DatabaseConnection openFactory() {
		if (ssr == null) {
			sessionFactory = configObject.buildSessionFactory();
		}
		if (configObject == null) {
			sessionFactory = new MetadataSources(ssr).buildMetadata().buildSessionFactory();
		}
		return this;
	}

	public DatabaseConnection openSession() {
		session = sessionFactory.openSession();
		return this;
	}

	public void persist(Object o) { // NO_UCD (use private)
		getSession().persist(o);
	}

	public DatabaseConnection rollback() { // NO_UCD (unused code)
		getSession().getTransaction().rollback();
		return this;
	}
	public Object merge(Object o) { // NO_UCD (unused code)
		return getSession().merge(o);
	}
	/**
	 * 
	 * @param {@link Object}
	 * @return the object saved
	 * @throws ConstraintViolationException, maybe the schema is wrong? Youre saving
	 *                                       a entity without saving a relationed
	 *                                       entity?
	 * @throws NonUniqueObjectException,     we need to have only one {@link Object}
	 *                                       with the same
	 *                                       {@link javax.persistence.Id}
	 */
	public Serializable save(Object o) { // NO_UCD (use private)
		return getSession().save(o);
	}

	/**
	 * Needs an active Transacction check
	 * {@link DatabaseConnection#transactionIsActive()}
	 * 
	 * @return this, for method chaining
	 * @throws PersistenceException, reserved keywords
	 */
	public DatabaseConnection transactionCommit() {
		getSession().getTransaction().commit();
		return this;
	}
	/**
	 * Checks the transaction for a commit or a begin.
	 * 
	 * @return the state of the transaction.
	 */
	public boolean transactionIsActive() { // NO_UCD (use private)
		return getSession().getTransaction().isActive();
	}
	public void saveOrUpdate(Object o) {
		this.getSession().saveOrUpdate(o);
	}
	public void commit() {
		this.getTransaction().commit();
	}
	public boolean sessionContains(Object o) {
		return getSession().contains(o);
	}

	public Query createQuery(CriteriaQuery<?> jquery) {
		return getSession().createQuery(jquery);
	}

	public static class Builder {
		private final Set<Class<?>> annotatedClasses;
		private final Configuration conf;
		private int flags = 0;

		public Builder() {
			annotatedClasses = new HashSet<>();
			conf = new Configuration();
					/*.setProperty("hibernate.show_sql", "true")
					.setProperty("hibernate.hbm2ddl.auto", "update")
					// TODO cambiar para que la configuracion salga de un archivo de configuracion
					.setProperty("hibernate.connection.url",
							"jdbc:mysql://localhost/tinkerdata?useLegacyDatetimeCode=false&serverTimezone=UTC")
					.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
					.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57InnoDBDialect")
					.setProperty("hibernate.connection.username", "root")
					.setProperty("hibernate.connection.password", "root");*/

		}
		public DatabaseConnection build() {
			for(Class<?> klass: annotatedClasses) {
				conf.addAnnotatedClass(klass);
			}
			DatabaseConnection conn = new DatabaseConnection(conf);
			conn.getSessionFactory();
			return conn;
		}
		private static final int ANNOTATED_CLASS_SET = 128;
		public Builder addAnnotatedClass(Class<?> klass) {
			this.annotatedClasses.add(klass);
			return this;
		}
		public Builder setProperty(String property, String value) {
			conf.setProperty(property,value);
			return this;
		}
		public Builder setProperties(Properties props) {
			conf.setProperties(props);
			return this;
		}
		public Builder addAnnotatedClasses(Collection<Class<?>> klasses) {
			this.annotatedClasses.addAll(klasses);
			return this;
		}

		private static final int CONNECTION_URL_SET = 1;
		public Builder setConnectionURL(String value) {
			flags|=CONNECTION_URL_SET;
			return this.setProperty("hibernate.connection.url",value);
		}

		private static final int HBM_AUTO_SET = 2;
		public Builder setHBMAuto(boolean value) {
			flags|=HBM_AUTO_SET;
			return this.setProperty("hibernate.hbm2ddl.auto",String.valueOf(value));
		}

		private static final int HBM_DRIVER_CLASS = 4;
		public Builder setDriverClass(String value) {
			flags|=HBM_DRIVER_CLASS;
			return this.setProperty("hibernate.connection.driver_class",value);
		}

		private static final int HBM_SHOW_SQL = 8;
		public Builder setShowSQL(boolean value) {
			flags|=HBM_SHOW_SQL;
			return this.setProperty("hibernate.show_sql",String.valueOf(value));
		}

		private static final int HBM_DIALECT_SET = 16;
		public Builder setDialect(String value) {
			flags|=HBM_DIALECT_SET;
			return this.setProperty("hibernate.dialect",value);
		}

		private static final int HBM_USERNAME_SET = 32;
		public Builder setUsername(String value) {
			flags|=HBM_USERNAME_SET;
			return this.setProperty("hibernate.connection.username",value);
		}

		private static final int HBM_PASSWORD_SET = 64;
		public Builder setPassword(String value) {
			flags|=HBM_PASSWORD_SET;
			return this.setProperty("hibernate.connection.password",value);
		}
		private boolean isFlagSet(byte value)
		{
			return (this.flags & value) == value;
		}
	}
 }
