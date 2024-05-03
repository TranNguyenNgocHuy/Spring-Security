package com.example.DemoSecurity.utils;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Component
public class GetPrimaryKey<T> {
    private final Map<Object, String> primaryKeys = new HashMap<>();

    public Object getPrimaryKey (T entity) {
        Field[] fields = entity.getClass().getDeclaredFields();
        Object objectValue = null;
        if (fields.length > 0) {
            for (Field field : fields) {
                if (field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(Embeddable.class)) {
                    try {
                        field.setAccessible(true);
                        objectValue = field.get(entity);
                        System.out.println(objectValue);
                        primaryKeys.put(objectValue, field.getName());
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        return objectValue;
    }

    public String getPrimaryKeysField(T entity){
        return primaryKeys.get(entity);
    }
}
