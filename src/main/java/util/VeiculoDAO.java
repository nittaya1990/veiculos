package util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entidade.EVeiculo;

public class VeiculoDAO {
	private static VeiculoDAO instance;
	protected EntityManager entityManager;
	
	private VeiculoDAO() {
		entityManager = getEntityManager();
	}
	
	
	public static VeiculoDAO getInstance() {
		if(instance == null) {
			instance = new VeiculoDAO();
		}
		
		return instance;
	}
	
	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("unit-jsf"); //Nome da Unit no persistence.xml
		
		if(entityManager == null) {
			entityManager =  factory.createEntityManager();
		}
		return entityManager;
	}
	
	public void salvar(EVeiculo veiculo) {
		try {
			entityManager.getTransaction().begin(); // Iniciando a transação
			entityManager.persist(veiculo); //Fazendo a inserção no banco
			entityManager.getTransaction().commit(); // Efetivar a transação
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback(); // Desfaz a operação no banco
		}
	}
	
	public EVeiculo buscarPorId(final long id) { //final pq o valor não será alterado
		return entityManager.find(EVeiculo.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<EVeiculo> listarTodos(){
		return entityManager.createQuery("FROM " + EVeiculo.class.getName()).getResultList();
	}
	
	public void alterar(EVeiculo veiculo) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(veiculo);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}
	
	public void remover(EVeiculo veiculo) {
		try {
			entityManager.getTransaction().begin();
			veiculo = entityManager.find(EVeiculo.class, veiculo.getId());
			entityManager.remove(veiculo);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}
	
	public void removerPorId(final long id) {
		try {
			EVeiculo veiculo =  this.buscarPorId(id);
			this.remover(veiculo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
