package de.clsg;

public class Ansi {
  public static final String RESET = "\u001B[0m";
  public static final String BOLD  = "\u001B[1m";

  public static final String RED   = "\u001B[31m";
  public static final String GREEN = "\u001B[32m";
  public static final String YELLOW= "\u001B[33m";
  public static final String BLUE  = "\u001B[34m";
  public static final String CYAN  = "\u001B[36m";

  public static String ok(String s)   { return GREEN + s + RESET; }
  public static String err(String s)  { return RED + s + RESET; }
  public static String warn(String s) { return YELLOW + s + RESET; }
  public static String info(String s) { return CYAN + s + RESET; }
  public static String title(String s){ return BOLD + BLUE + s + RESET; }
}