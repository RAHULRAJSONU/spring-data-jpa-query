package com.silvertech.springdatajpaquery.persistence.impl;

import com.silvertech.springdatajpaquery.data.model.EmployeeEntity;
import com.silvertech.springdatajpaquery.persistence.EmployeeRepositoryCustom;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<EmployeeEntity> findEmployeesByEmails(Set<String> emails) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<EmployeeEntity> query = cb.createQuery(EmployeeEntity.class);
    Root<EmployeeEntity> employee = query.from(EmployeeEntity.class);
    Path<String> emailPath = employee.get("email");

    List<Predicate> predicates = new ArrayList<>();
    for(String email: emails){
      predicates.add(cb.like(emailPath,email));
    }
    query.select(employee)
        .where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
    return entityManager.createQuery(query).getResultList();
  }
}
