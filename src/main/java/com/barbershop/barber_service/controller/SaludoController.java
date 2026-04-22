package com.barbershop.barber_service.controller;

import com.barbershop.barber_service.service.SaludoLangChainService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/saludo")
@CrossOrigin
public class SaludoController {

	private final SaludoLangChainService saludoLangChainService;

	public SaludoController(SaludoLangChainService saludoLangChainService) {
		this.saludoLangChainService = saludoLangChainService;
	}

	@GetMapping("/inicio")
	public String obtenerSaludoInicio(@RequestParam(defaultValue = "cliente") String nombre) {
		return saludoLangChainService.generarSaludoInicio(nombre);
	}
}

