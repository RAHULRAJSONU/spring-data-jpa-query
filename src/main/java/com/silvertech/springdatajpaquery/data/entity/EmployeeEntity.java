package com.silvertech.springdatajpaquery.data.entity;

import com.silvertech.springdatajpaquery.data.converter.ActivityConverter;
import com.silvertech.springdatajpaquery.data.model.Activity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

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
@Entity
@Table(name = "employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Indexed
public class EmployeeEntity implements Serializable {
  @Id
  private Long id;
  @Field(index= Index.YES, analyze= Analyze.NO, store= Store.NO)
  private String firstName;
  @Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
  private String lastName;
  @Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
  private String email;
  @Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
  private String gender;
  @Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
  private String ipAddress;
  @Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
  private Boolean status;
  @Convert(converter = ActivityConverter.class)
  private List<Activity> activity;
}
