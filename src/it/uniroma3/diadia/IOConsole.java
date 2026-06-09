package it.uniroma3.diadia;

import java.util.Scanner;

public class IOConsole implements IO {

    private final Scanner scanner;

    /**
     * Riceve lo Scanner come dipendenza: chi lo crea e' anche responsabile di
     * chiuderlo. IOConsole non chiude mai lo Scanner (in particolare quello su
     * System.in, che non e' riapribile).
     */
    public IOConsole(Scanner scanner) {
        this.scanner = scanner;
    }

    public IOConsole() {
        this(new Scanner(System.in));
    }

    public void mostraMessaggio(String msg) {
        System.out.println(msg);
    }

    public String leggiRiga() {
        return this.scanner.nextLine();
    }
}
