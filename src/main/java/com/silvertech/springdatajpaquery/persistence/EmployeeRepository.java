package com.silvertech.springdatajpaquery.persistence;

import com.silvertech.springdatajpaquery.data.entity.EmployeeEntity;
import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long>, EmployeeRepositoryCustom {

  @Query("select e from EmployeeEntity e where e.status=1")
  List<EmployeeEntity> findAllActiveEmployeesUsingQuery();

  @Query(
      value = "select * from employee e where e.status=1",
      nativeQuery = true)
  List<EmployeeEntity> findAllActiveEmployeesUsingNativeQuery();

  @Query(value = "select e from EmployeeEntity e")
  List<EmployeeEntity> findAll(Sort sort);

  @Query(value = "select e from EmployeeEntity e")
  Page<EmployeeEntity> findAllEmployeeWithPagination(Pageable pageable);

  @Query(
      value = "select * from employee e",
      countQuery = "select count(*) from employee",
      nativeQuery = true)
  Page<EmployeeEntity> findAllEmployeeWithPaginationByNativeQuery(Pageable pageable);

  @Query("select e from EmployeeEntity e where e.status=?1")
  EmployeeEntity findEmployeeByStatus(Integer status);

  @Query(
      value = "select e from employee e where e.status=?1",
      nativeQuery = true)
  EmployeeEntity findEmployeeByStatusWithNativeQuery(Integer status);

  @Query("select e from EmployeeEntity e where e.firstName=?1")
  EmployeeEntity findEmployeeByFirstName(String firstName);

  @Query(
      value = "select e from employee e where e.first_name=?1",
      nativeQuery = true)
  EmployeeEntity findEmployeeByFirstNameWithNativeQuery(String firstName);

  @Query("select e from EmployeeEntity e where e.firstName= :firstName and e.status= :status")
  EmployeeEntity findEmployeeByFirstNameAndStatus(@Param("firstName") String firstName, @Param("status") Integer status);

  @Query(
      value = "select e from employee e where e.first_name= :firstName and e.status= :status",
      nativeQuery = true)
  EmployeeEntity findEmployeeByFirstNameAndStatusWithNativeQuery(
      @Param("firstName") String firstName,
      @Param("status") Integer status);

  @Query("select e from EmployeeEntity e where e.firstName in :fnames")
  List<EmployeeEntity> findEmployeeInRange(@Param("fnames") Collection<String> firstName);

  @Query(
      value = "select e from employee e where e.first_name in :fnames",
      nativeQuery = true)
  List<EmployeeEntity> findEmployeeInRangeWithNativeQuery(@Param("fnames") Collection<String> firstName);

  @Modifying
  @Query("update EmployeeEntity e set e.status= :status where e.firstName= :firstName")
  int updateEmployeeStatusForName(@Param("firstName") String firstName, @Param("status") Integer status);

  @Modifying
  @Query(
      value = "update employee e set e.status= ?2 where e.first_name= ?1",
      nativeQuery = true)
  int updateEmployeeStatusForNameWithNativeQuery(@Param("firstName") String firstName, @Param("status") Integer status);

}
