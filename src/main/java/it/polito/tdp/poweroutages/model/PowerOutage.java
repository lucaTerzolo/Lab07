package it.polito.tdp.poweroutages.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PowerOutage {

	int nerc_id;
	int costumers_affected;
	long durata;
	LocalDateTime starting_date;
	LocalDateTime finish_date;
	
	public PowerOutage(int nerc_id, int l, LocalDateTime date,
			LocalDateTime date2) {
		
		this.nerc_id = nerc_id;
		this.costumers_affected = l;
		this.starting_date = date;
		this.finish_date = date2;
		durata=getDurata();
	}

	@Override
	public String toString() {
		return starting_date.getYear()+" "+starting_date+" "+finish_date+" "
	+durata+" "+costumers_affected;
	}
	
	public int getYear() {
		return starting_date.getYear();
	}
	
	private long getDurata() {
		LocalDateTime tempDateTime = LocalDateTime.from(starting_date);
		return tempDateTime.until(finish_date, ChronoUnit.HOURS);
	}
	
}
