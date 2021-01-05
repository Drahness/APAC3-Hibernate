package com.catalanrenegado.tinkdatabase;

import java.util.List;
import java.util.Map;

public class DatabaseUtilsV2 {
    public static String HEADER = "extraLinesHeader";
    public static String LINES = "extraLines";
    public static void printFormattedTable(List<Map<String,String>> table) {
        String[] keys ;
        if(!table.isEmpty()) {
            keys = table.get(0).keySet().toArray(new String[0]);
        } else {
            System.out.println(ConsoleColors.getErrorColored("La consulta no ha arrojado ningun resultado."));
            return;
        }
        int[] sizes = new int[keys.length];
        for (int i = 0; i < keys.length; i++) {
            sizes[i] = getMaxLenghtOfColumn(table, keys[i]);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("|");
        for (int i = 0; i < keys.length; i++) {
            sb.append(ConsoleColors.COLORS[i%12])
                    .append(String.format("%-"+sizes[i]+"s", keys[i]))
                    .append(ConsoleColors.RESET)
                    .append("|");
        }
        System.out.println(sb);
        sb = new StringBuilder();
        sb.append("|");
        for (int i = 0; i < keys.length; i++) {
            sb.append(String.format("%-"+sizes[i]+"s", " ")).append("|");
        }

        System.out.println((sb.toString().replace(" ", "-")));
        for (Map<String, String> map : table) {
            printRow(map, keys, sizes);
        }
    }


    public static int getMaxLenghtOfColumn(List<Map<String,String>> table, String column) {
        int max = column.length();
        for (Map<String, String> map : table) {
            String obj = map.get(column);
            if(obj != null) {
                int size = map.get(column).length();
                if(map.get(column).length() > max) {
                    max = size;
                }
            }
        }
        return max;
    }

    private static void printRow(Map<String,String> row,String[] keys, int[] sizes) {
        StringBuilder sb = new StringBuilder();
        sb.append("|");
        String[] extraLines = null;
        String extraLinesheader = null;
        for (int i = 0; i < keys.length; i++) {
            if(keys[i].equals(LINES)) {
                // dot comma delimited list of strings
                extraLines = row.get(LINES).split(";");
            }
            else if(keys[i].equals(HEADER)) {
                extraLinesheader = row.get(HEADER);
            }
            else {
                sb.append(ConsoleColors.COLORS[i % 12])
                        .append(String.format("%-" + sizes[i] + "s", row.get(keys[i])))
                        .append(ConsoleColors.RESET)
                        .append("|");
            }
        }
        if(extraLinesheader != null) {
            sb.append('\n').append(extraLinesheader);
        }
        if(extraLines != null) {
            for (String line: extraLines) {
                sb.append("\n").append('\t').append(line);
            }
        }
        System.out.println(sb);
    }

    public static String getRow(Map<String, String> row, String[] keys, int[] sizes) {
        StringBuilder sb = new StringBuilder();
        sb.append("|");
        String[] extraLines = null;
        String extraLinesheader = null;
        for (int i = 0; i < keys.length; i++) {
            if(keys[i].equals(LINES)) {
                extraLines = row.get(LINES).split(";");
            }
            else if(keys[i].equals(HEADER)) {
                extraLinesheader = row.get(HEADER);
            }
            else {
                sb.append(ConsoleColors.COLORS[i % 12])
                        .append(String.format("%-" + sizes[i] + "s", row.get(keys[i])))
                        .append(ConsoleColors.RESET)
                        .append("|");
            }
        }
        if(extraLinesheader != null) {
            sb.append('\n').append(extraLinesheader);
        }
        if(extraLines != null) {
            for (String line: extraLines) {
                sb.append("\n").append('\t').append(line);
            }
        }
        return sb.append('\n').toString();
    }

    public static String getFormattedTable(List<Map<String,String>> table) {
        String[] keys ;
        if(!table.isEmpty()) {
            keys = table.get(0).keySet().toArray(new String[0]);
        } else {
            //System.out.println(ConsoleColors.getErrorColored("La consulta no ha arrojado ningun resultado."));
            return null;
        }
        int[] sizes = new int[keys.length];
        for (int i = 0; i < keys.length; i++) {
            sizes[i] = getMaxLenghtOfColumn(table, keys[i]);
        }

        StringBuilder header = new StringBuilder();
        StringBuilder separator = new StringBuilder();
        StringBuilder rows = new StringBuilder();
        header.append("|");
        for (int i = 0; i < keys.length; i++) {
            header.append(ConsoleColors.COLORS[i%12])
                    .append(String.format("%-"+sizes[i]+"s", keys[i]))
                    .append(ConsoleColors.RESET)
                    .append("|");
        }
        header.append('\n');

        separator.append("|");
        for (int i = 0; i < keys.length; i++) {
            separator.append(String.format("%-"+sizes[i]+"s", " ")).append("|");
        }

        separator.replace(0,separator.length(),separator.toString().replace(" ", "-")).append('\n');
        for (Map<String, String> map : table) {
            rows.append(getRow(map, keys, sizes));
        }
        return String.valueOf(header) + separator + rows;
    }
}
