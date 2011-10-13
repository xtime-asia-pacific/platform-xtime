package com.xtime.xannotations.test.xannotations;


import org.junit.Test;
import com.xtime.xannotations.DataModel;
/**
 * $Id: //development/xtime/trunk/xannotations/src/test/java/com/xtime/xannotations/test/xannotations/TestXAnnotation.java#1 $
 * $Author: savadhanula $
 * <p/>
 * Description goes here
 */
@DataModel
public class TestXAnnotation{

    public TestXAnnotation()
    {}

    @Test
    public void testXAnnotation()
    {}

}

@DataModel
class  BasePerson
{
 public BasePerson()
 {}

}

@DataModel
class BaseAccount
{
    public BaseAccount()
    {}
}

class BaseVehicle
{
    public BaseVehicle()
    {}
}

@DataModel
class BaseUser
{
    public BaseUser()
    {}
}
