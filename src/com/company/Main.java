package com.company;

import java.util.*;

public class Main {

    static public class NodesPriorityComparator implements Comparator<Node> {
        @Override
        public int compare(Node x, Node y) {
            if (x.priority < y.priority) {
                return -1;
            }
            if (x.priority > y.priority) {
                return 1;
            }
            return 0;
        }
    }
    public static class Node{
        public int priority;
        public char data;
        public Node leftChild;
        public Node rightChild;

        public Node(int priority,char data){
             this.priority=priority;
             this.data=data;
        }
        public Node(int priority,Node leftChild,Node rightChild){
            this.priority=priority;
            this.leftChild=leftChild;
            this.rightChild=rightChild;
        }
        public void printNode(){
            System.out.println("KEY " + priority);
        }
    }
    public static class Tree{
        Node root;
        Comparator <Node> comparator = new NodesPriorityComparator();
        PriorityQueue<Node> nodesArray;
        public Tree(List<Character> CharsList, List<Integer> FrequencyList){
            nodesArray=new PriorityQueue<Node>(CharsList.size(),comparator);
            for(int i=0;i<CharsList.size();i++){
                Node temp = new Node(FrequencyList.get(i),CharsList.get(i));
                assert false;
                nodesArray.add(temp);
            }
            while(nodesArray.size()!=1){
                Node temp1=nodesArray.element();
                nodesArray.remove();
                Node temp2=nodesArray.element();
                nodesArray.remove();
                Node newNode = new Node(temp1.priority+temp2.priority,temp1,temp2);
                nodesArray.add(newNode);
            }
            root=nodesArray.element();
        }
        public Node getRoot(){
            return root;
        }
        public void print(Node startNode){
            if(startNode != null){
                if(startNode.leftChild!=null){
                    print(startNode.leftChild);
                }
                startNode.printNode();
                if(startNode.rightChild!=null){
                    print(startNode.rightChild);
                }
            }
        }
        public String Coding(char data,String code,Node previousNode){
            Node current = previousNode;
            String temp="";
            if(current.data!=data){
                if(current.leftChild!=null){
                    code+="0";
                    temp = Coding(data,code,current.leftChild);
                    if(!temp.equals("")){
                        return temp;
                    }
                }
                if(current.rightChild!=null){
                    code+="1";
                    temp = Coding(data, code, current.rightChild);
                    if(!temp.equals("")){
                        return temp;
                    }
                }
            }
            else{
                return code;
            }
            return "";
        }
    }

    static String CodeHaffman(String text){
        List<Character> CharsList =new ArrayList<>();
        List<Integer> FrequencyList= new ArrayList<>();
        List<String> charsCode = new ArrayList<>();
        for (int i=0;i<text.length();i++){
            if(!CharsList.contains(text.charAt(i))){
                CharsList.add(text.charAt(i));
                FrequencyList.add(1);
            }
            else{
                int temp=FrequencyList.get(CharsList.indexOf(text.charAt(i)));
               FrequencyList.set( CharsList.indexOf(text.charAt(i)),temp++);
            }
        }
        Collections.reverse(FrequencyList);
        Collections.reverse(CharsList);

        Tree tree=new Tree(CharsList,FrequencyList);
        for(int i =0;i<CharsList.size();i++){
            String code="";
            charsCode.add(tree.Coding(CharsList.get(i),code,tree.getRoot()));
        }
        String codingText="";
        for(int i=0;i<text.length();i++){

            codingText+=charsCode.get(CharsList.indexOf(text.charAt(i)));
        }
        return codingText;
    }
    static String GetNewChars(String text,int countOfChars,int startPosition){
        return text.substring(text.length()-startPosition,text.length()-startPosition+countOfChars);
    }
    static String Decode(String text){
        int countOfChars=0;
        int startPosition=0;
        String newText="";
        for(int i=0;i<text.length();i++){
            countOfChars=0;
            startPosition=0;
            if(text.charAt(i) == '(')
            {
                i++;
                String temp="";
                while(text.charAt(i)!=','){
                    temp+=text.charAt(i);
                    i++;
                }
                startPosition=Integer.valueOf(temp);
                i++;
                temp="";
                while(text.charAt(i)!=')'){
                    temp+=text.charAt(i);
                    i++;
                }
                countOfChars=Integer.valueOf(temp);
                newText+=GetNewChars(newText,countOfChars,startPosition);

            }
            else{
                newText+=text.charAt(i);
            }
        }

        return newText;
    }
    static int CountingChars(String text){
        String temp="";
        for(int i=0;i<text.length();i++){
            if(text.charAt(i)=='('){
                i++;
                while(text.charAt(i)!=','){
                    temp=temp+text.charAt(i);
                    i++;
                }
                i++;
                while(text.charAt(i)!=')'){
                    temp=temp+text.charAt(i);
                    i++;
                }
            }
            else{
                temp=temp+text.charAt(i);
            }
        }
        System.out.println(temp);
        return temp.length();
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        String textForCounting=text;
        text=Decode(text);
        int sizeOfNewText= text.length();
        System.out.println(text);
        System.out.println("Размер архивированного текста:"+CountingChars(textForCounting));
        System.out.println("Размер неархивированного текста:"+sizeOfNewText);
        System.out.println("Текст при архивации методом Хафмана:"+CodeHaffman(text));
    }
}
