package com.catalanrenegado.tinkdatabase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class DatabaseUtils {
	public static String HEADER = "extraLinesHeader";
	public static String LINES = "extraLines";
	public static String SEPARATOR = "♫";

	public static void printFormattedTable(List<Map<String,String>> table) {
		System.out.print(getFormattedTable(table));
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
		System.out.print(getRow(row,keys,sizes));
	}

	private static String getRow(Map<String, String> row, String[] keys, int[] sizes) {
		StringBuilder sb = new StringBuilder();
		sb.append("|");
		String[] extraLines = null;
		String extraLinesheader = null;
		if(row.containsKey(LINES)) {
			extraLines = row.get(LINES).split(SEPARATOR);
		}
		if(row.containsKey(HEADER)) {
			extraLinesheader = row.get(HEADER);
		}
		for (int i = 0; i < keys.length; i++) {
			sb.append(ConsoleColors.COLORS[i % 12])
					.append(String.format("%-" + sizes[i] + "s", row.get(keys[i])))
					.append(ConsoleColors.RESET)
					.append("|");
			}
		if(extraLinesheader != null) {
			sb.append('\n').append(extraLinesheader);
		}
		if(extraLines != null) {
			for (String line: extraLines) {
				sb.append("\n").append(line);
			}
		}
		return sb.append('\n').toString();
	}

	public static String getFormattedTable(List<Map<String,String>> table) {
		String[] keys ;
		if(!table.isEmpty()) {
			Set<String> keySet = table.get(0).keySet();
			keySet.remove(LINES);
			keySet.remove(HEADER);
			keys = keySet.toArray(new String[0]);
		} else {
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

	public static String getRow(Map<String,String> row) {
		String[] keys ;
		if(!row.isEmpty()) {
			keys = row.keySet().toArray(new String[0]);
		} else {
			return null;
		}
		int[] sizes = new int[keys.length];
		List<Map<String,String>> list = new ArrayList<>();
		list.add(row);
		for (int i = 0; i < keys.length; i++) {
			sizes[i] = getMaxLenghtOfColumn(list, keys[i]);
		}
		return getRow(row,keys,sizes);
	}
}
