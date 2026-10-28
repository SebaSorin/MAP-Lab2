package ui;

import domain.Friendship;
import domain.User;
import domain.validators.ValidationException;
import service.NetworkService;
import service.ServiceException;

import java.util.Scanner;

public class Console {
    private final NetworkService service;
    private final Scanner scanner = new Scanner(System.in);

    public Console(NetworkService service) {
        this.service = service;
    }

    private void printMenu(){
        System.out.println("Alegeti o optiune:");
        System.out.println("0. Iesire");
        System.out.println("1. Afisare utilizatori");
        System.out.println("2. Afisare prietenii");
        System.out.println("3. Adaugare utilizator");
        System.out.println("4. Sterge utilizator");
        System.out.println("5. Adaugare prietenie");
        System.out.println("6. Stergere prietenie");
        System.out.println("7. Afisare numar comunitati");
        System.out.println("8. Afisare cea mai sociabila comunitate");
        System.out.print(">>> ");
    }


    public void run(){
        while (true){
            System.out.println('\n');
            printMenu();
            String option = scanner.nextLine().trim();

            try{
                switch(option){
                    case "0":
                        return;
                    case "1":
                        printAllUsers();
                        break;
                    case "2":
                        printAllFriendships();
                        break;
                    case "3":
                        addUserUI();
                        break;
                    case "4":
                        removeUserUI();
                        break;
                    case "5":
                        addFriendshipUI();
                        break;
                    case "6":
                        removeFriendshipUI();
                        break;
                    case "7":
                        printCommunityCount();
                        break;
                    case "8":
                        printMostSociableCommunity();
                        break;
                }
            } catch(ServiceException | ValidationException e){
                System.err.println(e.getMessage());
            } catch(NumberFormatException e){
                System.err.println("Trebuie introdus un numar natural!");
            }
        }
    }


    private void printAllUsers(){
        for (User u : service.getUserRepo().findAll())
            System.out.println(u);
    }

    private void printAllFriendships() {
        for (Friendship f : service.getFriendshipRepo().findAll())
            System.out.println(f);
    }

    private void addUserUI(){
        System.out.print("id: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("nume: ");
        String name = scanner.nextLine().trim();
        service.addUser(id, name);
    }

    private void removeUserUI(){
        System.out.print("id: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        service.removeUser(id);
    }

    private void addFriendshipUI(){
        System.out.print("id1: ");
        int id1 = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("id2: ");
        int id2 = Integer.parseInt(scanner.nextLine().trim());
        service.addFriendship(id1, id2);
    }

    private void removeFriendshipUI(){
        System.out.print("id1: ");
        int id1 = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("id2: ");
        int id2 = Integer.parseInt(scanner.nextLine().trim());
        service.removeFriendship(id1, id2);
    }

    private void printCommunityCount(){
        System.out.println("Numarul de comunitati: " + service.getComunityCount());
    }

    private void printMostSociableCommunity(){
        System.out.println("Cea mai sociabila comunitate");
        for (User u : service.getMostSociableCommunity())
            System.out.println(u);
    }
}
