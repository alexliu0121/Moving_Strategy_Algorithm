import java.util.*;

public class A1073311_checkpoint7_BlockPriorityQueue implements A1073311_checkpoint7_Fringe {
    PriorityQueue<A1073311_checkpoint7_Block> priorityQueue;
    //Description : the constuctor of BlockPriorityQueue.
    
    public A1073311_checkpoint7_BlockPriorityQueue(Comparator<A1073311_checkpoint7_Block> c){
        //TODO(1) : Initialize the PriorityQueue.
        //Hint1: While initializing the PriorityQueue, you have to input the comparator to the constructor.
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        priorityQueue=new PriorityQueue(c);
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/

    }
    public void add(A1073311_checkpoint7_Block block){
        //TODO(2) : add block into the PriorityQueue.
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        priorityQueue.add(block);
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/

    }
    public A1073311_checkpoint7_Block remove(){
        //TODO(3) :First check the PriorityQueue is empty or not and return and remove the object from the PriorityQueue.
        // If PriorityQueue is empty return null.
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        if(priorityQueue.isEmpty()){
            return null;
        }else{
            return priorityQueue.poll();
        }

        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/
    }
    public boolean isEmpty(){
        //TODO(4) :Check the PriorityQueue is empty or not.
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        return priorityQueue.isEmpty();
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/
    }
}