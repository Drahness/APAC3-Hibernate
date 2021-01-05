package com.catalanrenegado.tinkdatabase;

public interface Shell {
    default void startShell() {
        String command;
        String help = dotShell() ? ".help" : "help" ;
        String quit = dotShell() ? ".quit" : "quit" ;

        do {
            boolean commandErrored;
            command = Leer.leerTexto(this.getShellString() + ": ");
            if (command.startsWith(help)) {
                commandErrored = false;
                printHelp(command.replace(help,""));
            } else if (command.equals(quit)) {
                break;
            } else {
                commandErrored = !startShell(command);
            }
            if (commandErrored) {
                System.out.println("Orden desconocida");
                this.printHelp();
            }
        } while (true);
    }

    String getShellString();

    /**
     * @param command command to execute.
     * @return boolean representing the if the command inserted is right.
     */
    boolean startShell(String command);

    void printHelp();
    void printHelp(String command);

    default boolean dotShell() {
        return true;
    }
}
