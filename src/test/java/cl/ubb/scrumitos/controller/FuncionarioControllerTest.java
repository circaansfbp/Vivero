package cl.ubb.scrumitos.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.AbstractJsonMarshalTester;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.ubb.scrumitos.exceptions.FuncionarioAlreadyExistsException;

import cl.ubb.scrumitos.model.Funcionario;

import cl.ubb.scrumitos.service.FuncionarioService;


@ExtendWith(MockitoExtension.class)
class FuncionarioControllerTest {

	private MockMvc mockMvc;
	
	@Mock
	private FuncionarioService funcService;
	
	@InjectMocks
	private FuncionarioController funcionarioController;
	
	  // This object will be magically initialized by the initFields method below.
    private JacksonTester<Funcionario> jsonFuncionario;
	
	@BeforeEach
	void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
		mockMvc = MockMvcBuilders.standaloneSetup(funcionarioController)
				.build();	
	}
	
	
	@Test
	void AlHacerClickEnELBotonParaAgregarUnNuevoFuncionarioSeDebeAgregarConExito() throws Exception {
		
		Funcionario funcionario = new Funcionario("19289859-7", "Javier", "Saavedra", "vendedor","+56985997895","j.Saavedra@gmail.com",1);
		MockHttpServletResponse response = mockMvc.perform(
		 post("/funcionarios").contentType(MediaType.APPLICATION_JSON)
		 .content(jsonFuncionario.write(funcionario).getJson()))
				.andReturn().getResponse();
		// then
		 assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
	}
	
	
}
