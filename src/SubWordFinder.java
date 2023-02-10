import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Subword finder
 * @author marin
 * @ version 2023 -02 - 09
 */
public class SubWordFinder implements WordFinder{
    private ArrayList<ArrayList<String>> dictionary;
    private String alpha = "abcdefghijklmnopqrstuvwxyz";

    /**
     * constructor method for dictionary
     * creates all our ole buckets
     */
    public SubWordFinder(){
        dictionary = new ArrayList<>();
        for(int i = 0; i < 26; i++){
            dictionary.add(new ArrayList<String>());
        }
        //System.out.println("DEBUG LINE 23 " + dictionary.size());
        populateDictionary();
    }



    private int binarySearch(ArrayList<String> arrayLost, int left, int right, String word)
    {
        if (left <= right) {
            int mid = (right + left) / 2;
            if (arrayLost.get(mid).equals(word))
                return mid;
            //else if(word.compareTo(arrayLost.get(mid)) < 0)
                //return binarySearch(arrayLost, mid+1, right, word);
            //else
                //return binarySearch(arrayLost, left, mid-1, word);
            return word.compareTo(arrayLost.get(mid)) > 0 ? binarySearch(arrayLost, mid + 1, right, word) : binarySearch(arrayLost, left, mid - 1, word);
        }

        // We reach here when element is not present
        // in array
        return -1;
    }
    @Override
    /**
     * Method to populate the database from the text file, and sort each of the buckets lexicographically
     */
    public void  populateDictionary() {
        try{
            Scanner in = new Scanner(new File("new_scrabble.txt"));
            //Scanner in = new Scanner(new File("words_all_os.txt"));
            String word;
            while(in.hasNext()){
                word = in.nextLine();
                //System.out.println("DEBUG: " + word);
                //System.out.println("DEBUG: " + alpha.indexOf(word.substring(0,1)));
                dictionary.get(alpha.indexOf(word.substring(0,1))).add(word);
            }
            in.close();
            for(int i = 0; i < dictionary.size(); i++){
                Collections.sort(dictionary.get(i));
            }
            //for(ArrayList<String> bucket : dictionary)
            //System.out.println(bucket);
        }
        catch(Exception e){
            System.out.println("HEYHEYHEYHEY: " + e);
            e.printStackTrace();
        }
    }

    @Override
    /**
     * Parse all the subwords and populate an arraylist with subword objects
     * @returns ArrayList subwords
     */
    public ArrayList<SubWord> getSubWords() {
        ArrayList<SubWord> subwords = new ArrayList<>();

        //System.out.println("DEBUG: " + dictionary.size());
        //String[] subs;
        for(ArrayList<String> bucket : dictionary) {
            //System.out.println("DEBUG: " + bucket.get(1).substring(0, 1) + " = " + bucket.size());
            for (String word : bucket) {
                String front, back;
                for (int i = 2; i < word.length() - 1; i++) {
                    front = word.substring(0, i);
                    back = word.substring(i);
                    //System.out.println(subs[0] + " " + subs [1] + " " + inDictionary(subs[0]) + " " + inDictionary(subs[1]));
                    if (inDictionary(front) && inDictionary(back)) {
                        subwords.add(new SubWord(word, front, back));
                    }
                }
            }
        }



        //System.out.println("DEBUG line 95: " + subwords.size());
        return subwords;
    }

    @Override
    /**
     * uses binarysearch to see if word is in database
     * @param word word to find
     * @returns inDictionary if the word is in dictionary
     */
    public boolean inDictionary(String word) {

        //return binarySearch(dictionary.get(word.indexOf(word.substring(0,1))), 0, dictionary.get(word.indexOf(word.substring(0,1))).size(), word ) >=0;

        ArrayList<String> bucket = dictionary.get(alpha.indexOf(word.substring(0,1)));
        return binarySearch(bucket, 0, bucket.size()-1, word) >= 0;
        //return Collections.binarySearch(bucket, word) >= 0;
    }

    /**
     * Prints out all the stuff
     * @param args
     * has this stupid copy-paste code for extra credit
     */
    public static void main(String[] args) {
        SubWordFinder app = new SubWordFinder();
        ArrayList<SubWord> words = app.getSubWords();
        ArrayList<String> prefixes = new ArrayList<>();
        ArrayList<String> suffixes = new ArrayList<>();

        //System.out.println("DEBUG IN MAIN: " + words.size());
        System.out.println("* List of SubWords in the file *");
        for(SubWord temp : words){
            System.out.println(temp);
        }

            for(SubWord object : app.getSubWords()){
                prefixes.add(object.getSubWords().substring(0,object.getSubWords().indexOf(" ")));
            }
            for(SubWord object : app.getSubWords()){
                suffixes.add(object.getSubWords().substring(object.getSubWords().indexOf(" ")).substring(3));
            }

        //System.out.println(suffixes);
        System.out.println("pls be patient the following code takes a while to run");
        int freq1 = 0;

        // res to store the most occurring string in the array of
        // strings
        String res1 = "";

        // running nested for loops to find the most occurring
        // word in the array of strings
        for (int i = 0; i < prefixes.size(); i++) {
            int count = 0;
            for (int j = i + 1; j < prefixes.size(); j++) {
                if (prefixes.get(j).equals(prefixes.get(i))) {
                    count++;
                }
            }

            // updating our max freq of occurred string in the
            // array of strings
            if (count >= freq1) {
                res1 = prefixes.get(i);
                freq1 = count;
            }
        }
        int freq2 = 0;

        // res to store the most occurring string in the array of
        // strings
        String res2 = "";

        // running nested for loops to find the most occurring
        // word in the array of strings
        for (int i = 0; i < suffixes.size(); i++) {
            int count = 0;
            for (int j = i + 1; j < suffixes.size(); j++) {
                if (suffixes.get(j).equals(suffixes.get(i))) {
                    count++;
                }
            }

            // updating our max freq of occurred string in the
            // array of strings
            if (count >= freq2) {
                res2 = suffixes.get(i);
                freq2 = count;
            }
        }
        System.out.println(words.size() + " total SubWords");

        System.out.println("The prefix that occurs most is : " + res1);
        System.out.println("No of times: " + freq1);

        System.out.println("The suffix that occurs most is : " + res2);
        System.out.println("No of times: " + freq2);


        //System.out.println("no one says the prefix and suffix finders have to be efficient go screw yourself");
        System.out.println("*end of program*");
    }

}
