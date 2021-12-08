package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

/**
 * A MsgTree class that processes .arch files
 *
 * @author Charles Yang
 */
public class MsgTree {
    /**
     * Driver method.
     *
     * @param args
     */
    public static void main(String[] args) { // TODO
        // User input
        Scanner s = new Scanner(System.in);
        System.out.println("Enter a file name ending in .arch to decode");
        File f = new File(s.nextLine());
        try {
            s = new Scanner(f);
        } catch (FileNotFoundException e) {
            System.out.println("Invalid File!");
            e.printStackTrace();
        }

        // Parse file
        String encoding = s.nextLine();
        String msg;
        String temp = s.nextLine();

        // Determining if encoding tree includes newline character.
        if (s.hasNextLine()) {
            encoding += '\n';
            encoding += temp;
            msg = s.nextLine();
        }
        else {
            msg = temp;
        }

        // Construct MsgTree, print codes, and decode msg.
        MsgTree root = new MsgTree(encoding);
        printCodes(root, "");
        decode(root, msg);
    }
    
    /**
     * Character stored in this node
     */
    public char payloadChar;

    /**
     * Left child
     */
    public MsgTree left;

    /**
     * Right child
     */
    public MsgTree right;

    /**
     * Constructs tree from given String
     * @param encodingString given preorder traversal of tree
     */
    public MsgTree(String encodingString) {
        this.payloadChar = '^';
        buildTree(encodingString);
    }

    /**
     * Constructs single node with null children and given char
     *
     * @param payloadChar char stored in node
     */
    public MsgTree(char payloadChar) {
        left = null;
        right = null;
        this.payloadChar = payloadChar;
    }

    /**
     * Prints the characters and their binary codes given a tree's root
     *
     * @param root
     * @param codes
     */
    public static void printCodes(MsgTree root, String codes) {
        System.out.println("character\tcode");
        System.out.println("-------------------------");
        root.printCodesRec(root, codes);
    }

    /**
     * Recursively determine the binary code for each character in the tree
     *
     * @param node
     * @param codes
     */
    private void printCodesRec(MsgTree node, String codes) {
        if (node == null) return;
        if (node.payloadChar != '^') {
            System.out.println('\t' +
                    String.valueOf(node.payloadChar).replace("\\", "\\\\")
                    .replace("\t", "\\t")
                    .replace("\b", "\\b")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\f", "\\f") +
                    "\t\t" + codes);
            return;
        }

        printCodesRec(node.left, codes + "0");
        printCodesRec(node.right, codes + "1");
    }

    /**
     * Decodes a binary msg using codes in a MsgTree
     *
     * @param codes
     * @param msg
     */
    public static void decode(MsgTree codes, String msg) { // print leaves and path to leaves
        MsgTree temp = codes;
        for (char c : msg.toCharArray()) {
            if (c == '0') {
                temp = temp.left;
            }
            else {
                temp = temp.right;
            }

            if (temp.payloadChar != '^') {
                System.out.print(temp.payloadChar);
                temp = codes;
            }
        }
    }

    /**
     * Builds the encoding tree iteratively for EXTRA CREDIT
     *
     * @param encoding
     * @return
     */
    private void buildTree(String encoding) {
        char[] encode = encoding.toCharArray();
        Stack<MsgTree> soil = new Stack<>();
        //MsgTree root = new MsgTree(null);
        soil.push(this);

        MsgTree temp;
        MsgTree next;
        for (int i = 1; i < encode.length; i++) {
            temp = soil.peek();
            while (temp.left != null && temp.right != null) {
                soil.pop();
                temp = soil.peek();
            }
            if (encode[i] == '^') { // Create internal node
                next = new MsgTree('^');
                if (temp.left == null) {
                    temp.left = next;
                }
                else {
                    temp.right = next;
                }
                soil.push(next);
            }
            else { // Character to insert into leaf
                if (temp.left == null) {
                    temp.left = new MsgTree(encode[i]);
                }
                else { // When right of temp is filled, the node is full.
                    temp.right = new MsgTree(encode[i]);
                    soil.pop();
                }
            }
        }
    }

    private void buildTreeRec(String encoding, int i) {
        char[] encode = encoding.toCharArray();
        if (i >= encode.length) return;
        if (encode[i] == '^') {
            if (left == null) {
                left = new MsgTree('^');
                left.buildTreeRec(encoding, i + 1);
            }
            else if (right == null) {
                right = new MsgTree('^');
                right.buildTreeRec(encoding, i + 1);
            }
        }
        else {
            if (left == null) {
                left = new MsgTree(encode[i++]);
            }
            else if (right == null) {
                right = new MsgTree(encode[i]);
            }
        }
    }

    /**
     * Basic PreOrder traversal algorithm. For Testing purposes
     *
     * @param node
     */
    private static void printPreorder(MsgTree node)
    {
        if (node == null) return;

        System.out.print(node.payloadChar);

        printPreorder(node.left);
        printPreorder(node.right);
    }
}
