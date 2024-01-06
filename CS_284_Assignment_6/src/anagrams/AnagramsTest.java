package anagrams;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.Test;

class AnagramsTest {

	//addWord()
	@Test
	void testAddWord() {
		Anagrams a = new Anagrams();
		a.addWord("cat");
		ArrayList<Map.Entry<Long,ArrayList<String>>> test = a.getMaxEntries();
		assertEquals("cat", test.get(0).getValue().get(0)); //get ArrayList, get value (is another ArrayList), get first of that ArrayList
	}
	
	@Test
	void testAddMultipleWords() {
		Anagrams a = new Anagrams();
		a.addWord("aaabaaa");
		a.addWord("aaaabaa");
		a.addWord("aabaaaa");
		ArrayList<Map.Entry<Long,ArrayList<String>>> test = a.getMaxEntries();
		assertEquals(3, test.get(0).getValue().size());
		assertEquals("aaabaaa", test.get(0).getValue().get(0));
		assertEquals("aaaabaa", test.get(0).getValue().get(1));
		assertEquals("aabaaaa", test.get(0).getValue().get(2));
	}
	
	@Test
	void testAddDuplicateValue() {
		Anagrams a = new Anagrams();
		a.addWord("nick");
		assertThrows(IllegalArgumentException.class, () -> a.addWord("nick"));
	}
	
	
	
	//myHashCode
	@Test
	void testHashCode() {
		Anagrams a = new Anagrams();
		a.addWord("cat");
		ArrayList<Map.Entry<Long,ArrayList<String>>> test = a.getMaxEntries();
		assertEquals(710, test.get(0).getKey());
	}
	
	@Test
	void testHashCodeHighVal() {
		Anagrams a = new Anagrams();
		a.addWord("zzzzzz");
		ArrayList<Map.Entry<Long,ArrayList<String>>> test = a.getMaxEntries();
		assertEquals(1061520150601L, test.get(0).getKey());
	}
	
	@Test
	void testHashEmpty() {
		Anagrams a = new Anagrams();
		assertThrows(IllegalArgumentException.class, () -> a.addWord(""));
	}
	
	
	
	//getMaxEntries()
	@Test
	void testMaxEntries() {
		Anagrams a = new Anagrams();
		a.addWord("cat");
		a.addWord("alerts");
		a.addWord("alters");
		a.addWord("artels");
		a.addWord("estral");
		ArrayList<Map.Entry<Long,ArrayList<String>>> test = a.getMaxEntries(); //shows if it changes once a hashcode with more entries is added
		assertEquals(4, test.get(0).getValue().size());
		assertEquals(1, test.size());
		a.addWord("yyyzyyy");
		a.addWord("yyyyzyy");
		a.addWord("yyzyyyy");
		a.addWord("yzyyyyy");
		a.addWord("yyyyyzy");
		a.addWord("zyyyyyy");
		a.addWord("yyyyyyz");
		test = a.getMaxEntries();
		assertEquals(7, test.get(0).getValue().size());
		assertEquals(1, test.size()); //showing that the original max has entirely removed itself from the ArrayList
	}
	
	@Test
	void testMaxWithTie() {
		Anagrams a = new Anagrams();
		a.addWord("cat");
		a.addWord("alerts");
		a.addWord("alters");
		a.addWord("artels");
		a.addWord("estral");
		a.addWord("yyyzyyy");
		a.addWord("yyyyzyy");
		a.addWord("yyzyyyy");
		a.addWord("yzyyyyy");
		ArrayList<Map.Entry<Long,ArrayList<String>>> test = a.getMaxEntries();
		test = a.getMaxEntries();
		assertEquals("alerts", test.get(0).getValue().get(0)); //is added in first
		assertEquals("yyyzyyy", test.get(1).getValue().get(0)); //is added in second
		assertEquals(2, test.size()); //is a tie between the two hashCode values
	}
	
	@Test
	void testMaxWithFile() {
		Anagrams a = new Anagrams();
		try {
			a.processFile("words_alpha.txt");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ArrayList<Map.Entry<Long,ArrayList<String>>> test = a.getMaxEntries();
		assertEquals(236204078, test.get(0).getKey());
		assertEquals("alerts", test.get(0).getValue().get(0));
	}
	
	@Test
	void testMaxEmptyArray() {
		Anagrams a = new Anagrams();
		assertThrows(IllegalArgumentException.class, () -> a.getMaxEntries());
	}
	
}
