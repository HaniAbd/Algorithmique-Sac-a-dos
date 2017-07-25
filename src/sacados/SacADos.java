package sacados;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import methodes.MethodeGloutonne;
import methodes.MethodePSE;
import methodes.MethodeProgrammationDynamique;

import java.io.File;
import java.io.FileNotFoundException;

public class SacADos {

	private float poidsMaximal;
	private float poidsReel;
	private float valeurTotale;
	private List<Objet> objetsPossibles;
	private List<Objet> objetsDansLeSac;
	private List<Methode> methodesDeResolution;

	public SacADos() {
		this.poidsMaximal = 0;
		this.poidsReel = 0;
		this.valeurTotale = 0;
	}

	public SacADos(String chemin, float poidsMaximal) {
		this.objetsPossibles = new ArrayList<Objet>();
		this.objetsDansLeSac = new ArrayList<Objet>();
		this.methodesDeResolution = new ArrayList<Methode>();
		this.methodesDeResolution.add(new MethodeGloutonne());
		this.methodesDeResolution.add(new MethodeProgrammationDynamique());
		this.methodesDeResolution.add(new MethodePSE());
		try {
			@SuppressWarnings("resource")
			Scanner r = new Scanner(new File(chemin));

			while (r.hasNext()) {
				String donnees = r.nextLine();
				String[] donneesSplit = donnees.split(" ; ");
				this.objetsPossibles.add(new Objet(donneesSplit[0], Float.parseFloat(donneesSplit[1]),
						Float.parseFloat(donneesSplit[2])));
			}

		} catch (FileNotFoundException e) {
			System.out.println("lien d'accès au fichier incorrect");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		this.poidsMaximal = poidsMaximal;
		this.poidsReel = 0;
		this.valeurTotale = 0;
	}

	public String toString() {
		StringBuilder str = new StringBuilder("Sac à Dos : \n");
		if (this.objetsDansLeSac.size() == 0) {
			str.append("vide");
			return str.toString();
		}
		for (Objet objet : this.objetsDansLeSac) {
			str.append(" - " + objet.toString());
		}
		str.append("\nLe sac pèse " + this.poidsReel + "kg et contient une valeur totale de " + this.valeurTotale + '\n');
		return str.toString();
	}

	public void mettreDansLeSac(Objet objet) {
		this.objetsDansLeSac.add(objet);
		this.poidsReel += objet.getPoids();
		this.valeurTotale += objet.getValeur();
	}

	public List<Objet> getObjetsPossibles() {
		return this.objetsPossibles;
	}

	public float getPoidsReel() {
		return this.poidsReel;
	}

	public float getPoidsMaximal() {
		return this.poidsMaximal;
	}

	public long resoudre(Methode methode) {
		return methode.resoudre(this);
	}

	public List<Methode> getMethodesDeResolution() {
		return this.methodesDeResolution;
	}

	public List<Objet> getObjetsDansLeSac() {
		return this.objetsDansLeSac;
	}

	public int getNbMethodes() {
		return this.methodesDeResolution.size();
	}

}
