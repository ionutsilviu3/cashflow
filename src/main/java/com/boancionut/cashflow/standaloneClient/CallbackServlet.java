package com.boancionut.cashflow.standaloneClient;

import com.boancionut.cashflow.ejb.model.User;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;

@WebServlet("/callback")
public class CallbackServlet extends HttpServlet {
    private static final String CLIENT_SECRET_FILE = "/client_secret.json";
    private static final String REDIRECT_URI = "http://localhost:8080/CashFlow-1.0-SNAPSHOT/callback";

    @Inject
    private UserBean userBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");

        if (code != null) {
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                    JacksonFactory.getDefaultInstance(),
                    new InputStreamReader(CallbackServlet.class.getResourceAsStream(CLIENT_SECRET_FILE))
            );

            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance(),
                    clientSecrets,
                    Collections.singletonList("https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email")
            ).setAccessType("offline").build();

            TokenResponse tokenResponse = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
            HttpRequestInitializer credential = flow.createAndStoreCredential(tokenResponse, null);

            Oauth2 oauth2 = new Oauth2.Builder(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance(),
                    credential
            ).setApplicationName("CashFlow").build();

            Userinfo userInfo = oauth2.userinfo().get().execute();
            System.out.println("User Info JSON: " + userInfo.toPrettyString());

            String email = userInfo.getEmail();
            if (email == null) {
                resp.getWriter().write("Error: Email not found in user info. Check consent and scopes.");
                return;
            }

            User user = new User();
            user.setEmail(email);
            user.setName(userInfo.getName());
            userBean.setUser(user);

            resp.getWriter().write("Login successful! User: " + email);
            resp.sendRedirect("index.xhtml");
        } else {
            resp.getWriter().write("Error: No code parameter found in the callback request.");
        }
    }
}

