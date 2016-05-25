/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 * 
 * Redev pour EdF
 * Version 2.0 :
 * 		Description : plugin pour le logiciel Archi, permet d'exporter les données de flux au format Excel EdF
 * 		Date : 25/05/2016
 * 
 */
package com.archimatetool.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

import com.archimatetool.editor.model.IModelExporter;
import com.archimatetool.model.FolderType;
import com.archimatetool.model.IArchimateModel;
import com.archimatetool.model.IFolder;


/**
 * Example Exporter of Archimate model
 * 
 * @author Phillip Beauvoir
 */
public class MyExporter implements IModelExporter {
    
    String MY_EXTENSION = ".xls"; //$NON-NLS-1$
    String MY_EXTENSION_WILDCARD = "*.xls"; //$NON-NLS-1$
    
	final String REALISATION_RELATIONSHIP	= "RealisationRelationship";
	final String USEDBY_RELATIONSHIP 		= "UsedByRelationship";
	final String ASSIGNMENT_RELATIONSHIP 	= "AssignmentRelationship";
	final String ASSOCIATION_RELATIONSHIP 	= "AssociationRelationship";
	final String COMPOSITION_RELATIONSHIP 	= "CompositionRelationship";
	final String FLOW_RELATIONSHIP 			= "FlowRelationship";
	
    private List<EObject> list = new ArrayList<EObject>();		//Liste des éléments
    
//    private OutputStreamWriter writer;
    
    public MyExporter() {
    }

    @Override
    public void export(IArchimateModel model) throws IOException {

    	File file = askSaveFile();
        if(file == null) {
            return;
        }
        
      
    	//Récupère les éléments du modèle
        getElements(model.getFolder(FolderType.BUSINESS), list);
        getElements(model.getFolder(FolderType.APPLICATION), list);
        getElements(model.getFolder(FolderType.TECHNOLOGY), list);
        getElements(model.getFolder(FolderType.CONNECTORS), list);
        getElements(model.getFolder(FolderType.RELATIONS), list);

    	
     globalCamilleFlow gcf = new globalCamilleFlow(list, file);	//C'est LA que ça se passe !
 
    }

    
    private void getElements(IFolder folder, List<EObject> list) {
        for(EObject object : folder.getElements()) {
            list.add(object);
        }
        
        for(IFolder f : folder.getFolders()) {
            getElements(f, list);
        }
    }

 
    /**
     * Ask user for file name to save to
     *  et verifie que le fichier n'existe pas déjà.
     */
    private File askSaveFile() {
        FileDialog dialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
        dialog.setText(Messages.MyExporter_0);
        dialog.setFilterExtensions(new String[] { MY_EXTENSION_WILDCARD, "*.*" } ); //$NON-NLS-1$
        String path = dialog.open();
        if(path == null) {
            return null;
        }
        
        // Only Windows adds the extension by default
        if(dialog.getFilterIndex() == 0 && !path.endsWith(MY_EXTENSION)) {
            path += MY_EXTENSION;
        }
        
        File file = new File(path);
        
        // Make sure the file does not already exist
        if(file.exists()) {
            boolean result = MessageDialog.openQuestion(Display.getCurrent().getActiveShell(),
                    Messages.MyExporter_0,
                    NLS.bind(Messages.MyExporter_1, file));
            if(!result) {
                return null;
            }
        }		
        
        return file;
    }	
}
