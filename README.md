# CENG495 HW1
Programming Language Used: Java

Vaadin 8 framework is used for UI components. ISPark API is used to retrieve data. Open Layers API is used to show park data on map.

URL of the app: https://e1881770.herokuapp.com/

Site contains 2 layout:
-Top layout is used to create select district and park. When these 2 are selected, available space is updated from API.
-Bottom layout is used to show the location of the selected park on map.

Classes:
-MainUI
  This is class contains UI elements. 2 Comboboxes, 1 TextField and 1 Label. Comboboxes list districts and park names. When both are selected, TextField fills up with the available space data and the location of the selected park is placed inside the label as HTML content.
  
-ParkModel
  This is a simple model class that contains ID, name and district information for a park object.
  
-ParkData
  This class is used to map the selected parks' coordinates and the available space information.
