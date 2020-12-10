package cl.ubb.scrumitos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ubb.scrumitos.exceptions.BlankDataException;
import cl.ubb.scrumitos.exceptions.ProductNotFoundException;
import cl.ubb.scrumitos.exceptions.WithoutChangesException;
import cl.ubb.scrumitos.model.Producto;
import cl.ubb.scrumitos.repository.ProductoRepository;

@Service
public class ProductoService {
	
	@Autowired
	private ProductoRepository productRepo;

	public Producto searchProduct(int codigo) throws ProductNotFoundException {
		Producto productFound = productRepo.findById(codigo);
		
		if (productFound == null) {
			throw new ProductNotFoundException();
		}
		
		return productFound;
	}

	public void guardarProducto(Producto productoBuscado) throws ProductNotFoundException {
		productRepo.save(productoBuscado);
		
	}

	public void eliminarProducto(int codigo) throws ProductNotFoundException {
		Producto deletedProduct = searchProduct(codigo);
		deletedProduct.setEstado("Inactivo");
		productRepo.save(deletedProduct);
	}

	public void modificarProducto(int codigo, String nombre, String marca, String descripcion, int precio, 
			int stock, String estado) throws ProductNotFoundException, WithoutChangesException, BlankDataException {
		Producto modifiedProduct = searchProduct(codigo);
		//Caso Sin cambios, mismos datos
		if(modifiedProduct.getNombre().equalsIgnoreCase(nombre) && modifiedProduct.getDescripcion().equalsIgnoreCase(descripcion)
				&& modifiedProduct.getMarca().equalsIgnoreCase(marca) && modifiedProduct.getPrecio() == precio
				&& modifiedProduct.getStock() == stock && modifiedProduct.getEstado().equalsIgnoreCase(estado)) {
			throw new WithoutChangesException();
		}
		//Caso Con datos en Blanco
		if((nombre.isEmpty() && descripcion.isEmpty() && marca.isEmpty() && estado.isEmpty()) && (precio == 0 && stock == 0)) {
			throw new BlankDataException();
		}
		//Caso donde se modifican los datos
		if(!modifiedProduct.getNombre().equalsIgnoreCase(nombre) && !nombre.isEmpty()) {
			modifiedProduct.setNombre(nombre);
		}
		if(!modifiedProduct.getDescripcion().equalsIgnoreCase(descripcion) && !descripcion.isEmpty()){
			modifiedProduct.setDescripcion(descripcion);
		}
		if(!modifiedProduct.getMarca().equalsIgnoreCase(marca) && !marca.isEmpty()) {
			modifiedProduct.setMarca(marca);
		}
		if(modifiedProduct.getPrecio() != precio && precio != 0) {
			modifiedProduct.setPrecio(precio);
		}
		if(modifiedProduct.getStock() != stock && stock !=0) {
			modifiedProduct.setStock(stock);
		}
		if(!modifiedProduct.getEstado().equalsIgnoreCase(estado) && !estado.isEmpty()) {
			modifiedProduct.setEstado(estado);
		}
		productRepo.save(modifiedProduct);
	}
}
