package cn.oddworld;

public class Client {

    public static void main(String[] args) {

        String property = System.getProperty("line.separator");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("c:")
                .append("\\hello")
                .append("\\hlle");
        System.out.println(stringBuilder.toString());
        System.out.println("1"+property+"c:\\hello\\haha\\2");
    }
}
