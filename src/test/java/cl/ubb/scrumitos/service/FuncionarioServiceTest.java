package cl.ubb.scrumitos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.ubb.scrumitos.exceptions.FuncionarioAlreadyExistsException;
import cl.ubb.scrumitos.exceptions.ProductNotFoundException;
import cl.ubb.scrumitos.model.Funcionario;
import cl.ubb.scrumitos.repository.FuncionarioRepository;

@ExtendWith(MockitoExtension.class)
class FuncionarioServiceTest {

	@Mock
	private FuncionarioRepository funcionarioRepo;
	
	@InjectMocks
	private FuncionarioService funcService;
		
	
	
	//Caso Exitoso
	@Test 
	void siSeRequiereIngresarUnNuevoFuncionarioYlosCamposEstanCompletosEntoncesSeAgrega() throws FuncionarioAlreadyExistsException{ 
	
		Funcionario funcionario = new Funcionario("19289859-7", "Javier", "Saavedra", "vendedor","+56985997895","j.Saavedra@gmail.com",1);
		
		//act
		funcService.agregar(funcionario);
		
		//assert
		
		assertNotNull(funcionario);
		assertAll("funcionario",
			() -> assertEquals("19289859-7",funcionario.getRun()),
			() -> assertEquals("Javier",funcionario.getNombre()),
			() -> assertEquals("Saavedra",funcionario.getApellido()),
			() -> assertEquals("vendedor",funcionario.getCargo()),
			() -> assertEquals("+56985997895",funcionario.getTelefono()),
			() -> assertEquals("j.Saavedra@gmail.com",funcionario.getEmail()),
			() -> assertEquals(1,funcionario.getIdFuncionario())
		);
		verify(funcionarioRepo, times(1)).save(funcionario);
		
	}
	//Caso fallido
	
	// para ingresar se debe buscar si el id ya esta en la lista, si esta se lanza una excepcion ya que no se puede ingresar si ya existe
	@Test
	void siSeIngresaUnNuevoPersonalYEsteYaExisteEntoncesNoSePodraIngresarYLanzaraUnaExcepcion() throws FuncionarioAlreadyExistsException {
		
		Funcionario funcionario = new Funcionario("19289859-7", "Javier", "Saavedra", "vendedor","+56985997895","j.Saavedra@gmail.com",1);
		when(funcionarioRepo.findById(1)).thenReturn(funcionario);
		
		
		assertThrows(FuncionarioAlreadyExistsException.class, () -> 
		funcService.searchFuncionario(1));
		
	}
	
	
	
}

	