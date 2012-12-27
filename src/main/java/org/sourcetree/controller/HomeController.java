/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * HomeController.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Venkaiah Chowdary Koneru
 */
@Controller
public class HomeController extends BaseController
{
	@RequestMapping(value = "/home")
	String viewHome(Model model)
	{
		return "home";
	}
}
