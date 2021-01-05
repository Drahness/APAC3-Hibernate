package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities;

import com.catalanrenegado.tinkdatabase.Leer;
import com.catalanrenegado.tinkdatabase.Utils;
import com.catalanrenegado.tinkdatabase.database.DatabaseConnection;
import com.catalanrenegado.tinkdatabase.interfaces.IEntity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "SPRITEREFERENCES")
@SuppressWarnings({"unchecked","unused"})
public class SpriteTConstructBase implements IEntity {
    private static final long serialVersionUID = 2526766613977136031L;
    private Integer id;
    private EntityModifier modifier = null;
    private EntityMod modId;
    private String module;
    private String category;
    private String itemBase;

    @SuppressWarnings("unused")
    public SpriteTConstructBase() {
    }

    @Override
    @Id
    public Integer getId() {
        if (id == null) {
            id = Objects.hash(modId, module, category, itemBase);
        }
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    public EntityMod getMod() {
        return modId;
    }

    public void setMod(EntityMod mod) {
        this.modId = mod;
    }

    @Column
    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @Column
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Column
    public String getItem() {
        return itemBase;
    }

    public void setItem(String item) {
        this.itemBase = item;
    }

    @Transient
    @Override
    public int hashCode() {
        return Objects.hash(modId, module, category, itemBase,/* entity_Component,*/modifier);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SpriteTConstructBase other = (SpriteTConstructBase) obj;
        return Objects.equals(modId, other.modId)
                && Objects.equals(module, other.module)
                && Objects.equals(modifier, other.modifier)
                && Objects.equals(category, other.category)
                && Objects.equals(itemBase, other.itemBase);

    }

    @Override
    public String toString() {
        return modId + ":" + module + "/" + category + "/" + itemBase;
    }

    @ManyToOne
    public EntityModifier getModifier() {
        return modifier;
    }

    public void setModifier(EntityModifier modifier) {
        this.modifier = modifier;
    }

    @Override
    public Map<String, String> getMap(boolean cascade) {
        Map<String, String> map = new HashMap<>();
        map.put("Id",String.valueOf(id));
        map.put("Modifier",String.valueOf(modifier));
        map.put("Mod",modId.getId().toString());
        map.put("Module",module);
        map.put("Category",category);
        map.put("ItemBase",itemBase);
        return map;
    }

    @Override
    public void changeAttributes(DatabaseConnection dbconn, boolean cascade) { // TODO
        if (this.id == null) {
            boolean existenceCheck;
            int identifier;
            do {
                identifier = Leer.leerEntero(String.format("<TinkersExtractor#%s> Identificador: ", this.getClass().getSimpleName()));
                if (identifier == 0) {
                    identifier = this.id;
                }
                existenceCheck = !Utils.identifierExistsInSession(dbconn, this, this.id, identifier);
                if (existenceCheck) {
                    System.out.printf("<TinkersExtractor#%s> Identificador ya asignado a un trait, prueba con otro.%n", this.getClass().getSimpleName());
                }
            } while (existenceCheck);
            this.id = identifier;
        }
        if(cascade || Leer.leerBoolean(String.format("Cambiar modificador? Actual(%s) S/N: ", modifier.getId()))) {
            List<EntityModifier> query = (List<EntityModifier>) dbconn.createQuery("FROM "+EntityModifier.class.getSimpleName()).list();
            EntityModifier modifier = (EntityModifier) Leer.selectObject(String.format("Existe el modificador la base de datos? Actual (%s)", this.modifier),query,true);
            if(modifier == null) {
                modifier = new EntityModifier();
                modifier.changeAttributes(dbconn,cascade);
            }
            this.modifier = modifier;
        }
        if(cascade || Leer.leerBoolean(String.format("Cambiar mod? Actual(%s) S/N: ", modId.getId()))) {
            List<EntityMod> query = (List<EntityMod>) dbconn.createQuery("FROM "+EntityMod.class.getSimpleName()).list();
            EntityMod modId = (EntityMod) Leer.selectObject(String.format("Existe el mod deseado la base de datos? Actual (%s)", this.modId),query,true);
            if(modId == null) {
                EntityModifier mod = new EntityModifier();
                mod.changeAttributes(dbconn,cascade);
            }
            this.modId = modId;
        }
        String module = Leer.leerTexto(String.format("Inserta el modulo de la textura (%s): ", this.module));
        String category = Leer.leerTexto(String.format("Inserta la categoria de la textura (%s): ", this.category));
        String itemBase = Leer.leerTexto(String.format("Inserta el atributo itembase de la textura (%s): ", this.itemBase));
        if(this.module != null && module.equals("")) {
            module = this.module;
        }
        if(this.category != null && category.equals("")) {
            category = this.category;
        }
        if(this.itemBase != null && itemBase.equals("")) {
            itemBase = this.itemBase;
        }
        this.module = module;
        this.category = category;
        this.itemBase = itemBase;
    }
}
