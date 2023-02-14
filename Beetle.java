public class Beetle extends Creature 
{
    protected boolean hasMoved = false;
    

    @Override
    public char move(String north, String south, String east, String west)
    {
         // convert string distances to integers for comparison
         String[] strN = north.split(" ");
         String[] strS = south.split(" ");
         String[] strE = east.split(" ");
         String[] strW = west.split(" ");

         int northDistance = Integer.parseInt(strN[0]);
         int southDistance = Integer.parseInt(strS[0]);
         int eastDistance = Integer.parseInt(strE[0]);
         int westDistance = Integer.parseInt(strW[0]);
        
         int northNeighbors = Integer.parseInt(strN[1]);
         int southNeighbors = Integer.parseInt(strS[1]);
         int eastNeighbors = Integer.parseInt(strE[1]);
         int westNeighbors = Integer.parseInt(strW[1]);

        int[] arr = {northDistance, eastDistance, southDistance, westDistance}; // order of prioritization {N, E, S, W}
        int[] neighborArr = {northNeighbors, eastNeighbors, southNeighbors, westNeighbors};
        int[] distTieArray = {0, 0, 0, 0};

        int min = 0;
        int minIndex = 5; // nonexistent index to initialize the variable
        int tieIndex = 5;
        int maxNeighborIndex = 5;

        boolean minFound = false;
        boolean tieFound = false;

        
        // NOTE: ant found distances will be >0 and wall found will be <0

        if(northDistance > 0 || southDistance > 0 || eastDistance > 0 || westDistance > 0) // if the beetle found an ant 
        {    //(a distance is positive)...
            // find minimum value - exclude negatives
            for(int c=0; c<4; c++)
            {
                if(!minFound && arr[c] > 0) // min not found 
                {
                    minFound = true;
                    min = arr[c]; // assigns first postive value to min
                    minIndex = c; // assigns its index to minIndex
                }
                else if(minFound && arr[c] > 0) // min found
                {
                    if(arr[c] < min) // tests multiple postive values against first value
                    {
                        min = arr[c];
                        minIndex = c;
                        tieFound = false; // reset tieFound if a smaller value is found
                    }
                    else if(arr[c] == min)
                    {
                        tieFound = true; // set tieFound if an equal value is found
                    }
                }
                
            }
            for(int c=0; c<4; c++)
            {
                if(arr[c] == min)    // look for tied values
                {                       
                   
                    tieIndex = c;
                    distTieArray[c] = 1;
                    
                    //System.out.println("ties found: " + numTies);
/*                    if(neighborArr[c] > neighborArr[minIndex])// && c != minIndex)
                    {
                        //maxNeighbor = neighborArr[c];
                        maxNeighborIndex = c;
                    }
                    else if(neighborArr[c] == neighborArr[minIndex])
                    {
                        //maxNeighbor = neighborArr[c];
                        //maxNeighborIndex = minIndex;
                    }
*/              }
                
                
            }
            for(int x = 0; x < 4; x++) // show which indices contained ties
            {
                //System.out.println("distTieArray[" + x + "]");
                if(distTieArray[x] == 1)
                {
                    if(neighborArr[x] > neighborArr[minIndex])
                    {
                        maxNeighborIndex = x;
                        minIndex = maxNeighborIndex;
                        //System.out.println(" > branch, maxNeighborIndex: " + maxNeighborIndex);
                    }
                    else if(neighborArr[x] <= neighborArr[minIndex])
                    {
                        maxNeighborIndex = minIndex;
                        //System.out.println(" <= branch, maxNeighborIndex: " + maxNeighborIndex);
                    }
                }
            }
            
            //System.out.println("min index: " + minIndex + " maxNeighborIndex: " + maxNeighborIndex + " tie index: " + tieIndex);
            //System.out.println("Max neighbor: " + neighborArr[maxNeighborIndex] + " at index " + maxNeighborIndex);


            if(!tieFound)
            
            {
                //System.out.println("no tie found");
                if(minIndex == 0)
                {
                    return 'N';
                }
                else if(minIndex == 1)
                {
                    return 'E';
                }
                else if(minIndex == 2)
                {
                    return 'S';
                }
                else
                {
                    return 'W';
                }
            }
            else // a tie was found
            {
                //System.out.println("tie found");
                if(neighborArr[minIndex] < neighborArr[tieIndex]) // more adjacent ants for tie index than min index
                {
                    //System.out.println("neighborArr[minIndex] < neighborArr[tieIndex]");
                    if(tieIndex == 0)
                    {
                        return 'N';
                    }
                    else if(tieIndex == 1)
                    {
                    return 'E';
                    }
                    else if(tieIndex == 2)
                    {
                    return 'S';
                    }
                    else
                    {
                    return 'W';
                    }
                }
                else if(neighborArr[minIndex] > neighborArr[tieIndex])        // more adjacent ants for min index than tie index
                {
                    //System.out.println("neighborArr[minIndex] > neighborArr[tieIndex]");
                    if(maxNeighborIndex == 0)
                    {
                        return 'N';
                    }
                    else if(maxNeighborIndex == 1)
                    {
                    return 'E';
                    }
                    else if(maxNeighborIndex == 2)
                    {
                    return 'S';
                    }
                    else
                    {
                    return 'W';
                    }
/*                    if(minIndex == 0)
                    {
                        return 'N';
                    }
                    else if(minIndex == 1)
                    {
                    return 'E';
                    }
                    else if(minIndex == 2)
                    {
                    return 'S';
                    }
                    else
                    {
                    return 'W';
                    }
                    
*/              }
                else if(neighborArr[minIndex] == neighborArr[tieIndex])
                {
                    //System.out.println("neighborArr[minIndex] == neighborArr[tieIndex]");
                    if(minIndex == 0)
                    {
                        return 'N';
                    }
                    else if(minIndex == 1)
                    {
                    return 'E';
                    }
                    else if(minIndex == 2)
                    {
                    return 'S';
                    }
                    else
                    {
                    return 'W';
                    }
                }
            } // end of tie found
            
        }
        else //if(northDistance < 0 || southDistance < 0 || eastDistance < 0 || westDistance < 0) // the beetle did not find an ant...
        {
            minIndex = 0;
            for(int x=0; x<4; x++)
            {
                if(arr[x] < arr[minIndex])
                {
                    minIndex = x;
                }
            }
            if(minIndex == 0)
                    {
                        return 'N';
                    }
                    else if(minIndex == 1)
                    {
                    return 'E';
                    }
                    else if(minIndex == 2)
                    {
                    return 'S';
                    }
                    else
                    {
                    return 'W';
                    }
/*             if(northDistance < southDistance && northDistance < eastDistance && northDistance < westDistance)
            {
                return 'N';
            }
            else if(southDistance < eastDistance && southDistance < westDistance)
            {
                return 'S';
            }
            //if east is the smallest distance (closest beetle), then try to go the opposite way
            else if(eastDistance < westDistance) 
            {
                return 'E';
            }
            //if all other branches are false, then west is the direction of the closest beetle
            else
            {
                return 'W';
            }
*/            
        }
        return '0';
    } // end of move()

            
            
            
            
            
            
            
/*             // find minimum value - exclude negatives
            for(int c=0; c<4; c++)
            {
                if(!minFound && arr[c] > 0) // min not found 
                {
                    minFound = true;
                    min = arr[c]; // assigns first postive value to min
                    minIndex = c; // assigns its index to minIndex
                }
                else if(minFound && arr[c] > 0) // min found
                {
                    if(arr[c] < min) // tests multiple postive values against first value
                    {
                        min = arr[c];
                        minIndex = c;
                    }
                }
                
            }
            for(int c=0; c<4; c++)
            {
                if(arr[c] == min && c != minIndex) // look for ties - exclude the minimum value thats already been found 
                {
                    tieFound = true;
                    tieIndex = c;
                }
                
            }


            if(!tieFound)
            {
                if(minIndex == 0)
                {
                    return 'N';
                }
                else if(minIndex == 1)
                {
                return 'E';
                }
                else if(minIndex == 2)
                {
                return 'S';
                }
                else
                {
                return 'W';
                }
            }
            else // a tie was found
            {
                if(neighborArr[minIndex] < neighborArr[tieIndex]) // more adjacent ants for tie index than min index
                {
                    if(tieIndex == 0)
                    {
                        return 'N';
                    }
                    else if(tieIndex == 1)
                    {
                    return 'E';
                    }
                    else if(tieIndex == 2)
                    {
                    return 'S';
                    }
                    else
                    {
                    return 'W';
                    }
                }
                else        // more adjacent ants for min index than tie index
                {
                    if(minIndex == 0)
                    {
                        return 'N';
                    }
                    else if(minIndex == 1)
                    {
                    return 'E';
                    }
                    else if(minIndex == 2)
                    {
                    return 'S';
                    }
                    else
                    {
                    return 'W';
                    }
                }
            } // end of tie found
            
        }
        else //if(northDistance < 0 || southDistance < 0 || eastDistance < 0 || westDistance < 0) // the beetle did not find an ant...
        {
            if(northDistance < southDistance && northDistance < eastDistance && northDistance < westDistance)
            {
                return 'N';
            }
            else if(southDistance < eastDistance && southDistance < westDistance)
            {
                return 'S';
            }
            //if east is the smallest distance (closest beetle), then try to go the opposite way
            else if(eastDistance < westDistance) 
            {
                return 'E';
            }
            //if all other branches are false, then west is the direction of the closest beetle
            else
            {
                return 'W';
            }
        }
*/        

        

/*          if(northDistance > 0 || southDistance > 0 || eastDistance > 0 || westDistance > 0) // beetle found an ant
         {
            if(northDistance < 0 && southDistance < 0 && eastDistance < 0 && westDistance > 0)
            {
                if(northDistance < southDistance && northDistance < eastDistance && northDistance < westDistance)
                {
                    return 'N';
                }
                else if(southDistance < eastDistance && southDistance < westDistance)
                {
                    return 'S';
                }
                //if east is the smallest distance (closest beetle), then try to go the opposite way
                else if(eastDistance < westDistance) 
                {
                    return 'E';
                }
                //if all other branches are false, then west is the direction of the closest beetle
                else
                {
                    return 'W';
                }
            }
            // if south is the smallest distance (closest beetle), then try to go the opposite way
            
         }

         else // beetle found a wall
         {
            return 'R';
          if(northDistance < southDistance && northDistance < eastDistance && northDistance < westDistance)
            {
                return 'N';
            }
            // if south is the smallest distance (closest beetle), then try to go the opposite way
            else if(southDistance < eastDistance && southDistance < westDistance)
            {
                return 'S';
            }
            //if east is the smallest distance (closest beetle), then try to go the opposite way
            else if(eastDistance < westDistance) 
            {
                return 'E';
            }
            //if all other branches are false, then west is the direction of the closest beetle
            else
            {
                return 'W';
            }
        }
        */              
    //return '0';

   
    
    @Override
    public boolean breed(int survivalCounter)
    {
        if(survivalCounter == 0)
        {
            return false;
        }
        else if(survivalCounter%8 == 0) // the beetle has survived a factor of (8 * numTurns) 
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean starve(int feedingCounter)
    {
        if(feedingCounter == 0)
        {
            return false;
        }
        else if(feedingCounter%5 == 0) // the beetle has survived a factor of (8 * numTurns) 
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
