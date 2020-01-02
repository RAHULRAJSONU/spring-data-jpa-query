package com.silvertech.springdatajpaquery.persistence;

import com.silvertech.springdatajpaquery.data.model.EmployeeEntity;
import java.util.List;
import java.util.Set;

public interface EmployeeRepositoryCustom {
  List<EmployeeEntity> findEmployeesByEmails(Set<String> email);
}
