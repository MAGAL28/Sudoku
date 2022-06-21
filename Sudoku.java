import java.util.Arrays;

public class Sudoku {
	/*
	 * 
	 * L2 - PO, TP n 2 Auteur : Serigne Saliou   ET      Simpara Ibrahim Kalilou
	 * 
	 */
	static final int n = 3; // taille des regions
	/*
	 * Terminologie
	 * 
	 * m est un plateau (de sudoku) si - m est un int [][] ne contenant que des
	 * entiers compris entre 0 et 9 - m.length = n^2 - m[i].length = n^2 pour tous
	 * les i de 0 a n^2-1
	 * 
	 */

	static String enClair(int[][] m) {
		/*
		 * Prerequis : m est un plateau de sudoku Resultat : une chaine dont l'affichage
		 * permet de visualiser m
		 * 
		 */
		String r = "";
		for (int i = 0; i < n * n; i++) {
			for (int j = 0; j < n * n; j++) {
				r = r + m[i][j] + " ";
				if (j % n == n - 1) {
					r = r + "  ";
				}
			}
			if (i % n == n - 1) {
				r = r + "\n";
			}
			r = r + "\n";
		}
		r = r + " ";
		return r;
	} // enClair

	static int[][] aPartirDe(String s) {
		/*
		 * Prerequis : s est une chaine contenant au moins n^4 chiffres decimaux
		 * Resultat : un plateau de sudoku initialise avec les n^4 premiers chiffres
		 * decimaux de s (les chiffres sont consideres comme ranges par lignes).
		 */
		int[][] m = new int[n * n][n * n];
		int k = 0;
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[i].length; j++) {
				while ("0123456789".indexOf(s.charAt(k)) == -1) {
					k++;
				}
				m[i][j] = (int) s.charAt(k) - (int) '0';
				k++;
			}
		}
		return m;

	} // aPartirDe

	static boolean presentLigne(int[][] m, int v, int i) {
		/*
		 * Prerequis : - m est un plateau de sudoku - v est compris entre 1 et n^2 - i
		 * est compris entre 0 et n^2-1 Resultat : dans m, v est present dans la ligne i
		 * 
		 */
		for (int j = 0; j < (n * n); j++) {
			if (m[i][j] == v) {
				return true;
			}
		}
		return false;
	} // presentLigne

	static boolean presentColonne(int[][] m, int v, int j) {
		/*
		 * Prerequis : - m est un plateau de sudoku - v est compris entre 1 et n^2 - j
		 * est compris entre 0 et n^2-1 Resultat : dans m, v est present dans la colonne
		 * j
		 * 
		 */
		for (int i = 0; i < (n * n); i++) {
			if (m[i][j] == v) {
				return true;
			}
		}
		return false; // A MODIFIER
	} // presentColonne

	static boolean presentRegion(int[][] m, int v, int i, int j) {
		/*
		 * Prerequis : - m est un plateau de sudoku - v est compris entre 1 et n^2 - i
		 * et j sont compris entre 0 et n^2-1 Resultat : dans m, v est present dans la
		 * region contenant la case <i, j>
		 * 
		 */
		int x = i % n;
		int y = j % n;
		for (int p = i - x; p < (i - x) + n; p++) {
			for (int q = j - y; q < (j - y) + n; q++) {
				if (m[p][q] == v)
					return true;
			}
		}
		return false; // A MODIFIER
	} // presentRegion

	static boolean[] lesPossiblesEn(int[][] m, int i, int j) {
		/*
		 * Prerequis : - m est un plateau de sudoku - i et j sont compris entre 0 et
		 * n^2-1 - m[i][j] vaut 0 Resultat : un tableau r de longueur n^2+1 tel que,
		 * dans la tranche r[1..n^2] r[v] indique si v peut etre place en <i, j>
		 * 
		 */
		boolean[] tabool = new boolean[n * n + 1];
		for (int a = 1; a < tabool.length; a++) {
			if (!presentLigne(m, a, i) && !presentColonne(m, a, j) && !presentRegion(m, a, i, j)) {
				tabool[a] = true;
			} else
				tabool[a] = false;
		}
		return tabool;
	} // lesPossiblesEn

	static String enClair(boolean[] t) {
		/*
		 * Prerequis : t.length != 0 Resultat : une chaine contenant tous les indices i
		 * de la tranche [1..t.length-1] tels que t[i] soit vrai
		 */
		String r = "{";
		for (int i = 1; i < t.length; i++) {
			if (t[i]) {
				r = r + i + ", ";
			}
		}
		if (r.length() != 1) {
			r = r.substring(0, r.length() - 2);
		}
		return r + "}";
	} // enClair

	static int toutSeul(int[][] m, int i, int j) {
		/*
		 * Prerequis : - m est un plateau de sudoku - i et j sont compris entre 0 et
		 * n^2-1 - m[i][j] vaut 0 Resultat : - v si la seule valeur possible pour <i, j>
		 * est v - -1 dans les autres cas
		 * 
		 */
		int a = 0;
		int count = 0;
		boolean[] tab = lesPossiblesEn(m, i, j);
		for (int p = 1; p < tab.length; p++) {
			if (tab[p]) {
				a = p;
				count += 1;
			}
		}
		if (count == 1)
			return a;
		else
			return -1;
	} // toutSeul

	static void essais(String grille) {
		/*
		 * Prerequis : grille represente une grille de sudoku (les chiffres sont
		 * consideres comme ranges par lignes)
		 * 
		 * Effet : 1) affiche en clair la grille 2) affecte, tant que faire se peut,
		 * toutes les cases pour lesquelles il n'y a qu'une seule possibilite 3) affiche
		 * en clair le resultat final
		 */
		int[][] m = aPartirDe(grille);
		System.out.println("Probleme\n\n" + enClair(m));

		for (int p = 0; p < n * n; p++) {
			for (int q = 0; q < n * n; q++) {
				int x = toutSeul(m, p, q);
				if (m[p][q] == 0 && x != -1) {
					m[p][q] = x;
				}
			}
		}
		System.out.println("Il se peut qu'on ait avance\n\n" + enClair(m));
	} // essais

	/**
	 * EXO 7 :
	 * 
	 * Nombre de ligne de mon programme qui doivent être modifiées pour ce problème
	 * : prenons un tableau de taille 5x5; On a la première ligne qui sera changée
	 * soit la ligne static final int n = 3; et si nous remplaçons 3 par 5 nous
	 * aurons 25. La deuxième ligne qui sera modifiées est -> while
	 * ("0123456789".indexOf(s.charAt(k))==-1){k++;}
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		String grille1 = 
				"040 001 006 \n" + 
				"007 900 800 \n" + 
				"190 086 074 \n" + 
				"            \n" + 
				"200 690 010 \n" + 
				"030 405 090 \n" + 
				"060 017 003 \n" + 
				"            \n" + 
				"910 750 042 \n" + 
				"008 002 700 \n" + 
				"400 300 080   " ;
		String grille2 = 
				"030 000 006 \n" + 
		        "000 702 300 \n" + 
			    "104 038 000 \n" + 
		        "            \n" + 
			    "300 020 810 \n" + 
		        "918 000 265 \n" + 
			    "062 050 007 \n" + 
		        "            \n" + 
			    "000 140 708 \n" + 
		        "001 209 000 \n" + 
			    "800 000 020   " ;

		essais(grille1);
		essais(grille2);

		// TESTS :
		// PrésentLigne
		int[][] tab = aPartirDe(grille1);
		System.out.println("Présent Ligne");
		System.out.println(presentLigne(tab, 0, 0));
		System.out.println(presentLigne(tab, 1, 0));
		System.out.println(presentLigne(tab, 0, 2));

		// PrésentColonne
		System.out.println("Présent Colonne");
		System.out.println(presentColonne(tab, 0, 0));
		System.out.println(presentColonne(tab, 1, 0));
		System.out.println(presentColonne(tab, 0, 2));

		// Présent région
		System.out.println("Présent région");
		System.out.println(presentRegion(tab, 5, 2, 2));
		System.out.println(presentRegion(tab, 5, 1, 2));

		// Les Possible En
		System.out.println("Les possibles En ");
		System.out.println(Arrays.toString(lesPossiblesEn(tab, 1, 1)));

		// toutSeul
		System.out.println(toutSeul(tab, 0, 0));
		System.out.println(toutSeul(tab, 2, 8));
		System.out.println(toutSeul(tab, 2, 5));

	}

}