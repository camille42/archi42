
/**
 * Parsing de la modélisation de Camille, version flow
 * Dans cette modélisation, on trouve les éléments de la façon suivante:
 * 
 * Status,			valeur par défaut, "Créé" 				
*  NumFlux,			name du flux, sous la forme U1:https -> à parser
*  user,			flow, source used by un dest de type Business Role 1:inspecteur nomade (à parser)
*  NomSiteS,		(source)	
*  ZoneS,
*  DomaineS,
*  BriqueS,			nom du source à parser (potentiellement B0.2:DA
*  NomSiteD, 		(dest)
*  ZoneD,
*  DomaineD,
*  BriqueD,			nom du dest à parser (
*  Protocole,		Aprés le parsing du nom
*  Volume,			property "volume"	(voir nom exact)
*  Tps,					temps
*  Debit,				debit
*  Concurents,			concurrents
*  Cumul,				débit cumulé
*  Conf,				confidentialité
*  Ics,					ICS
*  Dmf,					DMF
*  Description,		Documentation
*  Commentaire,		<not found>
*  Mecanisme,			Auth_method
*  Securisation,		auth_secure
*  Referentiel			auth_ref
 * 
 * Sur les sources et destinations :
 *    Brique - realisé par - F01 - usedBy - B01 infra - réalisé par - navigateur - assigné - poste client - associé - réseau - assigne - location
 * C'est tout simple ...
 * 
 *    ATTENTION : les sources et les target peuvent étre multiples, ils sont alors séparés par des espaces.
 * 
 * 
 * @author fred
 *
 *  
 * Versions:
 * V1 : version initiale, normalement les éléments sont uniques et le répertoire par défaut positionné
 * V1_0 : les index étaient des int ils passent en long
 * V1_1 : debug et suite du code
 * V1_2 : finalisation pour 4 flux du modèle, y compris les conflits de caractères
 * 
 * 
 * V2_0 : 
 * 		Description : première version complète, sous forme de plug in de Archi et nettoyé des éléments qui ne servent pas
 * 		Date : 25/05/2016
 * 
 */

package com.archimatetool.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;

import com.archimatetool.model.IArchimateComponent;
import com.archimatetool.model.impl.AssignmentRelationship;
import com.archimatetool.model.impl.AssociationRelationship;
import com.archimatetool.model.impl.CompositionRelationship;
import com.archimatetool.model.impl.RealisationRelationship;
import com.archimatetool.model.impl.UsedByRelationship;



public class globalCamilleFlow {


	private List<EObject> le;

	private boolean debug 	= false;
	
	final String REALISATION_RELATIONSHIP	= "RealisationRelationship";
	final String USEDBY_RELATIONSHIP 		= "UsedByRelationship";
	final String ASSIGNMENT_RELATIONSHIP 	= "AssignmentRelationship";
	final String ASSOCIATION_RELATIONSHIP 	= "AssociationRelationship";
	final String COMPOSITION_RELATIONSHIP 	= "CompositionRelationship";
	final String FLOW_RELATIONSHIP 			= "FlowRelationship";
	
	final String TYPE_SYSTEMSOFTWARE		= "SystemSoftware";
	final String TYPE_LOCATION				= "Location";
	final String TYPE_NODE					= "Node";
	final String TYPE_DEVICE				= "Device";
	final String TYPE_NETWORK				= "Network";
	final String TYPE_INFRAFUNCTION			= "InfrastructureFunction";
	final String TYPE_INFRASERVICE			= "InfrastructureService";
	final String TYPE_APPLIFUNCTION			= "ApplicationFunction";
	final String TYPE_APPLISERVICE			= "ApplicationService";
	final String TYPE_BUSINESSPROCESS		= "BusinessProcess";
	final String TYPE_BUSINESSROLE			= "BusinessRole";
	
	/*
	 * globalCamilleFlow
	 * 
	 * Point d'entrée
	 */
	public globalCamilleFlow(List<EObject> le1, File file)
	{
		le = le1;			//Récupère la liste des éléments

		
	//Debug hors Excel
	   	APIExcel ae = new APIExcel();			//Crée le contenu du  truc Excel	
		ArrayList<String> ListInt	= new ArrayList<String>();
		ArrayList<String> ListUser	= new ArrayList<String>();
		ArrayList<String> ListApp	= new ArrayList<String>();
		
	    //Parcourt la liste des éléments et décode les flux
	    for (int ii = 0; ii < le.size(); ii ++)
	    {
	    	EObject eObject = le.get(ii);				//Il existe une syntaxe plus sympas, pas le temps tout de suite ...
	    	if(eObject instanceof IArchimateComponent) 
	    	{
                IArchimateComponent component = (IArchimateComponent)eObject;
	            if ( component.eClass().getName().equals(FLOW_RELATIONSHIP))
		    	{
	            	Flow elm = new Flow(component);
	            	
		    		String s = decodeFlux(elm);		//Chaine qui décrit le flux (à parser)
		    		
	    		//On trie suivant les cas : USER ou INTERNE pour l'instant
		    		if (s.contains("INTERNE:"))     ListInt.add(s);
		    		if (s.contains("USER:"))	    ListUser.add(s);
		    		if (s.contains("APPLICATIF:"))  ListApp.add(s);
		    	}
	    	}
	    }
	    		
	    //Edition
	    //Tentative de tri des listes
    	//http://www.commentcamarche.net/forum/affich-3834997-aide-java-trier-en-ordre-alphabetique-o-v
	    // Trié par order alphabétique, genre : 1,11,12,13,100,102,2,3 etc ... Améliorable ?
	    	java.util.Collections.sort(ListApp);
	    	java.util.Collections.sort(ListInt);
	    	java.util.Collections.sort(ListUser);
	    
	    for (int ii = 0; ii < ListUser.size(); ii ++)
	    {
	    		String[] ee = ListUser.get(ii).split(":");
    			//System.out.println("Flux : " + ee[1] + ", Nb = " + ee.length + ", ligne = " + ListUser.get(ii) );
	    		if (ee.length > 23)
	    		{
	    			ae.setLigneFlux("Créé", ee[1], ee[2], ee[3], ee[4], ee[5], ee[6], 
	    					ee[7], ee[8], ee[9], ee[10], ee[11], ee[12], ee[13], ee[14], ee[15], 
	    					ee[16], ee[17], ee[18], ee[19], ee[20], ee[21], ee[22], ee[23], ee[24]);		
	    		}
	    		else
	    		{
	    			System.out.println(" longueur KO");
	    		}
	      }
	    //Crée les entetes des flux internes	    
		ae.FluxInterne();

	    //Les flux internes eux mêmes
	    for (int ii = 0; ii < ListInt.size(); ii ++)
	    {
	    		String[] ee = ListInt.get(ii).split(":");
    			//System.out.println("Flux : " + ee[1] + ", Nb = " + ee.length + ", ligne = " + ListInt.get(ii));

	    		if (ee.length > 23)
	    		{
	    			ae.setLigneFlux("Créé", ee[1], ee[2], ee[3], ee[4], ee[5], ee[6], 
	    					ee[7], ee[8], ee[9], ee[10], ee[11], ee[12], ee[13], ee[14], ee[15], 
	    					ee[16], ee[17], ee[18], ee[19], ee[20], ee[21], ee[22], ee[23], ee[24]);		
	    		}
	    		else
	    		{
	    			System.out.println(" longueur KO");
	    		}
	      }
	    
	    	ae.FluxExterne();
	    
		    //Les flux internes eux mêmes
		    for (int ii = 0; ii < ListApp.size(); ii ++)
		    {
		    		String[] ee = ListApp.get(ii).split(":");
	    			//System.out.println("Flux : " + ee[1] + ", Nb = " + ee.length + ", ligne = " + ListInt.get(ii));

		    		if (ee.length > 23)
		    		{
		    			ae.setLigneFlux("Créé", ee[1], ee[2], ee[3], ee[4], ee[5], ee[6], 
		    					ee[7], ee[8], ee[9], ee[10], ee[11], ee[12], ee[13], ee[14], ee[15], 
		    					ee[16], ee[17], ee[18], ee[19], ee[20], ee[21], ee[22], ee[23], ee[24]);		
		    			System.out.println("Créé "+ ee[1] + ", " + ee[2] + ", " + ee[3] + ", " + ee[4] + ", " + ee[5] + ", " + ee[6]  + ", " + 
		    					ee[7] + ", " + ee[8] + ", " + ee[9] + ", " + ee[10] + ", " + ee[11] + ", " + ee[12] + ", " + ee[13] + ", " + ee[14] + ", " + ee[15] + ", " + 
		    					ee[16] + ", " + ee[17] + ", " + ee[18] + ", " + ee[19] + ", " + ee[20] + ", " + ee[21] + ", " + ee[22] + ", " + ee[23] + ", " + ee[24]);		
		    		}
		    		else
		    		{
		    			System.out.println(" longueur KO");
		    		}
		      }
		    
	      enregistrementExcel(ae, file);		//Enregistre dans le fichier "file"
	      
	      JOptionPane.showMessageDialog(null,
				    "Opération terminée",
				    "Fichier généré",
				    JOptionPane.WARNING_MESSAGE);
	      
	}

	public String decodeFlux(Flow elm) {
		
		StringBuffer sour = new StringBuffer();
		
		sour.append(getElementsRoles(elm)).append(":");
		sour.append(getElementsSource(elm)).append(":");
		sour.append(getElementsDest(elm)).append(":");
		sour.append(getProtocole(elm)).append(":");
		
		sour.append(elm.getListe());
		
		return new String(sour);
	}
	
	/**
	 * @Description : On extrait le protocole dans le nom qui est de la forme <Nom>:<protocole>:<port>
	 *
	 * @param : le flux dont on cherche le protocole
	 * @return : le nom du protocole ou "nc" s'il n'est pas donné
	 */
	
	private String getProtocole(Flow elm)
	{
		String blaze[] = elm.getNomFlux().split(":");			//Normalement le nom, le protocole et ? le port
		if (blaze.length > 1)
			return blaze[1];
		
		return "nc";
	}
	
	/**
	 * @Descrition : enregistement des données dans le fichier Excel
	 * 
	 * @param : ae les données à écrire, file le fichier où enregistrer les odnnées
	 * @return : rien, ou une boite de dialogue qui décrit l'erreur
	 */
	
	private void enregistrementExcel(APIExcel ae, File file)
	{
	    FileOutputStream fileOut;
	    try 
	    {
	      fileOut = new FileOutputStream(file);
	      ae.getWorkBook().write(fileOut);
	      fileOut.close();
	    } 
	    catch (FileNotFoundException e) 
	    {
	      e.printStackTrace();
	   	 MessageDialog.openInformation(null,"PremierPlugin","File not found");
	        
	    } 
	    catch (IOException e) 
	    {
		   	 MessageDialog.openInformation(null,"PremierPlugin","Erreur " + e.getMessage());
	    }
	}
	
	/**
	 * @Description : retourne l'ensemble des rôles qui utilisent ce flux
	 * 
	 * @param : le flux
	 * @return : le nom des rôles concaténés
	 */
	private String getElementsRoles(Flow elm)
	{		
		String s = "";
		String id_appliFunction, id_businessProcess;
		List<String> listidRoles = null;
		
		String idSource = elm.getSource();
		try {
			id_appliFunction 	= getIdApplicationFunction(idSource);		//Cherche un source qui soit une ApplicationFunction			
			id_businessProcess	= getIdBusinessProcess(id_appliFunction);			//C'est là que ça se passe, il ne trouve pas de rôle
			if ( ! id_businessProcess.equals("0"))
			{
				listidRoles			= getIdBusinessRoleList(id_businessProcess);
				if (debug)   System.out.print(listidRoles+"\n");
			}			
			if ((listidRoles != null) && (listidRoles.size() > 0))
			{
				s = "USER:";									//Typage du flux
				s += elm.getNomFlux().split(":")[0];				//Nom du flux (et protocole que l'on vire)
				s += ":";
				for (int ii = 0; ii < listidRoles.size(); ii ++)
				{
					if (ii > 0)
						s += ", ";
					s += ((IArchimateComponent)getElemFromId(listidRoles.get(ii))).getName().split(":")[0];		//Suite des roles 			
				}
			}
			else
			{
				//** le type de flux est donné par la première lettre du nom du flux U=User, A=Applicatif, I=Interne
				//Si Ok, simplifier un jour ...
				s = elm.getNomFlux().split(":")[0].substring(0, 1);	//Première lettre
				if (s.equals("A"))    s="APPLICATIF:";
				if (s.equals("I"))	  s="INTERNE:";
				if (s.equals("U"))	  s="USER:";				
				s += elm.getNomFlux().split(":")[0];				//Nom du flux (et protocole que l'on vire)
				s += ":";
			}
		} catch (Exception e) {
			System.out.println("[ERREUR] - getElementsRoles -\n\t"+e.toString());
		}
		return s;
	}
	
	/**
	 * @Description : Retourne ce qu'il doit mettre dans le tableau des flux dans la colonne 'source':
	 * 				NomSiteS,		(source)	
	 * 				ZoneS,
	 * 				DomaineS,
	 * 				BriqueS,	nom du dest à  parser (potentiellement B0.2:DA
	 * 	   Retourne la concaténation de ces éléments, ça permet de parser plus loin ... 
	 * @param : flow
	 * @return : chaine de caracteres <site>:<zone>:<domaine>:<brique fonctionnelle>
	 * 
	 * 
	 */
		private String getElementsSource(Flow elm) {
		String ChampSource = " : : ";
		String id_infraService, id_appliFunction, id_infraFunction, id_nodeOrDevice; 
		String id_network, id_systemSoftware, idLocation;
		
		String idSource = elm.getSource();
		if (debug) System.out.print(" Source : " + ((IArchimateComponent)getElemFromId(idSource)).getName());
		String NomSource = ((IArchimateComponent)getElemFromId(idSource)).getName().split(":")[0];			//On garde que le nom de la brique				
		
	//	System.out.println("SOURCE ---------------------------");
		
		id_appliFunction 	= getIdApplicationFunction(idSource);						// F0.1	
		id_infraService		= getIdInfrastructureService(id_appliFunction);				// B0.1
		id_infraFunction	= getIdInfrastructureFunction(id_infraService);				// navigateur
		id_systemSoftware	= getIdSystemSoftware(id_infraFunction);					// IE/Firefox
		id_nodeOrDevice		= getIdNodeOrDevice(id_infraFunction, id_systemSoftware);	// PosteCient
		id_network			= getIdNetwork(id_nodeOrDevice);							// RLE EDF-SA:EDF Dérégulé
		if ( ! id_network.equals("0")) {													//Site, zone, domaine
			idLocation = getIdLocation(id_network);			//Récupère le nom du site (Location) à partir du network
			if ( ! idLocation.equals("0"))	ChampSource = ((IArchimateComponent)getElemFromId(idLocation)).getName() + ":";
			else					ChampSource = "-:";								//Pas trouvé la location
				
			ChampSource += ((IArchimateComponent)getElemFromId(id_network)).getName(); 	
		}
		return ChampSource + ":" + NomSource;		//ZSA Outillage: EdF Dérégulé : BO1
	}

	/**
	 * @Description : Retourne ce qu'il doit mettre dans le tableau des flux dans la colonne 'destination':
	 * 				NomSiteS,		(dest)	
	 * 				ZoneS,
	 * 				DomaineS,
	 * 				BriqueS,	nom du dest à  parser (potentiellement B0.2:DA
	 * 	   Retourne la concaténation de ces éléments, ça permet de parser plus loin ... 
	 * @param : flow
	 * @return : chaine de caracteres <site>:<zone>:<domaine>:<brique fonctionnelle>
	 * 
	 * 
	 */
	private String getElementsDest(Flow elm)
	{
		String idDest = elm.getDest();
		String id_infraService, id_appliFunction, id_infraFunction, id_nodeOrDevice;
		String id_network, id_systemSoftware, idLocation;
		String ChampDest = " : : ";					//Site, zone, domaine
		String NomDest 	= ((IArchimateComponent)getElemFromId(idDest)).getName().split(":")[0];			//On ne garde que le début
		
	//	System.out.println("DESTINATION ------------------------");
		{
			id_appliFunction 	= getIdApplicationFunction(idDest);
			id_infraService		= getIdInfrastructureService(id_appliFunction);
			id_infraFunction	= getIdInfrastructureFunction(id_infraService);
			id_systemSoftware	= getIdSystemSoftware(id_infraFunction);
			id_nodeOrDevice		= getIdNodeOrDevice(id_infraFunction, id_systemSoftware);
			id_network			= getIdNetwork(id_nodeOrDevice);
			if ( ! id_network.equals("0") ) {													//Site, zone, domaine
				idLocation = getIdLocation(id_network);			//Récupère le nom du site (Location) à partir du network
				if (! idLocation.equals("0"))	ChampDest = ((IArchimateComponent)getElemFromId(idLocation)).getName() + ":";
				else					ChampDest = "-:";								//Pas trouvé la location
					
				ChampDest += ((IArchimateComponent)getElemFromId(id_network)).getName(); 	
			}
		}
		return ChampDest + ":" + NomDest;
	}
	
	/**
	 * @description : retourne l'ID de l'éléments source des relations d'association 
	 * 				  dont le target.ID est donné par le parametre et dont le type est location
	 * @param :	id du target
	 * @return :  id du source, ou "0" s'il ne trouve pas
	 */
	private String getIdLocation(String id) 
	{
		for ( int i = 0 ; i < le.size(); i++)		//Scanne les objets 
		{
			//Si pas objet de type REALISATION_RELATIONSHIP, on continue
			if ( ! ((IArchimateComponent)le.get(i)).getClass().getName().contains(ASSIGNMENT_RELATIONSHIP) )	continue;

			IArchimateComponent component = (IArchimateComponent)le.get(i);			
			AssignmentRelationship r = ((AssignmentRelationship)component);	//r est une relation de realisation
			
			//Cherche si le target de r est celui cherché, si non, continue
			if ( ! r.getTarget().getId().equals(id) )  									continue;
			
			//Cherche si l'objet trouvé est du bon type
			if ( ! (r.getSource()).getClass().getName().contains(TYPE_LOCATION))	continue;
			
			return r.getSource().getId();
		}		
		return "0";			//En cas d'erreur
	}
	
	/**
	 * @description : retourne l'ID de l'éléments source des relations d'assignment 
	 * 				  dont le target.ID est donné par le parametre et dont le type est SystemSoftware
	 * @param :	id du target
	 * @return :  id du source, ou "0" s'il ne trouve pas
	 */
	private String getIdSystemSoftware(String id) 
	{
		for ( int i = 0 ; i < le.size(); i++)		//Scanne les objets 
		{
			//Si pas objet de type REALISATION_RELATIONSHIP, on continue
			if ( ! ((IArchimateComponent)le.get(i)).getClass().getName().contains(ASSIGNMENT_RELATIONSHIP) )	continue;

			IArchimateComponent component = (IArchimateComponent)le.get(i);			
			AssignmentRelationship r = ((AssignmentRelationship)component);	//r est une relation de realisation
			
			//Cherche si le target de r est celui cherché, si non, continue
			if ( ! r.getTarget().getId().equals(id) )  									continue;
			
			//Cherche si l'objet trouvé est du bon type
			if ( ! (r.getSource()).getClass().getName().contains(TYPE_SYSTEMSOFTWARE))	continue;
			
			return r.getSource().getId();
		}		
		return "0";			//En cas d'erreur
	}
	
	/**
	 * @description : retourne l'ID de l'éléments source des relations de réalisation
	 * 				  dont le target.ID est donné par le parametre et dont le type est ApplicationFunction
	 * @param :	id du target
	 * @return :  id du source, ou "0" s'il ne trouve pas
	 */
	private String getIdApplicationFunction(String id) 
	{
		for ( int i = 0 ; i < le.size(); i++)		//Scanne les objets 
		{
			//Si pas objet de type REALISATION_RELATIONSHIP, on continue
			if ( ! ((IArchimateComponent)le.get(i)).getClass().getName().contains(REALISATION_RELATIONSHIP) )	continue;

			IArchimateComponent component = (IArchimateComponent)le.get(i);			
			RealisationRelationship r = ((RealisationRelationship)component);	//r est une relation de realisation
			
			//Cherche si le target de r est celui cherché, si non, continue
			if ( ! r.getTarget().getId().equals(id) )  									continue;
			
			//Cherche si l'objet trouvé est du bon type
			if ( ! (r.getSource()).getClass().getName().contains(TYPE_APPLIFUNCTION))	continue;
			
			return r.getSource().getId();
		}		
		return "0";			//En cas d'erreur
	}
	
	/**
	 * @description : retourne l'ID de l'éléments source des relations de UsedBy 
	 * 				  dont le target.ID est donné par le parametre et dont le type est Service d'infra
	 * @param :	id du target
	 * @return :  id du source, ou "0" s'il ne trouve pas
	 */
	private String getIdInfrastructureService(String id) 
	{
		for ( int i = 0 ; i < le.size(); i++)		//Scanne les objets 
		{
			//Si pas objet de type REALISATION_RELATIONSHIP, on continue
			if ( ! ((IArchimateComponent)le.get(i)).getClass().getName().contains(USEDBY_RELATIONSHIP) )	continue;

			IArchimateComponent component = (IArchimateComponent)le.get(i);			
			UsedByRelationship r = ((UsedByRelationship)component);	//r est une relation de realisation
			
			//Cherche si le target de r est celui cherché, si non, continue
			if ( ! r.getTarget().getId().equals(id) )  									continue;
			
			//Cherche si l'objet trouvé est du bon type
			if ( ! (r.getSource()).getClass().getName().contains(TYPE_INFRASERVICE))	continue;
			
			return r.getSource().getId();
		}		
		return "0";			//En cas d'erreur
	}
	
	/**
	 * @description : retourne l'ID de l'éléments source des relations de réalisation 
	 * 				  dont le target.ID est donné par le parametre et dont le type est infraFunction
	 * @param :	id du target
	 * @return :  id du source, ou "0" s'il ne trouve pas
	 */
	private String getIdInfrastructureFunction(String id) 
	{
		for ( int i = 0 ; i < le.size(); i++)		//Scanne les objets 
		{
			//Si pas objet de type REALISATION_RELATIONSHIP, on continue
			if ( ! ((IArchimateComponent)le.get(i)).getClass().getName().contains(REALISATION_RELATIONSHIP) )	continue;

			IArchimateComponent component = (IArchimateComponent)le.get(i);			
			RealisationRelationship r = ((RealisationRelationship)component);	//r est une relation de realisation
			
			//Cherche si le target de r est celui cherché, si non, continue
			if ( ! r.getTarget().getId().equals(id) )  									continue;
			
			//Cherche si l'objet trouvé est du bon type
			if ( ! (r.getSource()).getClass().getName().contains(TYPE_INFRAFUNCTION))	continue;
			
			return r.getSource().getId();
		}		
		return "0";			//En cas d'erreur
	}
	
	/**
	 * @description : retourne l'ID de l'éléments source des relations d'association 
	 * 				  dont le target.ID est donné par le parametre et dont le type est Network
	 * @param :	id du target
	 * @return :  id du source, ou "0" s'il ne trouve pas
	 */
	private String getIdNetwork(String id) 
	{
		for ( int i = 0 ; i < le.size(); i++)		//Scanne les objets 
		{
			//Si pas objet de type REALISATION_RELATIONSHIP, on continue
			if ( ! ((IArchimateComponent)le.get(i)).getClass().getName().contains(ASSOCIATION_RELATIONSHIP) )	continue;

			IArchimateComponent component = (IArchimateComponent)le.get(i);			
			AssociationRelationship r = ((AssociationRelationship)component);	//r est une relation de realisation
			
			//Cherche si le target de r est celui cherché, si non, continue
			if ( r.getTarget().getId().equals(id) ) 
			{
						//Cherche si l'objet trouvé est du bon type
				if (  (r.getSource()).getClass().getName().contains(TYPE_NETWORK))
					return r.getSource().getId();
			}
			if ( r.getSource().getId().equals(id) ) 
			{
						//Cherche si l'objet trouvé est du bon type
				if (  (r.getTarget()).getClass().getName().contains(TYPE_NETWORK))
					return r.getTarget().getId();
			}
		}		
		return "0";			//En cas d'erreur
	}
	
	/**
	 * @description : retourne l'ID de l'éléments target des relations de used_by 
	 * 				  dont le source.ID est donné par le parametre et dont le type est BusinessProcess
	 * @param :	id du source
	 * @return :  id du target, ou "0" s'il ne trouve pas
	 */
	private String getIdBusinessProcess(String id) 
	{
		for ( int i = 0 ; i < le.size(); i++)		//Scanne les objets 
		{
			//Si pas objet de type REALISATION_RELATIONSHIP, on continue
			if ( ! ((IArchimateComponent)le.get(i)).getClass().getName().contains(USEDBY_RELATIONSHIP) )	continue;

			IArchimateComponent component = (IArchimateComponent)le.get(i);			
			UsedByRelationship r = ((UsedByRelationship)component);	//r est une relation de realisation
			
			//Cherche si le target de r est celui cherché, si non, continue
			if ( ! r.getSource().getId().equals(id) )  									continue;
			
			//Cherche si l'objet trouvé est du bon type
			if ( ! (r.getTarget()).getClass().getName().contains(TYPE_BUSINESSPROCESS))	continue;
			
			return r.getTarget().getId();
		}		
		return "0";			//En cas d'erreur
	}
	
	/**
	 * @description : retourne les ID des éléments sources des relations d'assignement 
	 * 				  dont le target.ID est donné par le parametre et dont le type est BusinessRole
	 * @param :	id du target 
	 * @return :  liste des id des sources, ou chaine vide
	 */
	private List<String> getIdBusinessRoleList(String id) 
	{
		if (debug) System.out.println("RoleList\n");
	
		List<String> l = new ArrayList<String>();		//Liste des réponses
		
		for ( int i = 0 ; i < le.size(); i++) 
		{
			if ( ! ((IArchimateComponent)le.get(i)).getClass().getName().contains(ASSIGNMENT_RELATIONSHIP) )	continue;

			IArchimateComponent component = (IArchimateComponent)le.get(i);			
			AssignmentRelationship r = ((AssignmentRelationship)component);	//r est une relation de realisation

			//Cherche si le target de r est celui cherché, si non, continue
			if ( ! r.getTarget().getId().equals(id) )  									continue;
			
			//Cherche si l'objet trouvé est du bon type
			if ( ! (r.getSource()).getClass().getName().contains(TYPE_BUSINESSROLE))	continue;

			l.add(r.getSource().getId());
		}
		
		return l;
	}
	
	//A une époque, l'objet retourné était une liste de string, vérifier à l'occasion
	private String getIdNodeOrDevice(String id, String id_soft) {
		if ( id_soft.equals("0")) 
		{			
			for ( int i = 0 ; i < le.size(); i++) 
			{
				if ( ! ((IArchimateComponent)le.get(i)).getClass().getName().contains(ASSIGNMENT_RELATIONSHIP) )	continue;

				IArchimateComponent component = (IArchimateComponent)le.get(i);			
				AssignmentRelationship r = ((AssignmentRelationship)component);	//r est une relation de realisation

				//Cherche si le target de r est celui cherché, si non, continue
				if ( ! r.getTarget().getId().equals(id) )  									continue;
				
				//Cherche si l'objet trouvé est du bon type
				if (((r.getSource()).getClass().getName().contains(TYPE_NODE)) ||
					((r.getSource()).getClass().getName().contains(TYPE_DEVICE)))
						
					return r.getSource().getId();
			}
			
			return "0";
		}
		else
		{
			for ( int i = 0 ; i < le.size(); i++)		//Scanne les objets 
			{
				//Si pas objet de type REALISATION_RELATIONSHIP, on continue
				if ( ! ((IArchimateComponent)le.get(i)).getClass().getName().contains(COMPOSITION_RELATIONSHIP) )	continue;

				IArchimateComponent component = (IArchimateComponent)le.get(i);			
				CompositionRelationship r = ((CompositionRelationship)component);	//r est une relation de realisation
				
				//Cherche si le target de r est celui cherché, si non, continue
				if ( ! r.getTarget().getId().equals(id_soft) )  									continue;
				
				//Cherche si l'objet trouvé est du bon type
				if ( ! (r.getSource()).getClass().getName().contains(TYPE_DEVICE))	continue;
				
				return r.getSource().getId();
			}		
			return "0";			//En cas d'erreur
		}
	}
	
	/*
	 * Récuoère l'élément dont l'id est le paramètre
	 * getElemFromId
	 * 
	 * En fait, on pourrait simplifier, l'API archimatetools donne directement l'élément. Comme on travaillait au début
	 * avec les ID, j'ai repris le process initial, mais c'est améliorable !
	 * 
	 */
	private EObject getElemFromId( String s)
	{
		for (int idx = 0; idx < le.size(); idx++)
		{	
			if (((IArchimateComponent)le.get(idx)).getId().equals(s))	
				return le.get(idx);
		}
		return null;
	}
	

	
}

