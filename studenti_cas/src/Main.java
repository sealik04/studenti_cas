import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BufferedReader fileReader = null;
        BufferedWriter fileWriter = null;

        System.out.println("Vyber si třídu");
        String trida = scanner.nextLine();

        System.out.println("Zadej čas začátku konzultace (formát HH:mm): ");
        String cas = scanner.nextLine();

        System.out.println("Zadej čas konce konzultace (formát HH:mm): ");
        String casKonec = scanner.nextLine();
        int pocetStudentu = 0;

        // rozdělení časů na minuty a hodiny a uložení času do pole
        String[] casZacatekSplit = cas.split(":");
        String[] casKonecSplit = casKonec.split(":");

        // převedení těch stringů času na int, aby se s nimi dalo operovat
        int hodinyZacatek = Integer.parseInt(casZacatekSplit[0]);
        int minutyZacatek = Integer.parseInt(casZacatekSplit[1]);
        int hodinyKonec = Integer.parseInt(casKonecSplit[0]);
        int minutyKonec = Integer.parseInt(casKonecSplit[1]);

        // celkový čas se převede na minuty
        int casZkousek = ((hodinyKonec * 60) + minutyKonec) - ((hodinyZacatek * 60) + minutyZacatek);

        // seznam pro jména studentů
        ArrayList<String> zaci = new ArrayList<>();

        try {
            // výběr souboru, se kterým chce uživatel pracovat
            fileReader = new BufferedReader(new FileReader("src/" + trida + ".txt"));

            // čtení studentů z daného souboru a uložení toho počtu
            String line;
            while ((line = fileReader.readLine()) != null) {
                zaci.add(line);
                pocetStudentu++;
            }

            fileWriter = new BufferedWriter(new FileWriter("vysledek.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // výpočet času, který je každému přidělen
        int casNaJednohoStudenta = casZkousek / pocetStudentu;

        int zkouskyMinuty = minutyZacatek;
        int zkouskyHodiny = hodinyZacatek;

        try {
            for (String student : zaci) {
                if (pocetStudentu == 0) {
                    break;
                }

                String result = student + " " + String.format("%02d:%02d", zkouskyHodiny, zkouskyMinuty);

                // výpis do konzole
                System.out.println(result);

                // zápis do souboru
                fileWriter.write(result);
                fileWriter.newLine();

                // aktualizace času
                zkouskyMinuty += casNaJednohoStudenta % 60;
                zkouskyHodiny += casNaJednohoStudenta / 60;

                if (zkouskyMinuty >= 60) {
                    zkouskyHodiny++;
                    zkouskyMinuty -= 60;
                }
                pocetStudentu--;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
