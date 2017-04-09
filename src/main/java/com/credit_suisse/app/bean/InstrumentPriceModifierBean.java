package com.credit_suisse.app.bean;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.credit_suisse.app.dao.InstrumentPriceModifierDao;
import com.credit_suisse.app.domain.InstrumentPriceModifier;

@Component
@ManagedBean
@SessionScoped
public class InstrumentPriceModifierBean {
	private static final Logger logger = LoggerFactory.getLogger(InstrumentPriceModifierBean.class);

	@Autowired
	InstrumentPriceModifierDao instrumentPriceModifierDao;

	public void setInstrumentPriceModifierDao(InstrumentPriceModifierDao instrumentPriceModifierDao) {
		this.instrumentPriceModifierDao = instrumentPriceModifierDao;
	}
	
	public List<InstrumentPriceModifier> getModifiers() {
		return modifiers;
	}

	public void setModifiers(List<InstrumentPriceModifier> modifiers) {
		this.modifiers = modifiers;
	}

	private List<InstrumentPriceModifier> modifiers;

    @PostConstruct
    public void setup()  {
		modifiers = instrumentPriceModifierDao.findAll();

		logger.debug(Arrays.toString(modifiers.toArray()));
   }
}