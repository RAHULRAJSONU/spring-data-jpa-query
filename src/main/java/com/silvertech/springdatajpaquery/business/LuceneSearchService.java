package com.silvertech.springdatajpaquery.business;

import java.util.List;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;

public interface LuceneSearchService<T> {
  FullTextEntityManager fullTextEntityManager();
  QueryBuilder getQueryBuilder();
  List<T> search(String str);
}
