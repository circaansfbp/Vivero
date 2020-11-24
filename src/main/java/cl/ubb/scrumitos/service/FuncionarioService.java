package cl.ubb.scrumitos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ubb.scrumitos.exceptions.FuncionarioAlreadyExistsException;
import cl.ubb.scrumitos.model.Funcionario;
import cl.ubb.scrumitos.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

	
	@Autowired
	private FuncionarioRepository funcionarioRepo;
	
	public Funcionario searchFuncionario(int id) throws FuncionarioAlreadyExistsException {
		
			Funcionario funcionario = funcionarioRepo.findById(id);
			
			if (funcionario != null) {
				throw new FuncionarioAlreadyExistsException();
			}
			
			return funcionario;
		
	}
	
	public void agregar(Funcionario funcionario) throws FuncionarioAlreadyExistsException{
		   
		   Funcionario aux=funcionarioRepo.findById(funcionario.getIdFuncionario());
	       if(aux!=null) {
	    	   throw new FuncionarioAlreadyExistsException();
	       }else {
	    	   funcionarioRepo.save(funcionario);
	       }
		
		}
	
	
	
	
	
	
	
	
	/*public void agregar(Funcionario funcionario) {
	
	   funcionarioRepo.save(funcionario);
	}
*/
}
