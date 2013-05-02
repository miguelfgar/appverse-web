package org.appverse.web.framework.frontend.gwt.commands.impl.live;

import org.appverse.web.framework.backend.api.model.presentation.AuthorizationDataVO;
import org.appverse.web.framework.frontend.gwt.callback.AppverseCallback;
import org.appverse.web.framework.frontend.gwt.commands.AuthenticationHandler;

public class AuthenticationCommandRestImpl implements AuthenticationHandler {

	@Override
	public void onAuthenticate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAuthenticatePrincipal(String username, String password,
			AppverseCallback<AuthorizationDataVO> callback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetXSRFSessionToken() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onIsPrincipalAuthenticated(AppverseCallback<Boolean> callback) {
		// TODO Auto-generated method stub

	}

}
