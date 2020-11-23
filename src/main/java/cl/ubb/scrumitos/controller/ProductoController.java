package cl.ubb.scrumitos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import cl.ubb.scrumitos.exceptions.ProductNotFoundException;
import cl.ubb.scrumitos.model.Producto;
import cl.ubb.scrumitos.service.ProductoService;
import javax.validation.Valid;
@Controller
@RequestMapping("/productos/")
public class ProductoController {

	@Autowired
	private ProductoService productoService;
	
		
	// Agregar un nuevo producto
		@PostMapping("agregar/{producto}")
		public ResponseEntity<Producto> logicAgregar(@Valid Producto producto, BindingResult result, Model model) {
			
			try {
				productoService.guardarProducto(producto);
				
			} catch (ProductNotFoundException e) {
				return new ResponseEntity<Producto>(HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<Producto>(HttpStatus.OK);
		}
		
		
	

}
