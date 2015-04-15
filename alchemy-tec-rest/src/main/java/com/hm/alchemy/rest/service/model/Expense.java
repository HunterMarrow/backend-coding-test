/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Expense.java
 *
 * Copyright 2015 Hunter Marrow (PTY) Ltd. All rights reserved.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package com.hm.alchemy.rest.service.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidParameterException;
import java.util.Date;

import com.hm.alchemy.rest.service.repo.DatabaseField;

public class Expense implements IModel {
	private Long id;
	private Date dateCaptured; 
	private BigDecimal amount; 
	private String reason; 
	private BigDecimal vat; 
	
	@Override
	public int compareTo(IModel o) {
		return id.compareTo(o.getId());
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@DatabaseField(name="date_captured")
	public Date getDateCaptured() {
		return dateCaptured;
	}

	public void setDateCaptured(Date dateCaptured) {
		this.dateCaptured = dateCaptured;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) throws Exception {
		if (amount == null) {
			throw new NullPointerException("The amount parameter may not be null");
		}
		
		if (amount.doubleValue() == 0) {
			throw new InvalidParameterException("The amount parameter may not be zero");
		}
		
		this.amount = amount;		
				
		setVat(amount.multiply(BigDecimal.valueOf(20)).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@DatabaseField(name="vat_amount")
	public BigDecimal getVat() {
		return vat;
	}

	private void setVat(BigDecimal vat) {
		this.vat = vat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result
				+ ((dateCaptured == null) ? 0 : dateCaptured.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result + ((vat == null) ? 0 : vat.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Expense other = (Expense) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (dateCaptured == null) {
			if (other.dateCaptured != null)
				return false;
		} else if (!dateCaptured.equals(other.dateCaptured))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (vat == null) {
			if (other.vat != null)
				return false;
		} else if (!vat.equals(other.vat))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Expense [id=" + id + ", dateCaptured=" + dateCaptured
				+ ", amount=" + amount + ", reason=" + reason + ", vat=" + vat
				+ "]";
	} 	
	
}
