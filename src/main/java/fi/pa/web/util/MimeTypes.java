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
package fi.pa.web.util;

import java.util.HashMap;

/**
 *
 * @author hobbes
 */
public enum MimeTypes {
    audio_aac ("audio/aac", ".aac"),    //	AAC audio file	
    application_x_abiword ("application/x-abiword", ".abw"), // AbiWord document	
    application_octet_stream ("application/octet-stream", ".arc", ".bin"),  	
    video_x_msvideo ("video/x-msvideo", ".avi"),    //	AVI: Audio Video Interleave	video/x-msvideo
    application_vnd_amazon_ebook ("application/vnd.amazon.ebook", ".azw"), //	Amazon Kindle eBook format	application/vnd.amazon.ebook
    application_x_bzip ("application/x-bzip", ".bz"), //	BZip archive	application/x-bzip
    application_x_bzip2 ("application/x-bzip2", ".bz2"), //	BZip2 archive	
    application_x_csh ("application/x-csh", ".csh"), //	C-Shell script	application/x-csh
    text_css ("text/css", ".css"),	// Cascading Style Sheets (CSS)	text/css
    text_csv ("text/csv", ".csv"),  //	Comma-separated values (CSV)	text/csv
    application_msword ("application/msword", ".doc"), //	Microsoft Word	application/msword
    application_vnd_ms_fontobject ("application/vnd.ms-fontobject", ".eot"), //	MS Embedded OpenType fonts	application/vnd.ms-fontobject
    application_epub_zip ("application/epub+zip", ".epub"), //	Electronic publication (EPUB)	application/epub+zip
    image_gif ("image/gif", ".gif"),    //	Graphics Interchange Format (GIF)	
    text_html ("text/html", ".htm", ".html"), //	HyperText Markup Language (HTML)	
    image_x_icon ("image/x-icon", ".ico"),      //	Icon format	
    text_calendar ("text/calendar", ".ics"),    //	iCalendar format	
    application_java_archive ("application/java-archive", ".jar"), //	Java Archive (JAR)	
    image_jpeg ("image/jpeg", ".jpeg", ".jpg"),	// JPEG images	
    application_javascript ("application/javascript", ".js"), //	JavaScript (ECMAScript)	
    application_json ("application/json", ".json"), //	JSON format	
    audio_midi ("audio/midi", ".mid", ".midi"), //	Musical Instrument Digital Interface (MIDI)	
    video_mpeg ("video/mpeg", ".mpeg"),	// MPEG Video	
    application_vnd_apple_installer_xml ("application/vnd.apple.installer+xml", ".mpkg"), //	Apple Installer Package	
    application_vnd_oasis_opendocument_presentation ("application/vnd.oasis.opendocument.presentation", ".odp"), //	OpenDocument presentation document	
    application_vnd_oasis_opendocument_spreadsheet ("application/vnd.oasis.opendocument.spreadsheet", ".ods"), //	OpenDocument spreadsheet document	
    application_vnd_oasis_opendocument_text ("application/vnd.oasis.opendocument.text", ".odt"), //	OpenDocument text document	
    audio_ogg ("audio/ogg", ".oga"), //	OGG audio	
    video_ogg ("video/ogg", ".ogv"), //	OGG video	
    application_ogg ("application/ogg", ".ogx"), //	OGG	
    font_otf ("font/otf", ".otf"), //	OpenType font	font/otf
    image_png ("image/png", ".png"), //	Portable Network Graphics	
    application_pdf ("application/pdf", ".pdf"),    //	Adobe Portable Document Format (PDF)	
    application_vnd_ms_powerpoint ("application/vnd.ms-powerpoint", ".ppt"), //	Microsoft PowerPoint	
    application_x_rar_compressed ("application/x-rar-compressed", ".rar"),  //	RAR archive	application/x-rar-compressed
    application_rtf ("application/rtf", ".rtf"), //	Rich Text Format (RTF)	
    application_x_sh ("application/x-sh", ".sh"), //	Bourne shell script	
    image_svg_xml ("image/svg+xml", ".svg"),    //	Scalable Vector Graphics (SVG)	
    application_x_shockwave_flash ("application/x-shockwave-flash", ".swf"),    //	Small web format (SWF) or Adobe Flash document	
    application_x_tar ("application/x-tar", ".tar"),    //	Tape Archive (TAR)	
    image_tiff ("image/tiff", ".tif", ".tiff"), //	Tagged Image File Format (TIFF)	
    application_typescript ("application/typescript", ".ts"),   //	Typescript file	
    font_ttf ("font/ttf", ".ttf"),  //	TrueType Font	
    application_vnd_visio ("application/vnd.visio", ".vsd"),    // 	Microsoft Visio	
    audio_x_wav ("audio/x-wav", ".wav"),    //	Waveform Audio Format	
    audio_webm ("audio/webm", ".weba"), //	WEBM audio	
    video_webm ("video/webm", ".webm"), //	WEBM video	
    image_webp ("image/webp", ".webp"), //	WEBP image	
    font_woff ("font/woff", ".woff"),   // 	Web Open Font Format (WOFF)	
    font_woff2 ("font/woff2", ".woff2"),    //	Web Open Font Format (WOFF)	
    application_xhtml_xml ("application/xhtml+xml", ".xhtml"),  //	XHTML	
    application_vnd_ms_excel ("application/vnd.ms-excel", ".xls", ".xlsx"), //	Microsoft Excel	+ application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
    application_xml ("application/xml", ".xml"),    //	XML	
    application_vnd_mozilla_xul_xml ("application/vnd.mozilla.xul+xml", ".xul"), //	XUL	
    application_zip("application/zip", ".zip"), //	ZIP archive	
    video_3gpp ("video/3gpp", ".3gp"), //	3GPP audio/video container + audio/3gpp if it doesn't contain video
    video_3gpp2 ("video/3gpp2", ".3g2"),    //	3GPP2 audio/video container+  audio/3gpp2 if it doesn't contain video
    application_x_7z_compressed ("application/x-7z-compressed", ".7z"); // 	7-zip archive	
    
    private String mimeType;
    private String[] ext;
    
    private static HashMap<String, MimeTypes> byExt = null;
    
    private MimeTypes(String mime, String...ext) {
        this.mimeType = mime;
        this.ext = new String[ext.length];
        for (int i = 0; i < ext.length; ++i) {
            this.ext[i] = ext[i];
        }
    }
    
    public static MimeTypes byExtension(String ext) {
        if (MimeTypes.byExt == null) {
            MimeTypes.byExt = new HashMap<>();
            
            for (MimeTypes m : MimeTypes.values()) {
                for (String e : m.ext) {
                    MimeTypes.byExt.put(e, m);
                }
            }
        }
        return MimeTypes.byExt.get(ext.toLowerCase());
    }
}
