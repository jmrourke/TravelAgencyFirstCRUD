package main.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import main.dao.TourDAO;
import main.model.Tour;

@Controller
public class HomeController {

	@Autowired
	private TourDAO tourDAO;
	
	@RequestMapping("/")
	public String getHome() {
		return "home";
	}
	
	@GetMapping("/addTour")
	public String showForm(Model model) {
		model.addAttribute("tour", new Tour());
		return "form";
	}
	
	@PostMapping("/processForm")
	public String showTourData(@Valid @ModelAttribute Tour tour, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "form";
		}
		tourDAO.saveOrUpdate(tour);
		return "redirect:showOffer";
	}
	
	@GetMapping("/showOffer")
	public String getTours(Model model) {
		List<Tour> tours = tourDAO.getAll();
		model.addAttribute("tours", tours);
		return "tours";
	}
	
	@GetMapping("/deleteTour/{id}")
	public String deleteTour(@PathVariable int id) {
		Tour tour = tourDAO.getById(id);
		if(tour != null) {
			tourDAO.delete(id);
		}
		return "redirect:/showOffer";
	}
	
	@GetMapping("/editTour/{id}")
	public String editTour(@PathVariable int id, Model model) {
		Tour tour = tourDAO.getById(id);
		if(tour != null) {
			model.addAttribute("tour", tour);
			return "form";
		}
		return "redirect:/showOffer";
	}
}
