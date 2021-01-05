package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities;

import com.catalanrenegado.tinkdatabase.Leer;
import com.catalanrenegado.tinkdatabase.Utils;
import com.catalanrenegado.tinkdatabase.database.DatabaseConnection;
import com.catalanrenegado.tinkdatabase.interfaces.IEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Catalan Renegado
 */
@Entity
@Table(name = "TRAITS")
public class EntityTrait implements IEntity {
    private static final long serialVersionUID = -7188699681319285288L;
    @Id
    @Column
    private String id;
    @Column(length = 500)
    private String description;

    @SuppressWarnings("unused")
    public EntityTrait() {
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EntityTrait other = (EntityTrait) obj;
        return Objects.equals(other.id, this.id)
                && Objects.equals(other.description, this.description);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id+"/"+description;
    }
    @Override
    public Map<String, String> getMap(boolean cascade) {
        Map<String,String> map = new HashMap<>();
        map.put("Id",id) ;
        map.put("Description",description);
        return map;
    }

    @Override
    public void changeAttributes(DatabaseConnection dbconn, boolean cascade) {
        if (this.id == null) {
            boolean existenceCheck;
            String identifier;
            do {
                identifier = Leer.leerTexto(String.format("<TinkersExtractor#%s> Identificador: ", this.getClass().getSimpleName()));
                if (identifier.equals("") && this.id != null) {
                    identifier = this.id;
                }
                existenceCheck = !Utils.identifierExistsInSession(dbconn, this, this.id, identifier);
                if (existenceCheck) {
                    System.out.printf("<TinkersExtractor#%s> Identificador ya asignado a un trait, prueba con otro.%n", this.getClass().getSimpleName());
                }
            } while (existenceCheck);
            this.id = identifier;
        }
        String description = Leer.leerTexto(String.format("<TinkersExtractor#%s> Inserta descripcion para esta Abilidad de herramienta \n(%s)\n: ", this.getClass().getSimpleName(),this.description));
        if (description.equals("") && this.description.equals("")) {
            description = this.description;
        }
        this.description = description;
    }
}
