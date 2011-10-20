package com.xtime.xannotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * $Id: //development/xtime/trunk/xannotations/src/main/java/com/xtime/xannotations/DAO.java#1 $
 * $Author: savadhanula $
 * $Id: //development/xtime/trunk/xannotations/src/main/java/com/xtime/xannotations/DAO.java#1 $
 * <p/>
 * DAO annotation to generate Cap DAO classes
 */

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface DAO {
}
