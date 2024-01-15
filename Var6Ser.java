import java.io.*;
import java.util.Scanner;

/*Вариант 4
Записать в исходный файл информацию о товарах:
Наименование, производитель, количество_единиц, цена
Количество объектов товаров задать с клавиатуры.
Создать программным способом другой файл и переписать
в него информацию о товарах ценой выше 1000 руб*/

class Product implements Serializable {
    String name;
    String manufacturer;
    int count;
    int price;
}

public class Var4Ser {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in,"cp1251");
        // создается файл на диске
        File f1 = new File("D:\\Java\\lab7.txt");
        f1.createNewFile();
        File f2 = new File("D:\\Java\\lab71.txt");
        f2.createNewFile();

        FileOutputStream fos = new FileOutputStream(f1);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        System.out.println("Введите количество товаров => ");
        int count = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < count; i++) {

            Product product = new Product();
            System.out.println("Введите информацию о товаре №" + (i + 1) + ": ");
            System.out.print("Название продукта => ");
            product.name = sc.nextLine();
            System.out.print("Производитель продукта => ");
            product.manufacturer = sc.nextLine();
            System.out.print("Количество единиц => ");
            product.count = sc.nextInt();
            sc.nextLine();
            System.out.print("Цена => ");
            product.price = sc.nextInt();
            sc.nextLine();

            oos.writeObject(product);
        }

        oos.flush();
        oos.close();


        FileInputStream fis = new FileInputStream(f1);
        ObjectInputStream oin = new ObjectInputStream(fis);
        fos = new FileOutputStream(f2);
        oos = new ObjectOutputStream(fos);
        try {
            while (true) {
                Product product = (Product) oin.readObject();
                PrintStream ps = new PrintStream(System.out, true, "cp1251");
                ps.println(product.name + " " + product.manufacturer + " " + product.count + " " + product.price);
                if (product.price > 1000) {
                    oos.writeObject(product);
                }
            }
        }
        catch (EOFException e) {
        }

        oin.close();
        oos.flush();
        oos.close();
    }
}