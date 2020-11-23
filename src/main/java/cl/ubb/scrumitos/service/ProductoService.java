package cl.ubb.scrumitos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ubb.scrumitos.exceptions.ProductNotFoundException;
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

	public void guardarProducto(Producto productoBuscado)throws ProductNotFoundException {
		productRepo.save(productoBuscado);
		
		
	}

	public void eliminarProducto(int codigo) throws ProductNotFoundException {
		Producto deletedProduct = searchProduct(codigo);
		deletedProduct.setEstado("Inactivo");
		productRepo.save(deletedProduct);
	}

}
