/**
 * API pour utiliser Excel dans le cadre du tableau des flux
 * 
 * Utilisables pour les constructions à partir du projet Archi
 * 
 * 
 * Status : on a le début de la première page
 * 		Amàliorable, la largeur des colonnes
 * 
 * Version 2.0 du plugin Archi.
 * 
 * Remplacer un jour le CellRangeAddress par qqch de non déprécated ...
 * 
 */

package com.archimatetool.example;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.hssf.util.Region;
//import org.apache.poi.ss.usermodel.CellStyle;


public class APIExcel {

    private HSSFWorkbook wb;
    private HSSFSheet sheet1;

    private HSSFCellStyle carto = null;		//"Cartographie des flux"
    private HSSFCellStyle fuser = null;		//"Flux utilisateur" ou "Flux internes..."
    private HSSFCellStyle enteteGris = null;	//"source", "dest", ...
    private HSSFCellStyle entete = null;
    private HSSFCellStyle enteteB = null;
    private HSSFCellStyle enteteTxt = null;
    private HSSFCellStyle enteteTxtB = null;
    
    private HSSFCellStyle Inchange = null;
    private HSSFCellStyle Cree = null;
    private HSSFCellStyle Modifie = null;
    private HSSFCellStyle Supprime = null;
    
    private HSSFRow row;
    private HSSFCell cell;

    private int numeroLigne;				//Numàro de la premiàre ligne libre oà insàrer e tableau
	
	public APIExcel()		//Constructeur, il cràe le fichier Excel oà on lui demande (à terme ... :-)
	{
	    wb = new HSSFWorkbook();
	    
	    //Cràe ses styles
	    createStyleCarto();
	    createStyleFluUser();
	    createStyleEnteteGris();
	    createStyleEntete();
	    createStyleEnteteB();
	    createStyleTxt();
	    createStyleBTxt();
	    createStyleStatusInchange();
	    createStyleStatusSupprime();
	    createStyleStatusModifie();
	    createStyleStatusCree();
	    
	    //Cràation de la trame de la page des flux
	    sheet1 = wb.createSheet("Tableau des flux");
	    	    
	    //Largeur des colonnes du tableau
	    setLargeurColonnes();
	    
	    
	    row = sheet1.createRow(1);	    
	    cell = row.createCell(0);   			//Cellule 0,1;
	    cell.setCellValue(new HSSFRichTextString("Cartographie des flux"));
	    cell.setCellStyle(carto);
	    
	    row = sheet1.createRow(4);
	    cell = row.createCell(0);   			//Cellule 0,4;
	    cell.setCellValue(new HSSFRichTextString("Flux utilisateurs"));
	    cell.setCellStyle(fuser);

	    numeroLigne = 6;
	    CreateLigneEntete1();				//Creation de la premiàre ligne d'en tete (rouge sur fond gris)
	    CreateLigneEntete2();				//Cràation de la 2eme ligne d'en tete
	    
	    numeroLigne = 8;					//premiàre ligne disponibe apràs le tableau 
	    
	}
	
	//Cràation du tableau des flux internes
	public void FluxInterne()
	{
		numeroLigne += 2;
	    row = sheet1.createRow(numeroLigne);
	    cell = row.createCell(0);   			//Cellule 0,4;
	    cell.setCellValue(new HSSFRichTextString("Flux internes à l'application"));
	    cell.setCellStyle(fuser);
		numeroLigne += 2;
	    CreateLigneEntete1();				//Creation de la premiàre ligne d'en tete (rouge sur fond gris)
	    CreateLigneEntete3();
		numeroLigne ++;
	
	}
	
	//Cràation du tableau des flux externes
	public void FluxExterne()
	{
		numeroLigne += 2;
	    row = sheet1.createRow(numeroLigne);
	    cell = row.createCell(0);   			//Cellule 0,4;
	    cell.setCellValue(new HSSFRichTextString("Interfaces externes à l'application"));
	    cell.setCellStyle(fuser);
		numeroLigne += 2;
	    CreateLigneEntete1();				//Creation de la premiàre ligne d'en tete (rouge sur fond gris)
	    CreateLigneEntete3();
	    
		numeroLigne ++;

	}
	
	//Cràation du tableau des flux administrateurs
	public void FluxAdmin()
	{
		numeroLigne += 2;
	    row = sheet1.createRow(numeroLigne);
	    cell = row.createCell(0);   			//Cellule 0,4;
	    cell.setCellValue(new HSSFRichTextString("Flux d'administration spécifique"));
	    cell.setCellStyle(fuser);
		numeroLigne += 2;
	    CreateLigneEntete1();				//Creation de la premiàre ligne d'en tete (rouge sur fond gris)
	    CreateLigneEntete2();				//Cràation de la 2eme ligne d'en tete
	    	    
		numeroLigne ++;
	    
	}
	
	//Cràation du tableau des flux d'àchange inter applicatifs
	public void ExchangeInter()
	{
		numeroLigne += 2;
	    row = sheet1.createRow(numeroLigne);
	    cell = row.createCell(0);   			//Cellule 0,4;
	    cell.setCellValue(new HSSFRichTextString("Echanges inter-environnements"));
	    cell.setCellStyle(fuser);
		numeroLigne += 2;
	    CreateLigneEntete1();				//Creation de la premiàre ligne d'en tete (rouge sur fond gris)
	    CreateLigneEntete3();
	    
		numeroLigne ++;
	
	}
	
	//Cràation du tableau des flux d'àchnages hors prod

	public void ExchangeHorsProd()
	{
		numeroLigne += 2;
	    row = sheet1.createRow(numeroLigne);
	    cell = row.createCell(0);   			//Cellule 0,4;
	    cell.setCellValue(new HSSFRichTextString("Echanges des environnements hors-production"));
	    cell.setCellStyle(fuser);
		numeroLigne += 2;
	    CreateLigneEntete1();				//Creation de la premiàre ligne d'en tete (rouge sur fond gris)
	    CreateLigneEntete3();
	    
	    
		numeroLigne ++;

	}
	
	//C'est toujours la meme suite de sting's
	public void setLigneFlux(String Status, 
							String NumFlux,
							String user,				//ou Ràseau, mais àa reste un string
							String NomSiteS,			//Nom du site Source
							String ZoneS,
							String DomaineS,
							String BriqueS,
							String NomSiteD,
							String ZoneD,
							String DomaineD,
							String BriqueD,
							String Protocole,
							String Volume,
							String Tps,
							String Debit,
							String Concurents,
							String Cumul,
							String Conf,
							String Ics,
							String Dmf,
							String Description,
							String Commentaire,
							String Mecanisme,
							String Securisation,
							String Referentiel
								)
	{
	    row = sheet1.createRow(numeroLigne);
	    cell = row.createCell(0);   			//Cellule 7,0;	    
	    if (Status.equals("Inchangé"))
	    	cell.setCellStyle(Inchange);
	    
	    if (Status.equals("Supprimé"))
	    	cell.setCellStyle(Supprime);
	    
	    if (Status.equals("Créé"))
		    	cell.setCellStyle(Cree);
	    
	    if (Status.equals("Modifié"))
		    	cell.setCellStyle(Modifie);
	    	    
	    cell.setCellValue(new HSSFRichTextString(Status));
	    cell = row.createCell(1);   			//Cellule 7,1;	    
	    if (Status.equals("Inchangé"))
	    	cell.setCellStyle(Inchange);
	    
	    if (Status.equals("Supprimé"))
	    	cell.setCellStyle(Supprime);
	    
	    if (Status.equals("Créé"))
		    	cell.setCellStyle(Cree);
	    
	    if (Status.equals("Modifié"))
		    	cell.setCellStyle(Modifie);

	    sheet1.addMergedRegion(new CellRangeAddress(numeroLigne,numeroLigne,0,1));		//Fusionne des cellules, r1 est l'indice de la ràgion
	
	    cell = row.createCell(2);   			//Cellule 	    
	    cell.setCellStyle(enteteTxt);
	    cell.setCellValue(new HSSFRichTextString(NumFlux));
	    
	    cell = row.createCell(3);   			//Cellule 	    
	    cell.setCellStyle(enteteTxtB);
	    cell.setCellValue(new HSSFRichTextString(user));
	
	    cell = row.createCell(4);   			//Cellule 	    
	    cell.setCellStyle(enteteTxt);
	    cell.setCellValue(new HSSFRichTextString(NomSiteS));
	
	    cell = row.createCell(5);   			//Cellule 	    
	    cell.setCellStyle(enteteTxt);
	    cell.setCellValue(new HSSFRichTextString(ZoneS));
	
	    cell = row.createCell(6);   			//Cellule 	    
	    cell.setCellStyle(enteteTxt);
	    cell.setCellValue(new HSSFRichTextString(DomaineS));
	
	    cell = row.createCell(7);   			//Cellule 	    
	    cell.setCellStyle(enteteTxtB);
	    cell.setCellValue(new HSSFRichTextString(BriqueS));
	
	    cell = row.createCell(8);   			//Cellule 	    
	    cell.setCellStyle(enteteTxt);
	    cell.setCellValue(new HSSFRichTextString(NomSiteD));
	
	    cell = row.createCell(9);   			//Cellule 	    
	    cell.setCellStyle(enteteTxt);
	    cell.setCellValue(new HSSFRichTextString(ZoneD));
	
	    cell = row.createCell(10);   			//Cellule 	    
	    cell.setCellStyle(enteteTxt);
	    cell.setCellValue(new HSSFRichTextString(DomaineD));
	
	    cell = row.createCell(11);   			//Cellule 	    
	    cell.setCellStyle(enteteTxt);
	    cell.setCellValue(new HSSFRichTextString(BriqueD));
	
	    cell = row.createCell(12);   			//Cellule 	    
	    cell.setCellStyle(enteteTxtB);
	    cell.setCellValue(new HSSFRichTextString(Protocole));
	
	    cell = row.createCell(13);   			//Cellule 	    
	    cell.setCellStyle(enteteTxt);
	    cell.setCellValue(new HSSFRichTextString(Volume));
	
	    cell = row.createCell(14);   			//Cellule 	    
	    cell.setCellStyle(enteteTxt);
	    cell.setCellValue(new HSSFRichTextString(Tps));
	
	    cell = row.createCell(15);   			//Cellule 	    
	    cell.setCellStyle(enteteTxt);
	    cell.setCellValue(new HSSFRichTextString(Debit));
	
	    cell = row.createCell(16);   			//Cellule 	    
	    cell.setCellStyle(enteteTxt);
	    cell.setCellValue(new HSSFRichTextString(Concurents));
	
	    cell = row.createCell(17);   			//Cellule 	    
	    cell.setCellStyle(enteteTxtB);
	    cell.setCellValue(new HSSFRichTextString(Cumul));
	
	    cell = row.createCell(18);   			//Cellule 	    
	    cell.setCellStyle(enteteTxt);
	    cell.setCellValue(new HSSFRichTextString(Conf));
	
	    cell = row.createCell(19);   			//Cellule 	    
	    cell.setCellStyle(enteteTxt);
	    cell.setCellValue(new HSSFRichTextString(Ics));
	
	    cell = row.createCell(20);   			//Cellule 	    
	    cell.setCellStyle(enteteTxt);
	    cell.setCellValue(new HSSFRichTextString(Dmf));
	
	    cell = row.createCell(21);   			//Cellule 	    
	    cell.setCellStyle(enteteTxt);
	    cell.setCellValue(new HSSFRichTextString(Description));
	
	    cell = row.createCell(22);   			//Cellule 	    
	    cell.setCellStyle(enteteTxtB);
	    cell.setCellValue(new HSSFRichTextString(Commentaire));
	
	    cell = row.createCell(23);   			//Cellule 	    
	    cell.setCellStyle(enteteTxt);
	    cell.setCellValue(new HSSFRichTextString(Mecanisme));
	
	    cell = row.createCell(24);   			//Cellule 	    
	    cell.setCellStyle(enteteTxt);
	    cell.setCellValue(new HSSFRichTextString(Securisation));
	
	    cell = row.createCell(25);   			//Cellule 	    
	    cell.setCellStyle(enteteTxtB);
	    cell.setCellValue(new HSSFRichTextString(Referentiel));
	    	    
	    
	    numeroLigne++;
	}
	
	
	public 	HSSFWorkbook getWorkBook()
	{
		return wb;
	}
	
	//*** Cràation des entetes
    private void CreateLigneEntete1()
    {
    //** premiàre ligne d'en tàte
    //Fusion de cellules, 1er regroupement 'identification'
	    row = sheet1.createRow(numeroLigne);
	    cell = row.createCell(2);   			//Cellule 6,2;	    
	    cell.setCellStyle(enteteGris);
	    cell.setCellValue(new HSSFRichTextString("Ident"));
	    cell = row.createCell(3);   			//Cellule 6,3;	    
	    cell.setCellStyle(enteteGris);
	    sheet1.addMergedRegion(new CellRangeAddress(numeroLigne,numeroLigne,2,3));		//Fusionne des cellules, r1 est l'indice de la ràgion
	    
	    //Source
	    cell = row.createCell(4);   			//Cellule 6,4;	    
	    cell.setCellStyle(enteteGris);
	    cell.setCellValue(new HSSFRichTextString("Source"));
	    cell = row.createCell(5);   			//Cellule 6,5;	    
	    cell.setCellStyle(enteteGris);
	    cell = row.createCell(6);   			//Cellule 6,5;	    
	    cell.setCellStyle(enteteGris);
	    cell = row.createCell(7);   			//Cellule 6,5;	    
	    cell.setCellStyle(enteteGris);
	    sheet1.addMergedRegion(new CellRangeAddress(numeroLigne,numeroLigne,4,7));		//Fusionne des cellules, r1 est l'indice de la ràgion
	
	    //Destination
	    cell = row.createCell(8);   			//Cellule 6,8;	    
	    cell.setCellStyle(enteteGris);
	    cell.setCellValue(new HSSFRichTextString("Cible"));
	    cell = row.createCell(9);   			//Cellule 6,9;	    
	    cell.setCellStyle(enteteGris);
	    cell = row.createCell(10);   			//Cellule 6,10;	    
	    cell.setCellStyle(enteteGris);
	    cell = row.createCell(11);   			//Cellule 6,11;	    
	    cell.setCellStyle(enteteGris);
	    cell = row.createCell(12);   			//Cellule 6,12;	    
	    cell.setCellStyle(enteteGris);
	    sheet1.addMergedRegion(new CellRangeAddress(numeroLigne,numeroLigne,8,12));		//Fusionne des cellules, r1 est l'indice de la ràgion
	
	    //Volumàtrie et dàbitmàtrie
	    cell = row.createCell(13);   			//Cellule 6,13;	    
	    cell.setCellStyle(enteteGris);
	    cell.setCellValue(new HSSFRichTextString("Volumétrie et débitmétrie"));
	    cell = row.createCell(14);   			//Cellule 6,14;	    
	    cell.setCellStyle(enteteGris);
	    cell = row.createCell(15);   			//Cellule 6,15;	    
	    cell.setCellStyle(enteteGris);
	    cell = row.createCell(16);   			//Cellule 6,16;	    
	    cell.setCellStyle(enteteGris);
	    cell = row.createCell(17);   			//Cellule 6,17;	    
	    cell.setCellStyle(enteteGris);
	    sheet1.addMergedRegion(new CellRangeAddress(numeroLigne,numeroLigne,13,17));		//Fusionne des cellules, r1 est l'indice de la ràgion
	    
	    //Description du flux
	    cell = row.createCell(18);   			//Cellule 6,18;	    
	    cell.setCellStyle(enteteGris);
	    cell.setCellValue(new HSSFRichTextString("Description du flux"));
	    cell = row.createCell(19);   			//Cellule 6,19;	    
	    cell.setCellStyle(enteteGris);
	    cell = row.createCell(20);   			//Cellule 6,20;	    
	    cell.setCellStyle(enteteGris);
	    cell = row.createCell(21);   			//Cellule 6,21;	    
	    cell.setCellStyle(enteteGris);
	    cell = row.createCell(22);   			//Cellule 6,22;	    
	    cell.setCellStyle(enteteGris);
	    sheet1.addMergedRegion(new CellRangeAddress(numeroLigne,numeroLigne,18,22));		//Fusionne des cellules, r1 est l'indice de la ràgion
	    
	    //Authentification du flux
	    cell = row.createCell(23);   			//Cellule 6,23;	    
	    cell.setCellStyle(enteteGris);
	    cell.setCellValue(new HSSFRichTextString("Authentification du flux"));
	    cell = row.createCell(24);   			//Cellule 6,24;	    
	    cell.setCellStyle(enteteGris);
	    cell = row.createCell(25);   			//Cellule 6,25;	    
	    cell.setCellStyle(enteteGris);
	    sheet1.addMergedRegion(new CellRangeAddress(numeroLigne,numeroLigne,23,25));		//Fusionne des cellules, 
    }

    private void CreateLigneEntete2()
    {
	    //2eme ligne
	    row = sheet1.createRow(++numeroLigne);
	    
	    cell = row.createCell(0);   			//Cellule 7,0;	    
	    cell.setCellStyle(enteteGris);
	    cell.setCellValue(new HSSFRichTextString("Statut du flux"));
	    cell = row.createCell(1);   			//Cellule 7,1;	    
	    cell.setCellStyle(enteteGris);
	    sheet1.addMergedRegion(new CellRangeAddress(numeroLigne,numeroLigne,0,1));		//Fusionne des cellules, r1 est l'indice de la ràgion
	
	    cell = row.createCell(2);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("N°"));
	    
	    cell = row.createCell(3);   			//Cellule 	    
	    cell.setCellStyle(enteteB);
	    cell.setCellValue(new HSSFRichTextString("N° Type Utilisateur"));
	
	    cell = row.createCell(4);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Nom des sites"));
	
	    cell = row.createCell(5);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Zone"));
	
	    cell = row.createCell(6);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Domaine (Silo)"));
	
	    cell = row.createCell(7);   			//Cellule 	    
	    cell.setCellStyle(enteteB);
	    cell.setCellValue(new HSSFRichTextString("Brique fonction."));
	
	    cell = row.createCell(8);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Nom des sites"));
	
	    cell = row.createCell(9);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Zone"));
	
	    cell = row.createCell(10);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Domaine (Silo)"));
	
	    cell = row.createCell(11);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Brique fonction."));
	
	    cell = row.createCell(12);   			//Cellule 	    
	    cell.setCellStyle(enteteB);
	    cell.setCellValue(new HSSFRichTextString("Protocole"));
	
	    cell = row.createCell(13);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Vol. (Mo)"));
	
	    cell = row.createCell(14);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Tps réseau (en s)"));
	
	    cell = row.createCell(15);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Débit unitaire"));
	
	    cell = row.createCell(16);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Nbre d'accès concurrents"));
	
	    cell = row.createCell(17);   			//Cellule 	    
	    cell.setCellStyle(enteteB);
	    cell.setCellValue(new HSSFRichTextString("Débit cumulé"));
	
	    cell = row.createCell(18);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Confidté"));
	
	    cell = row.createCell(19);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("ICS"));
	
	    cell = row.createCell(20);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("DMF"));
	
	    cell = row.createCell(21);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Description succincte"));
	
	    cell = row.createCell(22);   			//Cellule 	    
	    cell.setCellStyle(enteteB);
	    cell.setCellValue(new HSSFRichTextString("Commentaire"));
	
	    cell = row.createCell(23);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Mécanisme d'auth. du flux"));
	
	    cell = row.createCell(24);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Sécurisation des authentifiants"));
	
	    cell = row.createCell(25);   			//Cellule 	    
	    cell.setCellStyle(enteteB);
	    cell.setCellValue(new HSSFRichTextString("Référentiel d'authent."));

    }

    private void CreateLigneEntete3()
    {
	    //2eme ligne
	    row = sheet1.createRow(++numeroLigne);
	    
	    cell = row.createCell(0);   			//Cellule 7,0;	    
	    cell.setCellStyle(enteteGris);
	    cell.setCellValue(new HSSFRichTextString("Statut du flux"));
	    cell = row.createCell(1);   			//Cellule 7,1;	    
	    cell.setCellStyle(enteteGris);
	    sheet1.addMergedRegion(new CellRangeAddress(numeroLigne,numeroLigne,0,1));		//Fusionne des cellules, r1 est l'indice de la ràgion
	
	    cell = row.createCell(2);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("N°"));
	    
	    cell = row.createCell(3);   			//Cellule 	    
	    cell.setCellStyle(enteteB);
	    cell.setCellValue(new HSSFRichTextString("Support réseau"));
	
	    cell = row.createCell(4);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Nom des sites"));
	
	    cell = row.createCell(5);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Zone"));
	
	    cell = row.createCell(6);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Domaine (Silo)"));
	
	    cell = row.createCell(7);   			//Cellule 	    
	    cell.setCellStyle(enteteB);
	    cell.setCellValue(new HSSFRichTextString("Brique fonction."));
	
	    cell = row.createCell(8);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Nom des sites"));
	
	    cell = row.createCell(9);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Zone"));
	
	    cell = row.createCell(10);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Domaine (Silo)"));
	
	    cell = row.createCell(11);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Brique fonction."));
	
	    cell = row.createCell(12);   			//Cellule 	    
	    cell.setCellStyle(enteteB);
	    cell.setCellValue(new HSSFRichTextString("Protocole"));
	
	    cell = row.createCell(13);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Vol. (Mo)"));
	
	    cell = row.createCell(14);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Tps réseau (en s)"));
	
	    cell = row.createCell(15);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Débit unitaire"));
	
	    cell = row.createCell(16);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Nbre d'accés concurrents"));
	
	    cell = row.createCell(17);   			//Cellule 	    
	    cell.setCellStyle(enteteB);
	    cell.setCellValue(new HSSFRichTextString("Débit cumulé"));
	
	    cell = row.createCell(18);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Confidté"));
	
	    cell = row.createCell(19);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("ICS"));
	
	    cell = row.createCell(20);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("DMF"));
	
	    cell = row.createCell(21);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Description succincte"));
	
	    cell = row.createCell(22);   			//Cellule 	    
	    cell.setCellStyle(enteteB);
	    cell.setCellValue(new HSSFRichTextString("Commentaire"));
	
	    cell = row.createCell(23);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Mécanisme d'auth. du flux"));
	
	    cell = row.createCell(24);   			//Cellule 	    
	    cell.setCellStyle(entete);
	    cell.setCellValue(new HSSFRichTextString("Sécurisation des authentifiants"));
	
	    cell = row.createCell(25);   			//Cellule 	    
	    cell.setCellStyle(enteteB);
	    cell.setCellValue(new HSSFRichTextString("Référentiel d'authent."));
    }

    
    //*** Largeur à ajuster un jour
    private void setLargeurColonnes()
    {	//A ajuster proprement un jour
	    sheet1.setColumnWidth(0, 2000);		//500 correspond à 1,29
	    sheet1.setColumnWidth(1, 0);
	    sheet1.setColumnWidth(2, 1300);		//Nà
	    sheet1.setColumnWidth(3, 3700);		//Nà utilisateur
	    sheet1.setColumnWidth(4, 3700);		//Sites
	    sheet1.setColumnWidth(5, 3700);		//Zone
	    sheet1.setColumnWidth(6, 3700);		//Domaine
	    sheet1.setColumnWidth(7, 2000);		//Brique
	    sheet1.setColumnWidth(8, 3700);		//Sites
	    sheet1.setColumnWidth(9, 3700);		//Zone
	    sheet1.setColumnWidth(10, 3700);		//Domaine
	    sheet1.setColumnWidth(11, 2000);		//Brique
	    sheet1.setColumnWidth(12, 3000);		//protocole	    
	    sheet1.setColumnWidth(13, 3000);		//Volume
	    sheet1.setColumnWidth(14, 2000);		//tps
	    sheet1.setColumnWidth(15, 3000);		//debit
	    sheet1.setColumnWidth(16, 3000);		//Nb acces
	    sheet1.setColumnWidth(17, 3500);		//debit cumulé
	    sheet1.setColumnWidth(18, 2000);		//Confidentialité
	    sheet1.setColumnWidth(19, 1500);		//ICS
	    sheet1.setColumnWidth(20, 1500);		//DMF
	    sheet1.setColumnWidth(21, 3500);		//Description
	    sheet1.setColumnWidth(22, 3500);		//commentaires
	    sheet1.setColumnWidth(23, 3500);		//Authen
	    sheet1.setColumnWidth(24, 3500);		//Securisation 
	    sheet1.setColumnWidth(25, 3500);		//referentiel
    }

	
	//***** Creation des styles 
	//Je cràe le style 'carto' dont j'aurai besoin pour le tableau des flux
	private void createStyleCarto()
	{
	//Style pour àcrire "Cartographie des flux" dans la premiàre ligne	
	    carto = wb.createCellStyle();
		HSSFFont fonte = setFonte((short)16, "Arial", HSSFFont.BOLDWEIGHT_BOLD, HSSFColor.BLACK.index);	//Arial 16, noir bold		
		carto.setFont(fonte);
		carto.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	}
	//Pour les "Flux users"
	private void createStyleFluUser()
	{
	//Stype pour àcrire "Flux utilisateur" dans la premiàre ligne	
	    fuser = wb.createCellStyle();
		HSSFFont fonte = setFonte((short)10, "Arial", HSSFFont.BOLDWEIGHT_BOLD, HSSFColor.BLUE.index);	//Arial 16, noir bold		
		fuser.setFont(fonte);
		fuser.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	}
	
	private void createStyleEnteteGris()
	{
		//Style d'en tàte des colonnes fonte rouge sur fond gris
		enteteGris = wb.createCellStyle();
		HSSFFont fonte = setFonte((short)8, "Arial", HSSFFont.BOLDWEIGHT_BOLD, HSSFColor.RED.index);	//Arial 16, noir bold		
		enteteGris.setFont(fonte);
		//Fond de cellule en 25 % gris
		enteteGris.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		enteteGris.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//Encadrement
		enteteGris.setBottomBorderColor(HSSFColor.BLACK.index);
		enteteGris.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		enteteGris.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		enteteGris.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		enteteGris.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		enteteGris.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		enteteGris.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		enteteGris.setWrapText(true);  	
	}
		
    private void createStyleEntete()
	{
		//Style d'en tàte des colonnes fonte rouge sur fond gris
    	entete = wb.createCellStyle();
		HSSFFont fonte = setFonte((short)8, "Arial", HSSFFont.BOLDWEIGHT_BOLD, HSSFColor.BLACK.index);	//Arial 16, noir bold		
		entete.setFont(fonte);
		//Fond de cellule blanc
		entete.setFillForegroundColor(HSSFColor.WHITE.index);
		entete.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//Encadrement
		entete.setBottomBorderColor(HSSFColor.BLACK.index);
		entete.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		entete.setBorderRight(HSSFCellStyle.BORDER_THIN);
		entete.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		entete.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		entete.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		entete.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		entete.setWrapText(true);  										//A la ligne quand nàcessaire
	}

    //Pareil, mais le bord droit est àpais
    private void createStyleEnteteB()
	{
		//Style d'en tàte des colonnes fonte rouge sur fond gris
    	enteteB = wb.createCellStyle();
		HSSFFont fonte = setFonte((short)8, "Arial", HSSFFont.BOLDWEIGHT_BOLD, HSSFColor.BLACK.index);	//Arial 16, noir bold		
		enteteB.setFont(fonte);
		//Fond de cellule blanc
		enteteB.setFillForegroundColor(HSSFColor.WHITE.index);
		enteteB.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//Encadrement
		enteteB.setBottomBorderColor(HSSFColor.BLACK.index);
		enteteB.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		enteteB.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		enteteB.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		enteteB.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		enteteB.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		enteteB.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		enteteB.setWrapText(true);  										//A la ligne quand nàcessaire
	}
	
    private void createStyleTxt()
	{
		//Style d'en tàte des colonnes fonte rouge sur fond gris
    	enteteTxt = wb.createCellStyle();
		HSSFFont fonte = setFonte((short)8, "Arial", HSSFFont.BOLDWEIGHT_NORMAL, HSSFColor.BLACK.index);	//Arial 16, noir bold		
		enteteTxt.setFont(fonte);
		//Fond de cellule blanc
		enteteTxt.setFillForegroundColor(HSSFColor.WHITE.index);
		enteteTxt.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//Encadrement
		enteteTxt.setBottomBorderColor(HSSFColor.BLACK.index);
		enteteTxt.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		enteteTxt.setBorderRight(HSSFCellStyle.BORDER_THIN);
		enteteTxt.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		enteteTxt.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		enteteTxt.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		enteteTxt.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		enteteTxt.setWrapText(true);  										//A la ligne quand nàcessaire
	}

    //Pareil, mais le bord droit est àpais
    private void createStyleBTxt()
	{
		//Style d'en tàte des colonnes fonte rouge sur fond gris
    	enteteTxtB = wb.createCellStyle();
		HSSFFont fonte = setFonte((short)8, "Arial", HSSFFont.BOLDWEIGHT_NORMAL, HSSFColor.BLACK.index);	//Arial 16, noir bold		
		enteteTxtB.setFont(fonte);
		//Fond de cellule blanc
		enteteTxtB.setFillForegroundColor(HSSFColor.WHITE.index);
		enteteTxtB.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//Encadrement
		enteteTxtB.setBottomBorderColor(HSSFColor.BLACK.index);
		enteteTxtB.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		enteteTxtB.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		enteteTxtB.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		enteteTxtB.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		enteteTxtB.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		enteteTxtB.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		enteteTxtB.setWrapText(true);  										//A la ligne quand nàcessaire
	}
	
    
    private void createStyleStatusInchange()
	{
		//Style d'en tàte des colonnes fonte rouge sur fond gris
    	Inchange = wb.createCellStyle();
		HSSFFont fonte = setFonte((short)8, "Arial", HSSFFont.BOLDWEIGHT_NORMAL, HSSFColor.BLACK.index);	//Arial 16, noir bold		
		Inchange.setFont(fonte);
		//Fond de cellule blanc
		Inchange.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
		Inchange.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//Encadrement
		Inchange.setBottomBorderColor(HSSFColor.BLACK.index);
		Inchange.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		Inchange.setBorderRight(HSSFCellStyle.BORDER_THIN);
		Inchange.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		Inchange.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		Inchange.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		Inchange.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		Inchange.setWrapText(true);  										//A la ligne quand nàcessaire
	}
	
    private void createStyleStatusCree()
	{
		//Style d'en tàte des colonnes fonte rouge sur fond gris
    	Cree = wb.createCellStyle();
		HSSFFont fonte = setFonte((short)8, "Arial", HSSFFont.BOLDWEIGHT_NORMAL, HSSFColor.BLACK.index);	//Arial 16, noir bold		
		Cree.setFont(fonte);
		//Fond de cellule blanc
		Cree.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
		Cree.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//Encadrement
		Cree.setBottomBorderColor(HSSFColor.BLACK.index);
		Cree.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		Cree.setBorderRight(HSSFCellStyle.BORDER_THIN);
		Cree.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		Cree.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		Cree.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		Cree.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		Cree.setWrapText(true);  										//A la ligne quand nàcessaire
	}

    
    private void createStyleStatusModifie()
 	{
 		//Style d'en tàte des colonnes fonte rouge sur fond gris
    	Modifie = wb.createCellStyle();
 		HSSFFont fonte = setFonte((short)8, "Arial", HSSFFont.BOLDWEIGHT_NORMAL, HSSFColor.BLACK.index);	//Arial 16, noir bold		
 		Modifie.setFont(fonte);
 		//Fond de cellule blanc
 		Modifie.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
 		Modifie.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
 		//Encadrement
 		Modifie.setBottomBorderColor(HSSFColor.BLACK.index);
 		Modifie.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
 		Modifie.setBorderRight(HSSFCellStyle.BORDER_THIN);
 		Modifie.setBorderLeft(HSSFCellStyle.BORDER_THIN);
 		Modifie.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
 		Modifie.setAlignment(HSSFCellStyle.ALIGN_CENTER);
 		Modifie.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
 		Modifie.setWrapText(true);  										//A la ligne quand nàcessaire
 	}
    
    
    private void createStyleStatusSupprime()
 	{
 		//Style d'en tàte des colonnes fonte rouge sur fond gris
    	Supprime = wb.createCellStyle();
 		HSSFFont fonte = setFonte((short)8, "Arial", HSSFFont.BOLDWEIGHT_NORMAL, HSSFColor.BLACK.index);	//Arial 16, noir bold		
 		Supprime.setFont(fonte);
 		//Fond de cellule blanc
 		Supprime.setFillForegroundColor(HSSFColor.ORANGE.index);
 		Supprime.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
 		//Encadrement
 		Supprime.setBottomBorderColor(HSSFColor.BLACK.index);
 		Supprime.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
 		Supprime.setBorderRight(HSSFCellStyle.BORDER_THIN);
 		Supprime.setBorderLeft(HSSFCellStyle.BORDER_THIN);
 		Supprime.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
 		Supprime.setAlignment(HSSFCellStyle.ALIGN_CENTER);
 		Supprime.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
 		Supprime.setWrapText(true);  										//A la ligne quand nàcessaire
 	}
    
    //Dàfinition de la fonte
	private HSSFFont setFonte (short taille, String name, short weight, short couleur)
	{
		HSSFFont fonte;
		
		fonte = wb.createFont();
	    fonte.setFontHeightInPoints(taille);
	    fonte.setFontName(name);
	    
	    fonte.setBoldweight(weight);
	    fonte.setColor(couleur);
	    return fonte;
	}
/**
 * 

		    cell = row.createCell(3);
		    cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
		    cell.setCellFormula("SUM(A1:C1)");	
 */
	
}
