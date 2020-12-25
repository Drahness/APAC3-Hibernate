package com.catalanrenegado.tinkdatabase.abstractions;

import com.catalanrenegado.tinkdatabase.identity.ItemID;
import com.catalanrenegado.tinkdatabase.interfaces.IEntity;
import com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.EntityComponent;
import com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.EntityMod;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractTinkersItem implements IEntity {
	private static final long serialVersionUID = -2388358903407620640L;
	private ItemID id;
	private String name;
	private String description;
	private List<EntityComponent> components = new ArrayList<>();

	@Override
	public String toString() {
		return id + " " + name + " " + description + " " + components;
	}

	public AbstractTinkersItem(String id, String name, String description) {
		this.id = new ItemID(id);
		this.name = name;
		this.description = description;
	}
	public AbstractTinkersItem() {}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractTinkersItem other = (AbstractTinkersItem) obj;
		return Objects.equals(this.name, other.name) && Objects.equals(this.id, other.id)
				&& Objects.equals(this.description, other.description);
	}

	@Column
	public List<EntityComponent> getComponents() {
		return components;
	}

	public void setComponents(List<EntityComponent> components) {
		this.components = components;
	}

	@Column
	public String getDescription() {
		return description;
	}

	@Id
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = ItemID.class)
	public ItemID getCoresId() {
		return id;
	}

	@Column(name = "core_name")
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, id, description
		// ,components
		);
	}

	public synchronized void setDescription(String description) {
		this.description = description;
	}

	public synchronized void setCoresId(ItemID id) {
		this.id = id;
	}

	public synchronized void setName(String name) {
		this.name = name;
	}

	@Override@Transient
	public IEntity[] getDependentEntities() {
		IEntity[] arr = new IEntity[components.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = components.get(i);
		}
		return arr;
	}
}
