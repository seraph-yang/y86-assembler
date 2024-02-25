import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
  static Instructions instructions = new Instructions();
  public static void main(String[] args) throws IOException {
        String input = "input.txt";
        List<List<String>> instructions = getInstructions(input);
        for(List<String> s:instructions) {
          for(String str:s) {
            System.out.println(str);
          }
        }
    }

    public static List<List<String>> getInstructions(String path) {
        List<List<String>> instructions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                int commentPos = line.indexOf('#');
                if (commentPos != -1) {
                  line = line.substring(0, commentPos);
                }
                line = line.trim();
                if (line.isEmpty()) {
                  continue;
                }
                List<String> lineInstructions = Arrays.asList(line.split("\\s+"));
                instructions.add(lineInstructions);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instructions;
    }
}
