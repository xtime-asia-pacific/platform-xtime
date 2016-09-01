package com.xtime.findbugs2.test;

import org.hibernate.SessionFactory;

/**
 * Created by rmohammed on 8/29/16.
 * Copyright (c) 2015 Xtime, Inc.  All rights reserved.
 */
public final class Fail {

    private SessionFactory _sessionFactory;

    public void openSession() {
        _sessionFactory.openSession().createCriteria(Fail.class);
    }

    public void withOptions() {
        _sessionFactory.withOptions().openSession();
    }

    public void withStatelessOptions() {
        _sessionFactory.withStatelessOptions().openStatelessSession();
    }

    public void openStatelessSession() {
        _sessionFactory.openStatelessSession();
    }

    public void openStatelessSession2() {
        _sessionFactory.openStatelessSession(null);
    }

    public void close() {
        _sessionFactory.close();
    }

    public void getCache() {
        _sessionFactory.getCache();
    }

    public void evict() {
        _sessionFactory.evict(Fail.class);
    }

    public void evict2() {
        _sessionFactory.evict(Fail.class, null);
    }

    public void evictEntity() {
        _sessionFactory.evictEntity(null);
    }

    public void evictEntity2() {
        _sessionFactory.evictEntity(null, null);
    }

    public void evictCollection() {
        _sessionFactory.evictCollection(null);
    }

    public void evictCollection2() {
        _sessionFactory.evictCollection(null, null);
    }

    public void evictQueries() {
        _sessionFactory.evictQueries(null);
    }

    public void evictQueries2() {
        _sessionFactory.evictQueries();
    }
}
