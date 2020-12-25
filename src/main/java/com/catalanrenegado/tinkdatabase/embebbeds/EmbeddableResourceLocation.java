package com.catalanrenegado.tinkdatabase.embebbeds;

import java.beans.Transient;

import javax.persistence.Column;
import javax.persistence.Embeddable;

//import net.minecraft.util.ResourceLocation;

@Embeddable@Deprecated
public class EmbeddableResourceLocation {
	//private transient ResourceLocation rs;
	private String name;
	
	public EmbeddableResourceLocation() {
		
	}
	/*public EmbeddableResourceLocation(ResourceLocation rs) {
		this.rs = rs;
		name = rs.toString();
	}
	@Transient
	public ResourceLocation getRs() {
		return rs;
	}
		public void setRs(ResourceLocation rs) {
		this.rs = rs;
	}*/
	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		//result = prime * result + ((rs == null) ? 0 : rs.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmbeddableResourceLocation other = (EmbeddableResourceLocation) obj;
		if (name == null) {
			return other.name == null;
		} else return name.equals(other.name);
		/*if (rs == null) {
			if (other.rs != null)
				return false;
		} else if (!rs.equals(other.rs))
			return false;*/
	}
}
