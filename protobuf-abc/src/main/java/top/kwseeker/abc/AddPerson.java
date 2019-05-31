package top.kwseeker.abc;

import top.kwseeker.protobuf.AddressBookProtos.AddressBook;
import top.kwseeker.protobuf.AddressBookProtos.Person;

import java.io.*;

public class AddPerson {

    static Person PromptForAddress(BufferedReader stdin, PrintStream stdout) throws IOException {
        Person.Builder person = Person.newBuilder();

        stdout.print("Enter person ID: ");
        person.setId(Integer.valueOf(stdin.readLine()));

        stdout.print("Enter name: ");
        person.setName(stdin.readLine());

        stdout.print("Enter email address (blank for none): ");
        String email = stdin.readLine();
        if (email.length() > 0) {
            person.setEmail(email);
        }

        while (true) {
            stdout.print("Enter a phone number (or leave blank to finish): ");
            String number = stdin.readLine();
            if (number.length() == 0) {
                break;
            }

            Person.PhoneNumber.Builder phoneNumber =
                    Person.PhoneNumber.newBuilder().setNumber(number);

            stdout.print("Is this a mobile, home, or work phone? ");
            String type = stdin.readLine();
            if (type.equals("mobile")) {
                phoneNumber.setType(Person.PhoneType.MOBILE);
            } else if (type.equals("home")) {
                phoneNumber.setType(Person.PhoneType.HOME);
            } else if (type.equals("work")) {
                phoneNumber.setType(Person.PhoneType.WORK);
            } else {
                stdout.println("Unknown phone type.  Using default.");
            }

            person.addPhones(phoneNumber);
        }

        return person.build();
    }

    public static void main(String[] args) throws Exception {
        AddressBook.Builder addressBook = AddressBook.newBuilder(); //生成的类的初始化都是通过Builder
        File file = new File("temp/test.txt");
        try {
            if(!file.exists()) {
                if(!file.createNewFile()) {
                    System.out.println("file create failed");
                    return;
                }
            }
            addressBook.mergeFrom(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            System.out.println(args[0] + ": File not found.  Creating a new file.");
        }

        addressBook.addPeople(
                PromptForAddress(new BufferedReader(new InputStreamReader(System.in)), System.out));
        FileOutputStream output = new FileOutputStream(file);
        addressBook.build().writeTo(output);
        output.close();
    }
}
