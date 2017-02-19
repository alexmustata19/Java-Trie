package trie;

/**
 * @author MUSTATA Alexandru-Ionut - Grupa 326CB
 */

import test.Command;
import test.TestReader;
import test.TestWriter;

public class MainClass {
	public static void main(String[] args){
		//Crearea a doi arbori specifici pentru fiecare tip de implementare.
		ArboreTrie arbore1 = new ArboreTrie();
		ArboreTrie arbore2 = new ArboreTrie();
		
		//Crearea stream-urilor de input si output.
		TestReader tr = new TestReader("trie.in");
		TestWriter tw = new TestWriter("trie.out");
		
		//Initializarea celor doi trie cu lista de cuvinte initiala.
		String[] words = tr.getWords();
		for(int i=0; i<words.length; i++){
			Trie1 t1 = new Trie1(words[i]);
			Trie2 t2 = new Trie2(words[i]);
			arbore1.add(t1);
			arbore2.add(t2);
		}
		
		//Procurarea celor doua seturi de comenzi.
		Command[] c1 = tr.getFirstCommands();
		Command[] c2 = tr.getSecondCommands();
		
		//Aplicarea comenzilor c1 pe primul trie.
		for(int i=0; i<c1.length; i++){
			String word = c1[i].getWord();
			Trie1 t1 = new Trie1(word);
			switch (c1[i].getType()){
				case Command.ADD: 
					arbore1.add(t1);
					break;
				case Command.COUNT:
					tw.printCount(arbore1.count(t1));
					break;
				case Command.REMOVE:
					arbore1.remove(t1);
					break;
				case Command.LIST:
					tw.printSortedWords(arbore1.getSortedElements(t1));
					break;
			}	
		}
		
		//Aplicarea comenzilor c2 pe al doilea trie.
		for(int i=0; i<c2.length; i++){
			String word = c2[i].getWord();
			Trie2 t2 = new Trie2(word);
			switch (c2[i].getType()){
				case Command.ADD: 
					arbore2.add(t2);
					break;
				case Command.COUNT:
					tw.printCount(arbore2.count(t2));
					break;
				case Command.REMOVE:
					arbore2.remove(t2);
					break;
				case Command.LIST:
					tw.printSortedWords(arbore2.getSortedElements(t2));
					break;
			}	
		}
		
		//Inchiderea stream-ului de output.
		tw.close();
	}
}
