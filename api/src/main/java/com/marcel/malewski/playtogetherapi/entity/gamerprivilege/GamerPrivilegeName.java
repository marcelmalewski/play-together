package com.marcel.malewski.playtogetherapi.entity.gamerprivilege;

public final class GamerPrivilegeName {
    /*
	VIEW — przeglądanie encji
	ANALYSE — dostęp do bardziej zaawansowanych agregacji na encjach | Zawiera też VIEW
	CREATE — tworzenie encji
	EDIT — edytowanie encji
	DELETE — usuwanie encji
	MANAGE — zawiera wszystko
	 */

    public static final String VIEW = "VIEW_PRIVILEGE";
    public static final String ANALYSE = "ANALYSE_PRIVILEGE";
    public static final String CREATE = "CREATE_PRIVILEGE";
    public static final String EDIT = "EDIT_PRIVILEGE";
    public static final String DELETE = "DELETE_PRIVILEGE";
    public static final String MANAGE = "MANAGE_PRIVILEGE";

    public static final String GAMER_PREFIX = "GAMER";
    public static final String GAMER_VIEW_PRIVILEGE = GAMER_PREFIX + VIEW;
    public static final String GAMER_ANALYSE_PRIVILEGE = GAMER_PREFIX + ANALYSE;
    public static final String GAMER_CREATE_PRIVILEGE = GAMER_PREFIX + CREATE;
    public static final String GAMER_EDIT_PRIVILEGE = GAMER_PREFIX + EDIT;
    public static final String GAMER_DELETE_PRIVILEGE = GAMER_PREFIX + DELETE;
    public static final String GAMER_MANAGE_PRIVILEGE = GAMER_PREFIX + MANAGE;

    private GamerPrivilegeName(){}
}
