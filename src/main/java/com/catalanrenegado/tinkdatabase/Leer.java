package com.catalanrenegado.tinkdatabase;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author joange
 */
public class Leer {
    private final static InputStreamReader isr = new InputStreamReader(System.in);
    private final static BufferedReader entradaConsola = new BufferedReader(isr);

    /**
     * Llig un text del teclat.
     *
     * @param mensaje Text que es passa a l'usuaro
     * @return el text introduit. Blanc en cas d'error
     */
    public static String leerTexto(String mensaje) {
        limpiarBuffer();
        if (mensaje.charAt(mensaje.length() - 1) != ' ') {
            mensaje += " ";
        }
        String respuesta;
        do {
            try {
                System.out.print(ConsoleColors.YELLOW + "# " + mensaje + ConsoleColors.RESET);
                respuesta = entradaConsola.readLine();
            } // ()
            catch (IOException ex) {
                return "";
            }
        } while (respuesta == null);
        return respuesta;

    } // ()

    /**
     * Introducció de numeros enters
     *
     * @param mensaje Missatge que es dona a l'usuari
     * @return un enter amb el valor
     */
    public static int leerEntero(String mensaje) {
        int n = 0;
        boolean aconseguit = false;
        while (!aconseguit) {
            try {
                String text = leerTexto(mensaje);
                if (!text.equals("")) {
                    n = Integer.parseInt(text);
                } else {
                    return 0;
                }
                aconseguit = true;
            } catch (NumberFormatException ex) {
                ConsoleColors.printError("Deus posar un numero correcte");
            }
        }
        return n;
    }

    /**
     * @param mensaje mensaje a printar en pantalla.
     * @param limite  desde 0 (siendo 0 un numero valido) hasta X siendo x un numero valido, no se permiten limites negativos.
     * @return int input or 0 if input is ""
     */
    public static int leerEntero(String mensaje, int limite) {
        int n = leerEntero(mensaje);
        if (!(n < limite && n >= 0)) {
            ConsoleColors.printError("Deus posar un numero correcte maxim =" + limite);
            return leerEntero(mensaje, limite);
        }
        return n;
    }

    /**
     * Lee un entero entre A y B.
     *
     * @param mensaje a escribir
     * @param minimo  minimo numero posible que puede ser insertado.
     * @param maximo  maximo numero posible que puede ser insertado.
     * @return numero insertado.
     */
    public static int leerEntero(String mensaje, int minimo, int maximo) {
        int n = leerEntero(mensaje);
        if (!(n >= minimo && n <= maximo)) {
            ConsoleColors.printError(String.format("Tienes que elegir un numero entre %d y %d", minimo, maximo));
            return leerEntero(mensaje);
        }
        return n;
    }

    /**
     * Introducció de numeros enters
     *
     * @param mensaje Missatge que es dona a l'usuari
     * @return un enter amb el valor
     */
    public static double leerDouble(String mensaje) {
        double dNumber = leerBigDecimal(mensaje).doubleValue();
        if (dNumber != Double.NEGATIVE_INFINITY && dNumber != Double.POSITIVE_INFINITY && !Double.isNaN(dNumber)) {
            return dNumber;
        } else {
            ConsoleColors.printError("Error con el numero Double insertado, vuelve a intentarlo.");
            return leerDouble(mensaje);
        }
    }

    public static boolean leerBoolean(String mensaje) {
        String bool = leerTexto(mensaje);
        if (bool.equalsIgnoreCase("t") || bool.equalsIgnoreCase("true") || bool.equalsIgnoreCase("Si") || bool.equalsIgnoreCase("S") || bool.equalsIgnoreCase("Y") || bool.equalsIgnoreCase("Yes")) {
            return true;
        } else if (bool.equalsIgnoreCase("f") || bool.equalsIgnoreCase("false") || bool.equalsIgnoreCase("no") || bool.equalsIgnoreCase("n")) {
            return false;
        } else {
            ConsoleColors.printError("Error en la introduccion, opciones S/N.");
            return leerBoolean(mensaje);
        }
    }

    public static int leerCaracter(String mensaje, int minimo, int maximo) {
        int c = 1;
        System.out.print(mensaje);
        try {
            c = entradaConsola.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (c == '0') {
            return 0;
        }
        if (c < minimo || c > maximo) {
            String msg = String.format("Caracter minimo %c maximo %c", (char) minimo, (char) maximo);
            ConsoleColors.printError(msg);
            limpiarBuffer();
            return leerCaracter(mensaje, minimo, maximo);
        }
        return c;
    }

    private static boolean limpiarBuffer() {
        try {
            if (entradaConsola.ready()) {
                entradaConsola.readLine();
                return true;
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Date leerFecha(String mensaje) {
        try {
            String posibleFecha = leerTexto(mensaje.replace("$", "yyyy/MM/dd$"));
            if (posibleFecha.equals("")) {
                return new Date(0L);
            } else {
                return new SimpleDateFormat("yyyy/MM/dd").parse(posibleFecha);
            }
        } catch (ParseException e) {
            return leerFecha("Error, intentalo de nuevo");
        }
    }

    public static java.sql.Date leerFechaSQL(String mensaje) {
        return new java.sql.Date(leerFecha(mensaje).getTime());
    }

    public static float leerFloat(String mensaje) {
        return (float) Leer.leerDouble(mensaje);
    }

    public static BigDecimal leerBigDecimal(String mensaje) {
        BigDecimal n = null;
        boolean aconseguit = false;
        while (!aconseguit) {
            try {
                String texto = leerTexto(mensaje);
                if (texto.isEmpty()) {
                    n = new BigDecimal("0.0");
                } else {
                    n = new BigDecimal(texto);
                }
                aconseguit = true;
            } catch (NumberFormatException ex) {
                ConsoleColors.printError("Deus posar un numero correcte");
                n = leerBigDecimal(mensaje);
            }
        }
        return n;
    }

    public static long leerLong(String mensaje) {
        long n = 0;
        boolean aconseguit = false;
        while (!aconseguit) {
            try {
                String texto = leerTexto(mensaje);
                if (texto.isEmpty()) {
                    n = 0L;
                } else {
                    n = Long.parseLong(texto);
                }
                aconseguit = true;
            } catch (NumberFormatException ex) {
                ConsoleColors.printError("Deus posar un numero correcte");
                n = leerLong(mensaje);
            }
        }
        return n;
    }

    /**
     * @param listing   the list to select the object.
     * @param canBeNull if this is true, can insert a number out of the size of the list by one.
     * @return an object in the list or null if can be null
     */
    public static Object selectObject(String msg, List<?> listing, boolean canBeNull) {
        AtomicInteger i = new AtomicInteger(1);
        if (canBeNull) {
            System.out.println("\t - " + ConsoleColors.getColoredString("0", ConsoleColors.BLUE) + ConsoleColors.getColoredString(". Ninguno", ConsoleColors.PURPLE));
        }
        listing.forEach(obj -> System.out.printf("\t - " + ConsoleColors.getColoredString("%d", ConsoleColors.BLUE) + ConsoleColors.getColoredString(". %s. %n", ConsoleColors.PURPLE), i.getAndIncrement(), obj.toString()));
        System.out.println(ConsoleColors.YELLOW + msg + ConsoleColors.RESET);
        int index = leerEntero("Select:", listing.size() + 1);
        if (canBeNull && index == 0) {
            return null;
        } else {
            if (index > 0 && index <= listing.size()) {
                return listing.get(index - 1);
            } else {
                System.out.println("Errored, insert a valid object in the list.");
                return selectObject(msg, listing, canBeNull);
            }
        }
    }
}
