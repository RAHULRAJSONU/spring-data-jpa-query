package com.silvertech.springdatajpaquery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.silvertech.springdatajpaquery.business.EmployeeService;
import com.silvertech.springdatajpaquery.business.impl.EmployeeLuceneSearchService;
import com.silvertech.springdatajpaquery.data.entity.EmployeeEntity;
import com.silvertech.springdatajpaquery.data.model.EmployeeCreateRequest;
import com.silvertech.springdatajpaquery.util.DTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
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
@RestController
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;
  @Autowired
  private EmployeeLuceneSearchService employeeSearchService;

  @GetMapping("/activeUsers/{method}")
  public ResponseEntity<List<EmployeeEntity>> activeUsers(@PathVariable String method){
    List<EmployeeEntity> employeeList = null;
    switch (method){
      case "query": employeeList = employeeService.findAllActiveEmployeesUsingQuery();break;
      case "native-query": employeeList = employeeService.findAllActiveEmployeesUsingNativeQuery();break;
      default:employeeList = Collections.emptyList();
    }
    return new ResponseEntity<>(employeeList,HttpStatus.ACCEPTED);
  }

  @GetMapping("/allEmployee/sortedBy/{sortedBy}")
  @ApiOperation(value = "Get List of All Employee", notes = "Returns employee list in Sorted manner")
  public ResponseEntity<List<EmployeeEntity>> allEmployee(
      @ApiParam(value = "field name by which you want to sort the result") @PathVariable String sortedBy){
    return new ResponseEntity<>(employeeService.findAllSorted(Sort.by(Direction.ASC, sortedBy)),HttpStatus.ACCEPTED);
  }

  @GetMapping("/allEmployee/page/{queryType}")
  @ApiOperation(value = "Get List of All Employee", notes = "Return employee list with pagination \n Request may contains: ?page=number&size=number&sort=field,Sorting order: ASC OR DESC")
  public ResponseEntity<Page<EmployeeEntity>> allEmployeeWithPagination(
      @ApiIgnore @PageableDefault(size=10,page=10,sort="id",direction = Direction.ASC) Pageable pageable,
      @RequestParam String page,
      @RequestParam String size,
      @ApiParam(value = "Provide the field name on which you want to sort the result.\n you can provide sorting order after sort separated by comma ex: id,ASC|DESC") @RequestParam String sort){
    Page<EmployeeEntity> employees = employeeService.findAllEmployeeWithPagination(pageable);
    return new ResponseEntity<>(employees, HttpStatus.ACCEPTED);
  }

  @PostMapping("/addEmployee")
  public ResponseEntity<Boolean> addEmployee(@RequestBody EmployeeCreateRequest employeeCreateRequest){
    ModelMapper mm = new ModelMapper();
    EmployeeEntity ee= mm.map(employeeCreateRequest, EmployeeEntity.class);
    employeeService.addNew(ee);
    return new ResponseEntity<>(Boolean.TRUE,HttpStatus.ACCEPTED);
  }

  @PostMapping("/search-by-emails")
  @ApiOperation(value = "Get List of All Employee", notes = "<b>Return:</b> List of employee\n<b>Technique</b>: Criteria API")
  public ResponseEntity<List<EmployeeEntity>> searchAllByEmails(@RequestBody Set<String> emails){
    return new ResponseEntity<>(employeeService.findEmployeesByEmails(emails), HttpStatus.ACCEPTED);
  }

  @GetMapping("/search")
  @ApiOperation(value = "Get List of All Employee", notes = "<b>Return:</b> List of employee matching with the input string\n<b>Technique</b>: Lucene Search API")
  public ResponseEntity<List<EmployeeEntity>> search(@RequestParam String searchStr){
    return new ResponseEntity<>(employeeSearchService.search(searchStr),HttpStatus.ACCEPTED);
  }

  @GetMapping("/persist-activity")
  public boolean writeActivity(){
    return employeeService.writeActivity();
  }
}
