/**
 * Android Micro Web Server
 * Copyright (C) 2011  ScR4tCh
 * Contact scr4tch@scr4tch.org

 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Lesser GNU General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package org.scratch.microwebserver.http.services;

import java.sql.SQLException;

import org.json.scratch.fork.SJSONException;
import org.json.scratch.fork.SJSONObject;
import org.scratch.microwebserver.data.DatabaseManagerException;
import org.scratch.microwebserver.http.BasicWebService;
import org.scratch.microwebserver.http.WebConnection;
import org.scratch.microwebserver.http.WebServiceException;
import org.scratch.microwebserver.http.WebServiceReply;
import org.scratch.microwebserver.http.WebServices;


public class LogOutService extends BasicWebService
{

	public LogOutService() throws DatabaseManagerException
	{
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean acceptsMethod(int method)
	{
		if(method==WebServices.METHOD_POST)
			return true;
		
		return false;
	}

	@Override
	public boolean acceptsMime(String mime)
	{
		if(mime!=null && mime.equals(MIME_JSON))
			return true;
		
		return false;
	}

	@Override
	public String getMime()
	{
		return MIME_JSON;
	}

	@Override
	public WebServiceReply invoke(String[] ppc,int method,String mime,WebConnection wb) throws WebServiceException
	{
		try
		{
			SJSONObject json=new SJSONObject(readStream(wb).toString());
			if(json.has("token"))
			{
				String token=json.getString("token");
				
				database.invalidateToken(token);	
			}
			else
			{
				throw new WebServiceException(400,"Bad Request!");
			}
		}catch(SJSONException je)
		 {
			throw new WebServiceException(400,"Bad Request!");
		 }
		catch(SQLException e)
		{
			throw new WebServiceException(500,"Internal Server Error!");
		}
		
		WebServiceReply rret = new WebServiceReply();
		rret.setMime(MIME_JSON);
		rret.setDate(System.currentTimeMillis());
		
		return rret;
	}

	@Override
	public String getUri()
	{
		return "logOut";
	}

}
