package com.solution.fromVC.store.web;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Locale;

/**
 * Created by Влад on 22.11.2016.
 */
@Named(value = "localeBean")
@SessionScoped
public class LocaleBean implements Serializable{

    private static final long serialVersionUID = -4341604693599426607L;

    public LocaleBean() {
    }

    private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

    public Locale getLocale() {
        return locale;
    }

    public String getLanguage(){
        return locale.getLanguage();
    }

    public void setLanguage(String language){
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
}
