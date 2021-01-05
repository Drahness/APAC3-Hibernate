package com.catalanrenegado.tinkdatabase.modules.tconstruct.entities;

import com.catalanrenegado.tinkdatabase.ConsoleColors;
import com.catalanrenegado.tinkdatabase.DatabaseUtils;
import com.catalanrenegado.tinkdatabase.Leer;
import com.catalanrenegado.tinkdatabase.Utils;
import com.catalanrenegado.tinkdatabase.database.DatabaseConnection;
import com.catalanrenegado.tinkdatabase.identity.ItemID;
import com.catalanrenegado.tinkdatabase.interfaces.IEntity;

import javax.persistence.*;
import java.util.*;

@MappedSuperclass
public abstract class AbstractTinkersItem implements IEntity {
    private static final long serialVersionUID = -2388358903407620640L;
    private ItemID id;
    private String name;
    private String description;
    private List<EntityComponent> components = new ArrayList<>();

    @Override
    public Map<String, String> getMap(boolean cascade) {
        Map<String, String> map = new HashMap<>();
        map.put("Id",String.valueOf(id));
        map.put("Name",name);
        map.put("Desciption",description);
        if(cascade) {
            if(components.size() > 0) {
                List<Map<String ,String >> maps = new ArrayList<>();
                for (EntityComponent comp: components) {
                    Map<String,String> compMap = comp.getMap(false);
                    maps.add(compMap);
                }
                map.put(DatabaseUtils.HEADER, ConsoleColors.BLUE+EntityComponent.class.getSimpleName()+ConsoleColors.RESET);
                map.put(DatabaseUtils.LINES, map.put(DatabaseUtils.LINES, "\t"+ Objects.requireNonNull(DatabaseUtils.getFormattedTable(maps)).replace("\n","\n\t")));
            }
        }
        return map;
    }

    public AbstractTinkersItem(String id, String name, String description) {
        this.id = new ItemID(id);
        this.name = name;
        this.description = description;
    }

    public AbstractTinkersItem() {
    }

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

    @ManyToMany
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
    public void setDescription(String description) {
        this.description = description;
    }
    @Id
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = ItemID.class)
    public ItemID getCoresId() {
        return id;
    }
    public void setCoresId(ItemID id) {
        this.id = id;
    }
    @Column(name = "core_name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, id, description);
    }
    @Override
    @SuppressWarnings("unchecked")
    public void changeAttributes(DatabaseConnection dbconn, boolean cascade) {
        if (this.id == null) {
            boolean existenceCheck;
            String identifier;
            do { // The identity cannot be changed in runtime.
                identifier = Leer.leerTexto(String.format("<TinkersExtractor#%s> Identificador (%s): ", this.getClass().getSimpleName(), this.id));
                existenceCheck = !Utils.identifierExistsInSession(dbconn, this, this.id, identifier);
                if (existenceCheck) {
                    System.out.printf("<TinkersExtractor#%s> Identificador ya asignado a un item, prueba con otro.%n", this.getClass().getSimpleName());
                }
            } while (existenceCheck);
            this.id = new ItemID(identifier);
        }
        String name = Leer.leerTexto(String.format("<TinkersExtractor#%s> Inserta nombre del nuevo item (%s): ", this.getClass().getSimpleName(),this.name));
        String descripcion = Leer.leerTexto(String.format("<TinkersExtractor#%s> Inserta una descripcion (%-30s): ", this.getClass().getSimpleName(),this.description));
        if (name.equalsIgnoreCase("")) {
            name = this.name;
        }
        if (descripcion.equalsIgnoreCase("")) {
            descripcion = this.description;
        }
        StringBuilder sb = new StringBuilder();
        if (!cascade) {
            if (this.components.size() == 0) {
                sb.append("Ninguno.");
            } else {
                sb.append('\n');
                sb.append("\t Trait\t Material");
                this.components.forEach(component -> sb.append(String.format("\t %s\t - %s\n", component.getToolPart_Id(), component.getCore_Id())));
            }
        }
        if (cascade || Leer.leerBoolean(String.format("<TinkersExtractor#%s> Quieres cambiar los componentes que forman el item? Actual(%s) S/N", this.getClass().getSimpleName(), sb.toString()))) {
            boolean exit;
            do {
                EntityComponent selectedTrait = (EntityComponent) Leer.selectObject(String.format("<TinkersExtractor#%s> Elige un trait a cambiar o 0 para añadir uno nuevo", this.getClass().getSimpleName()), this.components, true);
                if (selectedTrait == null) {
                    List<EntityComponent> allComponents = new ArrayList<>((Collection<? extends EntityComponent>) dbconn.createQuery("FROM " + EntityComponent.class.getSimpleName()).list());
                    selectedTrait = (EntityComponent) Leer.selectObject(String.format("<TinkersExtractor#%s> Elige uno en la BBDD o 0 para añadir uno nuevo.", this.getClass().getSimpleName()), allComponents, true);
                    if (selectedTrait == null) {
                        selectedTrait = new EntityComponent();
                        selectedTrait.changeAttributes(dbconn, cascade);
                        dbconn.persist(selectedTrait);
                    }
                    this.components.add(selectedTrait);
                } else {
                    selectedTrait.changeAttributes(dbconn, cascade);
                }
                exit = Leer.leerBoolean(String.format("<TinkersExtractor#%s> Has acabado los ajustes? S/N", this.getClass().getSimpleName()));
            } while (!exit);
        }
        this.name = name;
        this.description = descripcion;
    }
    @Override
    public String toString() {
        return id.getItem();
    }
}
