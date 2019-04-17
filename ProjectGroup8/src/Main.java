import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String [] args){
        //Member project stuff
        Scanner input = new Scanner(System.in);
        System.out.println("How many Members will be coming?");

        int numberOfMembers = input.nextInt();
        input.skip("[\r\n]");
        Object listOfMembers[] = new Object[10];

        for (int i = 0; i < numberOfMembers; i++){
            System.out.println("Please enter name of member:");
            String name = input.nextLine();
            System.out.println("Please enter the ID of the member");
            String ID = input.nextLine();
            System.out.println("Please enter the phoneNr");
            String phoneNr = input.nextLine();
            Member newMember = new Member(name, ID, phoneNr);
            listOfMembers[i] = newMember.getOutput(name, ID,phoneNr);
        }

        for (int i = 0; i < numberOfMembers; i++){
            System.out.println(listOfMembers[i]);
        }


        //Book project stuff
        Book book1 = new Book ("Path Finder", "23465789", 389.99, "Mac author");

        Book book2 = new Book ("Modern Africa","234165789", 249.99, "Henry Adams", "Jacob luck" );

        Book book3 = new Book("Advanced History", "432435567", 199.99, "Jacob Luck", " Mack Paul", "Albert Henry");

        System.out.println(book1.toString());
        System.out.println(book2.toString());
        System.out.println(book3.toString());



        System.out.println("");
        double totalprice = book1.getPrice() + book2.getPrice() + book3.getPrice();
        System.out.println("Total price: " + totalprice);

    }
}
