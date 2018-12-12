package com.github.kanon.upms.vaildation;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/6
 */
public interface SysMenuValidation {

    String SYS_MENU_PARENT_NOT_EXIST = "{sys.menu.parent.not.exist}";
    String SYS_MENU_CODE_DUPLICATE = "{sys.menu.code.duplicate}";
    String SYS_MENU_NOT_EXIST = "{sys.menu.not.exist}";
    String SYS_MENU_CODE_EMPTY = "{sys.menu.code.empty}";
    String SYS_MENU_NAME_EMPTY = "{sys.menu.name.empty}";
    String SYS_MENU_CHILDREN_EXIST_NOT_REMOVE = "{sys.menu.children.exist.not.remove}";
}
