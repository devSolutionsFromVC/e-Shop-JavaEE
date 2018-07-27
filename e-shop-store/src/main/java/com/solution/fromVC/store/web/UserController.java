package com.solution.fromVC.store.web;

import com.solution.fromVC.entities.Groups;
import com.solution.fromVC.entities.Person;
import com.solution.fromVC.store.ejb.UserBean;
import com.solution.fromVC.store.qualifiers.LoggedIn;
import com.solution.fromVC.store.web.util.JsfUtil;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Влад on 22.11.2016.
 */
@Named(value = "userController")
@SessionScoped
public class UserController implements Serializable{

    private static final String BUNDLE = "bundles.Bundle";
    private static final long serialVersionUID = -8851462237612818158L;

    private Person user;
    @EJB
    private UserBean ejbFacade;
    private String username;
    private String password;
    @Inject
    private CustomerController customerController;

    public UserController() {
    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String result;

        try {
            request.login(this.getUsername(), this.getPassword());

            JsfUtil.addSuccessMessage(JsfUtil.getStringFromBundle(BUNDLE, "Login_Success"));

            this.user = ejbFacade.getUserByEmail(getUsername());
            this.getAuthenticatedUser();

            if (isAdmin()) {
                result = "/admin/index";
            } else {
                result = "/index";
            }

        } catch (ServletException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage(JsfUtil.getStringFromBundle(BUNDLE, "Login_Failed"));

            result = "login";
        }

        return result;
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            this.user = null;

            request.logout();
            ((HttpSession) context.getExternalContext().getSession(false)).invalidate();

            JsfUtil.addSuccessMessage(JsfUtil.getStringFromBundle(BUNDLE, "Logout_Success"));

        } catch (ServletException ex) {

            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage(JsfUtil.getStringFromBundle(BUNDLE, "Logout_Failed"));
        } finally {
            return "/index";
        }
    }

    public UserBean getEjbFacade() {
        return ejbFacade;
    }

    public @Produces
    @LoggedIn
    Person getAuthenticatedUser() {
        return user;
    }

    public boolean isLogged() {
        if (FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal() != null) {
            user = ejbFacade.getUserByEmail(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().toString());
            return true;
        } else {
            return false;
        }
    }

    public boolean isAdmin() {
        for (Groups g : user.getGroupsList()) {
            if (g.getName().equals("ADMINS")) {
                return true;
            }
        }
        return false;
    }

    public String goAdmin() {
        if (isAdmin()) {
            return "/admin/index";
        } else {
            return "index";
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Person getUser() {
        return user;
    }
}
