package second.task;

public class main {
    public static void main(String[] args) {
        var st = new SecondTask("src/main/resources/text.txt");
        st.readTextFromFile();
        System.out.println(st.SelectAllTrio());
    }
}
