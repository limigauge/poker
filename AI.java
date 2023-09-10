/*
 *   CLASS : AI.java
 *   Author: Michelle Li, 7866927
 *
 *   REMARKS: Abstract class for AI that is a subclass of player. Is the parent of the CPU types: random CPU and smarter CPU.
 */


public abstract class AI extends Player{

    public AI(){
        super();
    } //default constructor

    //force implementation of a method that selects discards to the CPUS.
    public abstract void selectDiscards();

}
