package cl.ubb.scrumitos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.ubb.scrumitos.exceptions.FuncionarioAlreadyExistsException;
import cl.ubb.scrumitos.model.Funcionario;
import cl.ubb.scrumitos.service.FuncionarioService;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
	
	
	private final FuncionarioService funcionarioService;
	
	@Autowired
	 public FuncionarioController(FuncionarioService funcionarioService) {
		this.funcionarioService= funcionarioService;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void guardarFuncionario(@RequestBody Funcionario funcionario) throws FuncionarioAlreadyExistsException {
		funcionarioService.agregar(funcionario);
	
	}

}
