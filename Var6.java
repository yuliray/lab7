import java.io.*;
import java.util.IllegalFormatException;
import java.util.Scanner;

/*Вариант 4
Записать в исходный файл информацию о товарах:
Наименование, производитель, количество_единиц, цена
Количество объектов товаров задать с клавиатуры.
Создать программным способом другой файл и переписать
в него информацию о товарах ценой выше 1000 руб*/

public class Var4 {
    private static final int MAX_STRING_LENGTH = 20;

    private static String ReadString(Scanner sc) throws IllegalArgumentException {
        String out = sc.nextLine();
        if (out.length() > MAX_STRING_LENGTH)
            throw new IllegalArgumentException();
        return out;
    }

    private static String ReadFromFileString(RandomAccessFile rf) throws IOException {
        String str = rf.readUTF();
        for (int i = 0; i < MAX_STRING_LENGTH - str.length(); i++)
            rf.readByte();
        return str;
    }
    private static void WriteToFileString(RandomAccessFile rf, String str) throws IOException, IllegalArgumentException {
        if (str.length() > MAX_STRING_LENGTH)
            throw new IllegalArgumentException();
        rf.seek(rf.length());
        rf.writeUTF(str);
        for (int i = 0; i < MAX_STRING_LENGTH - str.length(); i++)
            rf.writeByte(0);
    }

    public static void main(String[] args) throws IOException {
        File file1 = new File("D:\\Java\\result7lab1.txt");
        if (!file1.createNewFile()) {
            file1.delete();
            file1.createNewFile();
        }
        RandomAccessFile raf1 = new RandomAccessFile(file1, "rw");
        Scanner sc = new Scanner(System.in, "cp1251");
        System.out.println("Введите количество товаров => ");
        int count = sc.nextInt();
        sc.nextLine();
        try {
            for (int i = 0; i < count; i++) {
                System.out.println("Введите информацию о товаре №" + (i + 1) + ": ");
                System.out.print("Название продукта => ");
                WriteToFileString(raf1, ReadString(sc));
                System.out.print("Производитель продукта => ");
                WriteToFileString(raf1, ReadString(sc));
                System.out.print("Количество единиц => ");
                raf1.writeInt(sc.nextInt());
                sc.nextLine();
                System.out.print("Цена => ");
                raf1.writeInt(sc.nextInt());
                sc.nextLine();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка!)");
            raf1.close();
            return;
        }
        raf1.close();

        raf1 = new RandomAccessFile(file1, "r");
        File file2 = new File("D:\\Java\\result7lab2.txt");
        if (!file2.createNewFile()) {
            file2.delete();
            file2.createNewFile();
        }
        RandomAccessFile raf2 = new RandomAccessFile(file2, "rw");
        try {
            while (true) {
                String productName = ReadFromFileString(raf1);
                String productManufacturer = ReadFromFileString(raf1);
                int productCount = raf1.readInt();
                int productPrice = raf1.readInt();
                PrintStream ps = new PrintStream(System.out, true, "cp1251");
                ps.println(productName + " " + productManufacturer + " " + productCount + " " + productPrice);
                if (productPrice > 1000) {
                    WriteToFileString(raf2, productName);
                    WriteToFileString(raf2, productManufacturer);
                    raf2.writeInt(productCount);
                    raf2.writeInt(productPrice);
                }
            }
        } catch (EOFException e) {
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка!");
            raf2.close();
            raf1.close();
            return;
        }
        raf2.close();
        raf1.close();
    }
}