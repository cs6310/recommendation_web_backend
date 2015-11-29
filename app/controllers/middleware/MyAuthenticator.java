package controllers.middleware;

import controllers.routes;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public class MyAuthenticator extends Security.Authenticator {

	@Override
	public String getUsername(Context ctx) {
		return ctx.session().get("id");
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		return redirect(routes.Application.login());
	}

}
