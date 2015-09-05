package com.hilli.retrievehours;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

class FileInfo {
	String path = null;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFirstEntry() {
		return firstEntry;
	}

	public void setFirstEntry(String firstEntry) {
		this.firstEntry = firstEntry;
	}

	public String getPersonalnote() {
		return personalnote;
	}

	public void setPersonalnote(String personalnote) {
		this.personalnote = personalnote;
	}

	String firstEntry = null;
	String personalnote = "This is a fileinfo of Martin: <br/>";
	
	String theimageasString="";
	
	byte[] theimage = null;

	public FileInfo(String path) {
		this.path = path;
		firstEntry = "Some new text";
		FileInputStream fileInputStream = null;

		File file = new File(path);

		theimage = new byte[(int) file.length()];
		try {
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(theimage);
			fileInputStream.close();
		} catch (Exception e) {
			personalnote = e.getMessage();
		}
		
		theimageasString =  Base64.getEncoder().encodeToString(theimage);
	}
}

/**
 * Servlet implementation class Filestructure
 */
@WebServlet("/Filestructure")
public class Filestructure extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Filestructure() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");

		Gson gson = new Gson();
		JsonObject myObj = new JsonObject();

		FileInfo fileInfo = new FileInfo("D:\\dev\\2015_javatryouts\\ResizeImages\\tst\\test1_head.jpg");
		// Country countryInfo =getInfo(fileInfo);
		JsonElement fileObj = gson.toJsonTree(fileInfo);
		if (fileInfo.getFirstEntry() == null) {
			myObj.addProperty("success", false);
		} else {
			myObj.addProperty("success", true);
		}
		myObj.add("filestructure", fileObj);
		out.println(myObj.toString());

		out.close();

	}

}
