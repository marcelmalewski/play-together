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

	public static final String VIEW = "_VIEW_PRIVILEGE";
	public static final String ANALYSE = "_ANALYSE_PRIVILEGE";
	public static final String CREATE = "_CREATE_PRIVILEGE";
	public static final String EDIT = "_EDIT_PRIVILEGE";
	public static final String DELETE = "_DELETE_PRIVILEGE";
	public static final String MANAGE = "_MANAGE_PRIVILEGE";

	public static final String PRINCIPLE = "PRINCIPLE";

	public static final String GAMER_PREFIX = "GAMER";
	public static final String GAMER_VIEW_PRIVILEGE = GAMER_PREFIX + VIEW;
	public static final String GAMER_ANALYSE_PRIVILEGE = GAMER_PREFIX + ANALYSE;
	public static final String GAMER_CREATE_PRIVILEGE = GAMER_PREFIX + CREATE;
	public static final String GAMER_EDIT_PRIVILEGE = GAMER_PREFIX + EDIT;
	public static final String GAMER_DELETE_PRIVILEGE = GAMER_PREFIX + DELETE;
	public static final String GAMER_MANAGE_PRIVILEGE = GAMER_PREFIX + MANAGE;

	public static final String GAMER_PRIVATE_DATA_PREFIX = "GAMER_PRIVATE";
	public static final String GAMER_PRIVATE_DATA_VIEW_PRIVILEGE = GAMER_PRIVATE_DATA_PREFIX + VIEW;


	public static final String MODERATOR_PREFIX = "MODERATOR";
	public static final String MODERATOR_VIEW_PRIVILEGE = MODERATOR_PREFIX + VIEW;
	public static final String MODERATOR_DELETE_PRIVILEGE = MODERATOR_PREFIX + DELETE;


	private GamerPrivilegeName() {
	}
}
