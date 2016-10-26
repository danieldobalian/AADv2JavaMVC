package com.danieldobalian.controller;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.model.Verb;

public class AADv2Api extends DefaultApi20{

	private static final String AUTHORIZE_URL = "https://login.microsoftonline.com/common/oauth2/v2.0/authorize";
	private static final String ACCESS_TOKEN_URL = "https://login.microsoftonline.com/common/oauth2/v2.0/token";

	public AADv2Api() { }

	private static class InstanceHolder {
		private static final AADv2Api INSTANCE = new AADv2Api();
	}

	public static AADv2Api instance() {
	    return InstanceHolder.INSTANCE;
	}

	@Override
	public Verb getAccessTokenVerb() {
	    return Verb.POST;
	}

	@Override
	public String getAccessTokenEndpoint() {
		return ACCESS_TOKEN_URL;
	}

	@Override
	public String getAuthorizationBaseUrl() {
		return AUTHORIZE_URL;
	}

}
