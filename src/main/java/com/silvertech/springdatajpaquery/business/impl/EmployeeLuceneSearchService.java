package com.silvertech.springdatajpaquery.business.impl;

import com.silvertech.springdatajpaquery.business.LuceneSearchService;
import com.silvertech.springdatajpaquery.data.entity.EmployeeEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Service;

/*
 * spring-data-jpa-query
 * MIT License
 *
 * Copyright (c) 2020 Rahul Raj
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
@Service
public class EmployeeLuceneSearchService implements LuceneSearchService<EmployeeEntity> {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public FullTextEntityManager fullTextEntityManager() {
    return Search.getFullTextEntityManager(entityManager);
  }

  @Override
  public QueryBuilder getQueryBuilder() {
    org.hibernate.search.query.dsl.QueryBuilder queryBuilder;
    queryBuilder = this.fullTextEntityManager().getSearchFactory()
        .buildQueryBuilder()
        .forEntity(EmployeeEntity.class)
        .get();
    return queryBuilder;
  }

  @Override
  public List<EmployeeEntity> search(String str) {
    org.apache.lucene.search.Query query;
    query = this.getQueryBuilder()
        .keyword()
        .wildcard()
        .onFields("firstName","lastName","email","gender","ipAddress","status")
        .matching(str)
        .createQuery();
    return this.fullTextEntityManager().createFullTextQuery(query, EmployeeEntity.class)
        .getResultList();
  }
}