import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class LDAPTest {

    public static void main(String[] aa) throws Exception {
        new LDAPTest().GetADInfo();
    }



    public void GetADInfo() {
        Hashtable HashEnv = new Hashtable();

        //String adminName = "CN=OAWebUser,CN=Users,DC=Hebmc,DC=com";//AD的用户名
        //        String adminName = "AAXISCHINA\\terrecnewei"; //注意用户名的写法：domain\User 或 User@domain.com

        HashEnv.put(Context.SECURITY_AUTHENTICATION, "simple"); //LDAP访问安全级别
        HashEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory"); //LDAP工厂类
        //        HashEnv.put(Context.SECURITY_PRINCIPAL, "terrecnewei@aaxischina.com"); //AD User
        HashEnv.put(Context.PROVIDER_URL, "ldap://172.17.3.118:389");
        HashEnv.put(Context.SECURITY_PRINCIPAL, "cn=Terrence Wei,ou=Users,dc=aaxischina,dc=com"); //AD User
        HashEnv.put(Context.SECURITY_CREDENTIALS, "Dijieshen2"); //AD Password

        try {
            LdapContext ctx = new InitialLdapContext(HashEnv, null);
            SearchControls searchCtls = new SearchControls(); //Create the search controls
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE); //Specify the search scope

            String searchFilter = "objectClass=User"; //specify the LDAP search filter
            //String searchFilter = "objectClass=organizationalUnit";//specify the LDAP search filter

            String searchBase = "DC=Hebmc,DC=com"; //Specify the Base for the search//搜索域节点
            int totalResults = 0;

            //Specify the attributes to return
            //String returnedAtts[] = {"memberOf"};//定制返回属性
            String returnedAtts[] = { "url", "whenChanged", "employeeID", "name", "userPrincipalName",
                    "physicalDeliveryOfficeName", "departmentNumber", "telephoneNumber", "homePhone", "mobile",
                    "department", "sAMAccountName", "whenChanged", "mail" }; //定制返回属性

            searchCtls.setReturningAttributes(returnedAtts); //设置返回属性集

            //Search for objects using the filter
            NamingEnumeration answer = ctx.search(searchBase, searchFilter, searchCtls);

            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();
                System.out.println("************************************************");
                System.out.println(sr.getName());

                Attributes Attrs = sr.getAttributes();
                if (Attrs != null) {
                    try {
                        for (NamingEnumeration ne = Attrs.getAll(); ne.hasMore(); ) {
                            Attribute Attr = (Attribute) ne.next();

                            System.out.println(" AttributeID=" + Attr.getID().toString());

                            //读取属性值
                            for (NamingEnumeration e = Attr.getAll(); e.hasMore(); totalResults++) {
                                System.out.println("    AttributeValues=" + e.next().toString());
                            }
                            System.out.println("    ---------------");

                            //读取属性值
                            Enumeration values = Attr.getAll();
                            if (values != null) { // 迭代
                                while (values.hasMoreElements()) {
                                    System.out.println("    AttributeValues=" + values.nextElement());
                                }
                            }
                            System.out.println("    ---------------");
                        }
                    } catch (NamingException e) {
                        System.err.println("Throw Exception : " + e);
                    }
                }
            }
            System.out.println("Number: " + totalResults);
            ctx.close();
        } catch (NamingException e) {
            e.printStackTrace();
            System.err.println("Throw Exception : " + e);
        }
    }



    public LDAPTest() {
    }
}

