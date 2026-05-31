// 1. Smart Password Generator (Innovative Project)------------>

import java.util.*;
public class SmartPasswordGenerator {

    // ─── Password Strength Classifier ───────────────────────────────────────────
    static String classifyStrength(String password) {
        int score = 0;

        if (password.length() >= 12) score++;
        if (password.matches(".*[A-Z].*")) score++;
        if (password.matches(".*[a-z].*")) score++;
        if (password.matches(".*[0-9].*")) score++;
        if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{}].*")) score++;

        if (score <= 2) return "Weak";
        if (score <= 3) return "Medium";
        return "powerfull";
    }
    
    // ─── Password Generator ──────────────────────────────────────────────────────
    static String generatePassword(String name, String pan, String dob) {
        // Extract components
        String namePart  = name.length() >= 3
                           ? name.substring(0, 3).toUpperCase()
                           : name.toUpperCase();

        String panPart   = pan.substring(2, 6);           // 4 chars from middle of PAN

        // DOB: expects DD/MM/YYYY or DDMMYYYY
        String cleanDob  = dob.replaceAll("[^0-9]", "");
        String dobPart   = cleanDob.length() >= 6
                           ? cleanDob.substring(0, 2) + cleanDob.substring(4, 6)
                           : cleanDob;

        // Special characters pool
        String specials  = "!@#$%^&*";
        char   sp1       = specials.charAt(name.length() % specials.length());
        char   sp2       = specials.charAt(pan.length()  % specials.length());

        // Shuffle the combined base
        String base      = namePart + dobPart + panPart + sp1 + sp2;
        List<Character> chars = new ArrayList<>();
        for (char c : base.toCharArray()) chars.add(c);
        Collections.shuffle(chars, new Random(pan.hashCode()));   // deterministic shuffle

        StringBuilder sb = new StringBuilder();
        for (char c : chars) sb.append(c);
        return sb.toString();
    }

    // ─── Display Result ──────────────────────────────────────────────────────────
    static void displayResult(String name, String pan, String dob) {
        System.out.println("\n========================================");
        System.out.println("  User   : " + name);
        System.out.println("  PAN    : " + pan);
        System.out.println("  DOB    : " + dob);
        System.out.println("----------------------------------------");
        String password = generatePassword(name, pan, dob);
        String strength = classifyStrength(password);
        System.out.println("  Password : " + password);
        System.out.println("  Strength : " + strength);
        System.out.println("========================================");
    }

    // ─── Main – Test with Multiple Users ────────────────────────────────────────
    public static void main(String[] args) {
        // ── Hardcoded test users ──────────────────────────────────────────────
        System.out.println("\n--- Predefined Test Users ---");
        displayResult("Avijit Kr Sah",  "ABCPM1234D", "22/12/2002");
        displayResult("Tushar Singh",  "BCDPS5678E", "17/11/2003");
        displayResult("Rahul Kumar",  "CDERК9012F", "02/12/2001");

        // ── Interactive input ─────────────────────────────────────────────────
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Enter Your Details ---");

        System.out.print("Name        : ");
        String name = sc.nextLine().trim();

        System.out.print("PAN Number  : ");
        String pan  = sc.nextLine().trim().toUpperCase();

        System.out.print("Date of Birth (DD/MM/YYYY): ");
        String dob  = sc.nextLine().trim();

        displayResult(name, pan, dob);
        sc.close();
    }
}