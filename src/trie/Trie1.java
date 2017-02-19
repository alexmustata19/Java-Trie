package trie;

/**
 * @author MUSTATA Alexandru-Ionut - Grupa 326CB
 */

public class Trie1 implements TrieElement {

	private String cuvant = null;
	
	/**Constructor ce initializeaza continutul primului tip de TrieElement (Trie1) cu un cuvant.
	 * @param cuvant Cuvantul cu care se initializeaza continutul.
	 */
	public Trie1(String cuvant){
		this.cuvant = cuvant;
	}
	
	/**Se implementeaza metoda toCharArray() conform specificatiilor primului tip de trie.
	*@return Un char[] ce contine calea pentru plasare/identificare TrieElement in cadrul trie.
	*/
	public char[] toCharArray() {
		if(cuvant!=null){
			char[] aux = new char[cuvant.length()];
			int nr=0;
			for(int i=0; i<cuvant.length(); i++){
				char  c = cuvant.charAt(i);
				//Literele mari se inlocuiesc cu litere mici.
				if(Character.isUpperCase(c)){
					aux[nr++] = Character.toLowerCase(c);
				}else{
					aux[nr++] = c;
				}
			}
			char[] rezultat = new char[nr];
			for(int i=0; i<nr; i++){
				rezultat[i] = aux[i];
			}
			return rezultat;
		}
		return null;
	}

	/**Override la toString din Object pentru a putea realiza comparea.
	 * a doua elemente de tip TrieElement.
	 * @return Cuvant continut de TrieElement fara filtrarea realizata de toCharArray().
	 */
	public String toString(){
		return cuvant;
	}
}
