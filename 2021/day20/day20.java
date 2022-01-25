import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class day20 {
    public static String readAlgorithm(String filename) {
        String algorithm = "";
        try {
            Scanner sc = new Scanner(new File(filename));
            algorithm = sc.nextLine();
            sc.close();
        } catch (Exception e) {
            System.out.println("Something went wrong " + e);
        }
        return algorithm;
    }

    public static ArrayList<ArrayList<Character>> readImage(String filename) {
        ArrayList<ArrayList<Character>> image = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(filename));
            sc.nextLine();
            sc.nextLine();
            sc.useDelimiter("");
            while (sc.hasNextLine()) {
                ArrayList<Character> line = stringToList(sc.nextLine());
                image.add(line);
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Something went wrong " + e);
        }
        return image;
    }

    public static ArrayList<ArrayList<Character>> surroundWithDots(ArrayList<ArrayList<Character>> image) {
        ArrayList<ArrayList<Character>> newImage = new ArrayList<>();
        newImage.add(new ArrayList<Character>());
        for (ArrayList<Character> line : image) {
            ArrayList<Character> newLine = new ArrayList<>();
            newLine.add('.');
            for (char c : line) {
                newLine.add(c);
            }
            newLine.add('.');
            newImage.add(newLine);
        }
        newImage.add(new ArrayList<Character>());
        for (int i = 0; i < newImage.get(1).size(); i++) {
            newImage.get(0).add('.');
            newImage.get(newImage.size() - 1).add('.');
        }
        return newImage;
    }

    public static ArrayList<ArrayList<Character>> surroundWithHash(ArrayList<ArrayList<Character>> image) {
        ArrayList<ArrayList<Character>> newImage = new ArrayList<>();
        newImage.add(new ArrayList<Character>());
        for (ArrayList<Character> line : image) {
            ArrayList<Character> newLine = new ArrayList<>();
            newLine.add('#');
            for (char c : line) {
                newLine.add(c);
            }
            newLine.add('#');
            newImage.add(newLine);
        }
        newImage.add(new ArrayList<Character>());
        for (int i = 0; i < newImage.get(1).size(); i++) {
            newImage.get(0).add('#');
            newImage.get(newImage.size() - 1).add('#');
        }
        return newImage;
    }

    public static ArrayList<Character> stringToList(String line) {
        ArrayList<Character> result = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            result.add(line.charAt(i));
        }
        return result;
    }

    public static int getIndex(ArrayList<ArrayList<Character>> image, int x, int y, String algorithm) {
        StringBuilder stringIndex = new StringBuilder();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                stringIndex.append(image.get(y + i).get(x + j));
            }
        }
        String binaryString = stringIndex.toString().replace("#", "1").replace(".", "0");
        return Integer.parseInt(binaryString, 2);
    }

    public static void part1() {
        String algorithm = readAlgorithm("data1.txt");
        ArrayList<ArrayList<Character>> image = readImage("data1.txt");
        int var = 101;
        for (int i = 0; i < var; i++) {
            image = surroundWithDots(image);
        }

        for (int i = 0; i < 50; i++) {
            if (i%2==0){
                image = surroundWithDots(image);
            } else {
                image = surroundWithHash(image);
            }
            ArrayList<ArrayList<Character>> tmpImage = new ArrayList<>();
            for (ArrayList<Character> line : image) {
                ArrayList<Character> tmpLine = new ArrayList<>();
                for (char c : line){
                    tmpLine.add(c);
                }
                tmpImage.add(tmpLine);
            }
            for (int y = 1; y < tmpImage.size() - 1; y++) {
                for (int x = 1; x < tmpImage.get(0).size() - 1; x++) {
                    tmpImage.get(y).set(x, algorithm.charAt(getIndex(image, x, y, algorithm)));
                }
            }
            //print(tmpImage);
            image = tmpImage;
            int count = 0;
            for (int y = var; y < tmpImage.size() - var; y++) {
                for (int x = var; x < tmpImage.get(0).size() - var; x++) {
                    if (tmpImage.get(y).get(x) == '#'){
                        count++;
                    }
                }
            }
            System.out.println(count);
        }
    }

    public static void print(ArrayList<ArrayList<Character>> image){
        for (ArrayList<Character> line : image) {
            for (char c : line) {
                System.out.print(c);
            }
            System.out.println("");
        }
    }
    public static void main(String[] args) {
        part1();
    }
}