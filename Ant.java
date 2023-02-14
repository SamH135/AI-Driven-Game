public class Ant extends Creature 
{
    protected boolean hasMoved = false;

    @Override
    public char move(String north, String south, String east, String west) // NOTE: beetle found distance will be <10 and no beetle found distance will =10
    {
        // convert string distances to integers for comparison
        int northDistance = Integer.parseInt(north);
        int southDistance = Integer.parseInt(south);
        int eastDistance = Integer.parseInt(east);
        int westDistance = Integer.parseInt(west);

        // if all distances = 10, then the ant didn't find a beetle and won't try to move

        int[] distArr = {northDistance, eastDistance, southDistance, westDistance}; // {N, E, S, W} : (order of prioritization)
        
        int min = 10; // used to find values smaller than 10 - values that aren't walls 
        int max = 0; // used to find farthest beetle in case of tie and !noBeetle
        int maxIndex = 10; // random initialization so program will run
        //int minIndex = 5;  // ^^^
        
        boolean beetleFound = false; // ant has found an orthogonal beetle
        boolean tieFound = false; // ant has found 2+ beetles that are equidistant
        boolean noBeetle = false; // there exists a direction with no beetle - stored in noBeetleArr[] = {N, E, S, W}
        
        
        for(int c=0; c<4; c++) // will check {N, E, S, W} distances - in that order (order of prioritization)
        { // this loop checks for ties/orthognal beetles/existence of pathways with no beetles - if beetles exist orthogonal
            if(!beetleFound && distArr[c] != 10) // min not found yet
            {
                beetleFound = true; // something besides 10 was discovered - a beetle!!!!!! AHHHHHHH!!!!!
                min = distArr[c]; // assigns first postive value to min - closest beetle
                //minIndex = c;
            }
            
            else if(beetleFound && distArr[c] != 10) // a beetle has been found
            {
                if(distArr[c] < min) 
                { // a closer beetle has been discovered
                    min = distArr[c]; 
                    //minIndex = c;
                    tieFound = false; // resets tieFound boolean anytime a smaller value is discovered - no longer a tie for closest beetle
                }
                else if(distArr[c] == min) // if distance is = to current smallest distance, then a tie has been found
                {
                    tieFound = true;
                }
            }
            
            else if(distArr[c] == 10) // there exists a pathway with no beetle
            {
                noBeetle = true;
            }
            
        } // end for loop
        
        if(beetleFound)
        {
            if(!tieFound)   // if no ties, then the ant will move the opposite direction of the closest beetle
            {
                if(northDistance < southDistance && northDistance < eastDistance && northDistance < westDistance)
                {
                    return 'S';
                }
                // if south is the smallest distance (closest beetle), then try to go the opposite way
                else if(southDistance < eastDistance && southDistance < westDistance)
                {
                    return 'N';
                }
                //if east is the smallest distance (closest beetle), then try to go the opposite way
                else if(eastDistance < westDistance) 
                {
                    return 'W';
                }
                //if all other branches are false, then west is the direction of the closest beetle
                else
                {
                    return 'E';
                } 
/*                if(minIndex == 0)
                {
                    return 'S';
                    //System.out.print("South");
                }
                
                else if(minIndex == 1)
                {
                   return 'W';
                   //System.out.print("West");                    
                }
                
                else if(minIndex == 2)
                {
                   return 'N';
                   //System.out.print("North");
                }
                
                else
                {
                   return 'E';
                   //System.out.print("East");
                }
 */               
            } // end of if(!tieFound)
            
            else if(tieFound) // if there's a tie for closest beetle...
            {
                if(!noBeetle) // if beetles are in all directions...
                {
                    for(int c=0; c<4; c++) // will check N, E, S, W - in that order (order of prioritization)
                    {
                        if(distArr[c] > max) // if a beetle is further than the tied beetles... 
                        {
                            max = distArr[c];
                            maxIndex = c;
                        }
                    } // end of for loop
                    
                    
                    if(tieFound) // if there's two farthestBeetle
                    {
                       for(int c=0; c<4; c++) // will check if there's a tie for max index
                        {
                            if(distArr[c] == max) 
                            {
                                //secondTieFound = true;
                                maxIndex = c;
                                break;
                            }
                        } 
                    }
                } // end of if(!noBeetle)
                
                else // noBeetle is true (there's a tie for closest beetle and a clear pathway available)
                {
                    for(int c=0; c<4; c++) // will check if there's a tie for max index
                    {
                        if(distArr[c] == 10) 
                        {
                            maxIndex = c;
                            break;
                        }
                    } 
                }
                if(maxIndex == 0)
                {
                    return 'N';
                    //System.out.print("North");
                }
                else if(maxIndex == 1)
                {
                   return 'E';
                   //System.out.print("East");
                }
                else if(maxIndex == 2)
                {
                   return 'S';
                   //System.out.print("South");
                }
                else
                {
                   return 'W';
                   //System.out.print("West");
                }
                    
    
                    
            } // end of tieFound
            
        } // end of if(beetleFound)
        
        // no beetle was found
        return 'c'; // does nothing in main()
        
/*         if(northDistance == 10 && southDistance == 10 && eastDistance == 10 && westDistance == 10)
        {
            return 'F';
        }
        // if north is the smallest distance (closest beetle), then try to go the opposite way
        else if(northDistance < southDistance && northDistance < eastDistance && northDistance < westDistance)
        {
            return 'S';
        }
        // if south is the smallest distance (closest beetle), then try to go the opposite way
        else if(southDistance < eastDistance && southDistance < westDistance)
        {
            return 'N';
        }
        //if east is the smallest distance (closest beetle), then try to go the opposite way
        else if(eastDistance < westDistance) 
        {
            return 'W';
        }
        //if all other branches are false, then west is the direction of the closest beetle
        else
        {
            return 'E';
        }
*/
    } // end of move()
    
    @Override
    public boolean breed(int survivalCounter)
    {
        if(survivalCounter == 0)
        {
            return false;
        }
        else if(survivalCounter%3 == 0) // the ant has survived a factor of (3 * numTurns) 
        {
            return true;
        }
        else
        {
            return false;
        }
    } // end of breed()

     @Override
    public boolean starve(int survivalCounter)
    {
        return false;
    }
    
} // end of Ant class
