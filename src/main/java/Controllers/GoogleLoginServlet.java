/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.RegisterDAO;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author tvhun
 */
@WebServlet(name = "GoogleLoginServlet", urlPatterns = {"/GoogleLoginServlet"})
public class GoogleLoginServlet extends HttpServlet {

    private static final String CLIENT_ID = "602177457145-hq560vlldmpkad94eo4ssso6qnsvbk8e.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    private static final String REDIRECT_URI = "http://localhost:8080/GoogleLoginServlet";

    private final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");

        if (code != null) {
            GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    jsonFactory,
                    "https://oauth2.googleapis.com/token",
                    CLIENT_ID,
                    CLIENT_SECRET,
                    code,
                    REDIRECT_URI
            ).execute();

            GoogleIdToken idToken = tokenResponse.parseIdToken();
            GoogleIdToken.Payload payload = idToken.getPayload();

            String email = payload.getEmail();
            String name = (String) payload.get("name");

            RegisterDAO registerDAO = new RegisterDAO();

            request.getSession().invalidate();
            request.getSession(true);

            if (registerDAO.isEmailExists(email)) {
                request.getSession().setAttribute("email", email);
                response.sendRedirect("navbar.jsp");
            } else {
                request.getSession().setAttribute("email", email);
                request.getSession().setAttribute("name", name);
                response.sendRedirect("RegisterByGoogle");
            }

        } else {
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    new NetHttpTransport(),
                    jsonFactory,
                    CLIENT_ID,
                    CLIENT_SECRET,
                    java.util.Collections.singletonList("email")
            ).setAccessType("offline").build();

            String authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
            response.sendRedirect(authorizationUrl);
        }
    }
}
