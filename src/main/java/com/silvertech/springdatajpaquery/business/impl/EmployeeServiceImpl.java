package com.silvertech.springdatajpaquery.business.impl;

import com.silvertech.springdatajpaquery.business.EmployeeService;
import com.silvertech.springdatajpaquery.data.entity.EmployeeEntity;
import com.silvertech.springdatajpaquery.data.model.Activity;
import com.silvertech.springdatajpaquery.data.projection.EmployeeProjection;
import com.silvertech.springdatajpaquery.persistence.EmployeeRepository;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/*
 * spring-data-jpa-query
 * MIT License
 *
 * Copyright (c) 2019 Rahul Raj
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
public class EmployeeServiceImpl implements EmployeeService {

  @Autowired
  private EmployeeRepository employeeRepository;

  @Override
  public List<EmployeeEntity> findAllActiveEmployeesUsingQuery() {
    return employeeRepository.findAllActiveEmployeesUsingQuery();
  }

  @Override
  public List<EmployeeEntity> findAllActiveEmployeesUsingNativeQuery() {
    return employeeRepository.findAllActiveEmployeesUsingNativeQuery();
  }

  @Override
  public List<EmployeeEntity> findAllSorted(Sort sort) {
    return employeeRepository.findAll(sort);
  }

  @Override
  public Page<EmployeeEntity> findAllEmployeeWithPagination(Pageable pageable) {
    return employeeRepository.findAllEmployeeWithPagination(pageable);
  }

  @Override
  public Page<EmployeeEntity> findAllEmployeeWithPaginationByNativeQuery(Pageable pageable) {
    return employeeRepository.findAllEmployeeWithPaginationByNativeQuery(pageable);
  }

  @Override
  public EmployeeEntity addNew(EmployeeEntity ee) {
    return employeeRepository.save(ee);
  }

  @Override
  public List<EmployeeEntity> findEmployeesByEmails(Set<String> emails) {
    return employeeRepository.findEmployeesByEmails(emails);
  }

  @Override
  public boolean writeActivity() {
    employeeRepository.findAll()
        .forEach(employeeEntity -> {
          employeeEntity.setActivity(
              Arrays.asList(Activity.builder()
                  .name("Demo Activity")
                  .description("Adding Demo Activity for "+employeeEntity.getFirstName())
                  .timestamp(new Timestamp(new Date().getTime()))
                  .build()));
          employeeRepository.save(employeeEntity);
        });
    return true;
  }

}
