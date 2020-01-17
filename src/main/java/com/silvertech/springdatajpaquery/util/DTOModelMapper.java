package com.silvertech.springdatajpaquery.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collections;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

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
public class DTOModelMapper extends RequestResponseBodyMethodProcessor {

  private static final ModelMapper modelMapper = new ModelMapper();

  private EntityManager entityManager;

  public DTOModelMapper(ObjectMapper objectMapper, EntityManager entityManager){
    super(Collections.singletonList(new MappingJackson2HttpMessageConverter(objectMapper)));
    this.entityManager = entityManager;
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(DTO.class);
  }

  @Override
  protected void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
    binder.validate();
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    Object dto = super.resolveArgument(parameter, mavContainer,webRequest,binderFactory);
    Object id = getEntityId(dto);
    return modelMapper.map(dto, parameter.getParameterType());
  }

  @Override
  protected <T> Object readWithMessageConverters(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType)
      throws HttpMessageNotReadableException, IOException, HttpMediaTypeNotSupportedException {
    for(Annotation ann: parameter.getParameterAnnotations()){
      DTO dtoType = AnnotationUtils.getAnnotation(ann, DTO.class);
      if(dtoType != null){
        return super.readWithMessageConverters(inputMessage, parameter, dtoType.value());
      }
    }
    throw new RuntimeException();
  }

  private Object getEntityId(@NotNull Object dto){
    for (Field field: dto.getClass().getDeclaredFields()){
      if(field.getAnnotation(Id.class) != null){
        try{
          field.setAccessible(true);
          return field.get(dto);
        }catch (IllegalAccessException e){
          throw new RuntimeException(e);
        }
      }
    }
    return null;
  }
}
