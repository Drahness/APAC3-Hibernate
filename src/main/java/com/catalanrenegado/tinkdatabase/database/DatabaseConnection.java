package com.catalanrenegado.tinkdatabase.database;

import org.hibernate.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.internal.util.config.ConfigurationException;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.tool.schema.spi.CommandAcceptanceException;

import javax.persistence.Query;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.Metamodel;
import java.io.Serializable;
import java.util.*;

@SuppressWarnings("unused")
public class DatabaseConnection {

    private StandardServiceRegistry ssr;
    // or
    private Configuration configObject;
    private String configurationXML;

    private SessionFactory sessionFactory;
    private Session session;
    private Builder builder;

    /**
     * Finds the default xml configuration called hibernate.cfg.xml in resources folder.
     * This constructor don't build the hibernate session
     *
     * @throws ConfigurationException     if has a configuration error
     * @throws CommandAcceptanceException if a entity has reserved keywords as
     *                                    attributes
     */
    public DatabaseConnection() throws ConfigurationException, CommandAcceptanceException {
        this.configurationXML = "hibernate.cfg.xml";
        this.ssr = new StandardServiceRegistryBuilder().configure().build();
    }

    /**
     * This constructor don't build the hibernate session
     *
     * @param configuration, the configuration to build the connection.
     */
    public DatabaseConnection(Configuration configuration) {
        this.configObject = configuration;
    }

    public ClassMetadata getClassMetadata(Class<?> klass) {
        return this.getSessionFactory().getClassMetadata(klass);
    }

    public Metamodel getMetamodel() {
        return this.getSessionFactory().getMetamodel();
    }
    /**
     * This constructor don't build the hibernate session
     *
     * @param configuration, the path of the xml configuration
     * @throws ConfigurationException     if has a configuration error
     * @throws CommandAcceptanceException if a entity has reserved keywords as
     *                                    attributes
     */
    public DatabaseConnection(String configuration) throws ConfigurationException, CommandAcceptanceException {
        this.configurationXML = configuration;
        this.ssr = new StandardServiceRegistryBuilder().configure(configuration).build();
    }

    public Collection<Class<?>> getAnnotatedClasses() {
        if (builder == null) {
            return null;
        }
        return builder.annotatedClasses;
    }

    /**
     * Start a resource transaction. if its one active, this does nothing.
     *
     * @return this, for method chaining.
     */
    public DatabaseConnection beginTransaction() {
        if (!isActive()) {
            getSession().getTransaction().begin();
        }
        return this;
    }

    /**
     * Create a Query instance for the given HQL/JPQL query string.
     *
     * @param query The HQL/JPQL query
     * @return The Query instance for manipulation and execution
     */
    public org.hibernate.query.Query<?> createQuery(String query) {
        return this.getSession().createQuery(query);
    }

    /**
     * Create an instance of TypedQuery for executing a Java Persistence query language statement. The select list of the query must contain only a single item, which must be assignable to the type specified by the resultClass argument.
     *
     * @param query      a Java Persistence query string
     * @param resultType the type of the query result
     * @return the new query instance
     * @throws IllegalArgumentException if the query string is found to be invalid or if the query result is found to not be assignable to the specified type
     */
    public <T> org.hibernate.query.Query<T> createQuery(String query, Class<T> resultType) throws IllegalArgumentException {
        return this.getSession().createQuery(query, resultType);
    }

    /**
     * Closes the SessionFactory and Session, returns nothing.
     */
    public void close() {
        closeSession();
        closeSessionFactory();
    }

    /**
     * End the session by releasing the JDBC connection and cleaning up.
     */
    public void closeSession() {
        session.close();
    }

    /**
     * Destroy this SessionFactory and release all resources (caches, connection pools, etc).
     * <p>
     * It is the responsibility of the application to ensure that there are no open sessions before calling this method as the impact on those sessions is indeterminate.
     * <p>
     * No-ops if already closed.
     *
     * @throws HibernateException    Indicates an issue closing the factory.
     * @throws IllegalStateException if the entity manager factory has been closed.
     */
    public void closeSessionFactory() throws HibernateException, IllegalStateException {
        sessionFactory.close();
    }

    /**
     * Find by primary key. Search for an entity of the specified class and primary key. If the entity instance is contained in the persistence context, it is returned from there.
     *
     * @param klazz class to cast the result
     * @param id    id for the entity
     * @param <T>   class parameter
     * @return a persistent instance or null
     */
    public <T> T find(Class<T> klazz, Serializable id) {
        return getSession().find(klazz, id);
    }

    /**
     * Return the persistent instance of the given entity class with the given identifier, or null if there is no such persistent instance. (If the instance is already associated with the session, return that instance. This method never returns an uninitialized instance.)
     *
     * @param klazz The entity type
     * @param id    an identifier
     * @return a persistent instance or null
     */
    public <T> T get(Class<T> klazz, Serializable id) {
        return getSession().get(klazz, id);
    }

    /**
     * @return the configuration path. XML only, null otherwise
     */
    public String getConfiguration() {
        return configurationXML;
    }

    /**
     * Return an instance of CriteriaBuilder for the creation of CriteriaQuery objects.
     *
     * @return {@link CriteriaBuilder} instance
     * @throws IllegalStateException if the entity manager has been closed
     */
    public CriteriaBuilder getCriteriaBuilder() throws IllegalStateException {
        return getSession().getCriteriaBuilder();
    }

    /**
     * @return Get the {@link Transaction} instance associated with this session.
     * The concrete type of the returned {@link Transaction} object is determined by the hibernate.transaction_factory property.
     */
    public Transaction getTransaction() {
        return getSession().getTransaction();
    }

    /**
     * @return the current session linked to this instance. If dont have any till open a new session with {@link DatabaseConnection#openSession()}
     */
    public Session getSession() {
        if (session == null) {
            this.openSession();
        }
        return session;
    }

    /**
     * @return the session factory linked to this instance of {@link DatabaseConnection}
     */
    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            this.openFactory();
        }
        return sessionFactory;
    }

    public StandardServiceRegistry getStandardServiceRegistry() {
        return ssr;
    }

    /**
     * This method selects the building method, if in the constructor you provided an XML, will call the session factory like this, new MetadataSources(ssr).buildMetadata().buildSessionFactory()
     * <p>
     * If you used a {@link Configuration} object, this method will call the method {@link Configuration#buildSessionFactory()}
     *
     * @return this, for method chaining. To get the session see {@link DatabaseConnection#getSession()}
     */
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

    /**
     * @param o a transient instance to be made persistent
     * @throws EntityExistsException        if the entity already exists. (If the entity already exists, the EntityExistsException may be thrown when the persist operation is invoked, or the EntityExistsException or another PersistenceException may be thrown at flush or commit time.)
     * @throws TransactionRequiredException if the instance is not an entity
     * @throws IllegalArgumentException     if there is no transaction when invoked on a container-managed entity manager of that is of type PersistenceContextType.TRANSACTION
     */
    public void persist(Object o) throws EntityExistsException,
            TransactionRequiredException,
            IllegalArgumentException {
        getSession().persist(o);
    }

    /**
     * Roll back the current resource transaction.
     *
     * @return this for method chaining, can be ignored
     * @throws IllegalArgumentException if {@link DatabaseConnection#isActive()} is false
     * @throws PersistenceException     if an unexpected error condition is encountered
     */
    public DatabaseConnection rollback() throws IllegalArgumentException, PersistenceException {
        getSession().getTransaction().rollback();
        return this;
    }

    /**
     * @param o a detached instance with state to be copied
     * @return an updated persistent instance
     * @throws IllegalArgumentException     if instance is not an entity or is a removed entity
     * @throws TransactionRequiredException if there is no transaction when invoked on a container-managed entity manager of that is of type PersistenceContextType.TRANSACTION
     */
    public Object merge(Object o) throws IllegalArgumentException, TransactionRequiredException {
        return getSession().merge(o);
    }

    /**
     * @param o {@link Object} to save
     * @return the object saved
     * @throws ConstraintViolationException, maybe the schema is wrong? Youre saving
     *                                       a entity without saving a relationed
     *                                       entity?
     * @throws NonUniqueObjectException,     we need to have only one {@link Object}
     *                                       with the same
     *                                       {@link javax.persistence.Id}
     */
    public Serializable save(Object o) {
        return getSession().save(o);
    }


    /**
     * Get the Transaction instance associated with this session. The concrete type of the returned Transaction object is determined by the hibernate.transaction_factory property.
     *
     * @return boolean indicating whether transaction is in progress
     * @throws PersistenceException if an unexpected error condition is encountered
     */
    public boolean isActive() throws PersistenceException {
        return getSession().getTransaction().isActive();
    }

    /**
     * Either save(Object) or update(Object) the given instance, depending upon resolution of the unsaved-value checks (see the manual for discussion of unsaved-value checking).
     * <p>
     * This operation cascades to associated instances if the association is mapped with cascade="save-update"
     *
     * @param o a transient or detached instance containing new or updated state
     */
    public void saveOrUpdate(Object o) {
        this.getSession().saveOrUpdate(o);
    }

    /**
     * Commit the current resource transaction, writing any unflushed changes to the database.
     *
     * @throws IllegalArgumentException if isActive() is false
     * @throws RollbackException        if the commit fails
     */
    public void commit() throws IllegalArgumentException,
            RollbackException {
        this.getTransaction().commit();
    }

    /**
     * Check if the instance is a managed entity instance belonging to the current persistence context.
     *
     * @param o entity instance
     * @return boolean indicating if entity is in persistence context
     * @throws IllegalArgumentException if not an entity
     */
    public boolean sessionContains(Object o) throws IllegalArgumentException {
        return getSession().contains(o);
    }

    /**
     * Create an instance of TypedQuery for executing a criteria query.
     *
     * @param jquery a criteria query object
     * @return the new query instance
     * @throws IllegalArgumentException if the criteria query is found to be invalid
     */
    public Query createQuery(CriteriaQuery<?> jquery) throws IllegalArgumentException {
        return getSession().createQuery(jquery);
    }

    public static class Builder {
        private static final EnumSet<Flags> mustSet = EnumSet.allOf(Flags.class);
        private final EnumSet<Flags> currentSet = EnumSet.noneOf(Flags.class);
        private final Set<Class<?>> annotatedClasses;
        private final Configuration conf;

        public Builder() {
            annotatedClasses = new HashSet<>();
            conf = new Configuration();
        }

        public DatabaseConnection build() {
            if (!currentSet.containsAll(mustSet)) {
                StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + " building error.");
                sb.append("Un-setted properties:");
                if (!currentSet.contains(Flags.HBM_DIALECT_SET)) {
                    sb.append("/t- hibernate.dialect");
                }
                if (!currentSet.contains(Flags.HBM_DRIVER_CLASS)) {
                    sb.append("/t- hibernate.connection.driver_class");
                }
                if (!currentSet.contains(Flags.HBM_USERNAME_SET)) {
                    sb.append("/t- hibernate.connection.username");
                }
                if (!currentSet.contains(Flags.HBM_PASSWORD_SET)) {
                    sb.append("/t- hibernate.connection.password");
                }
                if (!currentSet.contains(Flags.CONNECTION_URL_SET)) {
                    sb.append("/t- hibernate.connection.url");
                }
                if (!currentSet.contains(Flags.ANNOTATED_CLASS_SET)) {
                    sb.append("- No annotated class set.");
                }
                throw new RuntimeException(sb.toString());
            }
            for (Class<?> klass : annotatedClasses) {
                conf.addAnnotatedClass(klass);
            }
            DatabaseConnection conn = new DatabaseConnection(conf);
            conn.builder = this;
            return conn.openFactory();
        }

        /**
         * @param klass the class annotated with {@link javax.persistence} annotations
         * @return this for method chaining
         */
        public Builder addAnnotatedClass(Class<?> klass) {
            currentSet.add(Flags.ANNOTATED_CLASS_SET);
            this.annotatedClasses.add(klass);
            return this;
        }

        /**
         * @param property the property key
         * @param value    the value to be linked to property
         * @return this, for method chaining
         */
        public Builder setProperty(String property, String value) {
            switch (property) {
                case "hibernate.connection.url":
                    currentSet.add(Flags.CONNECTION_URL_SET);
                    break;
                case "hibernate.connection.driver_class":
                    currentSet.add(Flags.HBM_DRIVER_CLASS);
                    break;
                case "hibernate.dialect":
                    currentSet.add(Flags.HBM_DIALECT_SET);
                    break;
                case "hibernate.connection.username":
                    currentSet.add(Flags.HBM_USERNAME_SET);
                    break;
                case "hibernate.connection.password":
                    currentSet.add(Flags.HBM_PASSWORD_SET);
                    break;
            }
            conf.setProperty(property, value);
            return this;
        }

        /**
         * @param props a {@link Properties} object.
         * @return this, for method chaining.
         */
        public Builder setProperties(Properties props) {
            conf.setProperties(props);
            return this;
        }

        /**
         * The classes must be annotated with {@link javax.persistence} annotations
         *
         * @param klasses a {@link Collection} of annotated classes
         * @return this, for method chaining
         */
        public Builder addAnnotatedClasses(Collection<Class<?>> klasses) {
            klasses.forEach(this::addAnnotatedClass);
            return this;
        }

        /**
         * @param value the connection string
         * @return this, for method chaining
         */
        public Builder setConnectionURL(String value) {
            return this.setProperty("hibernate.connection.url", value);
        }

        /**
         * Sets hibernate.hbm2ddl.auto to Update
         *
         * @return this, for method chaining.
         */
        public Builder setHBMAutoUpdate() {
            return this.setHBMAuto(HBMAuto.UPDATE);
        }

        /**
         * Sets hibernate.hbm2ddl.auto to Update
         *
         * @return this, for method chaining.
         */
        public Builder setHBMAutoValidate() {
            return this.setHBMAuto(HBMAuto.VALIDATE);
        }

        /**
         * Sets hibernate.hbm2ddl.auto to Update
         *
         * @return this, for method chaining.
         */
        public Builder setHBMAutoCreate() {
            return this.setHBMAuto(HBMAuto.CREATE);
        }

        /**
         * Sets hibernate.hbm2ddl.auto to Update
         *
         * @return this, for method chaining.
         */
        public Builder setHBMAutoCreateDrop() {
            return this.setHBMAuto(HBMAuto.CREATE_DROP);
        }

        /**
         * Sets hibernate.hbm2ddl.auto to Update
         *
         * @return this, for method chaining.
         */
        public Builder setHBMAutoCreateOnly() {
            return this.setHBMAuto(HBMAuto.CREATE_ONLY);
        }

        /**
         * Sets hibernate.hbm2ddl.auto to none, disables it
         *
         * @return this, for method chaining.
         */
        public Builder setHBMAutoNone() {
            return this.setHBMAuto(HBMAuto.NONE);
        }

        /**
         * @param value the value of hibernate.hbm2ddl.auto property, safe
         * @return this, for method chaining.
         */
        public Builder setHBMAuto(HBMAuto value) {
            return this.setHBMAuto(value.getValue());
        }

        /**
         * @param value the value of hibernate.hbm2ddl.auto property, unsafe use instead {@link Builder#setHBMAuto(HBMAuto)}
         * @return this, for method chaining.
         */
        public Builder setHBMAuto(String value) {
            return this.setProperty("hibernate.hbm2ddl.auto", value);
        }

        /**
         * @param value of hibernate.connection.driver_class property
         * @return this, for method chaining.
         */
        public Builder setDriverClass(String value) {
            return this.setProperty("hibernate.connection.driver_class", value);
        }

        /**
         * @param value boolean of the hibernate.show_sql property
         * @return this, for method chaining.
         */
        public Builder setShowSQL(boolean value) {
            return this.setProperty("hibernate.show_sql", String.valueOf(value));
        }

        /**
         * @param value the value of hibernate.dialect property.
         * @return this, for method chaining.
         */
        public Builder setDialect(String value) {
            return this.setProperty("hibernate.dialect", value);
        }

        /**
         * @param value the username of the database, represents hibernate.connection.username xml property
         * @return this, for method chaining.
         */
        public Builder setUsername(String value) {
            return this.setProperty("hibernate.connection.username", value);
        }

        /**
         * @param value the password of the user in the database, represents hibernate.connection.password xml property
         * @return this, for method chaining.
         */
        public Builder setPassword(String value) {
            return this.setProperty("hibernate.connection.password", value);
        }

        private enum Flags {
            ANNOTATED_CLASS_SET,
            CONNECTION_URL_SET,
            HBM_DRIVER_CLASS,
            HBM_DIALECT_SET,
            HBM_USERNAME_SET,
            HBM_PASSWORD_SET
        }

        /**
         * Enum to safe call {@link Builder#setHBMAuto(HBMAuto)}
         */
        public enum HBMAuto {
            UPDATE("update"),
            VALIDATE("validate"),
            CREATE("create"),
            CREATE_DROP("create-drop"),
            CREATE_ONLY("create-only"),
            NONE("none");
            private final String value;

            HBMAuto(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }
        }
    }
}
