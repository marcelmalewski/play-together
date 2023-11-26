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

    public static final String PRINCIPLE = "MANAGE_PRIVILEGE";

    public static final String GAMER_PREFIX = "GAMER";
    public static final String GAMER_VIEW = GAMER_PREFIX + VIEW;
    public static final String GAMER_ANALYSE = GAMER_PREFIX + ANALYSE;
    public static final String GAMER_CREATE = GAMER_PREFIX + CREATE;
    public static final String GAMER_EDIT = GAMER_PREFIX + EDIT;
    public static final String GAMER_DELETE = GAMER_PREFIX + DELETE;
    public static final String GAMER_MANAGE = GAMER_PREFIX + MANAGE;

    public static final String GAMER_PRIVATE_DATA_PREFIX = "GAMER_PRIVATE";
    public static final String GAMER_PRIVATE_DATA_VIEW = GAMER_PRIVATE_DATA_PREFIX + VIEW;


    public static final String MODERATOR_PREFIX = "MODERATOR";
    public static final String MODERATOR_VIEW = MODERATOR_PREFIX + VIEW;
    public static final String MODERATOR_DELETE = MODERATOR_PREFIX + DELETE;


    private GamerPrivilegeName(){}
}
