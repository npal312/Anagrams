package anagrams;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Anagrams {
	final Integer[] primes =  
			{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 
			31, 37, 41, 43, 47, 53, 59, 61, 67, 
			71, 73, 79, 83, 89, 97, 101};
	Map<Character,Integer> letterTable;
	Map<Long,ArrayList<String>> anagramTable;
	
	
	/**
	 * Builds the letterTable of type Character, Integer, with each letter of the alphabet corresponding to the first 26 prime numbers
	 */
	public void buildLetterTable() {
		//might have to make String (which would be pain)
		letterTable = new Hashtable<Character, Integer>();
	    int index = 0;
		for (char a = 'a'; a <= 'z'; a++) {
			letterTable.put(a, primes[index]);
			index++;
	    }
	}
	
	
	Anagrams() {
		buildLetterTable();
		anagramTable = new HashMap<Long,ArrayList<String>>();
	}

	
	//makes hashcode of string passed as argument and adds to hash table anagramTable
	//(for below comment) SPECIFICALLY IF EXACT WORD IS THERE (NOT if hash code is already represented)
	//if already there, throw new IllegalArgumentException("addWord: duplicate value");
	//To check and to add, find ArrayList with hashcode and search it, if not there, add it to ArrayList
	
	/**
	 * Adds the selected word to anagramTable, using the word's hashCode to determine which ArrayList to insert it into
	 * @param s The String to be added to anagramTable
	 * @throws IllegalArgumentException if the word to be added is already inside anagramTable
	 */
	public void addWord(String s) {
		    // Complete
		String s2 = s.toLowerCase(); //just in case you try to add any uppercase letters
		long hashCode = myHashCode(s2);
		if (anagramTable.get(hashCode) == null) { //adds ArrayList if not already there
			ArrayList<String> tempList = new ArrayList<String>();
			tempList.add(s2);
			anagramTable.put(hashCode, tempList);
		}
		else {
			int hashSize = anagramTable.get(hashCode).size();
			for (int i = 0; i < hashSize; i++) { //if 0, automatically skips, and runs the loop otherwise
				if (anagramTable.get(hashCode).get(i).equals(s2)) {
					throw new IllegalArgumentException("addWord: duplicate value");
				}
			}
			anagramTable.get(hashCode).add(s2);
		}	
	}
	
	
	//multiply the prime number for each letter by each other to get hashcode (alerts and alters should be the same)
	//(their hash code is 2.36204078E8)
	//throw new IllegalArgumentException if the string is empty
	
	/**
	 * Uses the letterTable to find each word's hashCode, letter by letter
	 * (by multiplying all of the prime numbers corresponding to each letter together)
	 * @param s The string whose hashCode is being calculated
	 * @return The hashCode of the selected String
	 * @throws IllegalArgumentException if the String is empty
	 */
	public long myHashCode(String s) {
		if (s.isEmpty()) throw new IllegalArgumentException("myHashCode: the String is empty");
		
		long hashCode = 1; //so it can be multiplied by immediately
		for (int i = 0; i < s.length(); i++) {
			hashCode *= letterTable.get(s.charAt(i)); //letter to number and multiply all at once
		}
	    return hashCode;
	}
	
	
	public void processFile(String s) throws IOException {
		FileInputStream fstream = new FileInputStream(s);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String strLine;
		while ((strLine = br.readLine()) != null)   {
		  this.addWord(strLine);
		}
		br.close();
	}
	
	
	//Finds the hashcode with the max amount of entries and returns the list
	//returns a list of map.entry in case there is a tie for first place
	
	/**
	 * Parses the anagramTable to find the hashCode(s) with the highest amount of entries
	 * @return An ArrayList of all the HashMap Keys/Values for the max number of entries
	 */
	public ArrayList<Map.Entry<Long,ArrayList<String>>> getMaxEntries() {
		if (anagramTable.isEmpty()) throw new IllegalArgumentException("getMaxEntries: table is empty, so no comparisons can be done");
		
	    Set<Long> tableKeys = anagramTable.keySet();
	    Iterator<Long> iterateTable = tableKeys.iterator();
	    ArrayList<Map.Entry<Long, ArrayList<String>>> maxEntries = new ArrayList<Map.Entry<Long, ArrayList<String>>>();
	    
	    while(iterateTable.hasNext()) {
	    	Long keyTime = iterateTable.next();
	    	if (maxEntries.isEmpty()) { //if there's nothing in maxEntries already
	    		maxEntries.add(new SimpleEntry<Long, ArrayList<String>>(keyTime, anagramTable.get(keyTime))); //adds Map.Entry to ArrayList
	    	}
	    	else if (maxEntries.get(0).getValue().size() == anagramTable.get(keyTime).size()) { //if both are tied in terms of entries
	    		maxEntries.add(new SimpleEntry<Long, ArrayList<String>>(keyTime, anagramTable.get(keyTime)));
	    	}
	    	else if (maxEntries.get(0).getValue().size() < anagramTable.get(keyTime).size()) { //if the compared value has more entries than the ones in maxEntries (new max)
	    		maxEntries.clear();
	    		maxEntries.add(new SimpleEntry<Long, ArrayList<String>>(keyTime, anagramTable.get(keyTime)));
	    	}
	    }
	    
	    return maxEntries;
	}
	
	
	public static void main(String[] args) {
		Anagrams a = new Anagrams();

		final long startTime = System.nanoTime();    
		try {
			a.processFile("words_alpha.txt");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ArrayList<Map.Entry<Long,ArrayList<String>>> maxEntries = a.getMaxEntries();
		final long estimatedTime = System.nanoTime() - startTime;
		final double seconds = ((double) estimatedTime/1000000000);
		System.out.println("Time: "+ seconds);
		System.out.println("List of max anagrams: "+ maxEntries);
	}
}
