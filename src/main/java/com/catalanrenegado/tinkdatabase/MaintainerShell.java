package com.catalanrenegado.tinkdatabase;

import com.catalanrenegado.tinkdatabase.database.DatabaseConnection;
import com.catalanrenegado.tinkdatabase.interfaces.IEntity;
import com.catalanrenegado.tinkdatabase.interfaces.Mantainable;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.query.Query;
import org.hibernate.type.Type;

import javax.persistence.PersistenceException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class MaintainerShell implements Shell {
    private final List<Class<? extends IEntity>> klasses;
    private final DatabaseConnection dbconn;

    public MaintainerShell(DatabaseConnection dbconn, Collection<Class<? extends IEntity>> entities) {
        this.dbconn = dbconn;
        this.klasses = new ArrayList<>();
        this.klasses.addAll(entities);
    }

    @Override
    public String getShellString() {
        return "<Tinkers Extractor>";
    }

    @Override
    public boolean startShell(String command) {
        String[] order = command.split(" ");
        int commandLenght = order[0].length();
        final String substring = command.substring(commandLenght);
        switch (order[0]) {
            case ".show":
                this.show(substring);
                return true;
            case ".add":
                this.add(substring);
                return true;
            case ".update":
                this.update(substring);
                return true;
            case ".delete":
                this.delete(substring);
                return true;
            case ".structure":
                this.structure(substring);
                return true;
            default:
                try {
                    List<?> queryResult = dbconn.createQuery(command).getResultList();
                    AtomicInteger i = new AtomicInteger();
                    if(queryResult.size() > 0 && queryResult.get(0) instanceof Object[]) {
                        queryResult.forEach(obj -> System.out.printf("\t - " + ConsoleColors.getColoredString("%d", ConsoleColors.BLUE) + ConsoleColors.getColoredString(". %s. %n", ConsoleColors.PURPLE), i.getAndIncrement(), Arrays.toString((Object[])obj)));
                    }
                    else {
                        queryResult.forEach(obj -> System.out.printf("\t - " + ConsoleColors.getColoredString("%d", ConsoleColors.BLUE) + ConsoleColors.getColoredString(". %s. %n", ConsoleColors.PURPLE), i.getAndIncrement(),obj));
                    }
                    return true;
                } catch(IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Usa el comando .help para mas informacion sobre el uso de este programa.");
                    return false;
                }

        }
    }

    private void structure(String substring) {
        Scanner sc = new Scanner(substring);
        Class<? extends IEntity> klass = null;
        if (sc.hasNextInt()) {
            int index = sc.nextInt();
            if (checkIndex(index)) {
                klass = this.klasses.get(index);
            } else {
                System.out.printf("Error en la introduccion de %d use un identificador de %d a %d\n", index, 0, this.klasses.size());
            }
        } else if (sc.hasNext()) {
            String className = sc.next();
            if ((klass = checkClassname(className)) == null) {
                System.out.printf("Error en la introduccion de %s, vuelve a intentarlo.\n", className);
            }
        } else {
            System.out.printf("Error en la introduccion %s\n", substring);
        }
        if (klass != null) {
            List<Map<String,String>> table = new ArrayList<>();
            ClassMetadata cm = dbconn.getClassMetadata(klass);
            String[] propertyNames = cm.getPropertyNames();
            for (int i = 0 ; i < propertyNames.length ; i++) {
                String prop = propertyNames[i];
                Map<String,String> map = new HashMap<>();
                map.put("index",String.valueOf(i));
                map.put("name",prop);
                map.put("type",cm.getPropertyType(prop).getReturnedClass().getSimpleName());
                map.put("nullability",String.valueOf(cm.getPropertyNullability()[i]));
                map.put("identifier","false");
                map.put("laziness",String.valueOf(cm.getPropertyLaziness()[i]));
                table.add(map);
            }
            Map<String,String> map = new HashMap<>();
            map.put("name",cm.getIdentifierPropertyName());
            map.put("type",cm.getIdentifierType().getReturnedClass().getSimpleName());
            map.put("nullability","false");
            map.put("identifier","true");
            map.put("index",String.valueOf(propertyNames.length));
            map.put("laziness","false");
            table.add(map);
            System.out.println("\t"+ConsoleColors.BLUE+klass.getSimpleName()+ConsoleColors.RESET);
            DatabaseUtils.printFormattedTable(table);
        }
        sc.close();
    }

    @Override
    public void printHelp(String command) {
        Scanner sc = new Scanner(command);
        System.out.print(ConsoleColors.PURPLE);
        if(sc.hasNext(".add")) {
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("Mantenimiento de la BBDD de Tinkers");
            System.out.println(" add ");
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("Opciones:");
            System.out.println("\t- [-c/--cascade] Muestra las relaciones.");
        }
        else if(sc.hasNext(".update")) {
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("Mantenimiento de la BBDD de Tinkers");
            System.out.println(" update ");
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("Opciones:");
            System.out.println("\t- [-c/--check] Se salta los checks para cambiar relaciones.");
        }
        else if(sc.hasNext(".delete")) {
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("Mantenimiento de la BBDD de Tinkers");
            System.out.println(" delete entityname or id ");
            System.out.println("-------------------------------------------------------------------------------");
        }
        else if(sc.hasNext(".show")) {
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("Mantenimiento de la BBDD de Tinkers");
            System.out.println(" show [-c/--cascade] [-w/--where (condiciones)] entityname or id");
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("Opciones:");
            System.out.println("\t- [-c/--cascade] Muestra las relaciones.");
            //System.out.println("\t- [-w/--where (condicion)] Muestra los registros dependiendo de la clausula where, pueden ser multiples");
            System.out.println("\t- [-l/--limit N] Establece el limite de registros.");
        }
        else if(sc.hasNext(".structure")) {
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("Mantenimiento de la BBDD de Tinkers");
            System.out.println(" structure entityname or id");
            System.out.println("-------------------------------------------------------------------------------");
            /*System.out.println("Opciones:");
            System.out.println("\t- [-c/--cascade] Muestra las relaciones.");
            System.out.println("\t- [-w/--where (condiciones)] Muestra los registros dependiendo de la clausula where");*/
        }
        /*else if(sc.hasNext("HQL")) {
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("Mantenimiento de la BBDD de Tinkers");
            System.out.println(" structure entityname or id");
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("Opciones:");
            System.out.println("\t- [-c/--cascade] Muestra las relaciones.");
            System.out.println("\t- [-w/--where (condiciones)] Muestra los registros dependiendo de la clausula where");
        }*/
        else {
            printHelp();
        }
        System.out.print(ConsoleColors.RESET);
    }

    @Override
    public void printHelp() {
        AtomicInteger i = new AtomicInteger();
        System.out.print(ConsoleColors.PURPLE);
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("Mantenimiento de la BBDD de Tinkers");
        System.out.println("Opciones:\t\tDescripcion");
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("\t- .help [comando]\t\t\t\t Muestra este mensaje u otro dependiendo de lo que pongas..");
        System.out.println("\t- .quit\t\t\t\t Sal del programa.");
        System.out.println("\t- .show [-c/--cascade] [-l/--limit N] entityname or id\t\t Muestra el contenido.");
        System.out.println("\t- .add [-ch/--check] entityname or id\t\t Añade un nuevo registro a la BBDD.");
        System.out.println("\t- .update [-ch/--check] entityname or id\t\t Actualizara un registro seleccionar de la BBDD.");
        System.out.println("\t- .delete entityname or id\t\t\t Borrara un registro a seleccionar de la BBDD.");
        System.out.println("\t- .structure entityname or id\t\t\t Describe una entidad de la BBDD.");
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("Entidades disponibles.");
        System.out.print(ConsoleColors.RESET);
        klasses.forEach(obj -> System.out.printf("\t - " + ConsoleColors.getColoredString("%d", ConsoleColors.BLUE) + ConsoleColors.getColoredString(". %s. %n", ConsoleColors.PURPLE), i.getAndIncrement(), obj.getSimpleName()));
    }

    public void show(String command) {
        Scanner sc = new Scanner(command);
        boolean cascade = false;
        Class<? extends IEntity> klass = null;
        int limit = -1;
        StringBuilder conditions = new StringBuilder();
        AtomicInteger i = new AtomicInteger();
        if (sc.hasNext("-c") || sc.hasNext("--cascade")) {
            cascade = true;
            sc.next();
        }
        if(sc.hasNext("-l") || sc.hasNext("--limit")) {
            sc.next();
            if(sc.hasNextInt()) {
                limit = sc.nextInt();
                if(limit < 1) {
                    System.out.printf("Error en la introduccion %s: El limite no puede ser menor o igual a 0\n", command);
                    return;
                }
            } else {
                System.out.printf("Error en la introduccion %s\n", command);
                return;
            }
        }
        if (sc.hasNextInt()) {
            int index = sc.nextInt();
            if (checkIndex(index)) {
                klass = this.klasses.get(index);
            } else {
                System.out.printf("Error en la introduccion de %d use un identificador de %d a %d\n", index, 0, this.klasses.size());
            }
        } else if (sc.hasNext()) {
            String className = sc.next();
            if ((klass = checkClassname(className)) == null) {
                System.out.printf("Error en la introduccion de %s, vuelve a intentarlo.\n", className);
            }
        } else {
            System.out.printf("Error en la introduccion %s\n", command);
        }

        if (klass != null) {
            String query = "FROM " + klass.getSimpleName();
           /* TODO if(conditions.length() > 0) {
                query += " " +conditions.toString();
            } */
            boolean finalCascade = cascade;
            List<Map<String,String>> entities = new ArrayList<>();
            Query<?> q = dbconn.createQuery(query);
            if(limit != -1) {
                q.setMaxResults(limit);
            }
            q.list().forEach(o -> entities.add(((IEntity) o).getMap(finalCascade)));
            System.out.println(ConsoleColors.BLUE_BOLD+klass.getSimpleName()+ConsoleColors.BLUE_BOLD);
            DatabaseUtils.printFormattedTable(entities);
            }
        sc.close();
    }

    @SuppressWarnings("unchecked")
    private void delete(String command) {
        Scanner sc = new Scanner(command);
        Class<? extends IEntity> klass = null;
        if (sc.hasNextInt()) {
            int index = sc.nextInt();
            if (checkIndex(index)) {
                klass = this.klasses.get(index);
            } else {
                System.out.printf("Error en la introduccion de %d use un identificador de %d a %d\n", index, 0, this.klasses.size());
            }
        } else if (sc.hasNext()) {
            String className = sc.next();
            if ((klass = checkClassname(className)) == null) {
                System.out.printf("Error en la introduccion de %s, vuelve a intentarlo.\n", className);
            }
        } else {
            System.out.printf("Error en la introduccion %s\n", command);
        }
        if (klass != null) {
            String query = "FROM " + klass.getSimpleName();
            List<IEntity> entities = (List<IEntity>) dbconn.createQuery(query).list();
            IEntity selectedInstance = (IEntity) Leer.selectObject("Seleccione objeto a eliminar. ", entities, true);
            dbconn.beginTransaction();
            if (selectedInstance != null) {
                dbconn.getSession().delete(selectedInstance);
            } else {
                System.out.println("No se ha eliminado nada.");
            }
            try {
                dbconn.commit();
                System.out.printf("Eliminada entidad %s clase %s.\n", selectedInstance, klass.getSimpleName());
            }
            catch(PersistenceException constraintViolationException) {
                System.out.println(ConsoleColors.getErrorColored("Ha habido un error de claves. Has intentado eliminar un objeto " +
                        "sin eliminar los objetos de los que puede colgar?"));
            }
        }
        sc.close();
    }

    private void add(String command) {
        Scanner sc = new Scanner(command);
        boolean cascade = false;
        // Class resolve
        Class<? extends IEntity> klass = null;
        if (sc.hasNext("-ch") || sc.hasNext("--check")) {
            cascade = true;
            sc.next();
        }
        if (sc.hasNextInt()) {
            int index = sc.nextInt();
            if (checkIndex(index)) {
                klass = this.klasses.get(index);
            } else {
                System.out.printf("Error en la introduccion de %d use un identificador de %d a %d\n", index, 0, this.klasses.size());
            }
        } else if (sc.hasNext()) {
            String className = sc.next();
            if ((klass = checkClassname(className)) == null) {
                System.out.printf("Error en la introduccion de %s, vuelve a intentarlo.\n", className);
            }
        } else {
            System.out.printf("Error en la introduccion %s\n", command);
        }
        // end class resolve
        if (klass != null) {
            IEntity newEntity = this.newInstance(klass);
            dbconn.beginTransaction();
            newEntity.changeAttributes(dbconn, cascade);
            dbconn.persist(newEntity);
            dbconn.commit();
        }
        sc.close();
    }

    private void update(String command) {
        Scanner sc = new Scanner(command);
        boolean cascade = false;
        if (sc.hasNext("-ch") || sc.hasNext("--check")) {
            cascade = true;
            sc.next();
        }
        // Class resolve
        Class<? extends IEntity> klass = null;
        if (sc.hasNextInt()) {
            int index = sc.nextInt();
            if (checkIndex(index)) {
                klass = this.klasses.get(index);
            } else {
                System.out.printf("Error en la introduccion de %d use un identificador de %d a %d\n", index, 0, this.klasses.size());
            }
        } else if (sc.hasNext()) {
            String className = sc.next();
            if ((klass = checkClassname(className)) == null) {
                System.out.printf("Error en la introduccion de %s, vuelve a intentarlo.\n", className);
            }
        } else {
            System.out.printf("Error en la introduccion %s\n", command);
        }
        // end class resolve
        if (klass != null) {
            String query = "FROM " + klass.getSimpleName();
            dbconn.beginTransaction();
            IEntity selectedEntity = (IEntity) Leer.selectObject("Seleccione objeto a actualizar. ", dbconn.createQuery(query).list(), false);
            if (selectedEntity != null) {
                selectedEntity.changeAttributes(dbconn, cascade);
            }
            try {
                dbconn.commit();
            }
            catch (ConstraintViolationException e) {
                System.out.println(ConsoleColors.getErrorColored("Ha habido un error de claves. Has intentado eliminar un objeto " +
                        "sin eliminar los objetos de los que puede colgar?"));
            }
        }
        sc.close();
    }

    private Class<? extends IEntity> checkClassname(String klass) {
        for (Class<? extends IEntity> klas : klasses) {
            if (klas.getSimpleName().equals(klass)) {
                return klas;
            }
        }
        return null;
    }

    private boolean checkIndex(int index) {
        return index > -1 && index < klasses.size();
    }

    private IEntity newInstance(Class<? extends IEntity> klass) {
        IEntity instance;
        try {
            Constructor<?> constructor = klass.getDeclaredConstructor();
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
                instance = (IEntity) constructor.newInstance();
                constructor.setAccessible(false);
            } else {
                instance = (IEntity) constructor.newInstance();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.err.printf("Class %s has no argument/nullary constructor.\n", klass.getCanonicalName());
            throw new RuntimeException();
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return instance;
    }
}
