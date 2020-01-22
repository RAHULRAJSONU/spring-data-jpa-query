package com.silvertech.springdatajpaquery.business;

import com.silvertech.springdatajpaquery.data.entity.EmployeeEntity;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface EmployeeService {
  List<EmployeeEntity> findAllActiveEmployeesUsingQuery();
  List<EmployeeEntity> findAllActiveEmployeesUsingNativeQuery();
  List<EmployeeEntity> findAllSorted(Sort sort);
  Page<EmployeeEntity> findAllEmployeeWithPagination(Pageable pageable);
  Page<EmployeeEntity> findAllEmployeeWithPaginationByNativeQuery(Pageable pageable);
  EmployeeEntity addNew(EmployeeEntity ee);
  List<EmployeeEntity> findEmployeesByEmails(final Set<String> emails);
  boolean writeActivity();
}
