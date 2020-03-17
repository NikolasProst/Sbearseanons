public class MyCompany {

    public static final MyCompany company = new  MyCompany();

    @Property(propertyName = "com.mycompany.name")
    private String myCompanyName;

    @Property(propertyName = "com.mycompany.owner", default= "I am owner.")
    private String myCompanyOwner;

    @Property(propertyName = "com.mycompany.address")
    private Address address;

    public static synchronized void doRefresh(){

    }
}
