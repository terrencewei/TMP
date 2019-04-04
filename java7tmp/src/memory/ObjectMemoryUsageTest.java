package memory;

public class ObjectMemoryUsageTest {
    public static void main(String[] s) throws Exception {
        long size = new ClassIntrospector().introspect(new MyObject()).getDeepSize();
        System.out.println(size / 1000 / 1000 + "MB");
    }



    private static class MyObject {
        MyObjectElement[] array = new MyObjectElement[260000];



        public MyObject() {
            for (int i = 0; i < 260000; i++) {
                array[i] = new MyObjectElement();
            }
        }
    }

    private static class MyObjectElement {
        private String value = "terrencewwei@asidjasidjsa.com";
    }

}

