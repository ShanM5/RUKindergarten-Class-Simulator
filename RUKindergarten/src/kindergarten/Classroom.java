package kindergarten;
/**
 * This class represents a Classroom, with:
 * - an SNode instance variable for students in line,
 * - an SNode instance variable for musical chairs, pointing to the last student in the list,
 * - a boolean array for seating availability (eg. can a student sit in a given seat), and
 * - a Student array parallel to seatingAvailability to show students filed into seats 
 * --- (more formally, seatingAvailability[i][j] also refers to the same seat in studentsSitting[i][j])
 * 
 * @author Ethan Chou
 * @author Kal Pandit
 * @author Maksims Kurjanovics Kravcenko
 */
public class Classroom {
    private SNode studentsInLine;             // when students are in line: references the FIRST student in the LL
    private SNode musicalChairs;              // when students are in musical chairs: references the LAST student in the CLL
    private boolean[][] seatingAvailability;  // represents the classroom seats that are available to students
    private Student[][] studentsSitting;      // when students are sitting in the classroom: contains the students

    /**
     * Constructor for classrooms. Do not edit.
     * @param l passes in students in line
     * @param m passes in musical chairs
     * @param a passes in availability
     * @param s passes in students sitting
     */
    public Classroom ( SNode l, SNode m, boolean[][] a, Student[][] s ) {
		studentsInLine      = l;
        musicalChairs       = m;
		seatingAvailability = a;
        studentsSitting     = s;
	}
    /**
     * Default constructor starts an empty classroom. Do not edit.
     */
    public Classroom() {
        this(null, null, null, null);
    }

    /**
     * This method simulates students coming into the classroom and standing in line.
     * 
     * Reads students from input file and inserts these students in alphabetical 
     * order to studentsInLine singly linked list.
     * 
     * Input file has:
     * 1) one line containing an integer representing the number of students in the file, say x
     * 2) x lines containing one student per line. Each line has the following student 
     * information separated by spaces: FirstName LastName Height
     * 
     * @param filename the student information input file
     */
    public void makeClassroom ( String filename ) {
        // WRITE YOUR CODE HERE
        StdIn.setFile(filename);
        int numberOfStudents = StdIn.readInt();
        Student[] inputedSample = new Student[numberOfStudents];

        //reading the file into an array
        for(int i = 0; i < numberOfStudents; i ++){
            String fName = StdIn.readString();
            String lName = StdIn.readString();
            int h = StdIn.readInt();
            Student inputStudent = new Student(fName, lName, h);
            inputedSample[i] = inputStudent;
        }

        //ordering the array of students by alphabetic order
        for(int j = 0; j < inputedSample.length; j++){
            for(int k = j + 1; k < inputedSample.length; k++){
                if(inputedSample[j].compareNameTo(inputedSample[k]) >= 1){
                    Student temp = inputedSample[j];
                    inputedSample[j] = inputedSample[k];
                    inputedSample[k] = temp;
                }

            }
        }
        SNode[] orderedArrayNodes = new SNode[inputedSample.length];
       
        //forming the array of SNodes
        for(int l = 0; l < inputedSample.length; l++){
                orderedArrayNodes[l] = new SNode(new Student(inputedSample[l].getFirstName(),inputedSample[l].getLastName(), inputedSample[l].getHeight()), null);
        }
       
        studentsInLine = (orderedArrayNodes[0]);
        //updating each SNodes.next to form the LL
        for(int amogus = 0; amogus < orderedArrayNodes.length-1; amogus++){
            orderedArrayNodes[amogus].setNext(orderedArrayNodes[amogus+1]);
        }

        
    }

    /**
     * 
     * This method creates and initializes the seatingAvailability (2D array) of 
     * available seats inside the classroom. Imagine that unavailable seats are broken and cannot be used.
     * 
     * Reads seating chart input file with the format:
     * An integer representing the number of rows in the classroom, say r
     * An integer representing the number of columns in the classroom, say c
     * Number of r lines, each containing c true or false values (true denotes an available seat)
     *  
     * This method also creates the studentsSitting array with the same number of
     * rows and columns as the seatingAvailability array
     * 
     * This method does not seat students on the seats.
     * 
     * @param seatingChart the seating chart input file
     */
    public void setupSeats(String seatingChart) {

	// WRITE YOUR CODE HERE
        StdIn.setFile(seatingChart);
        int r = StdIn.readInt();
        int c = StdIn.readInt();
        seatingAvailability = new boolean[r][c];
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                seatingAvailability[i][j] = StdIn.readBoolean();
            }
        }
        studentsSitting = new Student[r][c];

        
    }

    /**
     * 
     * This method simulates students taking their seats in the classroom.
     * 
     * 1. seats any remaining students from the musicalChairs starting from the front of the list
     * 2. starting from the front of the studentsInLine singly linked list
     * 3. removes one student at a time from the list and inserts them into studentsSitting according to
     *    seatingAvailability
     * 
     * studentsInLine will then be empty
     */
    public void seatStudents () {
    int checktoBreak = 0;
	// WRITE YOUR CODE HERE
        for(int i = 0; i < seatingAvailability.length; i++){
            for(int j = 0; j < seatingAvailability[0].length; j++){
                if(studentsInLine.getNext() != null && seatingAvailability[i][j] == true && studentsSitting[i][j] == null){
                    studentsSitting[i][j]  = studentsInLine.getStudent();
                    studentsInLine = studentsInLine.getNext();

                }
                else{
                    if(studentsInLine != null && seatingAvailability[i][j] == true && studentsSitting[i][j] == null){
                        studentsSitting[i][j] = studentsInLine.getStudent();
                        checktoBreak = 12345;
                        studentsInLine = null;
                    }
                }
                if(checktoBreak == 12345) break;
            }
            if(checktoBreak == 12345) break;
           
        }

	
    }

    /**
     * Traverses studentsSitting row-wise (starting at row 0) removing a seated
     * student and adding that student to the end of the musicalChairs list.
     * 
     * row-wise: starts at index [0][0] traverses the entire first row and then moves
     * into second row.
     */
    public void insertMusicalChairs () {
        int count = 0;
        
        //Getting the number of students.
        for(int i = 0; i < studentsSitting.length; i++){
            for(int j = 0; j < studentsSitting[0].length; j++){
                if((studentsSitting[i][j] != null) && seatingAvailability[i][j] == true){
                    count++;
                }
            }
        }

        SNode[] amogus = new SNode[count];
        int p = 0;
        for(int k = 0; k < studentsSitting.length; k++){
            for(int l = 0; l < studentsSitting[0].length; l++){
                if(p == count+1){
                    break;
                }
                else{
                    if(studentsSitting[k][l] != null && seatingAvailability[k][l] == true){
                        amogus[p] = new SNode(studentsSitting[k][l], null);
                        studentsSitting[k][l] = null;
                        p++;
                    }
                }
            }
            if(p == amogus.length){
                break;
            }
        }

        musicalChairs = amogus[p-1];
        for(int z = 0; z < amogus.length - 1; z++){
            amogus[z].setNext(amogus[z+1]);
        }
        musicalChairs.setNext(amogus[0]);
    }


      
      
      
      
      
      
      
        // WRITE YOUR CODE HERE
       // SNode ptr = musicalChairs;
        //SNode temp = null;
        //for(int i = 0; i < studentsSitting.length; i++){
        //    for(int j = 0; j < studentsSitting[0].length; j++){
       //         if(studentsSitting[i][j] != null && seatingAvailability[i][j] == true){
       //             temp = new SNode(studentsSitting[i][j], null);
       //             ptr.setNext(temp); 
       //             studentsSitting[i][j] = null;
       //             ptr = ptr.getNext();
       //         }
                
       //     }
       // }


     //}

    /**
     * 
     * This method repeatedly removes students from the musicalChairs until there is only one
     * student (the winner).
     * 
     * Choose a student to be elimnated from the musicalChairs using StdRandom.uniform(int b),
     * where b is the number of students in the musicalChairs. 0 is the first student in the 
     * list, b-1 is the last.
     * 
     * Removes eliminated student from the list and inserts students back in studentsInLine 
     * in ascending height order (shortest to tallest).
     * 
     * The last line of this method calls the seatStudents() method so that students can be seated.
     */
    public void playMusicalChairs() {

        // WRITE YOUR CODE HERE

        SNode counter = musicalChairs;
        //Count number of students in musical chairs list;
        int count = 0;
        while(counter.getNext() != musicalChairs){
            count++;
            counter = counter.getNext();
        }
        count++; //Total number of students in musicalchairs

        SNode prev = musicalChairs;
        SNode ptr = musicalChairs.getNext(); //Ptr is the first node.
        int b = count;

        SNode[] losers = new SNode[b-1]; //Losers
        int losersIndex = 0;
        while(b > 1){
            prev = musicalChairs;
            ptr = musicalChairs.getNext();
            //Finding a loser which is ptr
            int ran = StdRandom.uniform(b);
            for(int i = 0; i < ran; i++){
                prev = prev.getNext();
                ptr = ptr.getNext();
             //   System.out.println(ptr.getStudent().getFullName() + " INSIDE THE COUNTER");
            }

            //If the loser is the current musicalChairs
            if(ptr == musicalChairs){
                prev.setNext(musicalChairs.getNext());
                losers[losersIndex] = ptr;//dsdsdsdsdsd;
                musicalChairs = prev;
           //     System.out.println("IN THE IF STATEMENT"+ptr.getStudent().getFullName());
            }
            //If the loser is not the current musicalChairs
            else{
                prev.setNext(prev.getNext().getNext());
                losers[losersIndex] = ptr; //dsdsdsdsd;
            //    System.out.println("IN THE IF STATEMENT"+ptr.getStudent().getFullName());
            }
            losersIndex++;
            b--;
        }
        System.out.println("mucial: "+ musicalChairs.getStudent().getFullName());
        
        SNode firstInClass = musicalChairs;
        boolean exit = false;
        
        //Seating ONLY the first student (the winner)
        for(int i = 0; i < seatingAvailability.length; i++){
            for(int j = 0; j < seatingAvailability[0].length; j++){
                if(seatingAvailability[i][j] == true){
                    studentsSitting[i][j] = firstInClass.getStudent();
                    exit = true;
                }
                if(exit == true) break;
            }
            if(exit == true) break;
        }

        
        for(int i = 0; i < losers.length; i ++){
            losers[i].setNext(null);
        }
        
        //creating a duplicate in order of insertion
        SNode[] losersInsertion = new SNode[losers.length];
        for(int i = 0; i < losersInsertion.length; i++){
            losersInsertion[i] = losers[i];
            //System.out.println(losersInsertion[i].getStudent().getFullName()+ " " + losersInsertion[i].getStudent().getHeight());
        }
        
        int iSame = 0;
        int jSame = 0;
        for(int i = 0; i < losers.length; i++){
            for(int j = 0; j < losers.length; j++){
                if(losers[i].getStudent().getHeight() >  losers[j].getStudent().getHeight()){
                    SNode temporary = losers[i];
                    losers[i] = losers[j];
                    losers[j] = temporary;
                }

                //IF they are the same height
                if(losers[i].getStudent().getHeight() == losers[j].getStudent().getHeight()){
                    for(int checking = 0; checking < losersInsertion.length; checking++){
                        if(losers[i].getStudent() == losersInsertion[checking].getStudent()){
                            iSame = checking;
                        }
                        if(losers[j].getStudent() == losersInsertion[checking].getStudent()){
                            jSame = checking;
                        }
                    }
                    if(jSame > iSame){
                        SNode temporary = losers[i];
                        losers[i] = losers[j];
                        losers[j] = temporary;

                    }
                }
            }
       }
       

        for(int i = losers.length-1; i > 0; i--){
            losers[i].setNext(losers[i-1]);
        }

        //for(int i = 0; i < losers.length; i++){
        //    System.out.println(losers[i].getStudent().getFullName()+ " " + losers[i].getStudent().getHeight());
       // }
       
        
        //for(int i = 0; i < losersInsertion.length; i++){
       //     System.out.println(losersInsertion[i].getStudent().getFullName()+ " "+ losersInsertion[i].getStudent().getHeight());
      //  }

        musicalChairs = null;
        studentsInLine = losers[losers.length-1];
        seatStudents();
        
        

    }
    

        
        

    /**
     * Insert a student to wherever the students are at (ie. whatever activity is not empty)
     * Note: adds to the end of either linked list or the next available empty seat
     * @param firstName the first name
     * @param lastName the last name
     * @param height the height of the student
     */
    public void addLateStudent ( String firstName, String lastName, int height ) {
        
        // WRITE YOUR CODE HERE
        if(studentsInLine != null){
            SNode ptr = studentsInLine;
            while(ptr.getNext() != null){
                ptr = ptr.getNext();
            }
            SNode lateStudent = new SNode(new Student(firstName, lastName, height),null);
            ptr.setNext(lateStudent);

        }else{
            if(musicalChairs != null){
                SNode lateStudent = new SNode(new Student(firstName, lastName, height),musicalChairs.getNext());
                musicalChairs.setNext(lateStudent);
                musicalChairs = lateStudent;
            }
        }
        if(studentsInLine == null && musicalChairs == null && seatingAvailability != null){
            boolean exit = false;
            for(int i = 0; i < seatingAvailability.length; i++){
                if(exit == true) break;
                for(int j = 0; j < seatingAvailability[0].length; j++){
                    if(exit == true) break;
                    if(studentsSitting[i][j] == null && seatingAvailability[i][j] == true){
                        Student lateStudent = new Student(firstName,lastName,height);
                        studentsSitting[i][j] = lateStudent;
                        exit = true;
                    }
                }
            }
        }

        
    }

    /**
     * A student decides to leave early
     * This method deletes an early-leaving student from wherever the students 
     * are at (ie. whatever activity is not empty)
     * 
     * Assume the student's name is unique
     * 
     * @param firstName the student's first name
     * @param lastName the student's last name
     */
    public void deleteLeavingStudent ( String firstName, String lastName ) {
        // WRITE YOUR CODE HERE
        if(studentsInLine != null){
            
            //if its the last and only student that is leaving
            if(studentsInLine.getNext() == null){
                studentsInLine = null;
            }else{
                SNode prev = studentsInLine;
                SNode ptr = studentsInLine.getNext();
                while(ptr.getNext() != null){
                  if((ptr.getStudent()).getFirstName().equals(firstName) && ptr.getStudent().getLastName().equals(lastName)){
                     prev.setNext(ptr.getNext());
                  }
                  prev = prev.getNext();
                  ptr = ptr.getNext();
                }
            }
        }else{

        if(musicalChairs != null){
            if(musicalChairs.getNext() == musicalChairs && musicalChairs.getStudent().getFirstName().equals(firstName) && musicalChairs.getStudent().getLastName().equals(lastName)){
                musicalChairs = null;
            }else{

                SNode prev = musicalChairs;
                SNode ptr = musicalChairs.getNext();
                while(ptr.getNext() != musicalChairs){
                    if(ptr.getStudent().getFirstName().equals(firstName) && ptr.getStudent().getLastName().equals(lastName)){
                        prev.setNext(ptr.getNext());
                    }
                    prev = prev.getNext();
                    ptr = ptr.getNext();
                }
            }  
        }
        }
        if(musicalChairs == null && studentsInLine == null){
            for(int i = 0; i < seatingAvailability.length; i++){
            for(int j = 0; j < seatingAvailability[0].length; j++){
                if(seatingAvailability[i][j] == true && studentsSitting[i][j] != null &&studentsSitting[i][j].getFirstName().equals(firstName) && studentsSitting[i][j].getLastName().equals(lastName)){
                    studentsSitting[i][j] = null;
                }
            }
        }

        }
        

       

    }

    /**
     * Used by driver to display students in line
     * DO NOT edit.
     */
    public void printStudentsInLine () {

        //Print studentsInLine
        StdOut.println ( "Students in Line:" );
        if ( studentsInLine == null ) { StdOut.println("EMPTY"); }

        for ( SNode ptr = studentsInLine; ptr != null; ptr = ptr.getNext() ) {
            StdOut.print ( ptr.getStudent().print() );
            if ( ptr.getNext() != null ) { StdOut.print ( " -> " ); }
        }
        StdOut.println();
        StdOut.println();
    }

    /**
     * Prints the seated students; can use this method to debug.
     * DO NOT edit.
     */
    public void printSeatedStudents () {

        StdOut.println("Sitting Students:");

        if ( studentsSitting != null ) {
        
            for ( int i = 0; i < studentsSitting.length; i++ ) {
                for ( int j = 0; j < studentsSitting[i].length; j++ ) {

                    String stringToPrint = "";
                    if ( studentsSitting[i][j] == null ) {

                        if (seatingAvailability[i][j] == false) {stringToPrint = "X";}
                        else { stringToPrint = "EMPTY"; }

                    } else { stringToPrint = studentsSitting[i][j].print();}

                    StdOut.print ( stringToPrint );
                    
                    for ( int o = 0; o < (10 - stringToPrint.length()); o++ ) {
                        StdOut.print (" ");
                    }
                }
                StdOut.println();
            }
        } else {
            StdOut.println("EMPTY");
        }
        StdOut.println();
    }

    /**
     * Prints the musical chairs; can use this method to debug.
     * DO NOT edit.
     */
    public void printMusicalChairs () {
        StdOut.println ( "Students in Musical Chairs:" );

        if ( musicalChairs == null ) {
            StdOut.println("EMPTY");
            StdOut.println();
            return;
        }
        SNode ptr;
        for ( ptr = musicalChairs.getNext(); ptr != musicalChairs; ptr = ptr.getNext() ) {
            StdOut.print(ptr.getStudent().print() + " -> ");
        }
        if ( ptr == musicalChairs) {
            StdOut.print(musicalChairs.getStudent().print() + " - POINTS TO FRONT");
        }
        StdOut.println();
    }

    /**
     * Prints the state of the classroom; can use this method to debug.
     * DO NOT edit.
     */
    public void printClassroom() {
        printStudentsInLine();
        printSeatedStudents();
        printMusicalChairs();
    }

    /**
     * Used to get and set objects.
     * DO NOT edit.
     */

    public SNode getStudentsInLine() { return studentsInLine; }
    public void setStudentsInLine(SNode l) { studentsInLine = l; }

    public SNode getMusicalChairs() { return musicalChairs; }
    public void setMusicalChairs(SNode m) { musicalChairs = m; }

    public boolean[][] getSeatingAvailability() { return seatingAvailability; }
    public void setSeatingAvailability(boolean[][] a) { seatingAvailability = a; }

    public Student[][] getStudentsSitting() { return studentsSitting; }
    public void setStudentsSitting(Student[][] s) { studentsSitting = s; }

}
