import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListMaker {
    private static ArrayList<String> myList = new ArrayList<>();
    private static Scanner in = new Scanner(System.in);
    private static boolean needsToBeSaved = false;
    private static String currentFileName = null;

    public static void main(String[] args) {
        String menuChoice;
        boolean quit = false;

        do {
            displayListWithMenu();
            menuChoice = SafeInput.getRegExString(in,
                    "Enter your choice", "[AaDdIiMmOoSsCcVvQq]").toUpperCase();

            try {
                switch (menuChoice) {
                    case "A": addItem(); break;
                    case "D": deleteItem(); break;
                    case "I": insertItem(); break;
                    case "M": moveItem(); break;
                    case "O": openFile(); break;
                    case "S": saveFile(); break;
                    case "C": clearList(); break;
                    case "V": printList(); break;
                    case "Q": quit = quitProgram(); break;
                }
            } catch (IOException e) {
                System.out.println("File operation error: " + e.getMessage());
            }
        } while (!quit);

        System.out.println("Thank you for using File List Maker.");
    }

    private static void displayListWithMenu() {
        System.out.println("\n--- Your Current List ---");

        if (myList.isEmpty()) System.out.println("[Empty]");
        else {
            for (int i = 0; i < myList.size(); i++) {
                System.out.printf("%3d: %s\n", i + 1, myList.get(i));
            }
        }

        System.out.println("\nAvailable Menu:");
        System.out.println("A – Add item");
        System.out.println("D – Delete item");
        System.out.println("I – Insert item");
        System.out.println("M – Move item");
        System.out.println("O – Open file");
        System.out.println("S – Save file");
        System.out.println("C – Clear list");
        System.out.println("V – View list");
        System.out.println("Q – Quit");
    }

    private static void addItem() {
        String item = SafeInput.getNonZeroLenString(in, "Enter the item to add");
        myList.add(item);
        needsToBeSaved = true;
        System.out.println("Item added.");
    }

    private static void deleteItem() {
        if (myList.isEmpty()) {
            System.out.println("List is empty.");
            return;
        }
        int index = SafeInput.getRangedInt(in, "Enter item number to delete", 1, myList.size());
        String removed = myList.remove(index - 1);
        needsToBeSaved = true;
        System.out.println("Removed: " + removed);
    }

    private static void insertItem() {
        String item = SafeInput.getNonZeroLenString(in, "Enter item to insert");
        int index = SafeInput.getRangedInt(in, "Enter position (1 to " + (myList.size() + 1) + ")", 1, myList.size() + 1);
        myList.add(index - 1, item);
        needsToBeSaved = true;
        System.out.println("Item inserted.");
    }

    private static void moveItem() {
        if (myList.size() < 2) {
            System.out.println("Need at least 2 items to move.");
            return;
        }
        int from = SafeInput.getRangedInt(in, "Move FROM position", 1, myList.size()) - 1;
        int to = SafeInput.getRangedInt(in, "Move TO position", 1, myList.size()) - 1;

        String item = myList.remove(from);
        myList.add(to, item);
        needsToBeSaved = true;
        System.out.println("Item moved.");
    }

    private static void clearList() {
        if (SafeInput.getYNConfirm(in, "Clear the list?")) {
            myList.clear();
            needsToBeSaved = true;
            System.out.println("List cleared.");
        }
    }

    private static void printList() {
        System.out.println("\n--- Current List ---");
        if (myList.isEmpty()) System.out.println("[Empty]");
        else {
            for (int i = 0; i < myList.size(); i++) {
                System.out.printf("%3d: %s\n", i + 1, myList.get(i));
            }
        }
    }

    private static void saveFile() throws IOException {
        if (currentFileName == null) {
            currentFileName = SafeInput.getNonZeroLenString(in, "Enter file name to save") + ".txt";
        }

        Path filePath = Paths.get(currentFileName);
        Files.write(filePath, myList);
        needsToBeSaved = false;
        System.out.println("List saved to " + currentFileName);
    }

    private static void openFile() throws IOException {
        if (needsToBeSaved) {
            if (SafeInput.getYNConfirm(in, "Unsaved changes exist. Save first?")) {
                saveFile();
            }
        }

        String fileName = SafeInput.getNonZeroLenString(in, "Enter file name to open") + ".txt";
        Path filePath = Paths.get(fileName);
        List<String> lines = Files.readAllLines(filePath);
        myList = new ArrayList<>(lines);
        currentFileName = fileName;
        needsToBeSaved = false;
        System.out.println("Loaded file: " + fileName);
    }

    private static boolean quitProgram() throws IOException {
        if (needsToBeSaved) {
            if (SafeInput.getYNConfirm(in, "Unsaved changes. Save before quitting?")) {
                saveFile();
            }
        }
        return SafeInput.getYNConfirm(in, "Are you sure you want to quit?");
    }
}
