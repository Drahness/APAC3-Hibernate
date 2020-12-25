package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.catalanrenegado.tinkdatabase.interfaces.IEntity;

@Entity
@Table(name = "PARTTYPES")
public class EntityPartType implements IEntity {
	private static final long serialVersionUID = 6674731406224696980L;
	private String name;
	private EntityMod domain;

	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	public EntityMod getDomain() {
		return domain;
	}

	public void setDomain(EntityMod domain) {
		this.domain = domain;
	}

	@SuppressWarnings("unused")
	public EntityPartType() {}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityPartType other = (EntityPartType) obj;
		return Objects.equals(other.domain, this.domain) && Objects.equals(other.name, this.name);
	}
	
	@Override
	@Id
	@Column(name = "ID")
	public String getId() {
		return name;
	}
	public void setId(String id) {
		this.name = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, domain);
	}

	@Override
	public String toString() {
		return "EntityPartType [name=" + name + ", domain=" + domain + "]";
	}

	@Override@Transient
	public IEntity[] getRequiredEntities() {
		return new IEntity[] { domain };
	}

	@Override
	public boolean needToBePersisted() {
		return true;
	}

}
