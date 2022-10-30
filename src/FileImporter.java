import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Script that imports Fall_2019_Class_File.tsv
 */
public class FileImporter {
    public FileImporter() {
        try (FileReader fr = new FileReader("C:\\Users\\REC\\Desktop\\Fall_2019_Class_File.tsv");
             BufferedReader br = new BufferedReader(fr);
             FileWriter fw = new FileWriter("C:\\Users\\REC\\Desktop\\Fall_2019_Class_File.csv")) {

            String line;
            String newLine = "";
            do {
                line = br.readLine();
                System.out.println(line);

                newLine = "";
                newLine += line.substring(0, 7) + ',';  // Institution Code

                newLine += line.substring(7, 11) + ',';  // Subject Prefix

                newLine += line.substring(14, 18) + ',';  // Course Number

                newLine += line.substring(21, 26) + ',';  // Section Number

                newLine += line.substring(28, 34) + ',';  // Type of Institution

                if (!line.startsWith("      ", 34)) {
                    newLine += line.substring(34, 40) + ',';  // Other Higher Education Site
                } else {
                    newLine += ',';
                }

                if (!line.startsWith("  ", 41)) {
                    newLine += line.substring(41, 43) + ',';  // Composite Class Code
                } else {
                    newLine += ',';
                }


                newLine += line.substring(44, 45) + ',';  // Tenure

                if (!line.startsWith("     ", 47)) {
                    newLine += line.substring(47,52) + ',';  // Zip Code
                } else {
                    newLine += ',';
                }

                newLine += line.substring(52);  //

                System.out.println(newLine);
                break;
            } while (line != null);

            fw.write(newLine);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
