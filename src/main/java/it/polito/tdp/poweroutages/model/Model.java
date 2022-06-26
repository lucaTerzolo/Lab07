package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	private PowerOutageDAO dao;
	private List<PowerOutage> eventi;
	
	private List<PowerOutage> best;
	private int maxAffected;
	private long maxDuration;
	
	public Model() {
		dao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return dao.getNercList();
	}

	public List<PowerOutage> getPowerOutage(){
		List<PowerOutage> list=new ArrayList<>(this.getPowerOutage());
		System.out.println(list);
		return this.getPowerOutage();
	}
	
	public List<PowerOutage> simula (Nerc nercSelezionato, int x, int y) {
		eventi=new ArrayList<>(dao.getPowerOutagesByNerc(nercSelezionato));
		this.maxAffected=0;
		
		cerca(new ArrayList<PowerOutage>(), x, y);
		
		return this.best;
	}

	private void cerca(ArrayList<PowerOutage> parziale, int maxAnni, int maxOre) {
		if(this.sumAffectedPeople(parziale)>this.maxAffected) {
			this.maxAffected=this.sumAffectedPeople(parziale);
			best=new ArrayList<>(parziale);
		}
		
		for(PowerOutage p:this.eventi) {
			
			if(!parziale.contains(p)) {
				parziale.add(p);
				//Controllo soluzione valida
				if(dataCorretta(parziale,maxAnni) && oreCorrette(parziale,maxOre)) {
					cerca(parziale,maxAnni,maxOre);
				}
				parziale.remove(parziale.size()-1);
			}
		}
		
	}
	
	private boolean oreCorrette(ArrayList<PowerOutage> parziale, int MaxOre ) {
		int sum=this.sumHourOutage(parziale);
		if(sum>MaxOre)
			return false;
		else
			return true;
	}

	private int sumHourOutage(ArrayList<PowerOutage> parziale) {
		int sum=0;
		for(PowerOutage p:parziale) {
			LocalDateTime tempDateTime = LocalDateTime.from(p.starting_date);
			long outageDuration = tempDateTime.until(p.finish_date, ChronoUnit.HOURS);
			sum+=outageDuration;
		}
		return sum;
	}

	private boolean dataCorretta(ArrayList<PowerOutage> parziale, int maxAnni) {
		if(parziale.size()>1) {
			int anno1=parziale.get(1).getYear();
			int anno2=parziale.get(parziale.size()-1).getYear();
			if(anno2-anno1>maxAnni)
				return false;
		}
		return true;
	}

	private int sumAffectedPeople(ArrayList<PowerOutage> parziale) {
		int somma=0;
		for(PowerOutage p: parziale) {
			somma+=p.costumers_affected;
		}
		return somma;
	}
	
	public int getMaxPeopleAffected() {
		return this.maxAffected;
	}
	
	public long getMaxDuration() {
		int sum=0;
		for(PowerOutage p: this.best)
			sum+=p.durata;
		return sum;
	}
}
