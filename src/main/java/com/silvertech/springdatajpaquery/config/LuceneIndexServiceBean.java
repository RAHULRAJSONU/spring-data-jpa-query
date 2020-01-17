package com.silvertech.springdatajpaquery.config;

import javax.persistence.EntityManagerFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

public class LuceneIndexServiceBean {

    private FullTextEntityManager fullTextEntityManager;

    public LuceneIndexServiceBean(EntityManagerFactory entityManagerFactory){
        fullTextEntityManager = Search.getFullTextEntityManager(entityManagerFactory.createEntityManager());
    }

    public void triggerIndexing() {
        try {
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}