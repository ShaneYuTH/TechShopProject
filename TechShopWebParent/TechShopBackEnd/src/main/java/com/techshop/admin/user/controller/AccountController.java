package com.techshop.admin.user.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techshop.admin.FileUploadUtil;
import com.techshop.admin.security.TechShopUserDetails;
import com.techshop.admin.user.UserService;
import com.techshop.common.entity.User;

/**
 * The Class AccountController for the upper right logged-in user update function.
 */
@Controller
public class AccountController {

	/** The UserService object service. */
	@Autowired
	private UserService service;
	
	/**
	 * Get the user update form.
	 *
	 * @param loggedUser The logged user
	 * @param model The model
	 * @return The string address to the account_form file
	 */
	@GetMapping("/account")
	public String viewDetails(@AuthenticationPrincipal TechShopUserDetails loggedUser, 
			Model model) {
		String email = loggedUser.getUsername();
		User user = service.getByEmail(email);
		model.addAttribute("user", user);
		
		return "users/account_form";
	}
	
	/**
	 * Save user's update details.
	 *
	 * @param user The user
	 * @param redirectAttributes The redirect attributes
	 * @param loggedUser The logged user
	 * @param multipartFile The multipart file: user's image
	 * @return The string address to the account_form file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@PostMapping("/account/update")
	public String saveDetails(User user, RedirectAttributes redirectAttributes, 
			@AuthenticationPrincipal TechShopUserDetails loggedUser,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {

		// If user has existing image
		if (!multipartFile.isEmpty()) {
			// Get clean path of the photo
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);
			
			//update user details
			User savedUser = service.updateAccount(user);
			
			String uploadDir = "user-photos/" + savedUser.getId();
			
			// Remove previous image folder, then update with new folder/file
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			
		} else {
			if (user.getPhotos().isEmpty()) {
				user.setPhotos(null);
			}
			//update
			service.updateAccount(user);
		}
		
		// Update showed name on the upper right
		loggedUser.setFirstName(user.getFirstName());
		loggedUser.setLastName(user.getLastName());
		
		redirectAttributes.addFlashAttribute("message", "Your account details have been updated.");
		
		return "redirect:/account";
	}
}
