package cl.ubb.scrumitos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.ubb.scrumitos.exceptions.BlankDataException;
import cl.ubb.scrumitos.exceptions.ProductNotFoundException;
import cl.ubb.scrumitos.exceptions.WithoutChangesException;
import cl.ubb.scrumitos.model.Producto;
import cl.ubb.scrumitos.repository.ProductoRepository;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

	@Mock
	private ProductoRepository productRepo;
	
	@InjectMocks
	private ProductoService productService;
	

	// AGREGAR NUEVO PRODUCTO
	// Para agregar un producto se debe ver si todos los datos fueron ingresados
	// correctamente y si es que no hay un producto con los mismos datos.
	
	// Caso exitoso ()
	@Test
	void siLosDatosEstanCompletosEntoncesSeDebeIngresarElProducto() throws ProductNotFoundException {
		// Arrange
		Producto productoAIngresar = new Producto(1, "Arbol frutal", "Kilamapu", "Arbol de 30 centimetros", 500, 10,
				"Activo");

		// Act
		productService.guardarProducto(productoAIngresar);

		// Assert
		assertNotNull(productoAIngresar);
		assertAll("productoObtenido", () -> assertEquals(1, productoAIngresar.getCodigo()),
				() -> assertEquals("Arbol frutal".toLowerCase(), productoAIngresar.getNombre().toLowerCase()),
				() -> assertEquals("Kilamapu".toLowerCase(), productoAIngresar.getMarca().toLowerCase()),
				() -> assertEquals("Arbol de 30 centimetros".toLowerCase(),
						productoAIngresar.getDescripcion().toLowerCase()),
				() -> assertEquals(500, productoAIngresar.getPrecio()),
				() -> assertEquals(10, productoAIngresar.getStock()));
		verify(productRepo, times(1)).save(productoAIngresar);

	}

	// El producto a ingresar ya fue ingresado por lo cual se debe abortar el ingreso
	// Caso fallido
	@Test
	void siElProductoAIngresarYaSeEncuentraRegistradoEntoncesSeDebeAbortarElIngreso() throws ProductNotFoundException {
		// Arrange
		Producto productoIngresado = new Producto(1, "Arbol frutal", "Kilamapu", "Arbol de 30 centimetros", 500, 10,
				"Activo");
		when(productRepo.findById(1)).thenReturn(productoIngresado);

		// Act
		Producto productoObtenido = productService.searchProduct(1);
		// Assert
		assertNotNull(productoObtenido);
		assertEquals(productoIngresado, productoObtenido);
	}
	
	//Si el producto no se encuentra regstrado entonces debe ingresar
	//Caso exitoso
	@Test
	void siAlIngresarUnNuevoProductoYEsteNoEstaRegistradoEntoncesSeDebeIngresarSatisfatoriamente() throws ProductNotFoundException {
		// Arrange
		Producto productoAIngresar = new Producto(2, "Arbol frutal", "Kilamapu", "Arbol de 30 centimetros", 500, 10,
				"Activo");
		// Arrange
		when(productRepo.findById(2)).thenReturn(null);

		// Act 
		assertThrows(ProductNotFoundException.class, () -> productService.searchProduct(2));
		
		//Assert
		productService.guardarProducto(productoAIngresar);
	}

	
	
	// ELIMINACIÓN LÓGICA DE UN PRODUCTO
		// Para eliminar un producto, debe buscarlo, traerlo, cambiar atributo "Estado" de "Activo" a "Inactivo" y
		// luego guardarlo (Servicio), controlador invoca método de eliminación y retorna status OK si la operación se realizó exitosamente.
	
	// Buscar y obtener el producto
	// Caso exitoso
	@Test
	void siDeseaEliminarUnProductoEntoncesProductoServiceBuscaElProductoMedianteSuIdYLoRetorna() throws ProductNotFoundException { 
		// Arrange
		Producto productoBuscado = new Producto(3, "Tierra Biologica Compost", "ANASAC", 
				"Producto natural, hecho a partir de la compostación de residuos orgánicos", 4990, 20, "Activo");
		when(productRepo.findById(3)).thenReturn(productoBuscado);
		
		// Act
		Producto productoObtenido = productService.searchProduct(3);
		
		// Assert
		assertNotNull(productoObtenido);
		assertAll("productoObtenido", 
				() -> assertEquals(3, productoObtenido.getCodigo()),
				() -> assertEquals("Tierra Biologica Compost".toLowerCase(), productoObtenido.getNombre().toLowerCase()),
				() -> assertEquals("ANASAC".toLowerCase(), productoObtenido.getMarca().toLowerCase()),
				() -> assertEquals("Producto natural, hecho a partir de la compostación de residuos orgánicos".toLowerCase(), 
						productoObtenido.getDescripcion().toLowerCase()),
				() -> assertEquals(4990, productoObtenido.getPrecio()),
				() -> assertEquals(20, productoObtenido.getStock()));
		
	}
	
	// Buscar y obtener producto
	// Caso fallido (no encuentra el producto y retorna null, manejado mediante ProductNotFoundException)
	@Test
	void siDeseaEliminarUnProductoYProductoServiceLoBuscaPeroNoLoEncuentraEntoncesLanzaProductNotFoundException() {
		// Arrange
		when(productRepo.findById(3)).thenReturn(null);
		
		// Act + Assert
		assertThrows(ProductNotFoundException.class, () -> 
				productService.searchProduct(3));
	}
	
	// Guardar cambio en atributo "Estado" de Activo a Inactivo
	// Caso exitoso
	@Test
	void siDeseaEliminarProductoYEsteEsEncontradoEntoncesCambiaAtributoEstadoYGuardaLosCambios() throws ProductNotFoundException {
		// Arrange
		Producto productoBuscado = new Producto(3, "Tierra Biologica Compost", "ANASAC", 
				"Producto natural, hecho a partir de la compostación de residuos orgánicos", 4990, 20, "Activo");
		when(productRepo.findById(3)).thenReturn(productoBuscado);
		
		// Act
		productService.eliminarProducto(3);
		
		//Assert
		assertNotNull(productoBuscado);
		assertEquals("Inactivo".toLowerCase(), productoBuscado.getEstado().toLowerCase());
		verify(productRepo, times(1)).save(productoBuscado);
		
	}

	// Modificar los datos del producto
		//Caso Exitoso
		@Test
		void siDeseaModificarProductoYEsteEsEncontradoEntoncesLoModificaYGuardaLosCambios() 
				throws ProductNotFoundException, WithoutChangesException, BlankDataException {
			// Arrange
			Producto productoBuscado = new Producto(3, "Tierra Biologica Compost", "ANASAC", 
					"Producto natural, hecho a partir de la compostación de residuos orgánicos", 4990, 20, "Inactivo");
			when(productRepo.findById(3)).thenReturn(productoBuscado);
			
			// Act
			productService.modificarProducto(3, "Tierra Compost", "ARTHEMIS", 
					"Producto natural, hecho en base a compostación de residuos orgánicos", 5990, 15, "Activo");
			
			//Assert
			assertNotNull(productoBuscado);
			assertEquals("Tierra Compost", productoBuscado.getNombre());
			assertEquals("ARTHEMIS", productoBuscado.getMarca());
			assertEquals("Producto natural, hecho en base a compostación de residuos orgánicos", 
					productoBuscado.getDescripcion());
			assertEquals(5990, productoBuscado.getPrecio());
			assertEquals(15, productoBuscado.getStock());
			assertEquals("Activo", productoBuscado.getEstado());
			
			verify(productRepo, times(1)).save(productoBuscado);
		}

		// Modificar los datos del producto
		//Caso Sin Cambios
		@Test
		void siDeseaModificarProductoYEsteEsEncontradoEntoncesLoModificaSinCambiosYGuardaLosCambios() 
				throws ProductNotFoundException, WithoutChangesException {
			// Arrange
			Producto productoBuscado = new Producto(3, "Tierra Biologica Compost", "ANASAC", 
					"Producto natural, hecho a partir de la compostación de residuos orgánicos", 4990, 20, "Inactivo");
			when(productRepo.findById(3)).thenReturn(productoBuscado);
			
			// Act + Assert
			assertThrows(WithoutChangesException.class, ()-> productService.modificarProducto(3, "Tierra Biologica Compost", "ANASAC", 
				"Producto natural, hecho a partir de la compostación de residuos orgánicos", 4990, 20, "Inactivo"));
			}
		
		// Modificar los datos del producto
		//Caso Con datos en blanco
		@Test
		void siDeseaModificarProductoYEsteEsEncontradoEntoncesLoModificaConDatosEnBlancoYGuardaLosCambios() 
			throws ProductNotFoundException, WithoutChangesException, BlankDataException {
			// Arrange
			Producto productoBuscado = new Producto(3, "Tierra Biologica Compost", "ANASAC", 
				"Producto natural, hecho a partir de la compostación de residuos orgánicos", 4990, 20, "Inactivo");
			when(productRepo.findById(3)).thenReturn(productoBuscado);
					
			// Act + Assert
			assertThrows(BlankDataException.class, ()-> productService.modificarProducto(3, "", "", "", 0, 0, ""));
		}	
}
