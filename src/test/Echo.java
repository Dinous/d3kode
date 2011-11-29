/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package test;

import java.io.File;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "mTrace")
public class Echo extends ActionSupport {

    private File myUploadFile;
    private static final long serialVersionUID = 7968544374444173511L;
    

    @Action(value = "/echo", results = { @Result(location = "echo.jsp", name = "success") })
    public String execute() throws Exception {

	System.out.println("Echo");
	System.out.println("myUploadFile : " + myUploadFile);
	return SUCCESS;
    }
    public void setMyUploadFile(File myUploadFile) {
	this.myUploadFile = myUploadFile;
    }
    public File getMyUploadFile() {
	return myUploadFile;
    }
}
