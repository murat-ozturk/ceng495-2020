package com.ceng.cloud;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.annotation.WebServlet;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.vaadin.leif.headertags.Link;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
@JavaScript("https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.2.1/build/ol.js")
@StyleSheet("https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.2.1/css/ol.css")
public class MainUI extends UI {

	private Set<String> districtNames = new HashSet<String>();
	private List<ParkModel> parkModelList = new ArrayList<ParkModel>();
	private TextField tfAvailableSpace;
	private Label lblMap;

	@Override
	protected void init(VaadinRequest request) {

		try {
			retrieveData();
		} catch (Exception e) {
			e.printStackTrace();
		}

		VerticalLayout mainLayout = new VerticalLayout();
		HorizontalLayout selectionLayout = new HorizontalLayout();
		HorizontalLayout mapLayout = new HorizontalLayout();
		mapLayout.setId("mapLayout");
		mapLayout.setWidth(100, Unit.PERCENTAGE);
		mapLayout.setHeight(400, Unit.PIXELS);

		ComboBox<String> cbDistrict = new ComboBox<String>("Select District");
		cbDistrict.setItems(districtNames);
		cbDistrict.setEmptySelectionAllowed(false);

		ComboBox<ParkModel> cbParks = new ComboBox<ParkModel>("Select Park");
		cbParks.setItemCaptionGenerator(e -> e.getParkName());

		cbDistrict.addSelectionListener(new SingleSelectionListener<String>() {

			@Override
			public void selectionChange(SingleSelectionEvent<String> event) {
				if(cbParks.getValue() != null) {
					cbParks.setValue(null);
					tfAvailableSpace.setValue("");
					mapLayout.removeAllComponents();
				}
				List<ParkModel> selectedParks = new ArrayList<ParkModel>();
				parkModelList.forEach(e -> {
					if (e.getDistrict().equals(cbDistrict.getValue())) {
						selectedParks.add(e);
					}
				});
				cbParks.setItems(selectedParks);

			}
		});

		cbParks.addSelectionListener(new SingleSelectionListener<ParkModel>() {

			@Override
			public void selectionChange(SingleSelectionEvent<ParkModel> event) {
				if(cbParks.getValue() != null) {
					tfAvailableSpace.setValue(String.valueOf(cbParks.getValue().getAvailableSpace()));
					mapLayout.removeAllComponents();
					lblMap = new Label(
							"<div id=\"map\" class=\"map\" style=\"width: 100%; height: 400px;\"></div>",
							ContentMode.HTML);
					lblMap.setWidth(100, Unit.PERCENTAGE);
					lblMap.setHeight(400, Unit.PIXELS);
					mapLayout.addComponent(lblMap);
					
					StringBuilder sbScript = new StringBuilder();
					sbScript.append("var map = new ol.Map({ target: 'map', layers: [ new ol.layer.Tile({ source: new ol.source.OSM() }) ], view: new ol.View({ center: ol.proj.fromLonLat([");
					sbScript.append(cbParks.getValue().getLongitude());
					sbScript.append(",");
					sbScript.append(cbParks.getValue().getLatitude());
					sbScript.append("]), zoom: 18 })});");
					com.vaadin.ui.JavaScript.getCurrent().execute(sbScript.toString());
				}
				
			}
		});

		tfAvailableSpace = new TextField("Available Space");
		tfAvailableSpace.setEnabled(false);
		tfAvailableSpace.setValue("");

		selectionLayout.addComponents(cbDistrict, cbParks, tfAvailableSpace);
		selectionLayout.setSpacing(true);

		mainLayout.addComponents(selectionLayout, mapLayout);
		mainLayout.setSpacing(true);

		setContent(mainLayout);

	}

	private void retrieveData() throws Exception {

		HttpGet httpGet = new HttpGet("https://api.ibb.gov.tr/ispark/Park");

		CloseableHttpClient client = HttpClientBuilder.create().build();

		CloseableHttpResponse response = client.execute(httpGet);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		response.getEntity().writeTo(out);

		response.close();
		client.close();

		String data = out.toString();

		JsonParser parser = new JsonParser();
		JsonElement jsonTree = parser.parse(data);
		JsonArray jsonArray = jsonTree.getAsJsonArray();

		jsonArray.forEach(e -> {
			JsonObject parkObj = e.getAsJsonObject();
			String parkName = parkObj.get("Ilce").getAsString();
			districtNames.add(parkName);
			parkModelList.add(new ParkModel(parkObj));
		});

	}

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = true, ui = MainUI.class, heartbeatInterval = 30, closeIdleSessions = false)
	public static class Servlet extends VaadinServlet {

	}

}
