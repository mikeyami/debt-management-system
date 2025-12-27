import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

class Loan {
    private String name;
    private double principal;
    private double rate;
    private int timeMonths;
    private LocalDate dueDate;
    private boolean isPaid;
    
    public Loan(String name, double principal, double rate, int timeMonths, LocalDate dueDate) {
        this.name = name;
        this.principal = principal;
        this.rate = rate;
        this.timeMonths = timeMonths;
        this.dueDate = dueDate;
        this.isPaid = false;
    }
    
    public double calculateInterest() {
        return (principal * rate * timeMonths) / (100 * 12);
    }
    
    public double calculateTotal() {
        return principal + calculateInterest();
    }
    
    public long getDaysUntilDue() {
        return ChronoUnit.DAYS.between(LocalDate.now(), dueDate);
    }
    
    public String getName() { return name; }
    public double getPrincipal() { return principal; }
    public double getRate() { return rate; }
    public int getTimeMonths() { return timeMonths; }
    public LocalDate getDueDate() { return dueDate; }
    public boolean isPaid() { return isPaid; }
    public void setPaid(boolean paid) { isPaid = paid; }
}

class DebtManagementSystem {
    private static List<Loan> loans = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        // Add sample loans for demonstration
        loadSampleLoans();
        
        System.out.println("=====================================================");
        System.out.println("       PERSONAL DEBT MANAGEMENT SYSTEM");
        System.out.println("=====================================================");
        System.out.println("NOTE: Sample loans have been pre-loaded for demonstration");
        System.out.println();
        
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            System.out.println();
            
            switch (choice) {
                case 1:
                    addLoan();
                    break;
                case 2:
                    viewAllLoans();
                    break;
                case 3:
                    checkAlerts();
                    break;
                case 4:
                    suggestStrategy();
                    break;
                case 5:
                    viewAvalanchePriority();
                    break;
                case 6:
                    viewSnowballPriority();
                    break;
                case 7:
                    markLoanAsPaid();
                    break;
                case 8:
                    viewFinancialSummary();
                    break;
                case 9:
                    System.out.println("Thank you for using the Debt Management System!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private static void loadSampleLoans() {
        // Sample loan 1: Credit Card - High interest, urgent deadline
        loans.add(new Loan("Credit Card", 5000, 24.0, 12, LocalDate.now().plusDays(2)));
        
        // Sample loan 2: Electric Bill - Low balance, very urgent
        loans.add(new Loan("Electric Bill", 1500, 5.0, 1, LocalDate.now().plusDays(1)));
        
        // Sample loan 3: Car Loan - Medium interest, not urgent
        loans.add(new Loan("Car Loan", 15000, 12.0, 36, LocalDate.now().plusDays(20)));
        
        // Sample loan 4: Personal Loan - High balance, medium interest
        loans.add(new Loan("Personal Loan", 8000, 18.0, 24, LocalDate.now().plusDays(15)));
        
        // Sample loan 5: Student Loan - Low interest, not urgent
        loans.add(new Loan("Student Loan", 20000, 8.0, 48, LocalDate.now().plusDays(30)));
        
        // Sample loan 6: Medical Bill - Small balance, urgent
        loans.add(new Loan("Medical Bill", 2500, 10.0, 6, LocalDate.now().plusDays(3)));
    }
    
    private static void displayMenu() {
        
System.out.println("\n======================================================");
        System.out.println("                    MAIN MENU");
        System.out.println("=====================================================");
        System.out.println("1. Add New Loan");
        System.out.println("2. View All Loans");
        System.out.println("3. Check Payment Alerts");
        System.out.println("4. Get Strategy Suggestion (Smart Recommendation)");
        System.out.println("5. View Avalanche Priority");
        System.out.println("6. View Snowball Priority");
        System.out.println("7. Mark Loan as Paid");
        System.out.println("8. View Financial Summary");
        System.out.println("9. Exit");
        System.out.println("=====================================================");
    }
    
    private static void addLoan() {
        System.out.println("===================================================");
        System.out.println("                  ADD NEW LOAN");
        System.out.println("===================================================");
        
        String name = getStringInput("Enter loan name (e.g., Electric Bill, Credit Card): ");
        double principal = getDoubleInput("Enter principal amount: ");
        double rate = getDoubleInput("Enter interest rate (%): ");
        int timeMonths = getIntInput("Enter time period (months): ");
        
        System.out.print("Enter due date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();
        LocalDate dueDate = LocalDate.parse(dateStr);
        
        Loan loan = new Loan(name, principal, rate, timeMonths, dueDate);
        loans.add(loan);
        
        System.out.println("\nLoan added successfully!");
        System.out.println("Interest: " + String.format("%.2f", loan.calculateInterest()));
        System.out.println("Total Amount: " + String.format("%.2f", loan.calculateTotal()));
    }
    
    private static void viewAllLoans() {
        System.out.println("=======================================================================================");
        System.out.println("                                     ALL LOANS");
        System.out.println("=======================================================================================");
        
        if (loans.isEmpty()) {
            System.out.println("No loans added yet.");
            return;
        }
        
        System.out.printf("%-20s %-12s %-8s %-10s %-12s %-12s %-12s %-10s%n",
            "Loan Name", "Principal", "Rate(%)", "Time(mo)", "Interest", "Total", "Due Date", "Status");
        System.out.println("=======================================================================================");
        
        for (int i = 0; i < loans.size(); i++) {
            Loan loan = loans.get(i);
            System.out.printf("%-20s %-12.2f %-8.2f %-10d %-12.2f %-12.2f %-12s %-10s%n",
                loan.getName(),
                loan.getPrincipal(),
                loan.getRate(),
                loan.getTimeMonths(),
                loan.calculateInterest(),
                loan.calculateTotal(),
                loan.getDueDate().toString(),
                loan.isPaid() ? "PAID" : "PENDING");
        }
    }
    
    private static void checkAlerts() {
        System.out.println("===================================================");
        System.out.println("                PAYMENT ALERTS");
        System.out.println("===================================================");
        
        List<Loan> urgentLoans = new ArrayList<>();
        
        for (Loan loan : loans) {
            if (!loan.isPaid()) {
                long daysLeft = loan.getDaysUntilDue();
                
                if (daysLeft <= 3) {
                    urgentLoans.add(loan);
                    
                    if (daysLeft < 0) {
                        System.out.println("OVERDUE: " + loan.getName());
                        System.out.println("    Overdue by " + Math.abs(daysLeft) + " day(s)");
                    } else if (daysLeft == 0) {
                        System.out.println("CRITICAL: " + loan.getName());
                        System.out.println("    Due TODAY!");
                    } else if (daysLeft == 1) {
                        System.out.println("HIGH: " + loan.getName());
                        System.out.println("    Due TOMORROW");
                    } else {
                        System.out.println("MEDIUM: " + loan.getName());
                        System.out.println("    Due in " + daysLeft + " days");
                    }
                    
                    System.out.println("    Amount: " + String.format("%.2f", loan.calculateTotal()));
                    System.out.println("    Due Date: " + loan.getDueDate());
                    System.out.println();
                }
            }
        }
        
        if (urgentLoans.isEmpty()) {
            System.out.println("No urgent payments at this time.");
        }
    }
    
    private static void suggestStrategy() {
        System.out.println("===================================================");
        System.out.println("            SMART STRATEGY RECOMMENDATION");
        System.out.println("===================================================");
        
        List<Loan> unpaidLoans = new ArrayList<>();
        for (Loan loan : loans) {
            if (!loan.isPaid()) {
                unpaidLoans.add(loan);
            }
        }
        
        if (unpaidLoans.isEmpty()) {
            System.out.println("No unpaid loans. You're debt-free!");
            return;
        }
        
        boolean hasUrgentDeadlines = false;
        boolean hasHighInterestLoans = false;
        boolean hasSmallBalances = false;
        double avgInterestRate = 0;
        double avgPrincipal = 0;
        double minPrincipal = Double.MAX_VALUE;
        double maxInterestRate = 0;
        double minInterestRate = Double.MAX_VALUE;
        int urgentCount = 0;
        
        for (Loan loan : unpaidLoans) {
            long daysLeft = loan.getDaysUntilDue();
            if (daysLeft <= 7) {
                hasUrgentDeadlines = true;
                urgentCount++;
            }
            
            if (loan.getRate() >= 15) {
                hasHighInterestLoans = true;
            }
            
            if (loan.getPrincipal() <= 3000) {
                hasSmallBalances = true;
            }
            
            avgInterestRate += loan.getRate();
            avgPrincipal += loan.getPrincipal();
            minPrincipal = Math.min(minPrincipal, loan.getPrincipal());
            maxInterestRate = Math.max(maxInterestRate, loan.getRate());
            minInterestRate = Math.min(minInterestRate, loan.getRate());
        }
        
        avgInterestRate /= unpaidLoans.size();
        avgPrincipal /= unpaidLoans.size();
        
        double interestVariance = maxInterestRate - minInterestRate;
        
        System.out.println("SITUATION ANALYSIS:");
        System.out.println("================================================");
        System.out.println("Total unpaid loans: " + unpaidLoans.size());
        System.out.println("Urgent deadlines (<=7 days): " + urgentCount);
        System.out.println("Average interest rate: " + String.format("%.2f%%", avgInterestRate));
        System.out.println("Highest interest rate: " + String.format("%.2f%%", maxInterestRate));
        System.out.println("Interest rate variance: " + String.format("%.2f%%", interestVariance));
        System.out.println("Smallest loan: " + String.format("%.2f", minPrincipal));
        System.out.println("Average loan size: " + String.format("%.2f", avgPrincipal));
        System.out.println();
        
        // Determine recommended strategy
        String recommendedStrategy = "";
        String reason = "";
        
        if (interestVariance > 10 && hasHighInterestLoans) {
            recommendedStrategy = "AVALANCHE METHOD";
            reason = "Significant interest rate differences detected.\n" +
                    "Your highest rate (" + String.format("%.2f%%", maxInterestRate) + 
                    ") is much higher than your lowest (" + String.format("%.2f%%", minInterestRate) + ").\n\n" +
                    "BENEFITS:\n" +
                    "- Saves the MOST money on interest in the long run\n" +
                    "- Mathematically optimal approach\n" +
                    "- Best for financially disciplined individuals\n\n" +
                    "STRATEGY: Pay loans with highest interest rates first.\n" +
                    "Example: If Credit Card is 24% and Student Loan is 8%,\n" +
                    "         focus on Credit Card first to minimize interest costs.";
            
        } else if (hasSmallBalances && unpaidLoans.size() >= 3) {
            recommendedStrategy = "SNOWBALL METHOD";
            reason = "You have multiple loans with manageable balances.\n" +
                    "Interest rates are relatively similar (variance: " + 
                    String.format("%.2f%%", interestVariance) + ").\n\n" +
                    "BENEFITS:\n" +
                    "- Psychological wins - see debts eliminated quickly\n" +
                    "- Builds momentum and motivation\n" +
                    "- Simplifies finances faster (fewer accounts to manage)\n" +
                    "- Best for those who need motivation boosts\n\n" +
                    "STRATEGY: Pay smallest loans first, then move to larger ones.\n" +
                    "Example: If you have 1500, 2500, and 5000 loans,\n" +
                    "         knock out the 1500 first for a quick win.";
            
        } else {
            recommendedStrategy = "EITHER METHOD WORKS";
            reason = "Your loans are balanced in terms of:\n" +
                    "- Similar interest rates (variance: " + String.format("%.2f%%", interestVariance) + ")\n" +
                    "- Comparable loan sizes\n\n" +
                    "SUGGESTION: Choose based on your personality:\n" +
                    "- AVALANCHE if you're financially disciplined and want to save more\n" +
                    "- SNOWBALL if you need motivation from quick wins";
        }
        
        System.out.println("|====================================================|");
        System.out.println("|           RECOMMENDED PAYMENT STRATEGY             |");
        System.out.println("|====================================================|");
        System.out.println("|  >>> " + recommendedStrategy + " <<<                          |");
        System.out.println("|====================================================|");
        System.out.println();
        System.out.println("REASON:");
        System.out.println(reason);
        
        // Handle urgent deadlines separately
        if (urgentCount > 0) {
            System.out.println();
            System.out.println("??  IMPORTANT NOTICE:");
            System.out.println("================================================");
            System.out.println("You have " + urgentCount + " loan(s) with urgent deadlines (<=7 days)!");
            System.out.println("PRIORITY ACTION: Handle urgent deadlines FIRST to avoid:");
            System.out.println("- Late payment fees");
            System.out.println("- Interest rate increases");
            System.out.println("- Credit score damage");
            System.out.println();
            System.out.println("Then apply the " + recommendedStrategy + " to remaining loans.");
        }
        
        System.out.println();
        System.out.println("================================================");
        System.out.println("TIP: Regardless of method, always pay minimum on all loans");
        System.out.println("      to avoid defaults, then put extra money toward your");
        System.out.println("      priority loan based on " + recommendedStrategy + ".");
        System.out.println("================================================");
    }
    
    private static void viewAvalanchePriority() {
        System.out.println("===================================================");
        System.out.println("          AVALANCHE METHOD - PRIORITY ORDER");
        System.out.println("===================================================");
        System.out.println("Strategy: Pay highest interest rate first");
        System.out.println();
        
        List<Loan> unpaidLoans = new ArrayList<>();
        for (Loan loan : loans) {
            if (!loan.isPaid()) {
                unpaidLoans.add(loan);
            }
        }
        
        unpaidLoans.sort((a, b) -> Double.compare(b.getRate(), a.getRate()));
        
        displayPriorityList(unpaidLoans, "interest rate");
    }
    
    private static void viewSnowballPriority() {
        System.out.println("?????????????????????????????????????????????????????????");
        System.out.println("          SNOWBALL METHOD - PRIORITY ORDER");
        System.out.println("?????????????????????????????????????????????????????????");
        System.out.println("Strategy: Pay smallest balance first");
        System.out.println();
        
        List<Loan> unpaidLoans = new ArrayList<>();
        for (Loan loan : loans) {
            if (!loan.isPaid()) {
                unpaidLoans.add(loan);
            }
        }
        
        unpaidLoans.sort((a, b) -> Double.compare(a.getPrincipal(), b.getPrincipal()));
        
        displayPriorityList(unpaidLoans, "principal amount");
    }
    
    private static void displayPriorityList(List<Loan> loans, String sortBy) {
        if (loans.isEmpty()) {
            System.out.println("No unpaid loans.");
            return;
        }
        
        for (int i = 0; i < loans.size(); i++) {
            Loan loan = loans.get(i);
            System.out.println("Priority #" + (i + 1) + ": " + loan.getName());
            System.out.println("  Principal: " + String.format("%.2f", loan.getPrincipal()));
            System.out.println("  Rate: " + String.format("%.2f%%", loan.getRate()));
            System.out.println("  Total Amount: " + String.format("%.2f", loan.calculateTotal()));
            System.out.println("  Due: " + loan.getDueDate() + " (" + loan.getDaysUntilDue() + " days)");
            System.out.println();
        }
    }
    
    private static void markLoanAsPaid() {
        System.out.println("===================================================");
        System.out.println("              MARK LOAN AS PAID");
        System.out.println("===================================================");
        
        if (loans.isEmpty()) {
            System.out.println("No loans available.");
            return;
        }
        
        for (int i = 0; i < loans.size(); i++) {
            Loan loan = loans.get(i);
            System.out.println((i + 1) + ". " + loan.getName() + 
                             " - " + String.format("%.2f", loan.calculateTotal()) +
                             " [" + (loan.isPaid() ? "PAID" : "PENDING") + "]");
        }
        
        int choice = getIntInput("\nEnter loan number to toggle paid status: ");
        
        if (choice > 0 && choice <= loans.size()) {
            Loan loan = loans.get(choice - 1);
            loan.setPaid(!loan.isPaid());
            System.out.println("\n" + loan.getName() + " marked as " + 
                             (loan.isPaid() ? "PAID" : "PENDING"));
        } else {
            System.out.println("Invalid loan number.");
        }
    }
    
    private static void viewFinancialSummary() {
        System.out.println("====================================================");
        System.out.println("              FINANCIAL SUMMARY");
        System.out.println("====================================================");
        
        if (loans.isEmpty()) {
            System.out.println("No loans added yet.");
            return;
        }
        
        double totalPrincipal = 0;
        double totalInterest = 0;
        double totalAmount = 0;
        int paidCount = 0;
        int unpaidCount = 0;
        
        for (Loan loan : loans) {
            totalPrincipal += loan.getPrincipal();
            totalInterest += loan.calculateInterest();
            totalAmount += loan.calculateTotal();
            
            if (loan.isPaid()) {
                paidCount++;
            } else {
                unpaidCount++;
            }
        }
        
        System.out.println("Total Loans: " + loans.size());
        System.out.println("  Paid: " + paidCount);
        System.out.println("  Unpaid: " + unpaidCount);
        System.out.println();
        System.out.println("Total Principal: " + String.format("%.2f", totalPrincipal));
        System.out.println("Total Interest:  " + String.format("%.2f", totalInterest));
        System.out.println("===========================");
        System.out.println("TOTAL AMOUNT DUE: " + String.format("%.2f", totalAmount));
        System.out.println("===========================");
    }
    
    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Invalid input. " + prompt);
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }
    
    private static double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            scanner.next();
            System.out.print("Invalid input. " + prompt);
        }
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }
    
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}