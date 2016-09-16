package com.xtime.findbugs2.test.test;

import com.xtime.findbugs2.test.Fail;
import com.xtime.findbugs2.test.Succeed;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

/**
 * Created by rmohammed on 8/30/16.
 * Copyright (c) 2015 Xtime, Inc.  All rights reserved.
 */
public class FindbugsTest {

    @Test
    public void test() throws IOException {
        File file = new File(System.getProperty("buildDirectory"), "findbugs.xml");
        Assert.assertTrue("Did not find " + file, file.exists());

        StringBuilder builder = new StringBuilder();
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
        try {
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } finally {
            reader.close();
        }

        String fileString = builder.toString();
        Assert.assertTrue("Did not find fail " + Fail.class.getName(), fileString.contains(Fail.class.getName()));
        Assert.assertFalse("Found succeed " + Succeed.class.getName(), fileString.contains(Succeed.class.getName()));

        for (Method method : Fail.class.getDeclaredMethods()) {
            Assert.assertTrue("Did not find fail method " + method.getName(), fileString.contains(method.getName()));
        }
    }
}
