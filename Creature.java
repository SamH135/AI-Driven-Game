public abstract class Creature 
{
    

    protected boolean hasMoved;

    protected int survivalCounter = 0; // used for object breed functions

    protected int feedingCounter = 0; // used for beetle starve - reset when beetle takes ant space "eats it"
    
    
    abstract char move(String north, String south, String east, String west);
    
    abstract boolean breed(int s);
    abstract boolean starve(int s);
}
