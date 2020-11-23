package cl.ubb.scrumitos.controller;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.ubb.scrumitos.exceptions.ProductNotFoundException;
import cl.ubb.scrumitos.model.Producto;
import cl.ubb.scrumitos.service.ProductoService;

@ExtendWith(MockitoExtension.class)
class ProductoControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private ProductoService productService;
	
	@InjectMocks
	private ProductoController productController;
	
	@BeforeEach
	void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}
	private JacksonTester<Producto> jsonProducto;
	
	//AGREGAR NUEVO PRODUCTO
	
	@Test
	void alPresionarAgregarProductoEsteSeDebeIngresar() throws Exception {
		// given
		Producto producto = new Producto(1, "Arbol frutal", "Kilamapu", "Arbol de 30 centimetros", 500, 10,
				"Activo");
		// when
		MockHttpServletResponse response = mockMvc.perform(post("/productos/agregar/"+producto)
				.accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		
	}
	
	
	
}
