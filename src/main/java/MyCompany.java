
import com.toddfast.util.convert.TypeConverter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class MyCompany {

    private static volatile MyCompany instance;

    @Property(propertyName = "com.mycompany.name")
    private String myCompanyName;

    @Override
    public String toString() {
        return "MyCompany{" +
                "myCompanyName='" + myCompanyName + '\'' +
                ", myCompanyOwner='" + myCompanyOwner + '\'' +
                ", address=" + address +
                '}';
    }

    @Property(propertyName = "com.mycompany.owner")
    private String myCompanyOwner;

    @Property(propertyName = "com.mycompany.address")
    private Address address;

    private MyCompany() {

//        FileInputStream fis;
//        Properties property = new Properties();
//        /** Получение полей помеченных аннотацией*/
//        Set<Field> a = findFields(this.getClass(), Property.class);
//        for (Field f : a) {
//            System.out.println(f.toString());
//        }
//        try {
//            /** чтение файла properties */
//            fis = new FileInputStream("my.properties");
//            property.load(fis);
//
//            System.out.println(property.toString());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static MyCompany getInstance() {
        MyCompany localInstance = instance;
        if (localInstance == null) {
            synchronized (MyCompany.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new MyCompany();
                }
            }
        }
        return localInstance;
    }

    public void initializationValues(Object object, Class<?> clazz) throws Exception {
        if (object != null) {
            if (clazz.isAnnotationPresent(InitField.class)) {
                Properties properties = new Properties();
                InputStream is = getClass().getClassLoader().getResourceAsStream("my.properties");
                properties.load(is);
                for (Field field : clazz.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Property.class)) {
                        field.setAccessible(true);
                        field.set(object, castObject(field.getType(), properties.getProperty(field.getName())));
                    }
                }
            }
        }
    }

    private <T> T castObject(Class<T> to, Object from) {
        return (T) TypeConverter.convert(to, from);
    }

    public static Set<Field> findFields(Class<?> classs, Class<? extends Annotation> ann) {
        Set<Field> set = new HashSet<>();
        Class<?> c = classs;
        while (c != null) {
            for (Field field : c.getDeclaredFields()) {
                if (field.isAnnotationPresent(ann)) {
                    set.add(field);
                }
            }
            c = c.getSuperclass();
        }
        return set;
    }

    public static synchronized void doRefresh() {

    }


}
