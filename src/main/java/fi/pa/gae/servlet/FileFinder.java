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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author hobbes
 */
public class FileFinder {
    
    private final File base;
    private Map<String, File> fileMap;
    
    public FileFinder(String confDir, String appProperties) throws FileNotFoundException, IOException {
        
        String baseDir = null;
        String keyMap = null;
        
        if (!confDir.endsWith(File.separator)) {
            confDir += File.separator;
        }
        File apf = new File(confDir + appProperties);
        try (Reader r = new FileReader(apf)) {
            Properties p = new Properties();
            p.load(r);
            
            baseDir = p.getProperty("basedir");
            keyMap = p.getProperty("viewlist");
        }
   
        this.base = new File(baseDir);
        
        if (!(this.base.exists() && this.base.isDirectory())) {
            throw new FileNotFoundException("Directory " + this.base.getAbsolutePath() + " does not exist");
        }
        
        File pf = new File(confDir + keyMap);
        try (Reader r = new FileReader(pf)) {
            Properties p = new Properties();
            p.load(r);
            
            this.fileMap = new HashMap<> (p.size());
            for (String k : p.stringPropertyNames()) {
                // Luo file, tarkista että se on ja jos ok, lisää mappiin. Muuton poikkeus
                String fn = p.getProperty(k);
                if (!fn.startsWith(File.separator)) {
                    fn = File.separator + fn;
                }
                File f = new File(this.base + fn);
                if (f.exists() &&  f.canRead()) {
                    this.fileMap.put(k, f);
                }
                else {
                    throw new FileNotFoundException("File " + f.getPath() + " does not exist");
                }
            }
        }
      
    }
    
    public File find(String key) throws FileNotFoundException {

        System.out.println(key);
        
        File f = this.fileMap.get(key);
        if (f!= null && f.exists()) {
            return f;
        }
        else {
            if (key.startsWith(File.separator)) {
                key = key.substring(File.separator.length());
            }
            f = new File( key);
            if (f.exists() && f.isFile()) {
                return f;
            }
            return null;
 //           throw new FileNotFoundException();
        }
    }
}
