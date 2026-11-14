import java.util.*;
import java.io.*;
public class project{
    static Scanner sc=new Scanner(System.in);
    static String[] rollNo=new String[100];
    static String[] name=new String[100];

    // 2D array to store attendance
    // 1 = Present, 0 = Absent, -1 = Not Marked
    static int[][] attendance=new int[100][30];

    // HashMap to store roll number and array index
    static HashMap<String, Integer> map=new HashMap<>();
    static int count=0;
    static int nextRoll=101;
    static String fileName="attendance.txt";

    // Initialize attendance with -1
    static{
        for(int i=0;i<100;i++){
            for(int j=0;j<30;j++){
                attendance[i][j]=-1;
            }
        }
    }

    // 1. Add Student
    static void addStudent(){
        if(count==100){
            System.out.println("Student limit reached!");
            return;
        }
        System.out.print("Enter Student Name: ");
        String student=sc.nextLine();
        String roll="R"+nextRoll;
        rollNo[count]=roll;
        name[count]=student;
        map.put(roll,count);
        count++;
        nextRoll++;
        System.out.println("Student added. Roll: "+roll+" | "+student);
    }

    // 2. Mark Attendance
    static void markAttendance(){
        System.out.print("Enter Day (1-30): ");
        int day=sc.nextInt();
        sc.nextLine();
        if(day<1||day>30){
            System.out.println("Invalid day!");
            return;
        }
        System.out.print("Enter Roll Number: ");
        String roll=sc.nextLine().toUpperCase();

        // Search using HashMap
        if (!map.containsKey(roll)){
            System.out.println("Roll number "+roll+" not found.");
            return;
        }
        int index=map.get(roll);
        System.out.print("Enter Status (P/A): ");
        String status=sc.nextLine();
        if(status.equalsIgnoreCase("P")){
            attendance[index][day-1]=1;
        }else if(status.equalsIgnoreCase("A")){
            attendance[index][day-1]=0;
        }else{
            System.out.println("Invalid input! Enter P or A.");
            return;
        }
        System.out.println("Attendance marked for Day "+day+".");
    }

    // 3. Calculate Attendance Percentage
    static double calculatePercentage(int index){
        int present=0;
        int total=0;
        for(int j=0;j<30;j++){
            if(attendance[index][j]!=-1){
                total++;
                if(attendance[index][j]==1){
                    present++;
                }
            }
        }
        if(total==0){
            return 0;
        }
        return (present*100.0)/total;
    }

    // 4. View Percentage
    static void viewPercentage(){
        System.out.print("Enter Roll Number: ");
        String roll=sc.nextLine().toUpperCase();
        if (!map.containsKey(roll)){
            System.out.println("Roll number "+roll+" not found.");
            return;
        }
        int index=map.get(roll);
        int present=0;
        int total=0;
        for(int j=0;j<30;j++){
            if (attendance[index][j]!=-1){
                total++;
                if(attendance[index][j]==1){
                    present++;
                }
            }
        }
        double percentage=calculatePercentage(index);
        System.out.printf("%s | %s | Present: %d/%d | %.1f%%\n",rollNo[index], name[index],present, total, percentage);
    }

    // 5. View All Students
    static void viewAll(){
        if(count==0){
            System.out.println("No students found!");
            return;
        }
        System.out.println("\nRoll No\tName\t\tPercentage");
        for(int i=0;i<count;i++){
            System.out.printf("%s\t%-15s\t%.1f%%\n",rollNo[i], name[i],calculatePercentage(i));
        }
    }

    // 6. Search Student by Name - Linear Search
    static void searchStudent(){
        System.out.print("Enter Student Name: ");
        String student=sc.nextLine();
        boolean found=false;
        for(int i=0;i<count;i++){
            if(name[i].equalsIgnoreCase(student)){
                System.out.println("Student Found!");
                System.out.println("Roll: "+rollNo[i]);
                System.out.println("Name: "+name[i]);
                System.out.printf("Attendance: %.1f%%\n",calculatePercentage(i));
                found=true;
            }
        }
        if(!found){
            System.out.println("Student not found!");
        }
    }

    // 7. Sort Students - Bubble Sort
    static void sortStudents(){
        int[] order=new int[count];

        for(int i=0;i<count;i++){
            order[i]=i;
        }

        // Bubble Sort in descending order
        for(int i=0;i<count-1;i++){
            for(int j=0;j<count-i-1;j++){
                double p1=calculatePercentage(order[j]);
                double p2=calculatePercentage(order[j+1]);
                if(p1<p2){
                    int temp=order[j];
                    order[j]=order[j+1];
                    order[j+1]=temp;
                }
            }
        }
        System.out.println("\nStudents Sorted by Percentage:");
        for(int i=0;i<count;i++){
            int index=order[i];
            int present=0;
            int total=0;
            for(int j=0;j<30;j++){
                if(attendance[index][j]!=-1){
                    total++;
                    if(attendance[index][j]==1){
                        present++;
                    }
                }
            }
            System.out.printf("%s | %s | %d/%d | %.1f%%\n",rollNo[index], name[index],present, total,calculatePercentage(index));
        }
    }

    // 8. Save Data to File
    static void saveData(){
        try{
            FileWriter fw=new FileWriter(fileName);
            for(int i=0;i<count;i++){
                fw.write(rollNo[i]+"|"+name[i]+"|");
                for(int j=0;j<30;j++){
                    if(attendance[i][j]==1){
                        fw.write("P");
                    }else if(attendance[i][j]==0){
                        fw.write("A");
                    }else{
                        fw.write("-");
                    }
                }
                fw.write("\n");
            }
            fw.close();
            System.out.println("Data saved successfully!");
        }
        catch(IOException e){
            System.out.println("Error saving file!");
        }
    }

    // 9. Load Data from File
    static void loadData(){
        try {
            File file=new File(fileName);
            if (!file.exists()){
                System.out.println("No previous data found.");
                return;
            }
            Scanner fileScanner=new Scanner(file);
            while(fileScanner.hasNextLine()){
                String line=fileScanner.nextLine();
                String[] parts=line.split("\\|", 3);
                if(parts.length<3||count>=100){
                    continue;
                }
                rollNo[count]=parts[0];
                name[count]=parts[1];
                String record=parts[2];

                // Restore attendance
                for(int j=0;j<record.length() && j<30;j++){
                    if(record.charAt(j)=='P'){
                        attendance[count][j] = 1;
                    }else if(record.charAt(j)=='A'){
                        attendance[count][j] = 0;
                    }else{
                        attendance[count][j]=-1;
                    }
                }

                // Restore HashMap
                map.put(rollNo[count], count);

                // Update next roll number
                int number=Integer.parseInt(rollNo[count].substring(1));
                if(number>=nextRoll){
                    nextRoll=number+1;
                }
                count++;
            }
            fileScanner.close();
            System.out.println("Previous data loaded successfully!");
        }
        catch(Exception e){
            System.out.println("Error loading file!");
        }
    }
    public static void main(String[] args){

        // Load previous data
        loadData();
        int choice;
        do{
            System.out.println("\n===== ATTENDANCE TRACKER =====");
            System.out.println("1. Add Student");
            System.out.println("2. Mark Attendance");
            System.out.println("3. View All Students");
            System.out.println("4. View Percentage");
            System.out.println("5. Sort by Percentage");
            System.out.println("6. Search Student by Name");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice=sc.nextInt();
            sc.nextLine();
            switch(choice){
                case 1:
                    addStudent();
                    break;
                case 2:
                    markAttendance();
                    break;
                case 3:
                    viewAll();
                    break;
                case 4:
                    viewPercentage();
                    break;
                case 5:
                    sortStudents();
                    break;
                case 6:
                    searchStudent();
                    break;
                case 7:
                    saveData();
                    System.out.println("Thank you for using Attendance Tracker!");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }while(choice!=7);
    }
}
