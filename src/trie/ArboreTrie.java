package trie;

/**
 * @author MUSTATA Alexandru-Ionut - Grupa 326CB
 */

import java.util.ArrayList;

public class ArboreTrie implements AbstractTrie {
	/**Clasa interna pentru administrarea informatiilor dintr-un nod.
	 */
	private class Nod{
		public Nod[] fii;
		public TrieElement cuvant;
		public int nrAparitii;
		public static final int NRFII = 68;
		
		/**Constructor ce creeaza un nod fara element(cuvant).
		 */
		public Nod(){
			fii = new Nod[NRFII];
			nrAparitii = 0;
			cuvant = null;
		}
		
	}
	
	private Nod radacina;
	
	/**Constructor ce initializeaza arborele sa aiba un nod-radacina
	 * ce corespunde sirului/cuvantului/elementului vid.
	 */
	public ArboreTrie(){
		radacina = new Nod();
	}
	
	/**Metoda pentru afisarea tuturor elementelor din trie.
	 * A fost folosita pentru debug si teste.
	 */
	public void printTree(){
		Nod parcurgere = radacina;
		/*Se apeleaza meotda recursiva de afisare.
		 */
		print(parcurgere);
		System.out.println();
	}
	
	/**Meotda recursiva de afisare a contiutului unui nod(daca exista)
	 * si a subtrie-urilor acestuia.
	 * @param parcurgere Nod de la care se incepe afisarea.
	 */
	private void print(Nod parcurgere){
		/*Afisare continut nod daca exista.
		 */
		if(parcurgere.cuvant != null && parcurgere.nrAparitii > 0){
			System.out.print(parcurgere.cuvant.toString()+ " ");
		}
		/*Apeluri pentru afisare fii in ordine.
		 */
		for(int i=0; i<Nod.NRFII; i++){
			if(parcurgere.fii[i] != null){
				print(parcurgere.fii[i]);
			}
		}
	}
	
	/**Metoda intoarce indexul fiului pentru un caracter primit. Daca caracterul nu se afla in setul
	 * de caractere folosit atunci intoarce -1.
	 * @param c Caracter din vectorul returnat din metoda toCharArray().
	 * @return Indexul fiului corespunzator caracterului primit ca parametru.
	 */
	private int getFiuIndex(char c){
		if(c=='!'){
			return 0;
		}
		if(c=='('){
			return 1;
		}
		if(c==')'){
			return 2;
		}
		if(c=='-'){
			return 3;
		}
		if(Character.isDigit(c)){
			return ((int)c-44);
		}
		if(c=='?'){
			return 14;
		}
		if(Character.isLowerCase(c)){
			return ((int)c-55);
		}
		if(c=='_'){
			return 41;
		}
		if(Character.isUpperCase(c)){
			return ((int)c-50);
		}
		return -1;
	}

	
	/**Metoda adauga un nou element in trie.
	 * @param element Elementul ce trebuie adaugat in cadrul trie-ului.
	 */
	public void add(TrieElement element){
		/*Se obtine calea care trebuie urmata pentru a adauga elementul primit
		 *ca parametru in trie. Parcurgerea caii se incepe de la radacina.
		 */
		char[] cale = element.toCharArray();
		Nod parcurgere = radacina;
		
		/*Pentru fiecare caracter din cale se verifica daca exista calea respectiva in trie.
		*Daca da se avanseaza pe calea dictata de cale, altfel se creaza calea respectiva
		*prin crearea de noi noduri in trie.
		*/
		for(int i=0; i<cale.length; i++){
			int indexFiu = getFiuIndex(cale[i]);
			if(indexFiu==-1) return;
			if(parcurgere.fii[indexFiu] == null){
				parcurgere.fii[indexFiu] = new Nod();
			}
			parcurgere = parcurgere.fii[indexFiu];
		}
		
		/*Se incrementeaza numarul de aparitii al elementului in trie.
		 */
		parcurgere.nrAparitii++;
		
		/*Se verifica daca elementul este nou adaugat in trie. Daca da se
		 *memoreaza automat elementul, daca nu se compara din punct de vedere
		 *lexicografic elementul deja prezent cu cel primit ca parametru si
		 *este pastrat cel mai mic lexicografic.
		 */
		if(parcurgere.cuvant == null){
			parcurgere.cuvant = element;
		}else{
			if(element.toString().compareTo(parcurgere.cuvant.toString()) < 0){
				parcurgere.cuvant = element;
			}
		}
	}
	
	/**Metoda intoarce numarul de aparitii ale unui TrieElement in cadrul trie-ului.
	 * @param element TrieElement pentru care se doreste aflarea numarului de aparitii.
	 * @return Numarul de aparitii al TrieElement primit ca parametru.
	 */
	public int count(TrieElement element) {
		/*Parcurgerea se incepe de la radacina pe calea returnata de toCharArray().
		 */
		Nod parcurgere = radacina;
		char[] cale = element.toCharArray();
		
		/*Pentru fiecare caracter din cale se verifica daca exista calea respectiva in trie.
		*Daca da se avanseaza pe calea dictata de cale, altfel se intoarce zero deoarece nu
		*exista in trie elementul primit ca parametru.
		*/
		for(int i=0; i<cale.length; i++){
			int indexFiu = getFiuIndex(cale[i]);
			if(indexFiu == -1) return 0;
			if(parcurgere.fii[indexFiu] == null) return 0;
			parcurgere = parcurgere.fii[indexFiu];
		}
		
		/*Se intoarce numarul de aparitii al elementului gasit
		 * in nodul in care s-a terminat avansarea in trie.
		 */
		return parcurgere.nrAparitii;
	}
	
	/**Metoda intoarce numarul de fii al Nod primit ca parametru.
	 * @param n Nod pentru care se doreste aflat numarul de fii.
	 * @return Numarul de fii al nodului primit ca parametru.
	 */
	private int numarFii(Nod n){
		int nr=0;
		for(int i=0; i<Nod.NRFII; i++){
			if(n.fii[i] != null){
				nr++;
			}
		}
		return nr;
	}
	
	/**Metoda decrementeaza numarul de aparitii al TrieElementului primit ca parametru.
	 * In cazul in care numarul de aparitii este zero se sterg din trie nodurile asociate caii
	 * TrieElement primit ca parametru care nu mai contin informatie utila.
	 * @param element TrieElement care se doreste sters din trie.
	 */
	public void remove(TrieElement element) {
		/*Se incepe parcurgerea de la radacina pe calea dictata de toCharArray().
		 */
		Nod parcurgere = radacina;
		Nod ultimaBifurcatie = radacina;
		Nod ultimulCuvant = radacina;
		int pozitieLiteraUltimaBifurcatie = -1;
		int pozitieLiteraUltimulCuvant = -1;
		char[] cale = element.toCharArray();
		
		/*De fiecare data se verifica existenta urmatorului nod din cale(daca nu exista metoda se termina).
		 *In cadrul parcurgerii se retine pozitia ultimei bifurcatii(referinta catre nod si pozitie caracter in cale)
		 *si a ultimului subcuvant(referinta catre nod si pozitie caracter in cale) inainte sa se ajunga in nodul
		 *care contine elementul primit ca parametru.
		 */
		for(int i=0; i<cale.length; i++){
			int indexFiu = getFiuIndex(cale[i]);
			if(indexFiu == -1) return;
			if(parcurgere.fii[indexFiu] == null) return;
			
			/*Retinerea ultimei bifurcatii intalnita.
			 */
			if(numarFii(parcurgere.fii[indexFiu]) > 1){
				ultimaBifurcatie = parcurgere.fii[indexFiu];
				pozitieLiteraUltimaBifurcatie = i;
			}
			
			/*Retinerea ultimului subcuvant intalnit(cu exceptia cuvantului in sine <=> i=cale.length-1).
			 */
			if(parcurgere.fii[indexFiu].cuvant != null && parcurgere.fii[indexFiu].nrAparitii != 0 && i!=cale.length-1){
				ultimulCuvant = parcurgere.fii[indexFiu];
				pozitieLiteraUltimulCuvant = i;
			}
			
			parcurgere = parcurgere.fii[indexFiu];
		}
		
		/*Se decrementeaza numarul de aparitii al elementului.
		 */
		if(parcurgere.nrAparitii > 0){
			parcurgere.nrAparitii--;
		}
		
		/*Se verifica daca numarul de aparitii al elementului este zero
		 *si daca acesta nu contine cai catre alte elemente(este frunza).
		 */
		if(parcurgere.nrAparitii==0 && numarFii(parcurgere)==0){
			/*Se verifica daca inainte de sfarsitul parcurgerii elementului ultima data
			 *s-a intalnit o bifurcatie sau un subcuvant si in ambele cazuri se sterge
			 *informatia nefolositoare din trie(din acel punct pana unde se afla elementul).
			 */
			if(pozitieLiteraUltimaBifurcatie >= pozitieLiteraUltimulCuvant){
				/*In caz ca ultima bifurcatie este chiar pe cuvant nu putem sterge nimic.
				 */
				if(ultimaBifurcatie!=parcurgere){
					/*Se obtine litera imediat urmatoare literei corespunzatoare ultimei bifurcatii
					 * si legatura dintre bifurcatie si subtrie-ul desemnat de acel caracter este stearsa.
					 */
					char literaDupaUltimaBifurcatie = cale[pozitieLiteraUltimaBifurcatie+1];
					ultimaBifurcatie.fii[getFiuIndex(literaDupaUltimaBifurcatie)] = null;
				}
			}else{
					/*Se obtine litera imediat urmatoare literei corespunzatoare ultimului subcuvant
					 * si legatura dintre ultimul subcuvant si subtrie-ul desemnat de acel caracter este stearsa.
					 */
					char literaDupaUltimulCuvant = cale[pozitieLiteraUltimulCuvant+1];
					ultimulCuvant.fii[getFiuIndex(literaDupaUltimulCuvant)] = null;
			}
			
		}
		return;
	}
	
	/**Prin recursivitate, metoda adauga intr-o lista toate elementele de tip TrieElement care au
	 * prefixul calea returnata de toCharArray() pana la Nod parcurgere.
	 * @param parcurgere Nod care este examinat.
	 * @param lista Lista in care se salveaza elemente de tip TrieElement ce au prefixul cerut.
	 */
	private void getSortedElementsAux(Nod parcurgere, ArrayList<TrieElement> lista){
		/*Se adauga cuvant in lista doar daca exista si numarul de aparitii
		 * este mai mare decat 0.
		 */
		if (parcurgere.cuvant != null && parcurgere.nrAparitii > 0){
			lista.add(parcurgere.cuvant);
		}
		/*Se parcurg toti fii in ordine pentru a se obtine si celelalte
		 * elemente in ordine.
		 */
		for(int i=0; i<Nod.NRFII; i++){
			if(parcurgere.fii[i] != null){
				getSortedElementsAux(parcurgere.fii[i], lista);
			}
		}
		return;
	}
	
	/**Metoda care intoarce un TrieElement[] cu toate elementele ce incep cu prefix. In cazul
	 * @param prefix TrieElement ce desemneaza partea comuna de la inceputul elementelor. 
	 * @return Un TrieElement[] cu elementele ce incep cu prefix.
	 */
	public TrieElement[] getSortedElements(TrieElement prefix) {
		/*Parcurgerea se incepe de la radacina pe calea returnata de toCharArray().
		 */
		Nod parcurgere = radacina;
		char[] cale = prefix.toCharArray();
		
		/*Pentru fiecare caracter din cale se verifica daca exista calea respectiva in trie.
		*Daca da se avanseaza pe calea dictata de cale, altfel se intoarce un vector cu zero
		*elemente deoarece nu exista in trie cuvinte cu acest prefix dat de cale.
		*/
		for(int i=0; i<cale.length; i++){
			int indexFiu = getFiuIndex(cale[i]);
			if(indexFiu == -1) return new TrieElement[0];
			if(parcurgere.fii[indexFiu] == null) return new TrieElement[0];
			parcurgere = parcurgere.fii[indexFiu];
		}
		
		/*Elementele de tip TrieElement cu prefixul dat sunt memorate intr-o lista
		*cu ajutorul unei metode recursive auxiliare.
		*/
		ArrayList<TrieElement> lista = new ArrayList<TrieElement>();
		getSortedElementsAux(parcurgere, lista);
		
		/*Se creeaza un TrieElement[] pentru a returna continutul din lista.
		*/
		TrieElement[] rez = new TrieElement[lista.size()];
		for(int i=0; i<lista.size(); i++){
			rez[i] = lista.get(i);
		}
		return rez;
	}
}