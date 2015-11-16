package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {
    
    public static Result index() {
        return ok(views.html.index.render("Software Architecture and Design CS6310 Proj 4"));
    }
    
    public static Result showHome() {
        return ok(views.html.home.render("Software Architecture and Design"));
    }
    
}
