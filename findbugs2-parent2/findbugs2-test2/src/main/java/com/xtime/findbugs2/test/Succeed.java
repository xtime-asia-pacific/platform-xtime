package com.xtime.findbugs2.test;

import org.hibernate.SessionFactory;

/**
 * Created by rmohammed on 8/29/16.
 * Copyright (c) 2015 Xtime, Inc.  All rights reserved.
 */
public final class Succeed {

    private SessionFactory _sessionFactory;

    public void succeed() {
        _sessionFactory.getAllClassMetadata();
    }

}
