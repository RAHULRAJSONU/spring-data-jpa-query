package com.silvertech.springdatajpaquery.business;

import com.silvertech.springdatajpaquery.data.model.EmployeeEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface EmployeeService {
  List<EmployeeEntity> findAllActiveEmployeesUsingQuery();
  List<EmployeeEntity> findAllActiveEmployeesUsingNativeQuery();
  List<EmployeeEntity> findAllSorted(Sort sort);
  Page<EmployeeEntity> findAllEmployeeWithPagination(Pageable pageable);
  Page<EmployeeEntity> findAllEmployeeWithPaginationByNativeQuery(Pageable pageable);
}
