import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Properties;

public class MyCompany {

    private static volatile MyCompany instance = new MyCompany();

    @Property(propertyName = "com.mycompany.name")
    private String myCompanyName;

    @Property(propertyName = "com.mycompany.owner")
    private String myCompanyOwner;

    @Property(propertyName = "com.mycompany.address")
    private Address address;

    private MyCompany() {
        FileInputStream fis;
        Properties property = new Properties();
        Annotation[] a = this.getClass().getAnnotations();
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i].toString());
        }
        try {
            /** чтение файла properties */
            fis = new FileInputStream("my.properties");
            property.load(fis);

            //System.out.println(property.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static synchronized void doRefresh(){

    }

    public static void main(String[] args) {
        MyCompany company = MyCompany.getInstance();
    }
}
