package com;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toddfast.util.convert.TypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class MyCompany {

    private static volatile MyCompany instance;

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static Set<Class<?>> ret = new HashSet<Class<?>>() {
        {
            add(Boolean.class);
            add(String.class);
            add(Character.class);
            add(Byte.class);
            add(Short.class);
            add(Integer.class);
            add(Long.class);
            add(Float.class);
            add(Double.class);
            add(Void.class);
        }
    };

    private static Logger logger = LogManager.getRootLogger();

    @Property(propertyName = "com.mycompany.owner", defaultValue = "default value")
    private String myCompanyOwner;

    @Property(propertyName = "com.mycompany.years.old")
    private int old;

    @Property(propertyName = "com.mycompany.address")
    private Address address;

    @Property(propertyName = "com.mycompany.name")
    private String myCompanyName;

    @Override
    public String toString() {
        return "com.MyCompany{" +
                "myCompanyName='" + myCompanyName + '\'' +
                ", myCompanyOwner='" + myCompanyOwner + '\'' +
                ", address=" + address +
                '}';
    }

    private MyCompany(String propertyPath) {
        doRefresh(propertyPath);
    }

    public static MyCompany getInstance(String propertyPath) {
        MyCompany localInstance = instance;
        if (localInstance == null) {
            synchronized (MyCompany.class) {
                localInstance = instance;
                if (localInstance == null) {

                        instance = localInstance = new MyCompany(propertyPath);

                }
            }
        }
        return localInstance;
    }

    private static  <T> T castObject(Class<T> to, String from) throws JsonProcessingException {
        if (ret.contains(to)) {
            return (T) TypeConverter.convert(to, from);
        } else {
            return objectMapper.readValue(from, to);
        }
    }

    public synchronized void doRefresh(String propertyPath) {
        try {
            Properties properties = new Properties();
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(propertyPath);
            assert is != null;
            properties.load(is);
            for (Field field : this.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Property.class)) {
                    field.setAccessible(true);
                    String propName = properties.getProperty(field.getAnnotation(Property.class).propertyName());
                    if (propName == null) {
                        propName = field.getAnnotation(Property.class).defaultValue();
                    }
                    field.set(this, castObject(field.getType(), propName));
                }
            }
        } catch (IOException | IllegalAccessException e) {
            logger.error(e.getMessage());
        }
    }


}
