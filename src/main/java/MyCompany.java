import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Properties;

public class MyCompany {

    public static final MyCompany company = new  MyCompany();

    @Property(propertyName = "com.mycompany.name")
    private String myCompanyName;

    @Property(propertyName = "com.mycompany.owner")
    private String myCompanyOwner;

    @Property(propertyName = "com.mycompany.address")
    private Address address;

    private MyCompany() {
        FileInputStream fis;
        Properties property = new Properties();
        Class<? extends MyCompany> cls = company.getClass();
        Annotation[] a = cls.getAnnotations();
        for(int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
        try {
            /** чтение файла properties */
            fis = new FileInputStream("my.properties");
            property.load(fis);

            System.out.println(property.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void doRefresh(){

    }

    public static void main(String[] args) {

    }
}
