import java.io.*;
import java.util.*;
//main branch
public class PlayerFileProcessor {
    public static void main(String[] args) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(System.in)) {
			int choice;
			do {
                System.out.println("This program reads and sorts files!!!");
			    System.out.println("Choose an option:");
			    System.out.println("1. Show Player Data");
			    System.out.println("2. Sort Player Data");
			    System.out.println("3. Merge Files");
			    System.out.println("4. Exit");
			    System.out.print("Enter your choice: ");
			  
			    choice = scanner.nextInt();
			    scanner.nextLine(); 

			    switch (choice) {
			        case 1:
			            showPlayerData(getFileNameFromUser());
			            break;
			        case 2:
			            sortPlayerData(getFileNameFromUser());
			            break;
			        case 3:
			            mergeFiles(getFileNameFromUser(), getFileNameFromUser(), "mergedFile.txt");
			            break;
			        case 4:
			            System.out.println("Exiting program.");
			            break;                   
			        default:
			            System.out.println("Invalid choice. Please enter a valid option.");
			    }

			} while (choice != 4);
		}
    }

    private static String getFileNameFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the file name: ");
        return scanner.nextLine();
    }

    private static void showPlayerData(String fileName) throws FileNotFoundException {
        File file = new File(fileName);

        try (Scanner input = new Scanner(file)) {
            System.out.printf("%-20s %-15s %-5s %-5s%n", "Player Name", "Team", "GP", "Goals/Assists");
            System.out.println("-------------------------------------------------------");

            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] parts = line.split("\t");

                if (parts.length >= 4) {
                    String playerName = parts[0];
                    String team = parts[1];
                    String value1 = parts[2];
                    String value2 = parts[3];

                    int playerNameWidth = 20;
                    int teamWidth = 15;

                    System.out.printf("%-" + playerNameWidth + "s %-" + teamWidth + "s %-5s %-5s%n", playerName, team, value1, value2);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }



    private static void sortPlayerData(String fileName) throws FileNotFoundException {
        SortingFile sortingFile = new SortingFile(fileName);
        sortingFile.sortAndPrint();
    }

    private static void mergeFiles(String inputFile1, String inputFile2, String outputFile) {
        FileMerger fileMerger = new FileMerger();
        fileMerger.mergeFiles(inputFile1, inputFile2, outputFile);
    }


}

 class SortingFile {
    private TreeMap<String, String> dataset = new TreeMap<>();

    public SortingFile(String fileName) throws FileNotFoundException {
        try (Scanner input = new Scanner(new File(fileName))) {
            while (input.hasNextLine()) {
                String line = input.nextLine();

                String[] parts = line.split("\t");

                if (parts.length >= 3) {
                    String playerName = parts[0].trim();
                    dataset.put(playerName, line);
                }
            }
        }
    }


    public void sortAndPrint() {
        printTable(dataset);
    }

    private void printTable(TreeMap<String, String> map) {
    	System.out.println();
        System.out.printf("%-20s %-15s %-5s %-5s%n", "Player Name", "Team", "GP", "Goals/Assists");
        System.out.println("-------------------------------------------------------");

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String[] parts = entry.getValue().split("\t");
            String playerName = parts[0];
            String team = parts[1];
            String value1 = parts[2];
            String value2 = parts[3];

            System.out.printf("%-20s %-15s %-5s %-5s%n", playerName, team, value1, value2);
        }
    }
}

class FileMerger {
    public void mergeFiles(String inputFile1, String inputFile2, String outputFile) {
        try (Scanner scanner1 = new Scanner(new File(inputFile1));
             Scanner scanner2 = new Scanner(new File(inputFile2));
             PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {


            while (scanner1.hasNextLine()) {
                String line = scanner1.nextLine();
                writer.println(line);
            }


            while (scanner2.hasNextLine()) {
                String line = scanner2.nextLine();
                writer.println(line);
            }

            System.out.println("Files merged successfully. Merged data saved to " + outputFile);

        } catch (FileNotFoundException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error writing merged data to file: " + e.getMessage());
        }
    }
}
