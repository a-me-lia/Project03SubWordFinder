import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SubWordFinder implements WordFinder{
    private ArrayList<ArrayList<String>> dictionary;
    private String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public SubWordFinder(){
        dictionary = new ArrayList<>();
        for(int i = 0; i < 26; i++){
            dictionary.add(new ArrayList<String>());
        }
        populateDictionary();
    }

    private void parseWord(String word){
        String front = "", back ="";
        for(int i = 2; i< word.length()-2; i++){
            front = word.substring(0, i);
            back = word.substring(word.length()-i,word.length());
        }
    }

    int binarySearch(ArrayList<String> arrayLost, int left, int right, String word)
    {
        if (right >= left) {
            int mid = left + ((right - left) / 2);
            if (arrayLost.get(mid).equals(word))
                return mid;
            return word.compareTo(arrayLost.get(mid)) > 0 ? binarySearch(arrayLost, mid + 1, right, word) : binarySearch(arrayLost, left, mid - 1, word);
        }

        // We reach here when element is not present
        // in array
        return -1;
    }
    @Override
    public void  populateDictionary() {
        try{
            Scanner in = new Scanner(new File("words_all_os.txt "));
            while(in.hasNext()){
                String word = in.nextLine();
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
            System.out.println("Error here: " + e);
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<SubWord> getSubWords() {
        return null;
    }

    @Override
    public boolean inDictionary(String word) {

        //return binarySearch(dictionary.get(word.indexOf(word.substring(0,1))), 0, dictionary.get(word.indexOf(word.substring(0,1))).size(), word ) >=0;
        ArrayList<String> bucket = dictionary.get(word.indexOf(word.substring(0,1)));
        return binarySearch(bucket, 0, bucket.size()-1, word) >= 0;
    }
}
