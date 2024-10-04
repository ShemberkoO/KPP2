package second.task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecondTask {
    String text;
    String fileName = "text.txt";

    public SecondTask(String fileName) {
        this.fileName = fileName;
    }

    public boolean readTextFromFile() {
        StringBuilder contentBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n"); // Додаємо рядок до вмісту
            }
            text = contentBuilder.toString();
            return true; // Успішно прочитано
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Помилка при читанні
        }
    }

    public List<String> SelectAllTrio() {
        List<String> trios = new ArrayList<>();


        String regex = "[А-ЩЬЮЯЄІЇҐ][А-ЩЬЮЯЄІЇҐа-щьюяєіїґ']*\\s+[А-ЩЬЮЯЄІЇҐ][А-ЩЬЮЯЄІЇҐа-щьюяєіїґ']*" +
                "\\s+[А-ЩЬЮЯЄІЇҐ][А-ЩЬЮЯЄІЇҐа-щьюяєіїґ']*";


        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            System.out.println("Found: " + matcher.group());
            trios.add(matcher.group());
        }

        return trios;
    }


}
