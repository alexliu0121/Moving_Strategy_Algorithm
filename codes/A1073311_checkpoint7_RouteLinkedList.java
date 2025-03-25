public class A1073311_checkpoint7_RouteLinkedList{
    private A1073311_checkpoint7_Node head;
    //Description : the constructor of leading the head Node as null.
    public A1073311_checkpoint7_RouteLinkedList(){
        this.head = null;
    }
    //Description : the constructor of input a Node as the head node.
    public A1073311_checkpoint7_RouteLinkedList(A1073311_checkpoint7_Node head){
        this.head = head;
    }
    public void delete(int axis, int direction){ 
        /*********************************The Past TODO (Checkpoint5)******************************
        //TODO(1):      Input value of Node as the reference Node,
        //              you have to delete the first Node that is same as the reference Node,
        //              and connect the following one and the previous one.
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        A1073311_checkpoint7_Node current=head;
        A1073311_checkpoint7_Node prev=null;
        do{
            if(current==null){
                return;
            }
            if(current.getAxis()==axis && current.getDirection()==direction){
                if(current.getNext()==null){
                    head=null;
                    return;
                }else if(prev==null){
                    head=head.getNext();
                    return;
                }else{
                    prev.setNext(current.getNext());
                    return;
                }
            }
            prev=current;
            current=current.getNext();
        }while(current.getNext()!=null);
        
        /********************************************************************************************
         END OF YOUR CODE
        ********************************************************************************************/
    }

    public A1073311_checkpoint7_Node search(int axis, int direction){ 
        /*********************************The Past TODO (Checkpoint5)********************************
        //TODO(2):      Input value of Node as the reference Node,
        //              you have to find the first Node that is same as the reference Node,
        //              and return it.
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        A1073311_checkpoint7_Node prev = null;
        A1073311_checkpoint7_Node current = head; 
        if(head != null) {
            /*while (current.getAxis() != axis && current.getDirection() !=direction && current.getNext() != null){
                prev = current;
                current = current.getNext();
            }*/
            while(current.getNext() != null){
                if (current.getAxis() == axis&&current.getDirection() == direction){
                    return current;
                }
                prev = current;
                current = prev.getNext();
            }
            if (current.getAxis() == axis&&current.getDirection() == direction){
                return current;
            }
            else if(current.getNext()==null){
                return null;
            }
            
        }
        return null;

        
        /********************************************************************************************
         END OF YOUR CODE
        ********************************************************************************************/
    }
    public void insert(int referenceAxis, int referenceDirection, int axis, int direction){ 
        /*********************************The Past TODO (Checkpoint6)********************************
        //TODO(3):      Input value of Node as the reference Node,
        //              and insert a Node BEFORE the first Node same as the reference Node,
        //              and connect the following one and the previous one.
        //Hint          The value of the Node is int variable axis and dirsction.
        //Hint2         If there is no reference node in linkedlist, print "Insertion null".
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        A1073311_checkpoint7_Node node = new A1073311_checkpoint7_Node(direction,axis);
        A1073311_checkpoint7_Node prev = null;
        A1073311_checkpoint7_Node current = head;
        int check=0;
        if (head == null){
            this.head = node;
            node.setNext(null);
        }else{
            while (current != null){
                if (current.getAxis() == referenceAxis && current.getDirection()==referenceDirection){ 
                    this.head=node;
                    node.setNext(current);
                    check=1;
                    break;
                }
                prev = current;
                current =current.getNext();
            }
            // this.head=node;
            // node.setNext(current);
            // check=1;
            if (check==0){ 
                System.out.println("Insertion null");
                return;
                // System.out.println("there's no reference node");
            } 
            
        }

        /********************************************************************************************
         END OF YOUR CODE
        ********************************************************************************************/
    }
    public int length(){
        /*********************************The Past TODO (Checkpoint5)********************************
        //TODO(4):      return how long the LinkedList is.
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        A1073311_checkpoint7_Node prev = null;
        A1073311_checkpoint7_Node current = head; 
        int length=1;
        if (head == null) System.out.println("The list is empty!");
        else{
            while(current.getNext()!=null){
                current= current.getNext();
                length++;
            }
            
        }
        return length;
        
        /********************************************************************************************
         END OF YOUR CODE
        ********************************************************************************************/
    }
    public void append(int axis, int direction){
        A1073311_checkpoint7_Node node = this.head;
        if(head != null){
            while(node.getNext() != null){
                node = node.getNext();
            }
            node.setNext(new A1073311_checkpoint7_Node(direction, axis));
        }else{
            setHead(new A1073311_checkpoint7_Node(direction, axis));
        }

    }
    public A1073311_checkpoint7_Node getHead(){
        return this.head;
    }
    public void setHead(A1073311_checkpoint7_Node head){
        this.head = head;
    }
}
    

