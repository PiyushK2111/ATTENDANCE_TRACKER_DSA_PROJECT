# Attendance Tracker

A menu-driven, console-based attendance management application written in Java. It is a beginner-level **Data Structures and Algorithms (DSA)** project that uses arrays, a HashMap, linear search, Bubble Sort and file handling.

## Features

- Add students with auto-generated roll numbers (R101, R102, R103, ...)
- Mark attendance as Present (P) or Absent (A) for any day from 1 to 30
- View the attendance percentage of one student
- View all students with their percentages
- Sort students by attendance percentage (highest first)
- Search a student by name (case-insensitive)
- Save data to `attendance.txt` on exit and reload it on start

## Data Structures and Algorithms Used

| Concept | Where it is used |
|---|---|
| Arrays (`String[]`) | `rollNo` and `name` store student details |
| 2D array (`int[100][30]`) | `attendance` stores 1 = Present, 0 = Absent, -1 = Not Marked |
| HashMap<String, Integer> | Maps roll number to array index for fast lookup |
| Linear Search | Search a student by name |
| Bubble Sort | Sort students by percentage (sorts an index array, so original records stay unchanged) |
| File Handling | `FileWriter` to save, `Scanner(File)` to load |

## Time Complexity

(N = number of students, D = number of days, here 30)

| Operation | Complexity |
|---|---|
| Add a student | O(1) |
| Find a student by roll number (HashMap) | O(1) average |
| Mark attendance | O(1) average |
| Percentage of one student | O(D), constant since D = 30 |
| View all students | O(N x D) |
| Search by name (linear search) | O(N) |
| Sort by percentage (Bubble Sort) | O(N^2 x D) |
| Save / load file | O(N x D) |

## How to Run

Requirements: JDK 17 or later.

```bash
javac project.java
java project
```

> The file must be named `project.java` because the class is named `project`.

## Menu

```
===== ATTENDANCE TRACKER =====
1. Add Student
2. Mark Attendance
3. View All Students
4. View Percentage
5. Sort by Percentage
6. Search Student by Name
7. Exit
```

## Sample Output

```
Enter Roll Number: R101
R101 | Amit | Present: 2/3 | 66.7%
```

```
Students Sorted by Percentage:
R102 | Riya | 3/3 | 100.0%
R101 | Amit | 2/3 | 66.7%
R103 | Karan | 1/3 | 33.3%
```

```
Enter Student Name: zoya
Student not found!
```

## Data File Format

Records are saved in `attendance.txt`, one line per student:

```
R101|Amit|PPA---------------------------
```

The format is `roll|name|attendance`, where the attendance part has 30 characters: `P` = Present, `A` = Absent, `-` = Not Marked.

## Limitations

- Maximum of 100 students and 30 days
- Data is saved only when you choose **Exit (7)**
- Menu input must be a number (typing a letter crashes the program)
- No edit or delete option for students
- No database or GUI

## Future Scope

- Edit and delete students
- Use `ArrayList` instead of fixed-size arrays
- Auto-save after every change
- Faster sorting (Merge Sort or `Collections.sort()`)
- Input validation for the menu
- Database storage and a GUI or web interface
- Export reports and defaulter lists

## Author

**Piyush Kumar**
B.Tech CSE (AI & ML) in association with Google
Rungta International Skills University, Bhilai
Guided by Mr. Surya Mani Verma
