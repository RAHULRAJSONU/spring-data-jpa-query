package com.silvertech.springdatajpaquery.data.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.silvertech.springdatajpaquery.data.model.Activity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.AttributeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class ActivityConverter implements AttributeConverter<List<Activity>, String> {

  ObjectMapper objectMapper = new ObjectMapper();

  Logger logger = LoggerFactory.getLogger(ActivityConverter.class);

  @Override
  public String convertToDatabaseColumn(List<Activity> activities) {
    String activityJson = null;
    try {
      activityJson = objectMapper.writeValueAsString(activities);
    } catch (final JsonProcessingException e) {
      logger.error("JSON writing error", e);
    }
    return activityJson;
  }

  @Override
  public List<Activity> convertToEntityAttribute(String s) {
    ArrayList<Activity> activity = new ArrayList<>();
    if(null != s){
      try {
        activity = objectMapper.readValue(s,ArrayList.class);
      }catch (final IOException e){
        logger.error("JSON reading error ",e);
      }
    }
    return activity;
  }
}
