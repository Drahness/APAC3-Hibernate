package com.catalanrenegado.tinkdatabase.identity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "Item_Cores")
public class ItemID extends IDEntity {
	private static final long serialVersionUID = -3252238933177780257L;

	public ItemID() {}
	public ItemID(String id) {
		super(id);
	}
	@Transient
	public String getItem() {
		return getId().split(":")[1];
	}
	@Transient
	public String getModID() {
		return getId().split(":")[0];
	}
	public void setModID(String str) {
		setId(str + ":" +this.getId().split(":")[1]);
	}
	public void setItem(String str) {
		setId(this.getId().split(":")[0] + ":" + str);
	}
	@Override
	public boolean needToBePersisted() {
		return true;
	}
}
