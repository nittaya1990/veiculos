package controle;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.text.View;

import entidade.EVeiculo;
import util.VeiculoDAO;
import util.SessionContext;

@Named("veiculoMB")
@SessionScoped
public class VeiculoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	public VeiculoMB() throws SQLException {
		if (SessionContext.getInstance().getAttribute("usuario") == null) { /*Checando se o usario está setado na seção*/
			try {
//				FacesContext.getCurrentInstance().getExternalContext().redirect("Login.xhtml");
				
				boolean respostaComprometida = FacesContext.getCurrentInstance().getExternalContext()
						.isResponseCommitted();

				if (!respostaComprometida) {
					FacesContext.getCurrentInstance().getExternalContext().redirect("Login.xhtml");
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			this.carregarLista();
			
		}
		
		
		
	}
	
	@Inject
	private EVeiculo veiculo;
	
	private String valor;
	
		
	public String getValor() {
		return valor;
	}


	public void setValor(String salario) {
		this.valor = salario;
	}

	private List<EVeiculo> listaVeiculo = new ArrayList<>();
	
	MensagemMB oMsgMb = new MensagemMB();
	
	
	public EVeiculo getVeiculo() {		
		return veiculo;
	}
	

	public void setVeiculo(EVeiculo veiculo) {
		this.veiculo = veiculo;
	}
	
	public List<EVeiculo> getListaVeiculo() {
		return listaVeiculo;
	}
	
	public void salvar() { /*Tirar Duvidas sobre isso*/
		this.veiculo.setValor(new BigDecimal(this.valor));
		VeiculoDAO.getInstance().salvar(this.veiculo);
		System.out.println(this.veiculo.getFabricante());
		System.out.println(this.veiculo.getModelo());
		System.out.println(this.veiculo.getAnoFabricacao());
		System.out.println(this.veiculo.getAnoModelo());
		System.out.println(this.veiculo.getDescricao());
		System.out.println(this.veiculo.getValor());
		this.carregarLista();
		limpar();
	}
	
	public void carregarLista() {
		listaVeiculo.clear();
		listaVeiculo = VeiculoDAO.getInstance().listarTodos();
	}
	
	public void excluir() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		Object parametro = params.get("parametroIdent");
		veiculo.setId(Long.parseLong(parametro.toString()));
		VeiculoDAO.getInstance().remover(veiculo);
		this.limpar();
		this.carregarLista();
	}
	
	public void alterar() {
		VeiculoDAO.getInstance().alterar(veiculo);
		this.limpar();
		this.carregarLista();
	}

	
	public void limpar()   {
		this.veiculo =  new EVeiculo();
		this.valor = new  String();
		this.refresh();
	}
	
	public void prepararEdicao() throws ParseException{
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		Object parametroId = params.get("parametroId");
		this.veiculo = VeiculoDAO.getInstance().buscarPorId(Long.parseLong(parametroId.toString()));
		this.valor = this.veiculo.getValor().toPlainString();
		this.refresh();
	}
	
	public void refresh() { /* Atualizar os valores do objeto na tela após terem sido alterados*/
		FacesContext context =  FacesContext.getCurrentInstance();
		Application application =  context.getApplication();
		ViewHandler viewHandler = application.getViewHandler();
		UIViewRoot viewRoot =  viewHandler.createView(context, context.getViewRoot().getViewId());
		context.setViewRoot(viewRoot);
		context.renderResponse();
		
	}
	
	
}
