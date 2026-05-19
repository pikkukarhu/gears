/*
 * Copyright 2017 hobbes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fi.pa.gae.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hobbes
 */
@WebServlet(name = "AssetServlet", value = "/asset/*") 
public class AssetServlet  extends HttpServlet {

	private static final long serialVersionUID = -4771491523499643648L;
	private final int BUFFSZ = 65536;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        
        String fn = request.getPathInfo();
        if (fn.startsWith("/")) {
            fn = fn.substring(1);
        }
        try {
            File f = new File(fn);
 /*           response.setContentType(
                MimetypesFileTypeMap
                .getDefaultFileTypeMap()
                .getContentType(f.getName()));*/
            
            
            if (f.getName().endsWith(".js")) {
                response.setContentType("application/javascript");
            }
            else if (f.getName().endsWith(".css")) {
                response.setContentType("text/css");
            }
            
            OutputStream out = response.getOutputStream();
            
        
            /*
                MimetypesFileTypeMap
                .getDefaultFileTypeMap()
                .getContentType(f.getName()));*/
             
            try (InputStream in = new FileInputStream(f)) {
                byte[] buff = new byte[BUFFSZ];
                int n;
                while ((n = in.read(buff)) != -1) {
                    out.write(buff, 0, n);
                }
            }
            catch (FileNotFoundException ex) {
               response.setContentType("text/html");
               response.setStatus(HttpServletResponse.SC_NOT_FOUND);
               out.write(("<h1>Resource " + request.getServletPath() + request.getPathInfo() + " not in server</h1>").getBytes());
            }
            catch (IOException ex) {
                Logger.getLogger(AssetServlet.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                out.write(("<h1>Error reading resource " + request.getServletPath() + request.getPathInfo()).getBytes());
           }  
        }
        catch (IOException ex) {
            Logger.getLogger(AssetServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }
    }
	
}
