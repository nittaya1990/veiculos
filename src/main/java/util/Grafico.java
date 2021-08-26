package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import entidade.EVeiculo;

@ManagedBean(name = "GraficoAreaMB")
public class Grafico implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private LineChartModel areaModel;
	
	@PostConstruct //Pesquisar sobre isso 
	public void init() {
		createAreaModel();
	}
	
	public LineChartModel getAreaModel() {
		return areaModel;
	}
	
	private void createAreaModel() {
		List<EVeiculo> listaVeiculos =  new ArrayList<EVeiculo>();
		listaVeiculos =  VeiculoDAO.getInstance().listarTodos();
		areaModel =  new LineChartModel();
		
		LineChartSeries legenda = new LineChartSeries(); // A parte que mostra os dados
		legenda.setFill(false); //Preenchimento do gráfico
		legenda.setLabel("linha de valores"); // Título de valores
		for(EVeiculo eVeiculo : listaVeiculos) {
			legenda.set(eVeiculo.getFabricante() + " " + eVeiculo.getModelo(), eVeiculo.getValor()); // Antes da virgula - Eixo X. Após a virgula -  Eixo Y
		}
		
		areaModel.addSeries(legenda);
		
		areaModel.setTitle("Gráfico");
		areaModel.setLegendPosition("ne"); //Posição da legenda - Ex.: Norte, Sul, leste...
		areaModel.setShowPointLabels(false); //Mostrar as intersecções de valores de cada coluna
		areaModel.setNegativeSeriesColors("66cc66, 93b75f, E7E658, cc6666");
		
		/*Configurar o Eixo X e Y*/
		
		Axis xAxis =  new CategoryAxis("Veiculos"); // Criando Eixo X com nome
 		areaModel.getAxes().put(AxisType.X, xAxis); // Adicionando o xAxis criado no Eixo X do Gráfico
		
		Axis yAxis =  areaModel.getAxis(AxisType.Y); // Criando o eixo Y
		yAxis.setLabel("Valor"); // Setando um nome para o eixo Y
		yAxis.setMin(1000);
		yAxis.setMax(100000);
	}

}
