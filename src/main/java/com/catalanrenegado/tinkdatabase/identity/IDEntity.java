package com.catalanrenegado.tinkdatabase.identity;

import java.util.Objects;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.catalanrenegado.tinkdatabase.TinkersExtractor;
import com.catalanrenegado.tinkdatabase.interfaces.IEntity;

@MappedSuperclass
abstract class IDEntity implements IEntity {

	private static final long serialVersionUID = 4211979424330931105L;
	private String id;

	protected IDEntity() {
		id = "";
	}

	protected IDEntity(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IDEntity other = (IDEntity) obj;
		return Objects.equals(other.id, this.id);
	}

	@Id
	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return id;
	}
	public boolean canBeMerged() {
		return false;
	}
	public void merge(IEntity o) {}
}
