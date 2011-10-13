package com.xtime.xannotations;

/*
 * $Id: //development/xtime/trunk/xannotations/src/main/java/com/xtime/xannotations/DataModel.java#1 $
 * $Author: savadhanula $
 * Marker annotation to denote DataModel class
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;



@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface DataModel
{

}
