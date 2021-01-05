package com.catalanrenegado.tinkdatabase.identity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.HashMap;
import java.util.Map;

/**
 * Creada a partir de la necesidad de crear una forma de enlazar {@link com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.EntityComponent} y
 * tipos de items de {@link com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.AbstractTinkersItem}.
 *
 * Tengo que observar si esto es realmente necesario.
 */
@Entity
@Table(name = "Item_Cores")
public class ItemID extends IDEntity {
    private static final long serialVersionUID = -3252238933177780257L;

    public ItemID() {
    }

    public ItemID(String id) {
        super(id);
    }

    @Transient
    public String getItem() {
        return getId().split(":")[1];
    }

    public void setItem(String str) {
        setId(this.getId().split(":")[0] + ":" + str);
    }

    @Transient
    public String getModID() {
        return getId().split(":")[0];
    }

    public void setModID(String str) {
        setId(str + ":" + this.getId().split(":")[1]);
    }

    @Override
    public Map<String, String> getMap(boolean cascade) {
        Map<String, String> map = new HashMap<>();
        map.put("ItemID",this.getId());
        return map;
    }
}
