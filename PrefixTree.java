import edu.princeton.cs.algs4.StdIn;
public class PrefixTree{
    private Node root;
    private int ind;
    private int dind;
    private String decodeStr;
    private class Node{
        char c;
        Node right, left;
        public Node(char c){
            this.c=c;
        }
    }
    public PrefixTree(String preorder){
        ind = 0;
        root = reConstruct(preorder);
    }

    private Node reConstruct(String s){
        if(ind >= s.length()) return null;
        char ch = s.charAt(ind);
        Node x = new Node(ch);


        if(ch == '*') {
            ind++;
            x.left = reConstruct(s);
            x.right = reConstruct(s);


        }
        if (ch != '*'){
            ind++;

        }  //go left
        return x;
    }

    public void getEncodings(){
        System.out.println("character     bits      encoding");
        System.out.println("----------------------------------");
        getEncodings(root,"");
    }

    private void getEncodings(Node x, String path){

        if (x == null) return;

        if(x.c != '*') {
            System.out.println(x.c + "     " + path.length() + "     " + path);
            return;
        }
        else {
            getEncodings(x.left, path+"0");
            getEncodings(x.right, path+"1");
        }

        return;
    }

    public void decode (String encodings){
        dind = 0;
        decodeStr = "";
        printEncodings(encodings);
        System.out.println(decodeStr);
        System.out.println("Number of bits: " +encodings.length());
        System.out.println("Number of characters: " + decodeStr.length());
        double compRatio =( (double) encodings.length() / (double) (decodeStr.length() * 8))*100;
        System.out.println("Compression ratio: " + compRatio +"%");


    }
  /*  public void printEncodings(Node x, String s){ //causing stackoverflow when printing decoded large txt file
        if(x.c != '*') {
            decodeStr = decodeStr + x.c;
            x = root;
        }
        if (dind >= s.length()) return;
        char ch = s.charAt(dind);


        if(ch == '0') {
            dind++;
            printEncodings(x.left, s);
        }
        if (ch == '1'){
            dind++;
            printEncodings(x.right, s);

        }
        return;

    }*/

    public void printEncodings(String s){
        Node current = root;
        while (dind < s.length()){
            if (current.c != '*') {
                decodeStr = decodeStr + current.c;
                current = root;
                continue;

            }

            char ch = s.charAt(dind);
            dind ++;

            if (ch == '0') {current = current.left;}
            if (ch == '1') {current = current.right;}

        }

        if (current.c != '0') {decodeStr = decodeStr + current.c;}

    }

    public static void main(String[] args) {
        String preorder = StdIn.readLine();
        String encoding = StdIn.readLine();

        PrefixTree tree = new PrefixTree(preorder);
        tree.getEncodings();
        tree.decode(encoding);
    }






}