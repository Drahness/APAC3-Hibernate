package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities;

import com.catalanrenegado.tinkdatabase.ConsoleColors;
import com.catalanrenegado.tinkdatabase.DatabaseUtils;
import com.catalanrenegado.tinkdatabase.Leer;
import com.catalanrenegado.tinkdatabase.Utils;
import com.catalanrenegado.tinkdatabase.database.DatabaseConnection;
import com.catalanrenegado.tinkdatabase.embebbeds.EmbeddedTrait;
import com.catalanrenegado.tinkdatabase.interfaces.IEntity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "MATERIALS")
public class EntityMaterial implements IEntity {
    private static final long serialVersionUID = 3023059943109261927L;
    private String id;
    private String name;
    private EntityMod domain;
    private List<EmbeddedTrait> traits = new ArrayList<>();

    @SuppressWarnings("unused")
    public EntityMaterial() {}

    @Override
    public Map<String, String> getMap(boolean cascade) {
        Map<String, String> map = new HashMap<>();
        map.put("Id",id);
        map.put("Name",name);
        map.put("Mod", domain.getName());
        if(cascade) {
            if(traits.size() > 0) {
                List<Map<String,String>> maps = new ArrayList<>();
                for (EmbeddedTrait trait :traits) {
                    maps.add(trait.getMap(false));
                }
                map.put(DatabaseUtils.HEADER,"\t "+ConsoleColors.BLUE+EmbeddedTrait.class.getSimpleName()+ConsoleColors.RESET);
                map.put(DatabaseUtils.LINES, "\t"+Objects.requireNonNull(DatabaseUtils.getFormattedTable(maps)).replace("\n","\n\t"));
            }
        }
        return map;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        EntityMaterial obj = (EntityMaterial) o;
        return Objects.equals(obj.domain, this.domain) && Objects.equals(obj.id, this.id)
                && Objects.equals(obj.name, this.name)/* && Objects.equals(obj.textColor, this.textColor)*/
                && Objects.equals(obj.traits, this.traits);
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public EntityMod getDomain() {
        return domain;
    }

    public void setDomain(EntityMod domain) {
        this.domain = domain;
    }

    @Override
    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "MaterialName")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.domain, this.id, this.name);
    }

    @Override
    public String toString() {
        return id;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "MATERIAL_TRAITS_PARTTYPES")
    public List<EmbeddedTrait> getTraits() {
        return traits;
    }

    public void setTraits(List<EmbeddedTrait> traits) {
        this.traits = traits;
    }

    @Override
    @SuppressWarnings("unchecked")
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
                    System.out.printf("<TinkersExtractor#%s> Identificador ya asignado a un material, prueba con otro.%n", this.getClass().getSimpleName());
                }
            } while (existenceCheck);
            this.id = identifier;
        }
        String name = Leer.leerTexto(String.format("<TinkersExtractor#%s>(%s) Inserta un nombre para este material.%n", this.getClass().getSimpleName(), this.name));
        if (cascade || Leer.leerBoolean(String.format("<TinkersExtractor#%s> Quieres cambiar el Mod de este material? Actual(%s) S/N", this.getClass().getSimpleName(), this.domain))) {
            List<EntityMod> mods = (List<EntityMod>) dbconn.createQuery(String.format("FROM %s", EntityMod.class.getSimpleName())).list();
            EntityMod modSelected = (EntityMod) Leer.selectObject("El mod existe? S/N", mods, true);
            if (modSelected == null) {
                System.out.printf(ConsoleColors.YELLOW + "<TinkersExtractor#%s> Vamos a pasar a la creacion de un nuevo registro de mod.%n" + ConsoleColors.RESET, this.getClass().getSimpleName());
                modSelected = new EntityMod();
                modSelected.changeAttributes(dbconn, cascade);
                dbconn.persist(modSelected);
                this.domain = modSelected;
            }
            this.domain = modSelected;
        }
        StringBuilder sb = new StringBuilder();
        if (!cascade) {
            if (this.traits.size() == 0) {
                sb.append("Ninguno.");
            } else {
                sb.append('\n');
                sb.append("\t Trait\t Material");
                this.traits.forEach(partmat -> sb.append(String.format("\t %s\t - %s\n", partmat.getiTrait(), partmat.getPartType_Id())));
            }
        }
        if (cascade || Leer.leerBoolean(String.format("<TinkersExtractor#%s> Quieres cambiar los traits para cada parte de este material? Actual(%s) S/N", this.getClass().getSimpleName(), sb.toString()))) {
            boolean exit;
            do {
                EmbeddedTrait selectedTrait = (EmbeddedTrait) Leer.selectObject("Elige un trait a cambiar o para añadir uno nuevo", this.traits, true);
                if (selectedTrait == null) {
                    List<EmbeddedTrait> embeddedTraits = new ArrayList<>();
                    dbconn.createQuery("FROM " + this.getClass().getSimpleName()).list().forEach(mat -> embeddedTraits.addAll(((EntityMaterial) mat).traits));
                    selectedTrait = (EmbeddedTrait) Leer.selectObject("Elige uno en la BBDD o 0 para añadir uno nuevo.", embeddedTraits, true);
                    if (selectedTrait == null) {
                        selectedTrait = new EmbeddedTrait();
                        selectedTrait.changeAttributes(dbconn, cascade);
                    }
                    this.traits.add(selectedTrait);
                } else {
                    selectedTrait.changeAttributes(dbconn, cascade);
                }
                exit = Leer.leerBoolean("Has acabado los ajustes? S/N");
            } while (!exit);
        }
        if (!name.equalsIgnoreCase("")) {
            this.name = name;
        }
    }
}
