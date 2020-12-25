package com.catalanrenegado.tinkdatabase.interfaces;

import java.io.Serializable;
import javax.persistence.Transient;

import org.hibernate.Session;

import com.catalanrenegado.tinkdatabase.database.DatabaseConnection;

public interface IEntity extends Serializable {
	/**
	 * Get the id of this object
	 * 
	 * @return The id NEEDS to implement the {@link Serializable} and you can change
	 *         the return to label this as a property getter
	 */
	public Serializable getId();

	/**
	 * WARNING: Make sure to mark this getter to {@link Transient}, if not will be a
	 * mapping error.
	 * 
	 * @return the module is using
	 */
	//public IModule getModModule();

	/**
	 * If I need for the future
	 * 
	 * @return
	 */
	public default IEntity[] getRequiredEntities() {
		return new IEntity[] {};
	}

	public default IEntity[] getDependentEntities() {
		return new IEntity[] {};
	}

	/**
	 * A simple method to persist the constraints and this to a session, this need
	 * to check the existence of a Instance in the {@link Session}. See
	 * {@link IEntity#getConnection()}
	 */
	public default void persist() {
		DatabaseConnection s = this.getConnection();
		for (IEntity element : getRequiredEntities()) {
			if (element != null) {
				element.persist();
			}
		}
		if (!s.sessionContains(this) && this.needToBePersisted()) {
			s.beginTransaction();
			s.saveOrUpdate(this); // TODO Check if does something or change it to persist 
			// TinkersExtractor.log.info(this.getId() + " = ID " );
			s.commit();
		}
		for (IEntity element : getDependentEntities()) {
			if (element != null) {
				element.persist();
			}
		}
	}

	public boolean needToBePersisted();
	/**
	 * Check for the merge method, can be merged? Normally, i dont want to merge classes with hashcode id, i need to rethink that. TODO Maybe, the id will be the relation classes? And can change the other attributes.
	 * @return DatabaseConnection
	 */
	public default DatabaseConnection getConnection() {
		return new DatabaseConnection(); // TODO
	}
}
