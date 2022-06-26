/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;
import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmbNerc"
    private ComboBox<Nerc> cmbNerc; // Value injected by FXMLLoader

    @FXML // fx:id="txtYears"
    private TextField txtYears; // Value injected by FXMLLoader

    @FXML // fx:id="txtHours"
    private TextField txtHours; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    private Model model;
    
	

    @FXML
    void doRun(ActionEvent event) {
    	txtResult.clear();
    	int x=0;
    	int y=0;
    	try {
    		x=Integer.parseInt(this.txtYears.getText());
    		y=Integer.parseInt(this.txtHours.getText());
    	}catch(NumberFormatException e) {
    		e.printStackTrace();
    	}
    	Nerc nercSelezionato=this.cmbNerc.getValue();
    	
    	
    	List<PowerOutage> eventi=this.model.simula(nercSelezionato, x, y);
    	this.txtResult.setText("Tot people affected: "+this.model.getMaxPeopleAffected()
    	+"\nTot hours of outage: "+this.model.getMaxDuration()+"\n");
    	for(PowerOutage p:eventi)
    		this.txtResult.appendText(p+"\n");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbNerc != null : "fx:id=\"cmbNerc\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
        List<Nerc> nercList=PowerOutageDAO.getNercList();
        for(int i=0;i<nercList.size();i++)
        	cmbNerc.getItems().add(nercList.get(i));
        // Utilizzare questo font per incolonnare correttamente i dati;
        txtResult.setStyle("-fx-font-family: monospace");
    }
    
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
