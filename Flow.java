package com.archimatetool.example;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.archimatetool.model.IArchimateComponent;
import com.archimatetool.model.IProperty;
import com.archimatetool.model.impl.FlowRelationship;


//public class Flow  extends element{
public class Flow  {
	private String name=""; 
	private long   id;

	private String   source;
	private String   dest;
	private String nomFlux;
    
//Attributs du flux	(Faire le match un jour avec les liste ci dessous)
	private String Protocole=" ";
	private String Volume=" ";
	private String Temps=" ";
	private String Debit=" ";
	private String AccesConc=" ";
	private String DebitCumul=" ";
	private String Confid=" ";
	private String ICS=" ";
	private String DMF=" ";
	private String AutMeth=" ";
	private String AutRef=" ";
	private String AutSecure=" ";
	private String Comment=" ";
	private String Descr=" ";
	private String Nature=" ";
	private String liste = "";		//Liste à splitter pour avoir les éléments du tableau des flux
	
	public Flow(IArchimateComponent component) 
	{		
		name    = component.getClass().getName();
		id      =  Long.parseLong(component.getId(), 16);	//Traduit le hashcode (string en hexa) en entier
		
		source  =  ((FlowRelationship)component).getSource().getId();	//Traduit le hashcode (string en hexa) en entier
		dest    =  ((FlowRelationship)component).getTarget().getId();	//Traduit le hashcode (string en hexa) en entier
		nomFlux = component.getName();
		Descr   = component.getDocumentation();
		recupProperties(component);
	}
	
	public Flow(String nam, String i,String typ,String sourc,String targt, String nn) 
	{
		name = nam;
		id =  Long.parseLong(i, 16);	//Traduit le hashcode (string en hexa) en entier
		
		source =  sourc;	//Traduit le hashcode (string en hexa) en entier
		dest =  targt;	//Traduit le hashcode (string en hexa) en entier
		nomFlux = nn;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getListe()
	{
	    liste = Volume + ":" + Temps+ ":" + Debit+ ":" + AccesConc+ ":" + DebitCumul + ":" 
	    		+ Confid+ ":" + ICS	+ ":" + DMF+ ":" + Descr+ ":" + Comment + ":" + AutMeth + ":"
	    		+ AutSecure + ":" + AutRef;

		return liste;
	}
	
	public void setString(String s)
	{
		liste = s;
	}
	
	
	//Futur objet FLUX ...
	private void recupProperties(IArchimateComponent component)
	{
        List<IProperty> prop = component.getProperties();	//Liste des propriétés de l'objet
        String key = " ";
        for(IProperty obj : prop) 
        {						//Enumère les proprétés de la liste prop
        	key = obj.getKey();
    		if (key.equals("volume"))
    			Volume = obj.getValue();
    		if (key.equals("temps"))
    			Temps = obj.getValue();
    		if (key.equals("débit"))
    			Debit = obj.getValue();
    		if (key.equals("accès_concurrent"))
    			AccesConc = obj.getValue();
    		if (key.equals("débit_cumulé"))
    			DebitCumul = obj.getValue();
    		if (key.equals("confidentialité"))
    			Confid = obj.getValue();
    		if (key.equals("ics"))
    			ICS = obj.getValue();
    		if (key.equals("dmf (calculé)"))
    			DMF = obj.getValue();
    		if (key.equals("auth_method"))
    			AutMeth = obj.getValue();		//login/mdp
    		if (key.equals("auth_ref"))
    			AutRef = obj.getValue();		//E2
    		if (key.equals("auth_secure"))
    			AutSecure = obj.getValue();		//Chiffrement du flux

        }

	}

	
	
// **Voir si obsolete ?	
	public void setProtocole(String s)
	{
		Protocole = s;
	}
	public void setVolume(String s)
	{
		Volume = s;
	}
	public void setConfident(String s)
	{
		Confid = s;
	}
	public void setICS(String s)
	{
		ICS = s;
	}
	public void setComment(String s)
	{
		Comment = s;
	}
	public void setDescr(String s)
	{
		Descr = s;
	}
	public void setNature(String s)
	{
		Nature = s;
	}
//Get
	public String getProtocole()
	{
		return Protocole;
	}
	public String getVolume()
	{
		return Volume;
	}
	public String getConfident()
	{
		return Confid;
	}
	public String getICS()
	{
		return ICS;
	}
	public String getComment()
	{
		return Comment;
	}
	public String getDescr()
	{
		return Descr;
	}
	public String getNature()
	{
		return Nature;
	}
	
	public String getNomFlux()
	{
		return nomFlux;
	}
	
	public String getSource()
	{
		return source;
	}
	
	public String getDest()
	{
		return dest;
	}
}
