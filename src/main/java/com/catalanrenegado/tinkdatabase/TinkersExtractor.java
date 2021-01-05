package com.catalanrenegado.tinkdatabase;

import com.catalanrenegado.tinkdatabase.database.DatabaseConnection;
import com.catalanrenegado.tinkdatabase.identity.ItemID;
import com.catalanrenegado.tinkdatabase.interfaces.IEntity;
import com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.*;
import com.catalanrenegado.tinkdatabase.modules.tconstruct.entities.stats.*;
import org.hibernate.Session;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TinkersExtractor { // NO_UCD (unused code)
    public static final String MODID = "tinkdatabase";
    public static final String NAME = "Tinkers Database";
    public static final String VERSION = "0.9";

    public static void main(String[] args) throws InterruptedException {
        String defaultPassword = "1234";
        String defaultUsername = "root";
        String defaultConnectionUrl = "jdbc:mysql://localhost/tinkerdata?useLegacyDatetimeCode=false&serverTimezone=UTC";
        String defaultDriver = "com.mysql.jdbc.Driver";
        String defaultDialect = "org.hibernate.dialect.MySQL57InnoDBDialect";
        String password = Leer.leerTexto(String.format("Pon una password, actual (%s):", defaultPassword));
        String username = Leer.leerTexto(String.format("Pon un usuario, actual (%s):", defaultUsername));
        String connectionUrl = Leer.leerTexto(String.format("Pon una url de conexion, actual (%s):", defaultConnectionUrl));
        String driver = Leer.leerTexto(String.format("Pon un driver, actual (%s):", defaultDriver));
        String dialect = Leer.leerTexto(String.format("Pon un dialecto, actual (%s):", defaultDialect));
        if(password.equals("")) {
            password = defaultPassword;
        }
        if(username.equals("")) {
            username = defaultUsername;
        }
        if(connectionUrl.equals("")) {
            connectionUrl = defaultConnectionUrl;
        }
        if(driver.equals("")) {
            driver = defaultDriver;
        }
        if(dialect.equals("")) {
            dialect = defaultDialect;
        }

        Logger globalLogger = Logger.getLogger("global");
        Handler[] handlers = globalLogger.getHandlers();
        for(Handler handler : handlers) {
            globalLogger.removeHandler(handler);
        }
        Set<Class<? extends IEntity>> typeCasting = new HashSet<>();
        typeCasting.add(EntityAtlasSprite.class);
        typeCasting.add(SpriteTConstructBase.class);
        typeCasting.add(EntityMaterial.class);
        typeCasting.add(EntityMod.class);
        typeCasting.add(EntityToolCore.class);
        typeCasting.add(EntityHead.class);
        typeCasting.add(EntityShaft.class);
        typeCasting.add(EntityBowString.class);
        typeCasting.add(EntityExtra.class);
        typeCasting.add(EntityBowCore.class);
        typeCasting.add(EntityBowHead.class);
        typeCasting.add(EntityHandle.class);
        typeCasting.add(EntityFletching.class);
        typeCasting.add(EntityComponent.class);
        typeCasting.add(ItemID.class);
        typeCasting.add(PartTypes_Materials.class);
        typeCasting.add(EntityPart.class);
        typeCasting.add(EntityModifier.class);
        typeCasting.add(EntityPartType.class);
        typeCasting.add(EntityTrait.class);
        DatabaseConnection.Builder builder = new DatabaseConnection.Builder()
                .setProperty("hibernate.connection.url",
                        connectionUrl)
                .setProperty("hibernate.connection.driver_class", driver)
                .setProperty("hibernate.dialect", dialect)
                .setProperty("hibernate.connection.username", username)
                .setProperty("hibernate.connection.password", password);

        // builder.addAnnotatedClasses(typeCasting); no compila.
        for (Class<? extends IEntity> klass : typeCasting) {
            builder.addAnnotatedClass(klass);
            // Joan Gerard si ves esto explicame porque de esta forma si y de la otra no.
        }

        DatabaseConnection conn = builder.build();
        Session s = conn.getSession();
        Thread.sleep(1000);
        MaintainerShell m = new MaintainerShell(conn, typeCasting);
        m.startShell();
    }
}
