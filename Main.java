// Sam Heidler - srh220000 - Project 1 

import java.util.Scanner;
import java.io.*;

public class Main {

    // initialize attributes
    protected static final int COLUMNS = 10;
    protected static final int ROWS = 10;

    // declare a 2D array of creatures called "grid"
    // initialize the array of Creature objects with a size of 10x10 or ROWSxCOLUMNS
    protected static Creature[][] grid = new Creature[ROWS][COLUMNS];

    /*******************************************************
     *                       main                          *
     *   creates Scanner for user input and file input,    *
     *     tests file, passes it to getFileData, calls     *
     *   subsequent methods to perform logic on file data  *
     *******************************************************/
    public static void main(String[] args) throws IOException 
    {

        //int antCount = 0; // DELETE later
        //int beetleCount = 0; // DELETE later

        int numTurns;

        String northDistance;
        String southDistance;
        String eastDistance;
        String westDistance;

        char desiredMove;
        char antChar;
        char beetleChar;

        int desiredRow;
        int desiredColumn;



        Scanner scnr = new Scanner(System.in); // gets input from keyboard

        // get input file name from user
        System.out.print("Enter the file to open: "); // "testFile.txt" in my program - // DELETE 
        String filename = scnr.nextLine(); // read in file name to open

        // get char to represent ant 
        System.out.print("Enter the char to represent ant: "); // DELETE
        antChar = scnr.next().charAt(0);

        // get char to represent beetle 
        System.out.print("Enter the char to represent beetle: "); // DELETE
        beetleChar = scnr.next().charAt(0);

        // get number of turns to simulate from the user
        System.out.print("Enter the number of turns to simulate: "); // DELETE
        numTurns = scnr.nextInt();

        
        File inFS = new File(filename); // create file object
        Scanner inputFile = new Scanner(inFS); // scanner for input from file to console

        if (inFS.exists()) // if the file is able to be opened, use it
        { 

            // call function to parse the input file
            String[] arr = getFileData(inputFile); // returns file input as an array of strings and stores it in "arr"

            populateGrid(arr); // accepts array of strings and populates the grid
                               // by breaking each string into an array of characters


            for(int x = 1; x <= (numTurns); x++)
            {
                // this nested loop iterates through the grid from top to bottom of each column
                // and goes from left to right
                for (int column = 0; column < COLUMNS; column++) // starts at the leftmost column and moves to the right
                {

                    for (int row = 0; row < ROWS; row++) // inside this loop is where searching for objects/
                    {                                    // calling their methods will occur(as it moves 
                                                        // down the column by increasing row index)

                        if (grid[row][column] != null) // if the array index isn't empty, 
                        {                               // then determine what kind of object it is
                        
                            Creature obj = grid[row][column]; // stores object in "obj" for testing

                    
                            /********************************************************
                             *              Beetle function/method calls           *
                             ********************************************************/
                            // iterate through and move all beetles before ants move - TURN ORDER
                            if(obj instanceof Beetle && !obj.hasMoved) // if it's a Beetle and it hasn't moved this turn... 
                            {
                                obj.survivalCounter++; // if the object lived, count how many turns its been alive



                                Beetle obj1 = (Beetle) obj;
                                obj1.feedingCounter++; // used for beetle starve method




                                northDistance = findDistanceN(row, column, obj);
                                //System.out.println("Numeric string northDistance contains: " + northDistance);
                                southDistance = findDistanceS(row, column, obj);
                                //System.out.println("Numeric string southDistance contains: " + southDistance);
                                westDistance = findDistanceW(row, column, obj);
                                //System.out.println("Numeric string westDistance contains: " + westDistance);
                                eastDistance = findDistanceE(row, column, obj);
                                //System.out.println("Numeric string eastDistance contains: " + eastDistance);

                                // NOTE: ant found distances will be >0 and wall found will be <0
                                desiredMove = obj.move(northDistance, southDistance, eastDistance, westDistance);
                                //System.out.println("Beetles desired move: " + desiredMove);
                                // DELETE - used for testing 
                                //System.out.println(" N: " + northDistance + ", S: " + southDistance + ", E: " + eastDistance + ", W: " + westDistance);

                                if(desiredMove == 'N')
                                {
                                    desiredRow = (row-1);
                                    desiredColumn = column;

                                    checkAvailableThenMove(desiredRow, desiredColumn, row, column, obj);
                                }
                                else if(desiredMove == 'S')
                                {
                                    desiredRow = (row+1);
                                    desiredColumn = column;   
                                                            
                                    checkAvailableThenMove(desiredRow, desiredColumn, row, column, obj); 
                                }
                                else if(desiredMove == 'E')
                                {
                                    desiredRow = row;
                                    desiredColumn = (column+1);

                                    checkAvailableThenMove(desiredRow, desiredColumn, row, column, obj);
                                }
                                else if(desiredMove == 'W')
                                {
                                    desiredRow = row;
                                    desiredColumn = (column-1);

                                    checkAvailableThenMove(desiredRow, desiredColumn, row, column, obj);
                                }
                            } // end of if(obj instanceof Beetle && !obj.hasMoved)
                        } // end of if(grid[row][column] != null)
                    } // end of  for loop (rows)
                } // end of for loop(columns)  
                
                // iterate through and move all ants after beetles have moved
                for (int column = 0; column < COLUMNS; column++) // starts at the leftmost column and moves to the right
                {

                    for (int row = 0; row < ROWS; row++) // inside this loop is where searching for objects/
                    {                                    // calling their methods will occur(as it moves 
                                                        // down the column by increasing row index)

                        if (grid[row][column] != null) // if the array index isn't empty, 
                        {                               // then determine what kind of object it is
                        
                            Creature obj = grid[row][column]; // stores object in "obj" for testing


                            /********************************************************
                             *                 Ant function/method calls           *
                             ********************************************************/
                            if (obj instanceof Ant && !obj.hasMoved) // if it's an ant and it hasn't been called this turn...
                            {
                                obj.survivalCounter++; // if the object lived, count how many turns its been alive
                                // DELETE - used for testing
                                //antCount++; 
                                //System.out.print("\nANT in column " + (column + 1) + " row " + (row + 1)); // for testing - DELETE
                            
                                northDistance = findDistanceN(row, column, obj);
                                southDistance = findDistanceS(row, column, obj);
                                westDistance = findDistanceW(row, column, obj);
                                eastDistance = findDistanceE(row, column, obj);

                                // for testing - DELETE
                                //System.out.println(" N: " + northDistance + ", S: " + southDistance + ", E: " + eastDistance + ", W: " + westDistance);

                                // NOTE: beetle found distance will be <10 and no beetle found distance will =10
                                desiredMove = obj.move(northDistance, southDistance, eastDistance, westDistance);

                                if(desiredMove == 'N')
                                {
                                    desiredRow = (row-1);
                                    desiredColumn = column;

                                    checkAvailableThenMove(desiredRow, desiredColumn, row, column, obj);
                                }
                                else if(desiredMove == 'S')
                                {
                                    desiredRow = (row+1);
                                    desiredColumn = column;   
                                                            
                                    checkAvailableThenMove(desiredRow, desiredColumn, row, column, obj); 
                                }
                                else if(desiredMove == 'E')
                                {
                                    desiredRow = row;
                                    desiredColumn = (column+1);

                                    checkAvailableThenMove(desiredRow, desiredColumn, row, column, obj);
                                }
                                else if(desiredMove == 'W')
                                {
                                    desiredRow = row;
                                    desiredColumn = (column-1);

                                    checkAvailableThenMove(desiredRow, desiredColumn, row, column, obj);
                                }

                                

    /*                            else // for testing - DELETE
                                {
                                    // object does nothing 
                                    System.out.println(" doesn't want to move");
                                }
    */                            
                            }
                        } // end of if(grid[row][column] != null)
                    } // end of  for loop (rows)
                } // end of for loop(columns)

                // beetle starve checker
                if(numTurns >= 5) // start checking for starvation on turn 5
                {
                    for (int column = 0; column < COLUMNS; column++) // starts at the leftmost column and moves to the right
                    {

                        for (int row = 0; row < ROWS; row++) // inside this loop is where searching for objects/
                        {
                            if (grid[row][column] != null) // if the array index isn't empty, 
                            {                               // then determine what kind of object it is
                            
                                Creature obj = grid[row][column]; // stores object in "obj" for testing


                                if (obj instanceof Beetle)// && obj.starve(obj.survivalCounter)) // breed returns true if an object has survived for (counter%8 == 0)
                                {
                                    //Beetle obj1 = (Beetle) obj;
                                    if(obj.starve(obj.feedingCounter))
                                    {
                                        grid[row][column] = null; // kill the beetle
                                    }
                                        
                                }
                            }
                        }
                    }
                }


                for (int column = 0; column < COLUMNS; column++) // starts at the leftmost column and moves to the right
                {

                    for (int row = 0; row < ROWS; row++) // inside this loop is where searching for objects/
                    {
                        if (grid[row][column] != null) // if the array index isn't empty, 
                        {                               // then determine what kind of object it is
                        
                            Creature obj = grid[row][column]; // stores object in "obj" for testing


                            if (obj instanceof Ant && obj.breed(obj.survivalCounter)) // breed returns true if an object has survived for (counter%3 == 0)
                            {
                                    checkAvailableThenBreed(row, column, obj);
                            }
                        }
                    }
                }

                // call beetle breed method
                for (int column = 0; column < COLUMNS; column++) // starts at the leftmost column and moves to the right
                {

                    for (int row = 0; row < ROWS; row++) // inside this loop is where searching for objects/
                    {
                        if (grid[row][column] != null) // if the array index isn't empty, 
                        {                               // then determine what kind of object it is
                        
                            Creature obj = grid[row][column]; // stores object in "obj" for testing


                            if (obj instanceof Beetle && obj.breed(obj.survivalCounter)) // breed returns true if an object has survived for (counter%8 == 0)
                            {
                                    checkAvailableThenBreed(row, column, obj);
                            }
                        }
                    }
                }
                //int feed = 0;
/*                 System.out.println("T,U,R,N, ," + x);
                for (int row = 0; row < ROWS; row++)                
                {
                    for (int column = 0; column < COLUMNS; column++) // starts at the leftmost column and moves to the right
                    {
                        if (grid[row][column] == null)
                        {
                            System.out.print(" ,");
                        }
                        else if(grid[row][column] != null)
                        {
                            Creature obj = grid[row][column];
                            
                            if (obj instanceof Ant)
                            {
                                System.out.print(antChar);
                                System.out.print(",");

                                obj.hasMoved = false;   // reset bool so that the object can move in the next turn
                                //obj.survivalCounter++; // if the object lived, count how many turns its been alive

                            }
                            else
                            {
                                System.out.print(beetleChar);
                                System.out.print(",");

                                obj.hasMoved = false;
                                feed = obj.feedingCounter;

                                // might not work for beetle breed
                                //obj.survivalCounter++; 
                            
                            }

                        }
                       
                    }
                    System.out.print("\n");
                } // end of for loop (rows)
                System.out.print("\n");

                //System.out.println("feeding counter: " + feed);
            } // end of for loop (numTurns)
*/

                System.out.println("TURN " + x);
                for (int row = 0; row < ROWS; row++)                
                {
                    for (int column = 0; column < COLUMNS; column++) // starts at the leftmost column and moves to the right
                    {
                        if (grid[row][column] == null)
                        {
                            System.out.print(' ');
                        }
                        else if(grid[row][column] != null)
                        {
                            Creature obj = grid[row][column];
                            
                            if (obj instanceof Ant)
                            {
                                System.out.print(antChar);

                                obj.hasMoved = false;   // reset bool so that the object can move in the next turn
                                //obj.survivalCounter++; // if the object lived, count how many turns its been alive

                            }
                            else
                            {
                                System.out.print(beetleChar);

                                obj.hasMoved = false;

                                // might not work for beetle breed
                                //obj.survivalCounter++; 
                            
                            }

                        }
                       
                    }
                    System.out.print("\n");
                } // end of for loop (rows)
               System.out.print("\n");
            } // end of for loop (numTurns)
        



            //System.out.println("Ants: " + antCount + ", Beetles: " + beetleCount); // test that main found all the ants/beetles - DELETE

         

        // close scanner objects
        scnr.close();
        inputFile.close();
        } // end of if-statement that checks if file exists

    } // end of main() 


    /*******************************************************
     *                    findNeighbors                    *
     *  This function determines adjacent ant neighbors    *
     *    and returns that number as an integer to main    *
     *******************************************************/
    public static int findNeighbors(int objRow, int objColumn)
    {
        int antCount = 0;

        // check orthogonals and diagonals
        if((objRow-1) <= 9 && (objRow-1) >= 0)
        {
            if(grid[objRow-1][objColumn] instanceof Ant) // check one space up
            {
                antCount++;
            }

            if((objColumn+1) <= 9 && (objColumn+1) >= 0)
            {
                if(grid[objRow-1][objColumn+1] instanceof Ant) // check one space up & right
                {
                    antCount++;
                }
            }

        }
        if((objRow+1) <= 9 && (objRow+1) >= 0)
        {
            if(grid[objRow+1][objColumn] instanceof Ant) // check one space down
            {
                antCount++;
            }

            if((objColumn+1) <= 9 && (objColumn+1) >= 0)
            {
                if(grid[objRow+1][objColumn+1] instanceof Ant) // check one space down & right
                {
                    antCount++;
                }
            }

        }
        if((objColumn-1) <= 9 && (objColumn-1) >= 0)
        {
            if(grid[objRow][objColumn-1] instanceof Ant) // check one space left
            {
                antCount++;
            } 

            if((objRow+1) <= 9 && (objRow+1) >= 0)
            {
                if(grid[objRow+1][objColumn-1] instanceof Ant) // check one space left & down 
                {
                    antCount++;
                } 
            }

            if((objRow-1) <= 9 && (objRow-1) >= 0)
            {
                if(grid[objRow-1][objColumn-1] instanceof Ant) // check one space left & up
                {
                    antCount++;
                } 
            }
        }
        if((objColumn+1) <= 9 && (objColumn+1) >= 0)
        {
            if(grid[objRow][objColumn+1] instanceof Ant) // check one space right
            {
                antCount++;
            } 
           
        }
        


        return antCount; // returns total number of neighbors for a specified ant 
    }


    /*******************************************************
     *                   checkAvailableThenBreed           *
     *  This function determines availability of adjacent  *
     *    spaces for of objects - then creates new obj     *
     *******************************************************/
    public static void checkAvailableThenBreed(int objRow, int objColumn,  Creature obj1) 
    {
        if(obj1 instanceof Ant)
        {

            if((objRow-1) <= 9 && (objRow-1) >= 0 && grid[objRow-1][objColumn] == null) // check north
            {
                grid[objRow-1][objColumn] = new Ant();
                grid[objRow-1][objColumn].hasMoved = true;
            }
            else if((objColumn+1) <= 9 && (objColumn+1) >= 0 && grid[objRow][objColumn+1] == null) // check east
            {
                grid[objRow][objColumn+1] = new Ant();
                grid[objRow][objColumn+1].hasMoved = true;
            }
            else if((objRow+1) <= 9 && (objRow+1) >= 0 && grid[objRow+1][objColumn] == null) // check south
            {
                grid[objRow+1][objColumn] = new Ant();
                grid[objRow+1][objColumn].hasMoved = true;
            }
            else if((objColumn-1) <= 9 && (objColumn-1) >= 0 &&  grid[objRow][objColumn-1] == null) // check west
            {
                grid[objRow][objColumn-1] = new Ant();
                grid[objRow][objColumn-1].hasMoved = true;
            }
            else
            {
                // nothing
            }
        }
        else if(obj1 instanceof Beetle)
        {
            if((objRow-1) <= 9 && (objRow-1) >= 0 && grid[objRow-1][objColumn] == null) // check north
            {
                grid[objRow-1][objColumn] = new Beetle();
                grid[objRow-1][objColumn].hasMoved = true;
            }
            else if((objColumn+1) <= 9 && (objColumn+1) >= 0 && grid[objRow][objColumn+1] == null) // check east
            {
                grid[objRow][objColumn+1] = new Beetle();
                grid[objRow][objColumn+1].hasMoved = true;
            }
            else if((objRow+1) <= 9 && (objRow+1) >= 0 && grid[objRow+1][objColumn] == null) // check south
            {
                grid[objRow+1][objColumn] = new Beetle();
                grid[objRow+1][objColumn].hasMoved = true;
            }
            else if((objColumn-1) <= 9 && (objColumn-1) >= 0 &&  grid[objRow][objColumn-1] == null) // check west
            {
                grid[objRow][objColumn-1] = new Beetle();
                grid[objRow][objColumn-1].hasMoved = true;
            }
            else
            {
                // nothing
            }
        }
      
    } // end of checkAvailableThenBreed()

    /*******************************************************
     *                   checkAvailableThenMove            *
     *  This function determines availability of adjacent  *
     *    spaces for of objects - then moves it            *
     *******************************************************/
    public static void checkAvailableThenMove(int newRow, int newColumn, int oldRow, int oldColumn, Creature obj1) 
    {
        if(newRow >= 0 && newRow <= 9 && newColumn >= 0 && newColumn <= 9)
        {
            if(obj1 instanceof Ant)
            {
                if(grid[newRow][newColumn] == null) // check availability - for ANTS, BEETLES will check if empty or if ant
                {
                    grid[newRow][newColumn] = obj1; // move instance of ant to new position
                    grid[oldRow][oldColumn] = null; // remove instance of ant from original postion

                    obj1.hasMoved = true;
                }

            }
            else // if not an ant, obj1 must be a beetle
            {
                // split this to reset starve counter if beetle takes an ant's space
                if(grid[newRow][newColumn] == null) // check availability - BEETLES will check if empty or if ant
                {
                    grid[newRow][newColumn] = obj1; // move instance of ant to new position
                    grid[oldRow][oldColumn] = null; // remove instance of ant from original postion

                    obj1.hasMoved = true;                   
                }
                if(grid[newRow][newColumn] instanceof Ant)
                {
                    grid[newRow][newColumn] = obj1; // move instance of beetle to new position
                    grid[oldRow][oldColumn] = null; // remove instance of ant from original postion

                    obj1.hasMoved = true; 
                    
                    //Beetle obj = (Beetle) obj1;
                    obj1.feedingCounter = 0; // reset the feeding counter if the beetle eats an ant
                }
            }
            
/*             else
            {
                System.out.println(" can't move because space is taken");
            }
        }
        else // for testing - DELETE
        {
            System.out.println(" doesn't want to/can't move");
        }
    
    */     }   
} // end of checkAvailable()

    /*******************************************************
     *                       findDistanceN                 *
     *  This function determines distance of objects/walls *
     *  to the north of the object that's being examined   *
     *******************************************************/
    public static String findDistanceN(int rowIndex, int columnIndex, Creature obj1) 
    {

        boolean beetleFound = false;
        boolean antFound = false;

        int numAntNeighbors;

       /********************************************************
        *                 Ant Distance function                *
        ********************************************************/

        if (obj1 instanceof Ant) // this branches to run distance methods for the ant - which differ slightly
                                 // from the beetle
        {
            for (int x = (rowIndex - 1); x >= 0; x--) {
                if (grid[x][columnIndex] != null) // if the grid space isn't empty...
                {

                    Creature obj2 = grid[x][columnIndex]; // assign the space as a creature object

                    if (obj2 instanceof Beetle) {

                        beetleFound = true;

                        return (String.valueOf(rowIndex - x));
                        // System.out.println(" found beetle at a distance of " + (rowIndex - x));
                        // break;
                    }
                }
            } // end for loop

            if (!beetleFound) 
            {
                return "10";
            }

        } // end of ant distance block


       /********************************************************
        *                 Beetle Distance function             *
        ********************************************************/

        else // if the object isn't an Ant, then it's a Beetle - define beetle distance finding method 
        {
            for (int x = (rowIndex - 1); x >= 0; x--) 
            {
                if (grid[x][columnIndex] != null) // if the grid space isn't empty...
                {

                    Creature obj2 = grid[x][columnIndex]; // assign the space as a creature object

                    if (obj2 instanceof Ant) {

                        antFound = true;

                        numAntNeighbors = findNeighbors(x, columnIndex);

                        return (String.valueOf(rowIndex - x) + " " + String.valueOf(numAntNeighbors)); // append numAntNeighbors
                    }
                }
            } // end for loop
            if (!antFound) 
            {
                //return distance to wall as negative
                return (String.valueOf((rowIndex+1) * -1) + " 0");

            }
        } // end of beetle distance block

        return null; // added to make this function work b/c of conditional return statements in ant block

    } // end of findDistanceN()


    /*******************************************************
     *                       findDistanceS                 *
     *  This function determines distance of objects/walls *
     *  to the south of the object that's being examined   *
     *******************************************************/
    public static String findDistanceS(int rowIndex, int columnIndex, Creature obj1) 
                                                                                     
    {

        boolean beetleFound = false;
        boolean antFound = false;

        int numAntNeighbors = 0;

       /********************************************************
        *                 Ant Distance function                *
        ********************************************************/

        if (obj1 instanceof Ant) // this branches to run distance methods for the ant - which differ slightly
                                 // from the beetle
        {
            for (int x = (rowIndex + 1); x <= 9; x++) {
                if (grid[x][columnIndex] != null) // if the grid space isn't empty...
                {

                    Creature obj2 = grid[x][columnIndex]; // assign the space as a creature object

                    if (obj2 instanceof Beetle) 
                    {
                        beetleFound = true;

                        return (String.valueOf(x - rowIndex));
                    }
                }
            } // end for loop

            if (!beetleFound) 
            {
                return "10"; // large number that won't be considered by ant.move()
            }

        } // end of ant distance block


       /********************************************************
        *                 Beetle Distance function             *
        ********************************************************/

        else // if the object isn't an Ant, then it's a Beetle - define beetle distance finding method 
        {
            for (int x = (rowIndex + 1); x <= 9; x++) 
            {
                if (grid[x][columnIndex] != null) // if the grid space isn't empty...
                {

                    Creature obj2 = grid[x][columnIndex]; // assign the space as a creature object

                    if (obj2 instanceof Ant) {

                        antFound = true;

                        numAntNeighbors = findNeighbors(x, columnIndex);

                        return (String.valueOf(x - rowIndex) + " " + String.valueOf(numAntNeighbors)); // append numAntNeighbors

                        //return (String.valueOf(x - rowIndex));
                    }
                }
            } // end for loop
            if (!antFound) 
            {
                //return distance to wall as negative
                return (String.valueOf((10 - rowIndex) * -1) + " 0");

            }
        } // end of beetle distance block

        return null; // added to make this function work b/c of conditional return statements in ant block
    } // end of findDistanceS()


    /*******************************************************
     *                       findDistanceW                 *
     *  This function determines distance of objects/walls *
     *  to the west of the object that's being examined    *
     *******************************************************/
    public static String findDistanceW(int rowIndex, int columnIndex, Creature obj1) 
    {

        boolean beetleFound = false;
        boolean antFound = false;

        int numAntNeighbors = 0;

       /********************************************************
        *                 Ant Distance function                *
        ********************************************************/

        if (obj1 instanceof Ant) // this branches to run distance methods for the ant - which differ slightly
                                 // from the beetle
        {
            for (int x = (columnIndex - 1); x >= 0; x--) {
                if (grid[rowIndex][x] != null) // if the grid space isn't empty...
                {

                    Creature obj2 = grid[rowIndex][x]; // assign the space as a creature object

                    if (obj2 instanceof Beetle) 
                    {
                        beetleFound = true;

                        return (String.valueOf(columnIndex - x)); // (x = {(columnIndex - 1), ... ,0}) - possible set of values for x
                    }
                }
            } // end for loop

            if (!beetleFound) 
            {
                return "10"; // the ant won't consider 10 within it's move method 
            }

        } // end of ant distance block


       /********************************************************
        *                 Beetle Distance function             *
        ********************************************************/

        else // if the object isn't an Ant, then it's a Beetle - define beetle distance finding method 
        {
            for (int x = (columnIndex - 1); x >= 0; x--) 
            {
                if (grid[rowIndex][x] != null) // if the grid space isn't empty...
                {

                    Creature obj2 = grid[rowIndex][x]; // assign the space as a creature object

                    if (obj2 instanceof Ant) {

                        antFound = true;

                        numAntNeighbors = findNeighbors(rowIndex, x);

                        return (String.valueOf(columnIndex - x) + " " + String.valueOf(numAntNeighbors)); // append numAntNeighbors

                        //return (String.valueOf(columnIndex - x));
                    }
                }
            } // end for loop
            if (!antFound) 
            {
                //return distance to wall as negative
                return (String.valueOf((columnIndex+1) * -1) + " 0");

            }
        } // end of beetle distance block

        return null; // added to make this function work b/c of conditional return statements in ant block

    } // end of findDistanceW()


    /*******************************************************
     *                       findDistanceE                 *
     *  This function determines distance of objects/walls *
     *   to the east of the object that's being examined   *
     *******************************************************/
    public static String findDistanceE(int rowIndex, int columnIndex, Creature obj1) 
                                                                                     
    {

        boolean beetleFound = false;
        boolean antFound = false;

        int numAntNeighbors = 0;

       /********************************************************
        *                 Ant Distance function                *
        ********************************************************/

        if (obj1 instanceof Ant) // this branches to run distance methods for the ant - which differ slightly
                                 // from the beetle
        {
            for (int x = (columnIndex + 1); x <= 9; x++) {
                if (grid[rowIndex][x] != null) // if the grid space isn't empty...
                {

                    Creature obj2 = grid[rowIndex][x]; // assign the space as a creature object

                    if (obj2 instanceof Beetle) 
                    {
                        beetleFound = true;

                        return (String.valueOf(x - columnIndex));
                    }
                }
            } // end for loop

            if (!beetleFound) 
            {
                return "10"; // large number that won't be considered by ant.move()
            }

        } // end of ant distance block


       /********************************************************
        *                 Beetle Distance function             *
        ********************************************************/

        else // if the object isn't an Ant, then it's a Beetle - define beetle distance finding method 
        {
            for (int x = (columnIndex + 1); x <= 9; x++) 
            {
                if (grid[rowIndex][x] != null) // if the grid space isn't empty...
                {

                    Creature obj2 = grid[rowIndex][x]; // assign the space as a creature object

                    if (obj2 instanceof Ant) {

                        antFound = true;

                        numAntNeighbors = findNeighbors(rowIndex, x);

                        return (String.valueOf(x - columnIndex) + " " + String.valueOf(numAntNeighbors)); // append numAntNeighbors

                        //return (String.valueOf(x - columnIndex));
                    }
                }
            } // end for loop
            if (!antFound) 
            {
                //return distance to wall as negative
                return (String.valueOf((10 - columnIndex) * -1) + " 0");

            }
        } // end of beetle distance block

        return null; // added to make this function work b/c of conditional return statements in ant/beetle block
    } // end of findDistanceE()



    /*******************************************************
     *                       getFileData                   *
     * This function loops to get input for the user file  *
     *******************************************************/
    public static String[] getFileData(Scanner inFS) {

        String currWord; // holds the current line of input from the file
        String[] arrOfStr = new String[10]; // holds all 10 lines of input from the user file as string elements

        // this for loop reads each line of input as a string 
        // and stores that string in an array
        for (int x = 0; x < 10; x++) {
            currWord = inFS.nextLine(); // gets line of input from file as a string
            arrOfStr[x] = currWord; // stores string input into an array

        }

        return arrOfStr;

    } // end of getFileData

    /*******************************************************
     *                  populateGrid                       *
     * this function converts strings to character arrays  *
     * and uses those characters to place objects in the   *
     *     grid based on the type of character             *
     *     B = new Beetle object & a = new Ant object      *
     *******************************************************/
    public static void populateGrid(String[] arr) {

        String temp;
        char[] ch = new char[10]; // holds input file data string element as an array of characters

        for (int row = 0; row < 10; row++) { // iterates once for each entire row (10 rows)

            temp = arr[row]; // store current string element of arr in temp
            ch = temp.toCharArray(); // convert temp string to an array of 10 characters

            for (int column = 0; column < 10; column++) { // column represents the index in each row 
                                                          // (10 indexes per row)

                // based on the character (B or a) to decide which object to insert in grid
                if (ch[column] == 'B') {
                    grid[row][column] = new Beetle();
                } else if (ch[column] == 'a') {
                    grid[row][column] = new Ant();
                }
            } // end of nested for loop(columns)

        } // end of outter for loop (rows)

    } // end of populateGrid

} // end of Main class
