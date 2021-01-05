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
@Table(name = "PARTS")
@SuppressWarnings("unchecked")
public class EntityPart implements IEntity {
    private static final long serialVersionUID = -649166906032676389L;
    private String id;
    private String name;
    private EntityMod domain;

    @SuppressWarnings("unused")
    public EntityPart() {
    }

    @Override
    @Id
    public String getId() {
        return id;
    }

    public void setId(String iD) {
        id = iD;
    }

    @Column(name = "_name_")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public EntityMod getDomain() {
        return domain;
    }

    public void setDomain(EntityMod domain) {
        this.domain = domain;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EntityPart other = (EntityPart) obj;
        return Objects.equals(this.id, other.id) && Objects.equals(this.name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return  id + '\'' +
                "/" + name + '\'' +
                "/" + domain;
    }

    @Override
    public Map<String, String> getMap(boolean cascade) {
        Map<String,String> map = new HashMap<>();
        map.put("Id",id);
        map.put("Mod",domain.getName());
        map.put("Name",name);
        return map;
    }

    @Override
    public void changeAttributes(DatabaseConnection dbconn, boolean cascade) {
        if (this.id == null) {
            boolean existenceCheck;
            String identifier;
            do { // TODO The identity cannot be changed in runtime.
                identifier = Leer.leerTexto(String.format("<TinkersExtractor#%s> Identificador: ", this.getClass().getSimpleName()));
                if (identifier.equals("") && this.id != null) {
                    identifier = this.id;
                }
                existenceCheck = !Utils.identifierExistsInSession(dbconn, this, this.id, identifier);
                if (existenceCheck) {
                    System.out.printf("<TinkersExtractor#%s> Identificador ya asignado a un mod, prueba con otro.%n", this.getClass().getSimpleName());
                }
            } while (existenceCheck);
            this.id = identifier;
        }
        String name = Leer.leerTexto(String.format("<TinkersExtractor#%s>Cual es el nombre canónico de esta parte: ", this.getClass().getSimpleName()));
        if (cascade || Leer.leerBoolean(String.format("<TinkersExtractor#%s> Quieres cambiar el Mod de esta parte? Actual(%s) S/N", EntityMod.class.getSimpleName(), this.domain))) {
            List<EntityMod> mods = (List<EntityMod>) dbconn.createQuery("FROM " + EntityMod.class.getSimpleName()).list();
            EntityMod selectedMod = (EntityMod) Leer.selectObject(String.format("<TinkersExtractor#%s>Selecciona un mod, o 0 si quieres agregar uno nuevo.", this.getClass().getSimpleName()), mods, true);
            if (selectedMod == null) {
                selectedMod = new EntityMod();
                selectedMod.changeAttributes(dbconn, cascade);
                dbconn.persist(selectedMod);
            }
            this.domain = selectedMod;
        }
        if (!name.equalsIgnoreCase("")) {
            name = this.name;
        }
        this.name = name;
    }
}
